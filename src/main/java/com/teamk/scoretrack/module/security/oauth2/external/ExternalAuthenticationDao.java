package com.teamk.scoretrack.module.security.oauth2.external;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalAuthenticationDao extends JpaRepository<ExternalAuthentication, ExternalAuthentication.ExternalAuthenticationId> {
    Optional<ExternalAuthentication> findByAuthenticationBeanAndExternalId(AuthenticationBean authenticationBean, String externalId);
}
