package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents GoodFish cart page with its interactions
 */
public class CartPage extends PageHeader {
    private final By cartPageHeader = By.tagName("h1");
    private final By emptyCartText = By.cssSelector("div.bx-sbb-empty-cart-container div.item-title");
    private final By backToCatalogLink = By.linkText("Вернуться в каталог");
    private final By cartLinkedImage = By.cssSelector("div.bx-sbb-empty-cart-container svg");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage clickBackToCatalogLink() {
        driver.findElement(backToCatalogLink).click();
        return new CatalogPage(driver);
    }

    public CatalogPage clickCartLinkedImage() {
        driver.findElement(cartLinkedImage).click();
        return new CatalogPage(driver);
    }

    public String getCartPageHeader() {
        return driver.findElement(cartPageHeader).getText();
    }

    public String getEmptyCartText() {
        return driver.findElement(emptyCartText).getText();
    }
}
