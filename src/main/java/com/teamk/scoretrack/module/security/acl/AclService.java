package com.teamk.scoretrack.module.security.acl;

import com.teamk.scoretrack.module.core.entities.user.Privileges;
import com.teamk.scoretrack.module.core.entities.user.Role;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AclService {
    private final AuthenticationHolderService authenticationHolderService;

    @Autowired
    public AclService(AuthenticationHolderService authenticationHolderService) {
        this.authenticationHolderService = authenticationHolderService;
    }

    public boolean checkAcl(String requiredAuthority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !AuthenticationHolderService.isAnonymousAuthentication(authentication) && authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.contains(requiredAuthority));
    }

    public boolean checkApiAcl(int apiCode) {
        return checkAcl(Privileges.API_ACCESS, apiCode);
    }

    public boolean checkAcl(Privileges requiredAuthority) {
        return checkAcl(requiredAuthority, null);
    }

    public boolean checkAcl(Privileges requiredAuthority, Integer code) {
        return checkAcl(requiredAuthority.privilege(), code);
    }

    public boolean checkAcl(Role requiredAuthority) {
        return checkAcl(requiredAuthority.getRoleAlias(), null);
    }

    public boolean checkAcl(String requiredAuthority, Integer code) {
        if (AuthenticationHolderService.isAnonymousAuthentication()) {
            return false;
        } else {
            Optional<JwtAuthenticationToken> currentAuthenticationToken = authenticationHolderService.getCurrentAuthenticationToken();
            if (currentAuthenticationToken.isPresent()) {
                return currentAuthenticationToken.get().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(requiredAuthority) && isSubAuthPresent(code, (UserPrivilege) a));
            } else {
                Optional<AuthenticationBean> currentAuthentication = authenticationHolderService.getCurrentAuthentication();
                return currentAuthentication.map(authenticationBean -> authenticationBean.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(requiredAuthority) && isSubAuthPresent(code, a))).orElse(false);
            }
        }
    }

    private static boolean isSubAuthPresent(Integer code, UserPrivilege a) {
        return code == null || Arrays.stream(a.getSubAuthorities()).anyMatch(sub -> sub == code || sub == Privileges.ALL_SUBS);
    }
}
