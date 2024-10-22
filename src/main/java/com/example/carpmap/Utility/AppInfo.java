package com.example.carpmap.Utility;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@Component
public class AppInfo {

    public String getAppVersion() {
        try {
            Manifest manifest = new Manifest(getClass().getResourceAsStream("/META-INF/MANIFEST.MF"));
            Attributes attributes = manifest.getMainAttributes();
            return attributes.getValue("Implementation-Version");
        } catch (IOException e) {
            return "Version not found";
        }
    }
}
