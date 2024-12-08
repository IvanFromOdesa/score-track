package com.teamk.scoretrack.module.security.auth.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.commons.util.ReflectUtils;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationStatus;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationType;
import com.teamk.scoretrack.module.security.auth.domain.PasswordStatus;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationLock;
import com.teamk.scoretrack.module.security.track.domain.AuthenticationTrackingData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Component
public class AuthenticationBeanDeserializer extends CustomDeserializer<AuthenticationBean> {
    @Override
    public AuthenticationBean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        AuthenticationBean authenticationBean = new AuthenticationBean();

        if (rootNode.hasNonNull("id")) {
            authenticationBean.setId(rootNode.get("id").asLong());
        }
        if (rootNode.hasNonNull("externalId")) {
            authenticationBean.setExternalId(UUID.fromString(rootNode.get("externalId").asText()));
        }
        if (rootNode.hasNonNull("loginname")) {
            authenticationBean.setLoginname(rootNode.get("loginname").asText());
        }
        if (rootNode.hasNonNull("email")) {
            authenticationBean.setEmail(rootNode.get("email").asText());
        }
        if (rootNode.hasNonNull("phoneNumber")) {
            authenticationBean.setPhoneNumber(rootNode.get("phoneNumber").asText());
        }
        if (rootNode.hasNonNull("password")) {
            authenticationBean.setPassword(rootNode.get("password").asText());
        }

        if (rootNode.hasNonNull("status")) {
            authenticationBean.setStatus(AuthenticationStatus.LOOKUP_MAP.get(rootNode.get("status").asInt()));
        }
        if (rootNode.hasNonNull("ps")) {
            authenticationBean.setPs(PasswordStatus.LOOKUP_MAP.get(rootNode.get("ps").asInt()));
        }
        if (rootNode.hasNonNull("authType")) {
            authenticationBean.setAuthType(AuthenticationType.LOOKUP_MAP.get(rootNode.get("authType").asInt()));
        }

        if (rootNode.hasNonNull("createdAt")) {
            try {
                ReflectUtils.setField(authenticationBean, "createdAt", Instant.parse(rootNode.get("createdAt").asText()), AuthenticationBean.class);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ServerException(e);
            }
        }

        if (rootNode.hasNonNull("lastModified")) {
            authenticationBean.setLastModified(Instant.parse(rootNode.get("lastModified").asText()));
        }
        if (rootNode.hasNonNull("lastConfirmedOn")) {
            authenticationBean.setLastConfirmedOn(Instant.parse(rootNode.get("lastConfirmedOn").asText()));
        }

        if (rootNode.hasNonNull("user")) {
            JsonNode userNode = rootNode.get("user");

            JsonNode data = userNode.get("data");

            User user = p.getCodec().treeToValue(data, getUserClass(data));

            authenticationBean.setUser(user);
            user.setAuthenticationBean(authenticationBean);
        }

        if (rootNode.hasNonNull("modifiedBy")) {
            JsonNode modifiedByNode = rootNode.get("modifiedBy");
            AuthenticationBean modifiedBy = p.getCodec().treeToValue(modifiedByNode, AuthenticationBean.class);
            authenticationBean.setModifiedBy(modifiedBy);
        }

        if (rootNode.hasNonNull("latestFailureUnlock")) {
            JsonNode unlockNode = rootNode.get("latestFailureUnlock");
            AuthenticationLock latestFailureUnlock = new AuthenticationLock();
            if (unlockNode.hasNonNull("id")) {
                latestFailureUnlock.setId(unlockNode.get("id").asLong());
            }
            if (unlockNode.hasNonNull("unlockedAt")) {
                latestFailureUnlock.setUnlockedAt(Instant.parse(unlockNode.get("unlockedAt").asText()));
            }
            latestFailureUnlock.setAuthenticationBean(authenticationBean);
            authenticationBean.setLatestFailureUnlock(latestFailureUnlock);
        }

        if (rootNode.hasNonNull("trackingData")) {
            JsonNode trackingNode = rootNode.get("trackingData");
            AuthenticationTrackingData trackingData = new AuthenticationTrackingData();
            if (trackingNode.hasNonNull("id")) {
                trackingData.setId(trackingNode.get("id").asLong());
            }
            if (trackingNode.hasNonNull("lastLogOn")) {
                trackingData.setLastLogOn(Instant.parse(trackingNode.get("lastLogOn").asText()));
            }
            if (trackingNode.hasNonNull("totalLogIn")) {
                trackingData.setTotalLogIn(trackingNode.get("totalLogIn").asInt());
            }
            if (trackingNode.hasNonNull("consecutiveLogIn")) {
                trackingData.setConsecutiveLogIn(trackingNode.get("consecutiveLogIn").asInt());
            }
            authenticationBean.setTrackingData(trackingData);
        }

        return authenticationBean;
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends User> getUserClass(JsonNode data) {
        try {
            return (Class<? extends User>) Class.forName(data.get("@class").asText());
        } catch (ClassNotFoundException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public Class<AuthenticationBean> getDeserializationClass() {
        return AuthenticationBean.class;
    }
}
