package com.example.carpmap.AppConfiguration;

import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.Impl.CarpUserService;
import jakarta.servlet.http.HttpServletResponse;
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
                        .requestMatchers("/js/**", "/lib/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/home", "/about", "/blog", "/contact",
                                "/fish-list-type/fishing-type", "/fish-list-type/**",
                                "/announced", "/donate", "/cookiePolicy", "/subscribe/send",
                                "/gallery", "/search", "/info").permitAll()
                        .requestMatchers("/reservoirs/reservoirsByType/reservoirs",
                                "/reservoirs/reservoirsByType/private_reservoir",
                                "/reservoirs/reservoirsByType/free_reservoir",
                                "/reservoirs/reservoirsByType/countVisitors",
                                "/reservoirs/{type}",
                                "/reservoirs/reservoirsByType/{type}",
                                "/searchFishName", "/searchReservoir").permitAll()
                        .requestMatchers("/robots.txt", "/sitemap.xml",
                                "/videoApp/**", "/imagesApp/**").permitAll()
                        .requestMatchers("/", "/error", "/error/**",
                                "/users/login", "/users/login-error").permitAll()
                        .requestMatchers("/fish/add/adding-fish")
                        .hasAnyRole(RoleType.MODERATOR.name())
                        .requestMatchers("/reservoirs/add/reservoirAdd",
                                "/reservoirs/reservoirsEdit/{id}",
                                "/reservoirs/delete/{id}",
                                "/fish-list-type/delete/{urlName}",
                                "/fish-list-type/add/adding-fish")
                        .hasAnyRole(RoleType.MODERATOR.name())
                        .requestMatchers("/profile/profiles")
                        .hasAnyRole(RoleType.ADMIN.name())
                        .requestMatchers("/reservoirs/gallery/{id}")
                        .hasAnyRole(RoleType.ADMIN.name())
                        .requestMatchers("/admin/ip/all", "/admin/ip/findByUser", "/admin/ip/lastDay",
                                "/admin/ip/thirtyDaysAgo", "/admin/ip/newForToday", "/admin/profiles",
                                "/admin/details/byId/{id}")
                        .hasAnyRole(RoleType.MODERATOR.name())
                        .anyRequest().authenticated()

        ).exceptionHandling(
                exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/error")) {
                                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                            } else {
                                System.out.println("Redirecting to login due to unauthorized access: " +
                                        request.getRequestURI() + " " + request.getRemoteAddr());
                                response.sendRedirect("/users/login");
                            }
                        })
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
