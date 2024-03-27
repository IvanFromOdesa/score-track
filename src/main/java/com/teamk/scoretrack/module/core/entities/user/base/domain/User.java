package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.core.entities.user.Privileges;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.Role;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.core.entities.user.support.domain.SupportUser;
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

    // FIXME this and other helper methods that are currently tied to domain classes may be moved to a separate classes,
    //  although overall just a minor thing
    public void setDefaultLanguageAndAuth(AuthenticationBean authentication) {
        setAuthenticationBean(authentication);
        this.setPreferredLang(Language.byCode(LocaleContextHolder.getLocale().getLanguage()));
    }

    /**
     * @return array of api codes that are available for this user.
     * If this callback is fired for {@link SupportUser}, then returns {@link Privileges#ALL_SUBS} indicating that every API is accessible.
     * If the user is of type {@link ClientUser}, then gets api codes provided by specific {@link ViewershipPlan}.
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
        return getAnonymous();
    }

    @Override
    public Role getRole() {
        return Role.DEFAULT;
    }
}
