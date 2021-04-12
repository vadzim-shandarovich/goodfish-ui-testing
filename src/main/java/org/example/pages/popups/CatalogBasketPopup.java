package org.example.pages.popups;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents Catalog Basket Popup which appears every time after adding item to cart
 */
public class CatalogBasketPopup {
    private WebElement popup;
    private By basketPopup =
            By.cssSelector("div[id^='CatalogSectionBasket'][style^='display: block']");
    private By titleBar = By.className("popup-window-titlebar-text");
    private By continueButton = By.cssSelector("div.popup-window-buttons :nth-child(2)");
    private By closeButton = By.cssSelector("div.popup-window-buttons span");

    public CatalogBasketPopup(WebDriver driver) {
        popup = driver.findElement(basketPopup);
    }

    public void clickCloseButton() {
        popup.findElement(closeButton).click();
    }

    public void clickContinueButton() {
        popup.findElement(continueButton).click();
    }

    public String getTitleBarText() {
        return popup.findElement(titleBar).getText();
    }
}
