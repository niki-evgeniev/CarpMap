package com.example.carpmap.Utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class IpExtractor {

    public String extractIp(HttpServletRequest request) {
        String ip = request.getHeader("CF-Connecting-IP");

        if (ip == null || ip.isBlank()) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }
}
