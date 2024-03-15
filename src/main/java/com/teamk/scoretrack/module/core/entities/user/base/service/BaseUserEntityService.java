package com.teamk.scoretrack.module.core.entities.user.base.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.core.entities.Privileges;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.base.dao.BaseUserDao;
import com.teamk.scoretrack.module.core.entities.user.client.domain.PlannedViewership;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.support.SupportUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BaseUserEntityService extends AbstractJpaEntityService<User, Long, BaseUserDao> {
    public boolean checkApiAccess(User user, int apiCode) {
        if (user.getUserGroup().isBusiness()) {
            ViewershipPlan viewershipPlan = ((ClientUser) user).getViewershipPlan();
            if (viewershipPlan.isActive()) {
                PlannedViewership plannedViewership = viewershipPlan.getPlannedViewership();
                if (plannedViewership != null && plannedViewership.isValid()) {
                    return Arrays.stream(plannedViewership.getApiCodes()).anyMatch(code -> code == apiCode);
                }
                return viewershipPlan.getCustomAvailableApis().contains(SportAPI.UNDEFINED.getByKey(apiCode));
            }
        } else if (user.getUserGroup().isSupport()) {
            return Arrays.asList(((SupportUser) user).getRole().getPrivileges()).contains(Privileges.API_ACCESS);
        }
        return false;
    }

    @Override
    @Autowired
    protected void setDao(BaseUserDao dao) {
        this.dao = dao;
    }
}
