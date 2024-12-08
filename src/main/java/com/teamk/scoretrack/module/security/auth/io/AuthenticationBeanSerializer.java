package com.teamk.scoretrack.module.security.auth.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.teamk.scoretrack.module.commons.io.CustomTypeSerializer;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationLock;
import com.teamk.scoretrack.module.security.track.domain.AuthenticationTrackingData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

/**
 * Used to leverage Redis session storage.
 * @author Ivan Krylosov
 */
@Component
public class AuthenticationBeanSerializer extends CustomTypeSerializer<AuthenticationBean> {
    public AuthenticationBeanSerializer() {
        super(AuthenticationBean.class);
    }

    protected void writeToJson(AuthenticationBean authenticationBean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStringField("id", authenticationBean.getId().toString());
        gen.writeStringField("externalId", authenticationBean.getExternalId().toString());
        gen.writeStringField("loginname", authenticationBean.getLoginname());

        String email = authenticationBean.getEmail();
        if (email != null) {
            gen.writeStringField("email", email);
        }

        String phoneNumber = authenticationBean.getPhoneNumber();
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            gen.writeStringField("phoneNumber", phoneNumber);
        }

        gen.writeStringField("password", authenticationBean.getPassword());
        gen.writeNumberField("status", authenticationBean.getStatus().getCode());
        gen.writeStringField("createdAt", authenticationBean.getCreatedAt().toString());
        gen.writeNumberField("ps", authenticationBean.getPs().getKey());
        gen.writeNumberField("authType", authenticationBean.getAuthType().getKey());

        User user = authenticationBean.getUser();
        if (user != null) {
            gen.writeFieldName("user");
            gen.writeStartObject();

            gen.writeObjectField("data", user);

            gen.writeEndObject();
        }

        gen.writeStringField("lastModified", authenticationBean.getLastModified().toString());
        Instant lastConfirmedOn = authenticationBean.getLastConfirmedOn();
        if (lastConfirmedOn != null) {
            gen.writeStringField("lastConfirmedOn", lastConfirmedOn.toString());
        }

        AuthenticationBean modifiedBy = authenticationBean.getModifiedBy();
        if (modifiedBy != null) {
            gen.writeObjectField("modifiedBy", modifiedBy);
        }

        AuthenticationLock latestFailureUnlock = authenticationBean.getLatestFailureUnlock();
        if (latestFailureUnlock != null) {
            gen.writeFieldName("latestFailureUnlock");
            gen.writeStartObject();

            gen.writeStringField("id", latestFailureUnlock.getId().toString());
            gen.writeStringField("unlockedAt", latestFailureUnlock.getUnlockedAt().toString());

            gen.writeEndObject();
        }

        AuthenticationTrackingData trackingData = authenticationBean.getTrackingData();
        if (trackingData != null) {
            gen.writeFieldName("trackingData");
            gen.writeStartObject();

            gen.writeStringField("id", trackingData.getId().toString());
            gen.writeStringField("lastLogOn", trackingData.getLastLogOn().toString());
            gen.writeNumberField("totalLogIn", trackingData.getTotalLogIn());
            gen.writeNumberField("consecutiveLogIn", trackingData.getConsecutiveLogIn());

            gen.writeEndObject();
        }
    }

    @Override
    public Class<AuthenticationBean> getSerializationClass() {
        return AuthenticationBean.class;
    }
}
