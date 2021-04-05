package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.math.BigDecimal;

/**
 * Represents GoodFish page header with its interactions
 */
public class PageHeader {
    protected WebDriver driver;
    private final By cartIcon = By.id("bx_basket0xdXJz");
    private final By cartItemsNumIconOverlay = By.cssSelector("a#bx_basket0xdXJz div.total-num");
    private final By cartPriceText = By.cssSelector("a#bx_basket0xdXJz div.bx-basket-block-total");

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
        return !driver.findElements(cartItemsNumIconOverlay).isEmpty();
    }

    public boolean isCartPriceTextExist() {
        return !driver.findElements(cartPriceText).isEmpty();
    }
}
