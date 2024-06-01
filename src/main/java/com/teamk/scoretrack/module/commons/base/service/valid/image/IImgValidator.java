package com.teamk.scoretrack.module.commons.base.service.valid.image;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

public interface IImgValidator<CONTEXT extends ImageValidationContext> {

    /**
     * Default implementation only checks if the image url is valid
     * @param context
     * @return
     */
    default ErrorMap validate(CONTEXT context) {
        String url = context.getUrl();
        String msg = String.format("URL '%s' is malformed.", url);
        if (!url.matches("^https?:\\/\\/.+\\/.+$")) {
            context.getErrorMap().put(context.getFieldName(), msg);
        } else {
            // The external servers containing images can be inactive
            /*try {
                ImageIO.read(new URL(url));
            } catch (IOException e) {
                context.getErrorMap().put(context.getStatName(), msg);
            }*/
        }
        return context.getErrorMap();
    }
}
