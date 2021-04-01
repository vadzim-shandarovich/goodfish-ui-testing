package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.math.BigDecimal;

/**
 * Represents GoodFish page header with its interactions
 */
public class PageHeader {
    protected WebDriver driver;
    private By cartIcon = By.id("bx_basket0xdXJz");
    private By cartItemsNumIconOverlay = By.cssSelector("#bx_basket0xdXJz .total-num");
    private By cartPriceText = By.cssSelector("#bx_basket0xdXJz .bx-basket-block-total");

    public PageHeader(WebDriver driver) {
        this.driver = driver;
    }

    public CartPage clickCartIcon() {
        driver.findElement(cartIcon).click();
        return new CartPage(driver);
    }

    public int getCartItemsNumber() {
        if (driver.findElements(cartItemsNumIconOverlay).isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(driver.findElement(cartItemsNumIconOverlay).getText());
        }
    }

    public BigDecimal getCartPrice() {
        if (driver.findElements(cartPriceText).isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(driver.findElement(cartPriceText).getText());
        }
    }

    public boolean isCartItemsIconOverlayExist() {
        return driver.findElements(cartItemsNumIconOverlay).isEmpty();
    }

    public boolean isCartPriceTextExist() {
        return driver.findElements(cartPriceText).isEmpty();
    }
}
