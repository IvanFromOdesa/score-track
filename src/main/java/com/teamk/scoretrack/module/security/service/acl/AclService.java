package com.teamk.scoretrack.module.security.service.acl;

import com.teamk.scoretrack.module.core.entities.user.base.service.BaseUserEntityService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AclService implements IAclService {
    private final BaseUserEntityService userService;

    @Autowired
    public AclService(BaseUserEntityService userService) {
        this.userService = userService;
    }

    @Override
    public boolean checkAcl(Authentication authentication, String requiredAuthority) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.contains(requiredAuthority));
    }

    @Override
    public boolean checkAcl(Authentication authentication, int apiCode) {
        AuthenticationBean authenticationBean = (AuthenticationBean) authentication.getPrincipal();
        return userService.checkApiAccess(authenticationBean.getUser(), apiCode);
    }
}
