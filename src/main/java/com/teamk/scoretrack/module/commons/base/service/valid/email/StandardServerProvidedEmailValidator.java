package com.teamk.scoretrack.module.commons.base.service.valid.email;

import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import org.springframework.stereotype.Service;

/**
 * Alternatively, here we will validate the existence of email by calling external API
 */
@Service
public class StandardServerProvidedEmailValidator implements IEmailValidator<EmailValidationContext> {
    public static final String RFC_5322_EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    // TODO: improve validation for gmails and other email domains
    @Override
    public ErrorMap validate(EmailValidationContext context) {
        String ruleRegex = context.getRuleRegex();
        checkEmailValidity(context, ruleRegex == null ? RFC_5322_EMAIL_REGEX : ruleRegex);
        return context.getErrorMap();
    }

    private void checkEmailValidity(EmailValidationContext context, String ruleRegex) {
        if (!CommonsUtil.patternMatches(context.getEmail(), ruleRegex)) {
            context.getErrorMap().put(context.getFieldName(), context.getErrorMsg());
        }
    }
}
