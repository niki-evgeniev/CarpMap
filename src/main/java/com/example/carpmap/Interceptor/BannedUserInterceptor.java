package com.example.carpmap.Interceptor;


import com.example.carpmap.Service.BannedUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Locale;
import java.util.Map;

@Component
public class BannedUserInterceptor implements HandlerInterceptor {

    private final ThymeleafViewResolver thymeleafViewResolver;
    private final BannedUserService bannedUserService;

    public BannedUserInterceptor(ThymeleafViewResolver thymeleafViewResolver,
                                 BannedUserService bannedUserService) {
        this.thymeleafViewResolver = thymeleafViewResolver;
        this.bannedUserService = bannedUserService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String cloudflareIp = request.getRemoteAddr();

        boolean isBanned = bannedUserService.checkIfIpAddressIsBanned(cloudflareIp);

        if (isBanned) {
            View view = thymeleafViewResolver.resolveViewName("bannedUser", Locale.getDefault());
            if (view != null) {
                view.render(Map.of(), request, response);
            }
            return false;
        }
        return true;
    }
}
