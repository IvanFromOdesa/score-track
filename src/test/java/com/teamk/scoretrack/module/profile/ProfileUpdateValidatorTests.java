package com.teamk.scoretrack.module.profile;

import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.core.entities.SportType;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;
import com.teamk.scoretrack.module.core.entities.user.client.service.i18n.ProfilePageTranslatorService;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.ProfileUpdateFormValidationContext;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.ProfileUpdateValidator;
import com.teamk.scoretrack.module.security.io.service.valid.FileValidationContext;
import com.teamk.scoretrack.module.security.io.service.valid.IFileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileUpdateValidatorTests {
    @Mock
    private IFileValidator<FileValidationContext> fileValidator;
    @Mock
    private ProfilePageTranslatorService translatorService;
    @InjectMocks
    private ProfileUpdateValidator profileUpdateValidator;
    private ProfileUpdateFormValidationContext ctx;
    private ProfileUpdateDto dto;

    @BeforeEach
    void setUp() {
        dto = new ProfileUpdateDto();
        MultipartFile profileImg = mock(MultipartFile.class);
        ctx = new ProfileUpdateFormValidationContext(dto, profileImg);
    }

    @Test
    void testValidateProfileImage() {
        // Arrange
        ErrorMap fileErrors = ErrorMap.empty();
        when(fileValidator.validate(any(FileValidationContext.class))).thenReturn(fileErrors);

        // Act
        ErrorMap result = profileUpdateValidator.validate(ctx);

        // Assert
        verify(fileValidator).validate(any(FileValidationContext.class));
        assertTrue(result.isEmpty());
    }

    @Test
    void testValidateProfileImageWithError() {
        // Arrange
        ErrorMap fileErrors = ErrorMap.empty();
        fileErrors.put("profileImg", "error.profileImg");
        when(fileValidator.validate(any(FileValidationContext.class))).thenReturn(fileErrors);

        // Act
        ErrorMap result = profileUpdateValidator.validate(ctx);

        // Assert
        verify(fileValidator).validate(any(FileValidationContext.class));
        assertFalse(result.isEmpty());
        assertEquals("error.profileImg", result.getErrors().get("profileImg").getMsg());
    }

    @Test
    void testValidateDtoWithErrors() {
        // Arrange
        dto.setFirstName("Invalid Name 123");                            // Should trigger FirstNameValidationRule
        dto.setLastName("Invalid Name 123");                             // Should trigger LastNameValidationRule
        dto.setInstagramLink("invalid_link");                            // Should trigger InstagramProfileValidationRule
        dto.setxLink("invalid_link");                                    // Should trigger XProfileValidationRule
        dto.setDob(LocalDate.now().plusDays(1L).toString());   // Should trigger DobValidationRule
        dto.setNickname("Invalid@Nick");                                // Should trigger NicknameValidationRule

        when(translatorService.getMessage(anyString())).thenReturn("translated_error");

        ErrorMap fileErrors = ErrorMap.empty();
        when(fileValidator.validate(any(FileValidationContext.class))).thenReturn(fileErrors);

        // Act
        ErrorMap result = profileUpdateValidator.validate(ctx);

        // Assert
        Map<String, ErrorMap.Error> errors = result.getErrors();

        assertEquals(6, errors.size());  // Adjusted to 6
        assertEquals("translated_error", errors.get("firstName").getMsg());
        assertEquals("translated_error", errors.get("lastName").getMsg());
        assertEquals("translated_error", errors.get("instagramLink").getMsg());
        assertEquals("translated_error", errors.get("xLink").getMsg());
        assertEquals("translated_error", errors.get("dob").getMsg());
        assertEquals("translated_error", errors.get("nickname").getMsg());
    }

    @Test
    void testValidateDtoWithoutErrors() {
        // Arrange
        dto.setFirstName("ValidName");
        dto.setLastName("ValidName");
        dto.setInstagramLink("https://instagram.com/validprofile");
        dto.setxLink("https://twitter.com/validprofile");
        dto.setDob("2000-01-01");
        dto.setNickname("ValidNick");

        ErrorMap fileErrors = ErrorMap.empty();
        when(fileValidator.validate(any(FileValidationContext.class))).thenReturn(fileErrors);

        // Act
        ErrorMap result = profileUpdateValidator.validate(ctx);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testValidateSportPreferenceWithUndefined() {
        // Arrange
        dto.setSportPreference(List.of(SportType.UNDEFINED));
        when(translatorService.getMessage("error.sportPreference")).thenReturn("translated_error_sportPreference");

        ErrorMap fileErrors = ErrorMap.empty();
        when(fileValidator.validate(any(FileValidationContext.class))).thenReturn(fileErrors);

        // Act
        ErrorMap result = profileUpdateValidator.validate(ctx);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals("translated_error_sportPreference", result.getErrors().get("sportPreference").getMsg());
    }

    @Test
    void testValidateSportPreferenceWithoutUndefined() {
        // Arrange
        dto.setSportPreference(List.of(SportType.BASKETBALL, SportType.FOOTBALL));

        ErrorMap fileErrors = ErrorMap.empty();
        when(fileValidator.validate(any(FileValidationContext.class))).thenReturn(fileErrors);

        // Act
        ErrorMap result = profileUpdateValidator.validate(ctx);

        // Assert
        assertTrue(result.isEmpty());
    }
}
