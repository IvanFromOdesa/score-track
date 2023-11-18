package com.teamk.scoretrack.module.commons.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;

@Service
public class NoReplyEmailService implements IEmailService<NotificationEmail> {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${mail.noreply.address}")
    private String fromAddress;
    private static final String EMAIL_TEMPLATE = "mail/mailTemplate";
    private static final Logger LOGGER = LoggerFactory.getLogger(NoReplyEmailService.class);

    @Autowired
    public NoReplyEmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async("fixedThreadPool")
    public void sendEmail(NotificationEmail email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(email.getRecipient());
            helper.setSubject(email.getSubject());
            helper.setText(buildBody(email.getMessage(), email.getTitle()));
            for (Map.Entry<String, File> attachment : email.getAttachments().entrySet()) {
                helper.addAttachment(attachment.getKey(), attachment.getValue());
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error(e.toString());
        }
    }

    private String buildBody(String body, String title) {
        Context context = new Context();
        context.setVariable("body", body);
        context.setVariable("title", title);
        return templateEngine.process(EMAIL_TEMPLATE, context);
    }
}
