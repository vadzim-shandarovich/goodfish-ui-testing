package org.example.pages;

import org.example.exceptions.NoSuchCategoryException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents catalog page with its interactions
 */
public class CatalogPage extends PageHeader {
    private By catalogPageHeader = By.tagName("h1");
    private By backToMainPageLink = By.cssSelector("div#navigation a");
    private By categoriesLinksSet = By.cssSelector("div.bx_catalog_tile a.item-name");
    private By categorySubcategoryAreasSet = By.cssSelector("div.bx_catalog_tile div.item-wrap");
    private By categoryLinkInArea = By.cssSelector("a.item-name");
    private By categoryItemsCountInArea = By.tagName("span");
    private By subCategoryLinkSetInArea = By.cssSelector("div.item-links a");

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public void clickBackToMainPageLink() {
        driver.findElement(backToMainPageLink).click();
    }

    public CatalogCategoryPage clickCategoryByName(String categoryName) throws NoSuchCategoryException {
        Optional<WebElement> categoryWE = driver.findElements(categoriesLinksSet)
                .parallelStream().filter(e -> e.getText().equals(categoryName)).findAny();
        if (categoryWE.isPresent()) {
            categoryWE.get().click();
            return new CatalogCategoryPage(driver);
        } else {
            throw new NoSuchCategoryException("No such category to click");
        }
    }

    public String getCatalogPageHeader() {
        return driver.findElement(catalogPageHeader).getText();
    }

    public List<String> getCategoriesNames() {
        return driver.findElements(categoriesLinksSet)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    /*
     * Returns map of all catalog categories with subcategories
     */
    public Map<String, Set<String>> getCategorySubcategoryMap() {
        Map<String, Set<String>> categorySubcategoryMap = new HashMap<>();

        /* Loop through all category areas to create categorySubcategoryMap */
        for (WebElement categoryArea : getCategorySubcategoryAreas()) {
            String categoryName;
            Set<String> subcategoriesNames;

            categoryName = categoryArea.findElement(categoryLinkInArea).getText();
            subcategoriesNames = categoryArea.findElements(subCategoryLinkSetInArea)
                    .stream().map(WebElement::getText).collect(Collectors.toSet());

            /* Add category with subcategories to map */
            categorySubcategoryMap.put(categoryName, subcategoriesNames);
        }
        return categorySubcategoryMap;
    }

    /*
     * Returns category existing items count by category name
     */
    public int getCategoryItemsCount(String categoryName) throws NoSuchCategoryException {
        Integer itemsCount = null;

        /* Loop through all category areas to get certain category existing items count */
        for (WebElement categoryArea : getCategorySubcategoryAreas()) {
            if (categoryArea.findElement(categoryLinkInArea).getText().equals(categoryName)) {
                String itemsCountString = categoryArea.findElement(categoryItemsCountInArea).getText();

                /* Items count view on the page: (9). So firstly delete parentheses */
                itemsCountString = itemsCountString.replaceAll("[()]", "");
                itemsCount = Integer.parseInt(itemsCountString);
            }
        }

        /* If itemsCount variable was not initialized above then there is no such category on page */
        if (itemsCount == null) {
            throw new NoSuchCategoryException("No such category to get items count");
        } else {
            return itemsCount;
        }
    }

    private List<WebElement> getCategorySubcategoryAreas() {
        return driver.findElements(categorySubcategoryAreasSet);
    }
}
