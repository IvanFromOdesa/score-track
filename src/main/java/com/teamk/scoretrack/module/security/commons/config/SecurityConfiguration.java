package com.teamk.scoretrack.module.security.commons.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Privileges;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationSignUpService;
import com.teamk.scoretrack.module.security.auth.service.ExtendedDaoAuthenticationProvider;
import com.teamk.scoretrack.module.security.auth.service.PostAuthenticationChecks;
import com.teamk.scoretrack.module.security.auth.service.PreAuthenticationChecks;
import com.teamk.scoretrack.module.security.commons.filter.RedirectFilter;
import com.teamk.scoretrack.module.security.firewall.filter.XSSSanitizerFilter;
import com.teamk.scoretrack.module.security.geo.filter.GeoLiteFilter;
import com.teamk.scoretrack.module.security.handler.AuthSuccessHandler;
import com.teamk.scoretrack.module.security.handler.OAuth2SuccessHandler;
import com.teamk.scoretrack.module.security.handler.error.authfailure.AuthFailureHandler;
import com.teamk.scoretrack.module.security.ipblocker.ExtendedLoginUrlAuthEntryPoint;
import com.teamk.scoretrack.module.security.ipblocker.filter.IpBlockFilter;
import com.teamk.scoretrack.module.security.oauth2.ExtendedOAuth2UserService;
import com.teamk.scoretrack.module.security.recaptcha.filter.RecaptchaVerifyFilter;
import com.teamk.scoretrack.module.security.session.filter.SessionAccessTokenBindFilter;
import com.teamk.scoretrack.module.security.token.jwt.convert.JwtUserPrivilegeConverter;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;
import java.util.Set;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.commons.layout.preferences.PreferencesController.LANG;
import static com.teamk.scoretrack.module.commons.layout.preferences.PreferencesController.PREF;
import static com.teamk.scoretrack.module.core.api.commons.init.controller.ApiInitController.*;
import static com.teamk.scoretrack.module.security.auth.controller.AuthenticationController.*;
import static com.teamk.scoretrack.module.security.pwdreset.controller.PwdResetController.*;
import static com.teamk.scoretrack.module.security.session.controller.SessionExpirationAlertSseController.SESSION_EXPIRATION_ALERT;
import static com.teamk.scoretrack.module.security.session.controller.SessionExpirationAlertSseController.SUBSCRIBE;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    private final RsaKeyProperties rsaKeys;
    private final IpBlockFilter ipBlockFilter;
    private final GeoLiteFilter geoLiteFilter;
    private final RecaptchaVerifyFilter recaptchaVerifyFilter;
    private final XSSSanitizerFilter xssSanitizerFilter;
    private final RedirectFilter redirectFilter;
    @Value("${sym.remember-me}")
    private String rememberMeKey;
    private static final int REMEMBER_ME_TOKEN_DURATION = 60 * 60 * 24 * 7;

    public SecurityConfiguration(RsaKeyProperties rsaKeys,
                                 IpBlockFilter ipBlockFilter,
                                 GeoLiteFilter geoLiteFilter,
                                 RecaptchaVerifyFilter recaptchaVerifyFilter,
                                 XSSSanitizerFilter xssSanitizerFilter,
                                 RedirectFilter redirectFilter) {
        this.rsaKeys = rsaKeys;
        this.ipBlockFilter = ipBlockFilter;
        this.geoLiteFilter = geoLiteFilter;
        this.recaptchaVerifyFilter = recaptchaVerifyFilter;
        this.xssSanitizerFilter = xssSanitizerFilter;
        this.redirectFilter = redirectFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                            @Qualifier(HashingConfiguration.BCRYPT) PasswordEncoder passwordEncoder,
                                                            AuthenticationSignUpService authenticationSignUpService,
                                                            @Qualifier(PreAuthenticationChecks.NAME) UserDetailsChecker preAuthChecks,
                                                            @Qualifier(PostAuthenticationChecks.NAME) UserDetailsChecker postAuthChecks) {
        ExtendedDaoAuthenticationProvider extendedDaoAuthenticationProvider = new ExtendedDaoAuthenticationProvider(authenticationSignUpService);
        extendedDaoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        extendedDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        extendedDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        extendedDaoAuthenticationProvider.setPreAuthenticationChecks(preAuthChecks);
        extendedDaoAuthenticationProvider.setPostAuthenticationChecks(postAuthChecks);
        return extendedDaoAuthenticationProvider;
    }

    @Bean(ExtendedLoginUrlAuthEntryPoint.NAME)
    public AuthenticationEntryPoint authenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        return new ExtendedLoginUrlAuthEntryPoint(LOGIN, exceptionResolver);
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Configuration for mvc calls (thymeleaf templates)
     */
    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity,
                                               @Qualifier(AuthFailureHandler.NAME) AuthenticationFailureHandler failureHandler,
                                               @Qualifier(AuthSuccessHandler.NAME) AuthenticationSuccessHandler successHandler,
                                               @Qualifier(OAuth2SuccessHandler.NAME) AuthenticationSuccessHandler oauth2SuccessHandler,
                                               @Qualifier(ExtendedLoginUrlAuthEntryPoint.NAME) AuthenticationEntryPoint authenticationEntryPoint,
                                               // TODO: improve. There may be more oauth2 services in the future
                                               ExtendedOAuth2UserService oAuth2UserService) throws Exception {
        RequestMatcher csrfRequestMatcher = request -> csrfProtected().stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
        return httpSecurity
                .cors(Customizer.withDefaults())
                // TODO: configure
                /*.headers(h -> h.xssProtection(Customizer.withDefaults()).contentSecurityPolicy(c -> c.policyDirectives("script-src 'self'")))*/
                .csrf(configurer -> configurer.requireCsrfProtectionMatcher(csrfRequestMatcher))/*.csrf(AbstractHttpConfigurer::disable)*/
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HOME).permitAll()
                        .requestMatchers(SIGN_UP).permitAll()
                        .requestMatchers(ACTIVATE.concat("/**")).permitAll()
                        // TODO: refactor into one list all permitted preferences options
                        .requestMatchers(PWD_FORGOT).permitAll()
                        .requestMatchers(CONFIRM_URL_TOKEN.concat("/**")).permitAll()
                        .requestMatchers(REQ_PWD_RESET).permitAll()
                        .requestMatchers(NEW_PWD).permitAll()
                        .requestMatchers(INIT).permitAll()
                        .requestMatchers(REFRESH_ACCESS_TOKEN).permitAll()
                        .requestMatchers(PREF.concat(LANG).concat("/**")).permitAll()
                        .requestMatchers(getWhitelistedResources()).permitAll()
                        //.requestMatchers(new LoginPermittedQueryParametersRequestMatcher()).permitAll()
                        /*
                         * Permit actuator endpoints for allowed user group
                         */
                        .requestMatchers("/actuator/**").hasAuthority(Privileges.APP_MANAGEMENT.privilege())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage(LOGIN).permitAll().defaultSuccessUrl(HOME).failureHandler(failureHandler).successHandler(successHandler))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage(LOGIN)
                        .defaultSuccessUrl(HOME)
                        .successHandler(oauth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oAuth2UserService))
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                /*
                 * If using https, add this to clear everything (cache, cookies, storage and executionContexts) on logout.
                 * .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL)))
                 */
                .logout(logout -> logout.logoutUrl(LOGOUT).logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll())
                .rememberMe(configurer -> configurer.key(rememberMeKey).tokenValiditySeconds(REMEMBER_ME_TOKEN_DURATION))
                .addFilterAfter(xssSanitizerFilter, CsrfFilter.class)
                .addFilterBefore(ipBlockFilter, AuthorizationFilter.class)
                .addFilterAfter(recaptchaVerifyFilter, IpBlockFilter.class)
                .addFilterAfter(geoLiteFilter, RecaptchaVerifyFilter.class)
                .addFilterAfter(redirectFilter, RecaptchaVerifyFilter.class)
                .build();
    }

    /**
     * Public resource folders
     * @return whitelisted public resource folders.
     */
    public static String[] getWhitelistedResources() {
        return new String[] {"/layouts/**", "/bundles/**", "/js/**", "/vendor/**", "/api-logos/**", "/api/**", "/lang-icons/**", "/favicon.ico"};
    }

    private Set<RequestMatcher> csrfProtected() {
        return Set.of(
                new AntPathRequestMatcher(LOGIN, HttpMethod.POST.name()),
                new AntPathRequestMatcher(LOGOUT, HttpMethod.POST.name()),
                new AntPathRequestMatcher(SIGN_UP, HttpMethod.POST.name()),
                new AntPathRequestMatcher(OtpAuthController.RECOVER, HttpMethod.POST.name()),
                new AntPathRequestMatcher(OtpAuthController.RESEND_OTP, HttpMethod.POST.name()),
                new AntPathRequestMatcher(REQ_PWD_RESET, HttpMethod.POST.name()),
                new AntPathRequestMatcher(NEW_PWD, HttpMethod.POST.name())
                /*new AntPathRequestMatcher(PREF.concat(LANG).concat("/**"), HttpMethod.POST.name())*/
        );
    }

    /**
     * Configuration for api REST calls (e.g. React front-end)
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity, SessionAccessTokenBindFilter sessionAccessTokenBindFilter) throws Exception {
        return httpSecurity
                .securityMatcher(AntPathRequestMatcher.antMatcher(BASE_URL.concat("/**")))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SUPPORTED_APIS).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                /*
                 * This is related specifically to mis sync between session and jwt.
                 * Once the HttpSession is expired, client can still provide a valid jwt and set the authentication to JwtAuthenticationToken.
                 * To prevent that, we have to check if session and jwt are in sync before the undermentioned filter will set the authentication.
                 */
                .addFilterBefore(sessionAccessTokenBindFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    /**
     * This is used for testing dev react app in dev environment.
     */
    @Bean
    @ConditionalOnProperty(value = "react.enable.dev", havingValue = "true")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JwtUserPrivilegeConverter());
        return converter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }
}
