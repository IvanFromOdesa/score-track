package com.teamk.scoretrack.module.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.teamk.scoretrack.module.commons.controller.BaseRestController;
import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.geo.filter.GeoLiteFilter;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Set;

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
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration for mvc calls (thymeleaf templates)
     */
    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
        RequestMatcher csrfRequestMatcher = request -> csrfProtected().stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
        String[] resources = new String[] {"/layouts/**", "/js/**"};
        return httpSecurity
                .csrf(configurer -> configurer.requireCsrfProtectionMatcher(csrfRequestMatcher))/*.csrf(AbstractHttpConfigurer::disable)*/
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(AuthenticationController.HOME).permitAll()
                        .requestMatchers(AuthenticationController.SIGN_UP).permitAll()
                        .requestMatchers(AuthenticationController.ACTIVATE.concat("/**")).permitAll()
                        .requestMatchers(OtpAuthController.RECOVER).permitAll()
                        .requestMatchers(resources).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage(AuthenticationController.LOGIN).permitAll().defaultSuccessUrl(AuthenticationController.HOME))
                .logout(logout -> logout.logoutUrl(AuthenticationController.LOGOUT).logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll())
                .addFilterAfter(geoLiteFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private Set<RequestMatcher> csrfProtected() {
        return Set.of(
                new AntPathRequestMatcher(AuthenticationController.LOGIN, HttpMethod.POST.name()),
                new AntPathRequestMatcher(AuthenticationController.LOGOUT, HttpMethod.POST.name()),
                new AntPathRequestMatcher(AuthenticationController.SIGN_UP, HttpMethod.POST.name()),
                new AntPathRequestMatcher(OtpAuthController.RECOVER, HttpMethod.POST.name())
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
