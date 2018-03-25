package com.nullteam.ragpicker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: Config.java</p>
 * <p>Package: com.nullteam.ragpicker.config</p>
 * <p>Description: </p>
 * <p>Copyright: 2018 the original author or authors.</p>

 * @date 01/27/18
 * @author WangJie <i@i8e.net>
 */
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
