package com.teamk.scoretrack.module.commons.mail;

import com.google.common.collect.BiMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NotificationEmail {
    private String recipient;
    private String subject;
    private String message;
    private String title;
    //@Transient - serialize for now
    private Map<String, File> attachments;

    public static final NotificationEmail NULL_SAFE = new NotificationEmail().builder().recipient("").title("").subject("").message("").build();

    public NotificationEmail() {
        attachments = new HashMap<>();
    }

    public Builder builder() {
        return new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder recipient(String recipient) {
            setRecipient(recipient);
            return this;
        }

        public Builder subject(String subject) {
            setSubject(subject);
            return this;
        }

        public Builder title(String title) {
            setTitle(title);
            return this;
        }

        public Builder message(String message) {
            setMessage(message);
            return this;
        }

        public Builder attachment(BiMap<String, File> attachments) {
            setAttachments(attachments);
            return this;
        }

        public NotificationEmail build() {
            return NotificationEmail.this;
        }
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, File> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, File> attachments) {
        this.attachments = attachments;
    }
}
