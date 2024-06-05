package com.example.carpmap.AppConfiguration;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class CloudflareRealIpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String realIp = request.getHeader("CF-Connecting-IP");
        if (realIp != null) {
            request = new HttpServletRequestWrapper(request) {
                @Override
                public String getRemoteAddr() {
                    return realIp;

                }
            };
        }
        filterChain.doFilter(request, response);
    }
}
