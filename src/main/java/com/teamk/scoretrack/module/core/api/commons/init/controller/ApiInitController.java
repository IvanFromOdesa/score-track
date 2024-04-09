package com.teamk.scoretrack.module.core.api.commons.init.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.init.dto.InitResponse;
import com.teamk.scoretrack.module.core.api.commons.init.service.ApiInitServiceDelegator;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.security.session.filter.SessionAccessTokenBindFilter;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.token.jwt.service.AccessTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Consumer;

@RestController
public class ApiInitController extends BaseRestController {
    /**
     * /api with no version specified is used to initialize user data
     */
    public static final String INIT = "/api/init";
    public static final String SUPPORTED_APIS = BASE_URL + "/apis";
    /**
     * Access token is bound to current session, so we don't need refresh tokens
     * as a new access token can be created from bound session data.
     */
    public static final String REFRESH_ACCESS_TOKEN = "/api/token/refresh";
    private final ApiInitServiceDelegator apiInitServiceDelegator;
    private final AccessTokenService accessTokenService;

    @Autowired
    public ApiInitController(ApiInitServiceDelegator apiInitServiceDelegator, AccessTokenService accessTokenService) {
        this.apiInitServiceDelegator = apiInitServiceDelegator;
        this.accessTokenService = accessTokenService;
    }

    /**
     * This should be session accessible as to create jwt from it
     */
    @GetMapping(INIT)
    public InitResponse init(Authentication authentication, HttpServletRequest request) {
        RestForm<InitResponse> form = new RestForm<>(new InitResponse(), authentication);
        apiInitServiceDelegator.prepareFormOptions(form, getAccessTokenSessionBind(request));
        return form.getDto();
    }

    /**
     * Change url on client to that in {@link com.teamk.scoretrack.module.core.api.commons.search.ApiSearchController}
     */
    @GetMapping(SUPPORTED_APIS)
    @Deprecated
    public ResponseEntity<List<SportAPI>> supportedApis() {
        return new ResponseEntity<>(SportAPI.supported(), HttpStatus.OK);
    }

    @GetMapping(REFRESH_ACCESS_TOKEN)
    public ResponseEntity<AccessToken> refreshAccessToken(HttpServletRequest request) {
        return accessTokenService.generateToken().map(token -> {
            getAccessTokenSessionBind(request).accept(token);
            return ResponseEntity.ok(token);
        }).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @NotNull
    private Consumer<AccessToken> getAccessTokenSessionBind(HttpServletRequest request) {
        return accessToken -> request.getSession().setAttribute(SessionAccessTokenBindFilter.NAME, accessToken);
    }
}
