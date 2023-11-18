package com.teamk.scoretrack.module.core.entities.user.base.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.base.dao.AbstractUserDao;
import com.teamk.scoretrack.module.core.entities.user.base.domain.PlannedViewership;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.ViewershipPlan;
import com.teamk.scoretrack.module.core.entities.user.base.domain.business.BusinessUser;
import com.teamk.scoretrack.module.core.entities.user.base.ctx.BusinessUserProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;
import org.springframework.data.util.Pair;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractUserEntityService<ENTITY extends User, DAO extends AbstractUserDao<ENTITY>, USER_CTX extends UserProcessingContext> extends AbstractJpaEntityService<ENTITY, Long, DAO> {
    protected <USER extends BusinessUser> USER processBusinessUser(BusinessUserProcessingContext ctx, USER user) {
        user.setViewershipPlan(getOrDefaultVP(ctx.getViewershipCreationContext()));
        user.setDefaultLanguageAndAuth(ctx.authenticationBean());
        return user;
    }

    protected ViewershipPlan getOrDefaultVP(BusinessUserProcessingContext.ViewershipCreationContext ctx) {
        if (ctx != null) {
            return getCtxBasedViewershipPlan(ctx);
        } else {
            return ViewershipPlan.getDefault();
        }
    }

    protected ViewershipPlan getCtxBasedViewershipPlan(BusinessUserProcessingContext.ViewershipCreationContext ctx) {
        ViewershipPlan viewershipPlan = new ViewershipPlan();
        int code = ctx.getPlannedViewershipCode();
        PlannedViewership byKey = PlannedViewership.LOOKUP_MAP.get(code);
        long duration;
        if (byKey.isValid()) {
            viewershipPlan.setPlannedViewership(byKey);
            duration = byKey.getDuration();
        } else {
            Pair<Long, int[]> customApiPlan = ctx.getCustomApiPlan();
            List<SportAPI> sportAPIs = Arrays.stream(customApiPlan.getSecond()).filter(SportAPI.LOOKUP_MAP::containsKey).mapToObj(SportAPI.LOOKUP_MAP::get).toList();
            viewershipPlan.setCustomAvailableApis(sportAPIs);
            duration = customApiPlan.getFirst();
        }
        viewershipPlan.setEndDateTime(Instant.now().plus(duration, ChronoUnit.MILLIS));
        return viewershipPlan;
    }

    public abstract void processUserCreation(USER_CTX ctx);
}
