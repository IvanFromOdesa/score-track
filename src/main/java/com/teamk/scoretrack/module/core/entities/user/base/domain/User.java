package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.core.entities.Privileges;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationIdentifier;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;

@jakarta.persistence.Entity
@Table(name = "user_t")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AuthenticationIdentifier implements IUserAware {
    private Instant lastSeen;
    private Language preferredLang;

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Language getPreferredLang() {
        return preferredLang;
    }

    public void setPreferredLang(Language preferredLang) {
        this.preferredLang = preferredLang;
    }

    public void setDefaultLanguageAndAuth(AuthenticationBean authentication) {
        setAuthenticationBean(authentication);
        this.setPreferredLang(Language.byCode(LocaleContextHolder.getLocale().getLanguage()));
    }

    /**
     * @return array of api codes that are available for this user or else array of {@link com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert#CODE_UNDEFINED},
     * if the user is of type {@link UserGroup#SUPPORT_USER}
     */
    public int[] getAvailableApiCodes() {
        if (this.getUserGroup().isSupport()) {
            return new int[] { Privileges.ALL_SUBS };
        } else {
            ViewershipPlan viewershipPlan = ((ClientUser) this).getViewershipPlan();
            return viewershipPlan.isActive() ? viewershipPlan.getAvailableApiCodes() : new int[]{ SportAPI.CODE_UNDEFINED };
        }
    }

    @Override
    public UserGroup getUserGroup() {
        return UserGroup.DEFAULT;
    }
}
