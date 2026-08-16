package com.hendisantika.springbootemail.dto;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-email
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 13/10/25
 * Time: 06.30
 */
public class BrevoEmailRequest {
    private EmailContact sender;
    private List<EmailContact> to;
    private String subject;
    private String htmlContent;
    private String textContent;
    private List<Attachment> attachment;

    public EmailContact getSender() {
        return sender;
    }

    public void setSender(EmailContact sender) {
        this.sender = sender;
    }

    public List<EmailContact> getTo() {
        return to;
    }

    public void setTo(List<EmailContact> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    public static class EmailContact {
        private String email;
        private String name;

        public EmailContact() {
        }

        public EmailContact(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Attachment {
        private String content;
        private String name;

        public Attachment() {
        }

        public Attachment(String content, String name) {
            this.content = content;
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
