package com.example.carpmap.AppConfiguration;

import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.Impl.CarpUserService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final String rememberMeKey;

    public SecurityConfiguration(@Value("${carpmap.remember.me.key}")
                                 String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(
                authorizeRequest -> authorizeRequest
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/js/**", "/lib/**").permitAll()
                        .requestMatchers("/", "/users/login", "/users/profile",
                                "/users/login-error").permitAll()
                        .requestMatchers("/reservoirs/reservoirsByType/ALL", "/reservoirs/{id}",
                                "/reservoirs/reservoirsByType/{type}").permitAll()
                        .requestMatchers("/donate", "/cookiePolicy").permitAll()
                        .requestMatchers("/home", "/robots.txt", "/sitemap.xml").permitAll()
                        .requestMatchers("/about", "/blog", "/contact").permitAll()
                        .requestMatchers("/subscribe/send").permitAll()
                        .requestMatchers("/gallery", "/search").permitAll()
                        .requestMatchers("/reservoirs/reservoirsEdit/{id}")
                        .hasAnyRole(RoleType.MODERATOR.name())
                        .requestMatchers("/reservoirs/add/reservoirAdd")
                        .hasAnyRole(RoleType.MODERATOR.name())
                        .requestMatchers("/reservoirs/delete/{id}", "/profile/profiles")
                        .hasAnyRole(RoleType.ADMIN.name())
                        .requestMatchers("/reservoirs/gallery/{id}")
                        .hasAnyRole(RoleType.ADMIN.name())
                        .anyRequest().authenticated()

        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/users/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
                            .failureForwardUrl("/users/login-error");
                }
        ).logout(
                logout -> {
                    logout.logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);

                }
        ).rememberMe(
                rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("remember-me")
                            .rememberMeCookieName("remember-me");
                }
        ).csrf(
                csfr -> {
//                    csfr.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//                    csfr.ignoringRequestMatchers(new AntPathRequestMatcher("/"));
                }
        ).portMapper(
                httpSecurityHTTPS -> {
                    httpSecurityHTTPS
                            .http(80).mapsTo(443)
                            .http(8080).mapsTo(443);
                }
        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CarpUserService(userRepository);
    }
}
