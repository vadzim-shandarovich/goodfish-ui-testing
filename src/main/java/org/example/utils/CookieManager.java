package org.example.utils;

import org.example.settings.ProjectURI;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import java.util.Set;

/**
 * Operates with cookies by current WebDriver
 */
public class CookieManager {
    private WebDriver.Options driverOptions;

    public CookieManager(WebDriver driver) {
        this.driverOptions = driver.manage();
    }

    /*
     * Add cookie by name and value for default domain and default path
     */
    public void addDefaultDomainCookie(String name, String value) {
        Cookie cookie = new Cookie.Builder(name, value)
                .domain(ProjectURI.BASE)
                .path(ProjectURI.DEFAULT_PATH)
                .build();
        driverOptions.addCookie(cookie);
    }

    public void deleteCookieNamed(String name) {
        driverOptions.deleteCookieNamed(name);
    }

    public void deleteAllCookies() {
        driverOptions.deleteAllCookies();
    }

    /*
     * Returns all cookies by current WebDriver URL
     */
    public Set<Cookie> getCookies() {
        return driverOptions.getCookies();
    }
}
