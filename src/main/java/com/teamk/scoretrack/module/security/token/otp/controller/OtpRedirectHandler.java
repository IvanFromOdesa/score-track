package com.teamk.scoretrack.module.security.token.otp.controller;

import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public interface OtpRedirectHandler {
    static void onBlockStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        onBlockStatus(request, response, request.getSession());
    }

    static void onBlockStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session.getAttribute(OtpAuthController.RECOVER_REDIRECT) == null) {
            session.setAttribute(OtpAuthController.RECOVER_REDIRECT, "");
        }
        response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        response.sendRedirect(HttpUtil.getBaseUrl(request).concat(OtpAuthController.RECOVER));
    }
}
