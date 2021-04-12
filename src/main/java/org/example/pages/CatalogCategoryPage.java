package org.example.pages;

import org.example.exceptions.NoSuchItemException;
import org.example.objects.Item;
import org.example.pages.popups.CatalogBasketPopup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents catalog category page with its interactions
 */
public class CatalogCategoryPage extends PageHeader {
    private By categoryPageHeader = By.tagName("h1");
    private By itemCardsSet = By.cssSelector("div.product-item-container");
    private By availableItemsCardsSet = By.xpath(
            "//div[contains(@class,'product-item-container')][.//a[contains(@class,'basketPlus')]]");
    private By notAvailableItemsCardsSet = By.xpath(
            "//div[contains(@class,'product-item-container')][.//a[contains(@class,'noAvail')]]");
    private By itemNameLinkInCard = By.className("item-name");
    private By itemPriceOldTextInCard = By.cssSelector("div.product-item-price-old");
    private By itemBasketButtonInCard = By.cssSelector("a.basketPlus");
    private By notAvailableBasketButtonInCard = By.cssSelector("a.noAvail");
    private By itemAmountFieldInCard = By.tagName("input");
    private By showMoreButton = By.cssSelector("div.gfShowMoreBtn div.btn");

    public CatalogCategoryPage(WebDriver driver) {
        super(driver);
    }

    /*
     * Clicks add available item to cart by itemName and returns item description
     */
    public Item clickAddAvailableItemToCart(String itemName) throws NoSuchItemException {
        Item pickedItem = new Item();

        /* Look for required item card by item name */
        Optional<WebElement> itemCardWE = getAvailableItemsCardsSet()
                .parallelStream()
                .filter(e -> e.findElement(itemNameLinkInCard).getText().equals(itemName)).findAny();

        /* Create item if such item exists */
        if (itemCardWE.isPresent()) {
            String priceStr;

            pickedItem.setName(itemCardWE.get().findElement(itemNameLinkInCard).getText());

            /* Price format on the page is 4.24 руб. So lets get only decimal price part */
            priceStr =  itemCardWE.get()
                    .findElement(itemPriceOldTextInCard).getAttribute("textContent");
            pickedItem.setPrice(new BigDecimal(priceStr.strip().split(" ")[0]));
            pickedItem.setAmount(new BigDecimal(
                    itemCardWE.get().findElement(itemAmountFieldInCard).getAttribute("value")));

            itemCardWE.get().findElement(itemBasketButtonInCard).click();
            return pickedItem;
        } else {
            throw new NoSuchItemException("No such item to click");
        }
    }

    /*
     * Clicks add not available item to cart by itemName
     */
    public void clickAddNotAvailableItemToCart(String itemName) throws NoSuchItemException {
        Item pickedItem = new Item();

        /* Look for required item card by item name */
        Optional<WebElement> itemCardWE = getNotAvailableItemsCardsSet()
                .parallelStream()
                .filter(e -> e.findElement(itemNameLinkInCard).getText().equals(itemName)).findAny();

        /* Clicks addItem if item is on the page */
        if (itemCardWE.isPresent()) {
            itemCardWE.get().findElement(notAvailableBasketButtonInCard).click();
        } else {
            throw new NoSuchItemException("No such item to click");
        }
    }

    public void clickShowMoreButton() {
        driver.findElement(showMoreButton).click();
    }

    /*
     * Returns List of available to order items names
     */
    public List<String> getAvailableItemsNames() {
        return getAvailableItemsCardsSet()
                .parallelStream().map(e -> e.findElement(itemNameLinkInCard).getText())
                .collect(Collectors.toList());
    }

    /*
     * Returns List of not available to order items names
     */
    public List<String> getNotAvailableItemsNames() {
        return getNotAvailableItemsCardsSet()
                .parallelStream().map(e -> e.findElement(itemNameLinkInCard).getText())
                .collect(Collectors.toList());
    }

    public CatalogBasketPopup getCatalogBasketPopup() {
        return new CatalogBasketPopup(driver);
    }

    public String getCategoryPageHeader() {
        return driver.findElement(categoryPageHeader).getText();
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

    private List<WebElement> getAvailableItemsCardsSet() {
        return driver.findElements(availableItemsCardsSet);
    }

    private List<WebElement> getItemCardsSet() {
        return driver.findElements(itemCardsSet);
    }

    private List<WebElement> getNotAvailableItemsCardsSet() {
        return driver.findElements(notAvailableItemsCardsSet);
    }
}
