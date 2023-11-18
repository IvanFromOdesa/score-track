package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.commons.other.ScoreTrackConfig;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@jakarta.persistence.Entity
@Table(name = "user_t")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends Identifier implements IUserAware {
    private Instant lastSeen;
    private Language preferredLang;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = AuthenticationBean.FK_NAME, referencedColumnName = "id", nullable = false)
    private AuthenticationBean authentication;

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

    public AuthenticationBean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationBean authentication) {
        this.authentication = authentication;
    }

    public void setDefaultLanguageAndAuth(AuthenticationBean authentication) {
        this.authentication = authentication;
        this.setPreferredLang(Language.UNDEFINED.getByKey(ScoreTrackConfig.CURRENT_LOCALE.getLanguage()));
    }

    @Override
    public UserGroup getUserGroup() {
        return UserGroup.DEFAULT;
    }
}
