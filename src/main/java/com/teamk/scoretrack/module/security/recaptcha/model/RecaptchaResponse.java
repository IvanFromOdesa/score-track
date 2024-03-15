package com.teamk.scoretrack.module.security.recaptcha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecaptchaResponse(boolean success, String challenge_ts, String hostname, double score, String action, @JsonProperty("error-codes") String[] errorCodes) {
}
