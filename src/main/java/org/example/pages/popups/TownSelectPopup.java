package org.example.pages.popups;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents Town Select Popup which could appear on any page
 */
public class TownSelectPopup {
    private WebElement popup;
    private By townPopup = By.id("townSelectPopup");
    private By bigTownsMinskButton = By.cssSelector("div.bigTowns label[for='topLoc47']");

    public TownSelectPopup(WebDriver driver) {
        popup = driver.findElement(townPopup);
    }

    public void clickBigTownsMinskButton() {
        popup.findElement(bigTownsMinskButton).click();
    }
}
