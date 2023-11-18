package com.teamk.scoretrack.module.security.history.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.dao.AuthenticationHistoryDao;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationHistoryService extends AbstractHistoryService<AuthenticationHistory, AuthenticationHistoryDao> {
    public boolean resolveAuthHistory(AuthenticationBean authenticationBean, Long id) {
        return baseTransactionManager.doInNewTransaction(status -> {
            Optional<AuthenticationHistory> byId = dao.findByIdAndAuthenticationBean(id, authenticationBean);
            if (byId.isPresent()) {
                AuthenticationHistory history = byId.get();
                history.setStatus(AuthenticationHistory.Status.TRUSTED);
                save(history);
                return true;
            }
            return false;
        });
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationHistoryDao dao) {
        this.dao = dao;
    }
}
