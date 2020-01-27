package com.hendisantika.springbootemail.controller;

import com.hendisantika.springbootemail.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
