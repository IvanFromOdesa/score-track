package com.teamk.scoretrack.module.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.security.auth.service.ExtendedDaoAuthenticationProvider;
import com.teamk.scoretrack.module.security.geo.filter.GeoLiteFilter;
import com.teamk.scoretrack.module.security.handler.AuthSuccessHandler;
import com.teamk.scoretrack.module.security.handler.error.authfailure.AuthFailureHandler;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;

import static com.teamk.scoretrack.module.security.auth.controller.AuthenticationController.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    private final RsaKeyProperties rsaKeys;
    private final GeoLiteFilter geoLiteFilter;

    public SecurityConfiguration(RsaKeyProperties rsaKeys, GeoLiteFilter geoLiteFilter) {
        this.rsaKeys = rsaKeys;
        this.geoLiteFilter = geoLiteFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        ExtendedDaoAuthenticationProvider extendedDaoAuthenticationProvider = new ExtendedDaoAuthenticationProvider();
        extendedDaoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        extendedDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        extendedDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return extendedDaoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration for mvc calls (thymeleaf templates)
     */
    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity, @Qualifier(AuthFailureHandler.NAME) AuthenticationFailureHandler failureHandler, @Qualifier(AuthSuccessHandler.NAME) AuthenticationSuccessHandler successHandler) throws Exception {
        RequestMatcher csrfRequestMatcher = request -> csrfProtected().stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
        return httpSecurity
                .csrf(configurer -> configurer.requireCsrfProtectionMatcher(csrfRequestMatcher))/*.csrf(AbstractHttpConfigurer::disable)*/
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HOME).permitAll()
                        .requestMatchers(SIGN_UP).permitAll()
                        .requestMatchers(ACTIVATE.concat("/**")).permitAll()
                        .requestMatchers(getResources()).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage(LOGIN).permitAll().defaultSuccessUrl(HOME).failureHandler(failureHandler).successHandler(successHandler))
                .logout(logout -> logout.logoutUrl(LOGOUT).logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll())
                .addFilterAfter(geoLiteFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private String[] getResources() {
        return new String[] {"/layouts/**", "/js/**"};
    }

    private Set<RequestMatcher> csrfProtected() {
        return Set.of(
                new AntPathRequestMatcher(LOGIN, HttpMethod.POST.name()),
                new AntPathRequestMatcher(LOGOUT, HttpMethod.POST.name()),
                new AntPathRequestMatcher(SIGN_UP, HttpMethod.POST.name()),
                new AntPathRequestMatcher(OtpAuthController.RECOVER, HttpMethod.POST.name()),
                new AntPathRequestMatcher(OtpAuthController.RESEND_OTP, HttpMethod.POST.name())
        );
    }

    /**
     * Configuration for api REST calls (e.g. React front-end)
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher(AntPathRequestMatcher.antMatcher(BaseRestController.BASE_URL.concat("/**")))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
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
