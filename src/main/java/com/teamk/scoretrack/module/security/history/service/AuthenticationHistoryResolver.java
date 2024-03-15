package com.teamk.scoretrack.module.security.history.service;

import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationHistoryResolver {

    /**
     * Meant for internal filters use. Result is cacheable by id.
     */
    AuthenticationHistory.Status resolve(HttpServletRequest request, String ip);
}
