package com.nullteam.ragpicker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${config.url}")
    private String url;

    @Value("${config.auto-update-menu}")
    private boolean autoUpdateMenu;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAutoUpdateMenu() {
        return autoUpdateMenu;
    }

    public void setAutoUpdateMenu(boolean autoUpdateMenu) {
        this.autoUpdateMenu = autoUpdateMenu;
    }
}
