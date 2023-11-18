package com.teamk.scoretrack.module.commons.base.service.valid.image;

import com.teamk.scoretrack.module.commons.base.service.valid.CommonValidationContext;

public class ImageValidationContext extends CommonValidationContext {
    private String url;
    private String fieldName;

    public ImageValidationContext(String url, String fieldName) {
        this.url = url;
        this.fieldName = fieldName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
