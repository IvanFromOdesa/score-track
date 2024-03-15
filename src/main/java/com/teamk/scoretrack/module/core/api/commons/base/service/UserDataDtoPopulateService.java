package com.teamk.scoretrack.module.core.api.commons.base.service;

import com.teamk.scoretrack.module.core.api.commons.base.dto.UserDataDto;
import com.teamk.scoretrack.module.core.entities.io.AccessStatus;
import com.teamk.scoretrack.module.core.entities.io.img.ImageData;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.client.domain.*;
import com.teamk.scoretrack.module.security.recaptcha.RecaptchaKeyProperties;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: refactor?
@Service
public class UserDataDtoPopulateService {
    private final RecaptchaKeyProperties recaptchaKeyProperties;

    @Autowired
    public UserDataDtoPopulateService(RecaptchaKeyProperties recaptchaKeyProperties) {
        this.recaptchaKeyProperties = recaptchaKeyProperties;
    }

    public UserDataDto fill(User user, AccessToken token) {
        UserDataDto dto = new UserDataDto();
        dto.setToken(token);
        dto.setRecaptchaKey(recaptchaKeyProperties.publicKey());
        dto.setProfile(getProfile(user));
        dto.setViewershipPlan(getViewershipPlan(user));
        dto.setLastSeen(user.getLastSeen());
        dto.setLocale(LocaleContextHolder.getLocale().getLanguage());
        return dto;
    }

    public UserDataDto.ProfileDto getProfile(User user) {
        UserDataDto.ProfileDto dto = new UserDataDto.ProfileDto();
        if (user instanceof ClientUser clientUser) {
            Profile profile = clientUser.getProfile();
            if (profile != null) {
                dto.setFirstName(profile.getFirstName());
                dto.setLastName(profile.getLastName());
                dto.setDob(profile.getDob());
                dto.setNickname(profile.getNickname());
                dto.setProfileImg(getProfileImg(profile.getProfileImg()));
                dto.setBio(profile.getBio());
                dto.setSportPreference(profile.getSportPreference().stream().map(SportPreference::getSport).collect(Collectors.toList()));
                fillSocials(dto, profile);
            }
        }
        return dto;
    }

    private static void fillSocials(UserDataDto.ProfileDto dto, Profile profile) {
        Supplier<Stream<Socials>> socials = () -> profile.getSocials().stream();
        socials.get().filter(s -> s.getSocialId().getSocialType().isInstagram()).findFirst().ifPresent(s -> dto.setInstagramLink(s.buildSocialLink()));
        socials.get().filter(s -> s.getSocialId().getSocialType().isX()).findFirst().ifPresent(s -> dto.setxLink(s.buildSocialLink()));
    }

    private static UserDataDto.ProfileImgDto getProfileImg(@Nullable ImageData imageEntity) {
        UserDataDto.ProfileImgDto profileImgDto = new UserDataDto.ProfileImgDto();
        if (imageEntity != null) {
            AccessStatus accessStatus = imageEntity.getAccessStatus();
            profileImgDto.setAccessStatus(accessStatus);
            if (accessStatus.isAccessible()) {
                profileImgDto.setUrl(imageEntity.getExternalUrl());
            }
        } else {
            profileImgDto.setAccessStatus(AccessStatus.UNDEFINED);
        }
        return profileImgDto;
    }

    private static UserDataDto.ViewershipPlanDto getViewershipPlan(User user) {
        UserDataDto.ViewershipPlanDto dto = new UserDataDto.ViewershipPlanDto();
        if (user instanceof ClientUser clientUser) {
            ViewershipPlan viewershipPlan = clientUser.getViewershipPlan();
            dto.setPlannedViewership(viewershipPlan.getPlannedViewership());
            dto.setCustomAvailableApis(viewershipPlan.getCustomAvailableApis());
            dto.setEndDateTime(viewershipPlan.getEndDateTime());
            dto.setActive(viewershipPlan.isActive());
        }
        return dto;
    }
}
