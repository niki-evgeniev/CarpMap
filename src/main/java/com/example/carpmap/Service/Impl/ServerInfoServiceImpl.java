package com.example.carpmap.Service.Impl;

import com.example.carpmap.Service.ServerInfoService;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;


@Service
public class ServerInfoServiceImpl implements ServerInfoService {
    @Override
    public String getUptime() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptimeMachine = runtimeMXBean.getUptime();
        String formatedUptime = formatUptime(uptimeMachine);
        return formatedUptime;
    }

    private String formatUptime(long uptimeMachine) {
        long allSeconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMachine);
        long days = allSeconds / 86400;
        long hours = (allSeconds % 86400) / 3600;
        long minutes = (allSeconds % 3600) / 60;
        long seconds = allSeconds % 60;
        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
