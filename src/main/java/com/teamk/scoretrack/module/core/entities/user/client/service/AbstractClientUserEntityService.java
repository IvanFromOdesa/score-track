package com.teamk.scoretrack.module.core.entities.user.client.service;

import com.teamk.scoretrack.module.core.entities.sport_api.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.base.dao.AbstractUserDao;
import com.teamk.scoretrack.module.core.entities.user.base.service.AbstractUserEntityService;
import com.teamk.scoretrack.module.core.entities.user.client.ctx.ClientUserProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.domain.PlannedViewership;
import com.teamk.scoretrack.module.core.entities.user.client.domain.Profile;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.data.util.Pair;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractClientUserEntityService<USER extends ClientUser, DAO extends AbstractUserDao<USER>, USER_CTX extends ClientUserProcessingContext> extends AbstractUserEntityService<USER, DAO, USER_CTX> {
    protected USER processClientUser(USER_CTX ctx, USER user) {
        AuthenticationBean authenticationBean = ctx.authenticationBean();
        user.setViewershipPlan(getOrDefaultVP(ctx.getViewershipCreationContext()));
        user.setDefaultLanguageAndAuth(authenticationBean);
        user.setProfile(createProfile(authenticationBean));
        return user;
    }

    protected Profile createProfile(AuthenticationBean authenticationBean) {
        Profile profile = new Profile();
        profile.setNickname(authenticationBean.getUsername());
        return profile;
    }

    protected ViewershipPlan getOrDefaultVP(ClientUserProcessingContext.ViewershipCreationContext ctx) {
        if (ctx != null) {
            return getCtxBasedViewershipPlan(ctx);
        } else {
            return ViewershipPlan.getDefault();
        }
    }

    protected ViewershipPlan getCtxBasedViewershipPlan(ClientUserProcessingContext.ViewershipCreationContext ctx) {
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
}
