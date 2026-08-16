package com.hendisantika.springbootemail.controller;

import com.hendisantika.springbootemail.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-email
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/01/20
 * Time: 06.31
 */
@RestController
@RequestMapping("/email")
public class EmailController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    EmailService emailService;

    @GetMapping(value = "/simple-email/{user-email}")
    public @ResponseBody
    ResponseEntity<String> sendSimpleEmail(@PathVariable("user-email") String email) {

        try {
            emailService.sendSimpleEmail(email, "Welcome", "This is a welcome email for you!!");
            LOG.info("Email already sent! Please check your inbox for order confirmation!");
        } catch (Exception exception) {
            LOG.error("Error while sending out email..{}", exception.getMessage());
            LOG.error("Error while sending out email..{}", exception.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

    @GetMapping(value = "/simple-order-email/{user-email}")
    public @ResponseBody
    ResponseEntity<String> sendEmailAttachment(@PathVariable("user-email") String email) {

        try {
            emailService.sendEmailWithAttachment(email, "Order Confirmation", "Thanks for your recent order",
                    "classpath:purchase_order.pdf");
            LOG.info("Email already sent! Please check your inbox for order confirmation!");
        } catch (FileNotFoundException | RuntimeException exception) {
            LOG.error("Error while sending out email..{}", exception.getMessage());
            LOG.error("Error while sending out email..{}", exception.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox for order confirmation", HttpStatus.OK);
    }

    @PostMapping(value = "/send-with-attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    ResponseEntity<String> sendEmailWithAttachments(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam(value = "attachments", required = false) List<MultipartFile> attachments) {

        try {
            if (to == null || to.trim().isEmpty()) {
                return new ResponseEntity<>("Email address is required", HttpStatus.BAD_REQUEST);
            }

            if (subject == null || subject.trim().isEmpty()) {
                return new ResponseEntity<>("Subject is required", HttpStatus.BAD_REQUEST);
            }

            if (message == null || message.trim().isEmpty()) {
                return new ResponseEntity<>("Message is required", HttpStatus.BAD_REQUEST);
            }

            emailService.sendEmailWithAttachments(to, subject, message, attachments);
            LOG.info("Email with attachments sent successfully to: {}", to);

            if (attachments != null && !attachments.isEmpty()) {
                return new ResponseEntity<>("Email with " + attachments.size() + " attachment(s) sent successfully! Please check your inbox.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Email sent successfully! Please check your inbox.", HttpStatus.OK);
            }
        } catch (Exception exception) {
            LOG.error("Error while sending email with attachments: {}", exception.getMessage());
            LOG.error("Error details: ", exception);
            return new ResponseEntity<>("Unable to send email: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
