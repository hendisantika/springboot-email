package com.hendisantika.springbootemail.service;

import com.hendisantika.springbootemail.config.BrevoConfig;
import com.hendisantika.springbootemail.dto.BrevoEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-email
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/01/20
 * Time: 06.30
 */
@Service
public class DefaultEmailService implements EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEmailService.class);

    @Autowired
    private BrevoConfig brevoConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        BrevoEmailRequest request = new BrevoEmailRequest();

        // Set sender
        request.setSender(new BrevoEmailRequest.EmailContact(
                brevoConfig.getSender().getEmail(),
                brevoConfig.getSender().getName()
        ));

        // Set recipient (extract name from email if provided)
        String recipientName = toAddress.contains("@") ? toAddress.split("@")[0] : null;
        request.setTo(Collections.singletonList(
                new BrevoEmailRequest.EmailContact(toAddress, recipientName)
        ));

        // Set subject and content - use HTML format
        request.setSubject(subject);
        String htmlContent = "<html><head></head><body><p>Hello,</p><p>" + message + "</p></body></html>";
        request.setHtmlContent(htmlContent);

        // Send email via Brevo API
        sendEmailRequest(request);
    }

    @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws FileNotFoundException {
        BrevoEmailRequest request = new BrevoEmailRequest();

        // Set sender
        request.setSender(new BrevoEmailRequest.EmailContact(
                brevoConfig.getSender().getEmail(),
                brevoConfig.getSender().getName()
        ));

        // Set recipient (extract name from email if provided)
        String recipientName = toAddress.contains("@") ? toAddress.split("@")[0] : null;
        request.setTo(Collections.singletonList(
                new BrevoEmailRequest.EmailContact(toAddress, recipientName)
        ));

        // Set subject and content - use HTML format
        request.setSubject(subject);
        String htmlContent = "<html><head></head><body><p>Hello,</p><p>" + message + "</p></body></html>";
        request.setHtmlContent(htmlContent);

        // Add attachment
        try {
            File file = ResourceUtils.getFile(attachment);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64Content = Base64.getEncoder().encodeToString(fileContent);

            BrevoEmailRequest.Attachment attach = new BrevoEmailRequest.Attachment(
                    base64Content,
                    "Purchase Order.pdf"
            );
            request.setAttachment(Collections.singletonList(attach));
        } catch (Exception e) {
            LOG.error("Error reading attachment file: {}", e.getMessage());
            throw new RuntimeException("Failed to read attachment file", e);
        }

        // Send email via Brevo API
        sendEmailRequest(request);
    }

    @Override
    public void sendEmailWithAttachments(String toAddress, String subject, String message, List<MultipartFile> attachments) {
        BrevoEmailRequest request = new BrevoEmailRequest();

        // Set sender
        request.setSender(new BrevoEmailRequest.EmailContact(
                brevoConfig.getSender().getEmail(),
                brevoConfig.getSender().getName()
        ));

        // Set recipient (extract name from email if provided)
        String recipientName = toAddress.contains("@") ? toAddress.split("@")[0] : null;
        request.setTo(Collections.singletonList(
                new BrevoEmailRequest.EmailContact(toAddress, recipientName)
        ));

        // Set subject and content - use HTML format
        request.setSubject(subject);
        String htmlContent = "<html><head></head><body><p>Hello,</p><p>" + message + "</p></body></html>";
        request.setHtmlContent(htmlContent);

        // Add attachments if provided
        if (attachments != null && !attachments.isEmpty()) {
            List<BrevoEmailRequest.Attachment> attachmentList = new ArrayList<>();

            for (MultipartFile file : attachments) {
                try {
                    byte[] fileContent = file.getBytes();
                    String base64Content = Base64.getEncoder().encodeToString(fileContent);

                    BrevoEmailRequest.Attachment attach = new BrevoEmailRequest.Attachment(
                            base64Content,
                            file.getOriginalFilename()
                    );
                    attachmentList.add(attach);
                    LOG.info("Added attachment: {}", file.getOriginalFilename());
                } catch (Exception e) {
                    LOG.error("Error reading attachment file: {}", e.getMessage());
                    throw new RuntimeException("Failed to read attachment file: " + file.getOriginalFilename(), e);
                }
            }

            request.setAttachment(attachmentList);
        }

        // Send email via Brevo API
        sendEmailRequest(request);
    }

    private void sendEmailRequest(BrevoEmailRequest request) {
        try {
            LOG.info("=========== Brevo API Request Details ===========");
            LOG.info("API URL: {}", brevoConfig.getApi().getUrl());
            LOG.info("Sender: {} <{}>", brevoConfig.getSender().getName(), brevoConfig.getSender().getEmail());
            LOG.info("Recipient: {}", request.getTo().get(0).getEmail());
            LOG.info("Subject: {}", request.getSubject());

            // Log API key (first 10 and last 4 characters for debugging)
            String apiKey = brevoConfig.getApi().getKey();
            if (apiKey != null && apiKey.length() > 14) {
                LOG.info("API Key: {}...{}", apiKey.substring(0, 10), apiKey.substring(apiKey.length() - 4));
            }

            LOG.info("Request Payload:");
            LOG.info("  - Sender Email: {}", request.getSender().getEmail());
            LOG.info("  - Sender Name: {}", request.getSender().getName());
            LOG.info("  - To Email: {}", request.getTo().get(0).getEmail());
            LOG.info("  - To Name: {}", request.getTo().get(0).getName());
            LOG.info("  - HTML Content: {}", request.getHtmlContent() != null ? request.getHtmlContent().substring(0, Math.min(50, request.getHtmlContent().length())) + "..." : "null");
            LOG.info("================================================");

            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", apiKey);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");

            HttpEntity<BrevoEmailRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    brevoConfig.getApi().getUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            LOG.info("✓ Email sent successfully via Brevo API!");
            LOG.info("Response Status: {}", response.getStatusCode());
            LOG.info("Response Body: {}", response.getBody());
        } catch (Exception e) {
            LOG.error("✗ Error sending email via Brevo API: {}", e.getMessage());
            LOG.error("Please check:");
            LOG.error("  1. Is your API key valid? (Check at https://app.brevo.com/settings/keys/api)");
            LOG.error("  2. Is the sender email '{}' verified in your Brevo account?", brevoConfig.getSender().getEmail());
            LOG.error("  3. Does your API key have permission to send transactional emails?");
            throw new RuntimeException("Failed to send email via Brevo API", e);
        }
    }
}
