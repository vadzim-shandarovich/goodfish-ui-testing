package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import java.util.logging.Logger;

/**
 * Operates with occurred page events by current WebDriver
 */
public class PageEventListener implements WebDriverEventListener {

    /**
     * Sends to console every pageElement that was clicking during the test.
     * Format console string: Element tagname.attribute(value) was clicked
     * Example console string: Element button.id(start) was clicked
     * @param element clicked WebElement
     * @param driver WebDriver
     */
    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        final String ID_ATTR = "id";
        final String NAME_ATTR = "name";
        final String STYLE_ATTR = "style";

        /* Create console string "Element tagname." */
        String consoleString = "Element " + element.getTagName() + ".";

        /* If element has text, add "text(textSubString)" to console string */
        if (!element.getText().isEmpty()) {
            String elementTextSubString;
            try {
                elementTextSubString = element.getText().substring(0, 20);
            } catch (IndexOutOfBoundsException ex) {

                /*
                 * If element text is less then 20 symbols in length
                 * elementTextSubString will be the whole element text
                 */
                elementTextSubString = element.getText();
            }
            consoleString += "text(" + elementTextSubString + ")";

            /* If element has id attribute, add "id(idValue)" to console string */
        } else if (!element.getAttribute(ID_ATTR).isEmpty()) {
            consoleString += "id(" + element.getAttribute(ID_ATTR) + ")";

            /* If element has name attribute, add "name(nameValue)" to console string */
        } else if (!element.getAttribute(NAME_ATTR).isEmpty()) {
            consoleString += "name(" + element.getAttribute(NAME_ATTR) + ")";

            /* If element has style attribute, add "style(styleSubValue)" to console string */
        } else if (!element.getAttribute(STYLE_ATTR).isEmpty()) {
            String elementStyleSubString;

            try {
                elementStyleSubString = element.getAttribute(STYLE_ATTR).substring(0, 20);
            } catch (IndexOutOfBoundsException ex) {

                /*
                 * If element style atrribute is less then 20 symbols in length
                 * elementStyle will be the whole style name
                 */
                elementStyleSubString = element.getAttribute(STYLE_ATTR);
            }
            consoleString += "style(" + elementStyleSubString + ")";
        }
        consoleString += " was clicked on page " + driver.getCurrentUrl();

        Logger log = Logger.getLogger("");
        log.info(consoleString);
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        // Do not used now
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        // Do not used now
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        // Do not used now
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {
        // Do not used now
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
        // Do not used now
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        // Do not used now
    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
        // Do not used now
    }
}
