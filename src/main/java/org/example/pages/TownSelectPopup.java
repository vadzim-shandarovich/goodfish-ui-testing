package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents Town Select Popup which could appear on any page
 */
public class TownSelectPopup {
    private WebDriver driver;
    private final WebElement popup;
    private final By townSelectPopup = By.id("townSelectPopup");
    private final By bigTownsMinskButton = By.cssSelector("div.bigTowns label[for='topLoc47']");

    public TownSelectPopup(WebDriver driver) {
        this.driver = driver;
        popup = driver.findElement(townSelectPopup);
    }

    public void clickBigTownsMinskButton() {
        popup.findElement(bigTownsMinskButton).click();
    }
}
