package org.example.utils;

import org.openqa.selenium.WebDriver;
import java.net.URL;
import java.util.Set;

/**
 * Operates with browser windows (tabs)
 */
public class WindowManager {
    private WebDriver driver;
    private WebDriver.Navigation navigator;

    public WindowManager(WebDriver driver) {
        this.driver = driver;
        navigator = driver.navigate();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void goBack(){
        navigator.back();
    }

    public void goForward(){
        navigator.forward();
    }

    public void goTo(String url){
        navigator.to(url);
    }

    public void refresh(){
        navigator.refresh();
    }

    /**
     * Switches to certain window tab
     * @param nameOrHandle title of certain window or tab
     */
    public void switchToWindow(String nameOrHandle){
        driver.switchTo().window(nameOrHandle);
    }

    /**
     * Switches to certain window tab by URL
     * Switches to the last opened tab if there is no necessary tab among opened tabs
     * @param url URL of certain window or tab to switch
     */
    // TODO: Throw exception if there is no such opened tab and stay on the current tab
    public void switchToWindow(URL url) {

        /* Get handles of all opened tabs */
        Set<String> windowHandles = driver.getWindowHandles();

        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);

            /* Exit the loop if active tab's url equals to necessary tab URL */
            if (driver.getCurrentUrl().equals(url.toString())) {
                break;
            }
        }
    }
}
