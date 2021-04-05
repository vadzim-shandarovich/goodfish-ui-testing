package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents catalog category page with its interactions
 */
public class CatalogCategoryPage extends PageHeader {
    private final By categoryPageHeader = By.tagName("h1");
    private final By itemCardsSet = By.cssSelector("div.product-item-container");
    private final By availableItemsCardsSet =
            By.xpath("//div[@class='product-item-container'][.//a[contains(@class,'basketPlus')]]");
    private final By showMoreButton = By.cssSelector("div.gfShowMoreBtn div.btn");

    public CatalogCategoryPage(WebDriver driver) {
        super(driver);
    }

    public void clickShowMoreButton() {
        driver.findElement(showMoreButton).click();
    }

    public List<WebElement> getAvailableItemsCardsSet() {
        return driver.findElements(availableItemsCardsSet);
    }

    public List<String> getAvailableItemsNames() {
        return getAvailableItemsCardsSet().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getCategoryPageHeader() {
        return driver.findElement(categoryPageHeader).getText();
    }

    public List<WebElement> getItemCardsSet() {
        return driver.findElements(itemCardsSet);
    }

    public int getItemCardsCount() {
        return getItemCardsSet().size();
    }

    public boolean isShowMoreButtonExist() {
        return !driver.findElements(showMoreButton).isEmpty();
    }

    /*
     * Basic page shows only 12 items maximum.
     * This method allows to show all category items on the page
     */
    public void showAllCategoryItems() {
        while (isShowMoreButtonExist()) {
            clickShowMoreButton();
        }
    }
}
