package com.teamk.scoretrack.integration;

import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.auth.controller.AuthenticationFailureInterceptor;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationSignUpService;
import com.teamk.scoretrack.module.security.auth.service.form.AuthFormOptionsService;
import com.teamk.scoretrack.module.security.auth.service.valid.AuthenticationSignUpFormValidator;
import com.teamk.scoretrack.utils.AuthenticationBeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

// TODO: adequate testing
@WebMvcTest(value = AuthenticationController.class, useDefaultFilters = false)
@ContextConfiguration(classes = SpringSecurityWebAuxTestConfig.class)
public class AuthenticationIntegrationTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthFormOptionsService authFormOptionsService;
    @MockBean
    private AuthenticationSignUpService authenticationSignUpService;
    @MockBean
    private AuthenticationSignUpFormValidator authenticationSignUpFormValidator;
    @MockBean
    private AuthenticationFailureInterceptor authenticationFailureInterceptor;
    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void givenValidCredentials_shouldSucceedRedirectToHomePage() throws Exception {
        MockHttpServletRequestBuilder login =
                getHttpServletRequestBuilder(
                        AuthenticationBeanUtils.DEFAULT_LOGINNNAME,
                        AuthenticationBeanUtils.DEFAULT_PASSWORD);

        mvc.perform(login)
                // Expect redirect to main page
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    void givenInvalidUsername_shouldRedirectBackToLogin() throws Exception {
        mvc.perform(getHttpServletRequestBuilder("notExistingLoginname", AuthenticationBeanUtils.DEFAULT_PASSWORD))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }

    @Test
    void givenInvalidPassword_shouldRedirectBackToLogin() throws Exception {
        mvc.perform(getHttpServletRequestBuilder(AuthenticationBeanUtils.DEFAULT_LOGINNNAME, "_dummy_pass"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }

    @Test
    void givenNonActivatedAccount_shouldRedirectBackToLogin() throws Exception {
        mvc.perform(getHttpServletRequestBuilder(AuthenticationBeanUtils.NOT_ACTIVATED_LOGINNAME, AuthenticationBeanUtils.DEFAULT_PASSWORD))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }

    private static MockHttpServletRequestBuilder getHttpServletRequestBuilder(String loginname, String password) {
        return MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", loginname)
                .param("password", password)
                .with(csrf());
    }
}
