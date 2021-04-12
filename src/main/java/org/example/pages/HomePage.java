package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents GoodFish home page with its interactions
 */
public class HomePage extends PageHeader {
    private By catalogBtn = By.cssSelector("div.catRubrcs a.gBtn");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage clickCatalogBtn() {
        driver.findElement(catalogBtn).click();
        return new CatalogPage(driver);
    }
}
