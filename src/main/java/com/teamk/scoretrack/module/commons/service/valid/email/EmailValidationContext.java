package com.teamk.scoretrack.module.commons.service.valid.email;

import com.teamk.scoretrack.module.commons.service.valid.CommonValidationContext;

public class EmailValidationContext extends CommonValidationContext {
    private String email;
    private String fieldName;
    private String errorMsg;
    private String ruleRegex;

    public EmailValidationContext(String email, String fieldName, String errorMsg) {
        this.email = email;
        this.fieldName = fieldName;
        this.errorMsg = errorMsg;
    }

    public EmailValidationContext(String email, String fieldName, String errorMsg, String ruleRegex) {
        this.email = email;
        this.fieldName = fieldName;
        this.errorMsg = errorMsg;
        this.ruleRegex = ruleRegex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRuleRegex() {
        return ruleRegex;
    }

    public void setRuleRegex(String ruleRegex) {
        this.ruleRegex = ruleRegex;
    }
}
