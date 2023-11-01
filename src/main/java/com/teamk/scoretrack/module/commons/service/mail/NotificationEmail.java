package com.teamk.scoretrack.module.commons.service.mail;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.File;

public class NotificationEmail {
    private String recipient;
    private String subject;
    private String message;
    private String title;
    private BiMap<String, File> attachments;

    public NotificationEmail() {
        attachments = HashBiMap.create();
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

    public BiMap<String, File> getAttachments() {
        return attachments;
    }

    public void setAttachments(BiMap<String, File> attachments) {
        this.attachments = attachments;
    }
}
