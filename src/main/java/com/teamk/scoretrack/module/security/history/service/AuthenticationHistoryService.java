package com.teamk.scoretrack.module.security.history.service;

import com.teamk.scoretrack.module.commons.exception.BaseEntityNotFoundException;
import com.teamk.scoretrack.module.commons.other.ScoreTrackConfig;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationStatus;
import com.teamk.scoretrack.module.security.history.dao.AuthenticationHistoryDao;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationHistoryService extends AbstractHistoryService<AuthenticationHistory, AuthenticationHistoryDao> {
    public boolean resolveAuthHistory(AuthenticationBean authenticationBean, Long id) {
        return baseTransactionManager.doInNewTransaction(status -> {
            boolean unblocked = false;
            List<AuthenticationHistory> blocked = dao.findByAuthenticationBeanAndStatusBlocked(authenticationBean, PageRequest.of(0, 2));
            Optional<AuthenticationHistory> byId = blocked.stream().filter(h -> h.getId().equals(id)).findFirst();
            if (byId.isPresent()) {
                byId.get().setStatus(AuthenticationHistory.Status.TRUSTED);
            } else {
                throw new BaseEntityNotFoundException("AuthHistory (blocked) not found by id: %s".formatted(id), ScoreTrackConfig.IS_REQUEST_SCOPE);
            }
            // If only this history was blocked
            if (blocked.size() == 1) {
                authenticationBean.setStatus(AuthenticationStatus.ACTIVATED);
                unblocked = true;
            }
            save(byId.get());
            return unblocked;
        });
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationHistoryDao dao) {
        this.dao = dao;
    }
}
