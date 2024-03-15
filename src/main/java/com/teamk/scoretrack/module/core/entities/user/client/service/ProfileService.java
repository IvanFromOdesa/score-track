package com.teamk.scoretrack.module.core.entities.user.client.service;

import com.teamk.scoretrack.module.commons.base.service.BaseTransactionManager;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.commons.util.HibernateRelations;
import com.teamk.scoretrack.module.core.api.commons.base.dto.UserDataDto;
import com.teamk.scoretrack.module.core.api.commons.base.service.UserDataDtoPopulateService;
import com.teamk.scoretrack.module.core.entities.SportType;
import com.teamk.scoretrack.module.core.entities.io.FileData;
import com.teamk.scoretrack.module.core.entities.io.img.ImageData;
import com.teamk.scoretrack.module.core.entities.io.service.FileUploadService;
import com.teamk.scoretrack.module.core.entities.user.client.domain.*;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules.DobValidationRule;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules.InstagramProfileValidationRule;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules.XProfileValidationRule;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationWrapper;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final ClientUserEntityService clientUserEntityService;
    private final BaseTransactionManager transactionManager;
    private final AuthenticationEntityService authenticationEntityService;
    private final FileUploadService fileUploadService;
    private final AuthenticationHolderService authenticationHolderService;
    private final UserDataDtoPopulateService dtoPopulateService;

    @Autowired
    public ProfileService(ClientUserEntityService clientUserEntityService,
                          BaseTransactionManager transactionManager,
                          AuthenticationEntityService authenticationEntityService,
                          FileUploadService fileUploadService,
                          AuthenticationHolderService authenticationHolderService,
                          UserDataDtoPopulateService dtoPopulateService) {
        this.clientUserEntityService = clientUserEntityService;
        this.transactionManager = transactionManager;
        this.authenticationEntityService = authenticationEntityService;
        this.fileUploadService = fileUploadService;
        this.authenticationHolderService = authenticationHolderService;
        this.dtoPopulateService = dtoPopulateService;
    }

    @PreAuthorize("@aclService.checkAcl(T(com.teamk.scoretrack.module.core.entities.Privileges).PROFILE_ACCESS)")
    public UserDataDto.ProfileDto update(ProfileUpdateDto dto, MultipartFile profileImg) {
        Optional<AuthenticationWrapper> authenticationWrapper = authenticationHolderService.getAuthenticationWrapper();
        if (authenticationWrapper.isPresent()) {
            return transactionManager.doInNewTransaction((transactionStatus) -> {
                /*
                 * Load the authentication into persistence context and lazy load the mapped user.
                 */
                AuthenticationWrapper authentication = authenticationWrapper.get();
                AuthenticationBean authenticationBean = (AuthenticationBean) authenticationEntityService.loadUserByUsername(authentication.loginname());
                /*
                 * Acl should have already checked for accessing this method only for client users.
                 */
                ClientUser user = (ClientUser) authenticationBean.getUser();
                Profile profile = user.getProfile();
                fillProfile(dto, profile);
                if (profileImg != null && !profileImg.isEmpty()) {
                    FileData fileData = fileUploadService.uploadFile(profileImg, authenticationBean);
                    profile.setProfileImg((ImageData) fileData);
                }
                clientUserEntityService.save(user);
                return dtoPopulateService.getProfile(user);
            });
        }
        throw new IllegalStateException("Should not be able to access without authentication.");
    }

    public byte[] getProfilePicture(UUID externalUserId, String filename) {
        return fileUploadService.downloadFile(externalUserId, filename);
    }

    private void fillProfile(ProfileUpdateDto dto, Profile profile) {
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        String nickname = dto.getNickname();
        if (nickname != null) {
            profile.setNickname(nickname);
        }
        profile.setBio(dto.getBio());
        profile.setDob(CommonsUtil.orNull(dto.getDob(), dob -> LocalDate.parse(dob, DobValidationRule.DATE_VALIDATOR.getDtf())));
        HibernateRelations.updateManyToOne(dto.getSportPreference(), profile.getSportPreference(), sportTypes -> getSportPreference(sportTypes, profile));
        HibernateRelations.updateManyToOne((Supplier<Collection<Socials>>) () -> getSocials(profile, dto), profile.getSocials());
    }

    private List<Socials> getSocials(Profile profile, ProfileUpdateDto dto) {
        List<Socials> res = new ArrayList<>();
        CommonsUtil.runIfNonNull(dto.getInstagramLink(), instagramLink -> res.add(getSocial(profile, SocialType.INSTAGRAM, instagramLink, InstagramProfileValidationRule.INSTAGRAM_PROFILE, 1)));
        CommonsUtil.runIfNonNull(dto.getxLink(), xLink -> res.add(getSocial(profile, SocialType.X, xLink, XProfileValidationRule.X_PROFILE, 3)));
        return res;
    }

    private Socials getSocial(Profile profile, SocialType socialType, String profileLink, Pattern profilePattern, int group) {
        Socials socials = new Socials();
        Socials.SocialId socialId = new Socials.SocialId();
        socialId.setSocialType(socialType);
        socialId.setProfileId(profile.getId());
        socials.setSocialId(socialId);
        socials.setProfile(profile);
        Matcher matcher = profilePattern.matcher(profileLink);
        if (matcher.matches()) {
            socials.setProfileUrl(matcher.group(group));
        }
        return socials;
    }

    @NotNull
    private List<SportPreference> getSportPreference(List<SportType> sports, Profile profile) {
        return sports.stream().map(s -> {
            SportPreference sportPreference = new SportPreference();
            sportPreference.setSport(s);
            sportPreference.setProfile(profile);
            return sportPreference;
        }).collect(Collectors.toList());
    }
}
