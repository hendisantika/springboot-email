package com.hendisantika.springbootemail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
    @Autowired
    public JavaMailSender emailSender;
}
