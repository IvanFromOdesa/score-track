package com.teamk.scoretrack.module.core.entities.user.client.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.core.api.commons.base.dto.BundleResponse;
import com.teamk.scoretrack.module.core.api.commons.base.dto.GenericPostServerResponse;
import com.teamk.scoretrack.module.core.api.commons.base.dto.UserDataDto;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;
import com.teamk.scoretrack.module.core.entities.user.client.service.ProfileService;
import com.teamk.scoretrack.module.core.entities.user.client.service.form.ProfileInitFormOptionsService;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.ProfileUpdateFormValidationContext;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.ProfileUpdateValidator;
import com.teamk.scoretrack.module.security.token.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.teamk.scoretrack.module.core.entities.user.client.controller.ProfilePageController.PROFILE;

@RestController
@RequestMapping(PROFILE)
public class ProfilePageController extends BaseRestController {
    public static final String PROFILE = BASE_URL + "/profile";
    private static final String INIT = "/init";
    private static final String UPDATE = "/update";
    public static final String PICTURE = "/pic";
    private final ProfileInitFormOptionsService formOptionsService;
    private final ProfileUpdateValidator profileUpdateValidator;
    private final ProfileService profileService;

    @Autowired
    public ProfilePageController(ProfileInitFormOptionsService formOptionsService,
                                 ProfileUpdateValidator profileUpdateValidator,
                                 ProfileService profileService) {
        this.formOptionsService = formOptionsService;
        this.profileUpdateValidator = profileUpdateValidator;
        this.profileService = profileService;
    }

    @GetMapping(INIT)
    public ResponseEntity<BundleResponse> init() {
        RestForm<BundleResponse> form = new RestForm<>(new BundleResponse());
        formOptionsService.prepareFormOptions(form);
        return ResponseEntity.ok(form.getDto());
    }

    @PostMapping(path = UPDATE/*, consumes = MediaType.MULTIPART_FORM_DATA_VALUE*/)
    public ResponseEntity<GenericPostServerResponse<UserDataDto.ProfileDto>> update(@RequestPart(name = "data", required = false) ProfileUpdateDto dto,
                                                                                    @RequestPart(name = "profileImg", required = false) MultipartFile profileImg) {
        GenericPostServerResponse<UserDataDto.ProfileDto> response = new GenericPostServerResponse<>();
        ErrorMap errorMap = profileUpdateValidator.validate(new ProfileUpdateFormValidationContext(dto, profileImg));
        if (!errorMap.isEmpty()) {
            response.setErrors(errorMap.getErrors());
        } else {
            response.setData(profileService.update(dto, profileImg));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(PICTURE + "/{externalId}/{filename}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String externalId, @PathVariable String filename) {
        byte[] profilePicture = profileService.getProfilePicture(UUIDUtils.fromBase64Url(externalId), filename);
        return profilePicture.length > 0 ? ResponseEntity.ok(profilePicture) : ResponseEntity.notFound().build();
    }
}
