package org.example.pages;

import org.example.objects.Item;
import org.openqa.selenium.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents GoodFish cart page with its interactions
 */
public class CartPage extends PageHeader {
    private By cartPageHeader = By.tagName("h1");

    /* Empty cart elements */
    private By emptyCartText = By.cssSelector("div.bx-sbb-empty-cart-container div.item-title");
    private By backToCatalogLink = By.linkText("Вернуться в каталог");
    private By cartLinkedImage = By.cssSelector("div.bx-sbb-empty-cart-container svg");

    /* Full cart elements */
    private By cartItemRowsSet = By.className("basket-items-list-item-container");
    private By itemNameInRow = By.cssSelector("span[data-entity='basket-item-name']");
    private By itemPriceInRow = By.cssSelector("span[id^='basket-item-price']");
    private By itemAmountFieldInRow = By.cssSelector("input.basket-item-amount-filed");
    private By checkoutButton = By.cssSelector("button.basket-btn-checkout");

    /* Last visible item row before 30 more rows download to page (in case more 30 items) */
    private By lastSubSetItemsRow = By.cssSelector("tr:last-child");

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

    /*
     * Returns list of items in full cart
     */
    public List<Item> getCartItemsInfo() {
        List<Item> cartItemsList = new ArrayList<>();

        for (WebElement cartItemWE : getCartItemRowsSet()) {
            String priceStr;
            Item cartItem = new Item();
            cartItem.setName(cartItemWE.findElement(itemNameInRow).getText());

            /* Price format on the page is 4.24 руб. So lets get only decimal price part */
            priceStr = cartItemWE.findElement(itemPriceInRow).getText();
            cartItem.setPrice(new BigDecimal(priceStr.split(" ")[0]));

            cartItem.setAmount(new BigDecimal(
                    cartItemWE.findElement(itemAmountFieldInRow).getAttribute("value")));
            cartItemsList.add(cartItem);
        }
        return cartItemsList;
    }

    public String getCartPageHeader() {
        return driver.findElement(cartPageHeader).getText();
    }

    public String getEmptyCartText() {
        return driver.findElement(emptyCartText).getText();
    }

    /*
     * Cart items are downloaded asynchronous. You have to scroll to the bottom of the page
     * to see all added items
     */
    public void scrollToLastItem() {
        String scrollToLastItemScript = "arguments[0].scrollIntoView(true);";

        /* This js function returns list of items which have not be displayed yet */
        String getItemsToLoadScript = "return BX.Sale.BasketComponent.getItemsAfter();";

        /* List of items id */
        List<Object> listID;

        /* If all list items are on the page then listID is empty */
        do {
            jsExecutor.executeScript(scrollToLastItemScript, driver.findElement(lastSubSetItemsRow));
            listID = (List<Object>) jsExecutor.executeScript(getItemsToLoadScript);
        } while (!listID.isEmpty());
    }

    private List<WebElement> getCartItemRowsSet() {
        return driver.findElements(cartItemRowsSet);
    }
}
