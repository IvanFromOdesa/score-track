package com.teamk.scoretrack.module.core.entities.user.client.service;

import com.teamk.scoretrack.module.core.entities.io.img.ExternalImageData;
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
        user.setProfile(createProfile(authenticationBean, ctx));
        return user;
    }

    protected Profile createProfile(AuthenticationBean authenticationBean, USER_CTX ctx) {
        Profile profile = new Profile();
        ClientUserProcessingContext.ProfileCreationContext profileCreationContext = ctx.getProfileCreationContext();
        String username = authenticationBean.getUsername();
        if (profileCreationContext != null) {
            String nickname = profileCreationContext.getNickname();
            profile.setNickname(nickname != null ? nickname : username);
            profile.setFirstName(profileCreationContext.getFirstName());
            profile.setLastName(profileCreationContext.getLastName());
            profile.setDob(profileCreationContext.getDob());
            profile.setBio(profileCreationContext.getBio());
            profile.setExternalProfileImg(getExternalProfileImg(
                    profileCreationContext.getImageUrl(),
                    authenticationBean));
        } else {
            profile.setNickname(username);
        }
        return profile;
    }

    private static ExternalImageData getExternalProfileImg(String url, AuthenticationBean authentication) {
        ExternalImageData profileImg = new ExternalImageData();
        profileImg.setPublicUrl(url);
        profileImg.setUploadedBy(authentication);
        return profileImg;
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
