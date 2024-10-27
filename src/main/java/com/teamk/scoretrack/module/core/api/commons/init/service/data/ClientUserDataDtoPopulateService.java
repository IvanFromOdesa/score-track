package com.teamk.scoretrack.module.core.api.commons.init.service.data;

import com.teamk.scoretrack.module.core.api.commons.init.dto.ClientUserDataDto;
import com.teamk.scoretrack.module.core.entities.io.AccessStatus;
import com.teamk.scoretrack.module.core.entities.io.img.ExternalImageData;
import com.teamk.scoretrack.module.core.entities.io.img.ImageData;
import com.teamk.scoretrack.module.core.entities.sport_type.SportTypeDtoService;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.domain.Profile;
import com.teamk.scoretrack.module.core.entities.user.client.domain.Socials;
import com.teamk.scoretrack.module.core.entities.user.client.domain.SportPreference;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientUserDataDtoPopulateService extends UserDataDtoPopulateService<ClientUser> {
    private final SportTypeDtoService sportTypeDtoService;

    @Autowired
    public ClientUserDataDtoPopulateService(SportTypeDtoService sportTypeDtoService) {
        this.sportTypeDtoService = sportTypeDtoService;
    }

    @Override
    public ClientUserDataDto fill(ClientUser user, AccessToken token) {
        ClientUserDataDto dto = new ClientUserDataDto();
        this.setCommonProps(token, dto);
        dto.setProfile(getProfile(user));
        dto.setViewershipPlan(getViewershipPlan(user));
        dto.setLastSeen(user.getLastSeen());
        return dto;
    }

    public ClientUserDataDto.ProfileDto getProfile(ClientUser user) {
        ClientUserDataDto.ProfileDto dto = new ClientUserDataDto.ProfileDto();
        Profile profile = user.getProfile();
        if (profile != null) {
            dto.setFirstName(profile.getFirstName());
            dto.setLastName(profile.getLastName());
            dto.setDob(profile.getDob());
            dto.setNickname(profile.getNickname());
            dto.setProfileImg(getProfileImg(
                    profile.getProfileImg(),
                    profile.getExternalProfileImg()));
            dto.setBio(profile.getBio());
            dto.setSportPreference(
                    profile.getSportPreference().stream()
                            .map(SportPreference::getSport)
                            .map(sportTypeDtoService::toDto)
                            .collect(Collectors.toList())
            );
            fillSocials(dto, profile);
        }
        return dto;
    }

    private static void fillSocials(ClientUserDataDto.ProfileDto dto, Profile profile) {
        Supplier<Stream<Socials>> socials = () -> profile.getSocials().stream();
        socials.get().filter(s -> s.getSocialId().getSocialType().isInstagram()).findFirst().ifPresent(s -> dto.setInstagramLink(s.buildSocialLink()));
        socials.get().filter(s -> s.getSocialId().getSocialType().isX()).findFirst().ifPresent(s -> dto.setxLink(s.buildSocialLink()));
    }

    private static ClientUserDataDto.ProfileImgDto getProfileImg(@Nullable ImageData imageEntity,
                                                                 @Nullable ExternalImageData externalImageData) {
        ClientUserDataDto.ProfileImgDto profileImgDto = new ClientUserDataDto.ProfileImgDto();
        if (imageEntity != null) {
            AccessStatus accessStatus = imageEntity.getAccessStatus();
            profileImgDto.setAccessStatus(accessStatus);
            if (accessStatus.isAccessible()) {
                profileImgDto.setUrl(imageEntity.getExternalUrl());
            }
        } else if (externalImageData != null) {
            profileImgDto.setUrl(externalImageData.getPublicUrl());
            profileImgDto.setAccessStatus(AccessStatus.ACCESSIBLE);
        } else {
            profileImgDto.setAccessStatus(AccessStatus.UNDEFINED);
        }
        return profileImgDto;
    }

    private static ClientUserDataDto.ViewershipPlanDto getViewershipPlan(ClientUser user) {
        ClientUserDataDto.ViewershipPlanDto dto = new ClientUserDataDto.ViewershipPlanDto();
        ViewershipPlan viewershipPlan = user.getViewershipPlan();
        dto.setPlannedViewership(viewershipPlan.getPlannedViewership());
        dto.setCustomAvailableApis(viewershipPlan.getCustomAvailableApis());
        dto.setEndDateTime(viewershipPlan.getEndDateTime());
        dto.setActive(viewershipPlan.isActive());
        return dto;
    }
}
