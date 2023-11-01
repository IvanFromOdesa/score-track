package com.teamk.scoretrack.module.security.auth.dto;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

import java.util.Map;

public class SignUpResponseDto {
    private Map<String, ErrorMap.Error> errors;
    private String result;

    public Map<String, ErrorMap.Error> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, ErrorMap.Error> errors) {
        this.errors = errors;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
