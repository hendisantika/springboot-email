package com.hendisantika.springbootemail.service;

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
 * Time: 06.28
 */
public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);

    void sendEmailWithAttachment(final String toAddress, final String subject, final String message, final String
            attachment) throws FileNotFoundException;

    void sendEmailWithAttachments(final String toAddress, final String subject, final String message,
                                   final List<MultipartFile> attachments);
}
