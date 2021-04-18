package org.example.pages;

import org.example.exceptions.NoSuchItemException;
import org.example.objects.Item;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private By itemPriceTitleInRow = By.className("basket-item-price-title");
    private By itemAmountFieldInRow = By.cssSelector("input.basket-item-amount-filed");
    private By amountFieldDescription = By.className("basket-item-amount-field-description");
    private By amountButtonMinusInRow = By.className("basket-item-amount-btn-minus");
    private By amountButtonPlusInRow = By.className("basket-item-amount-btn-plus");
    private By itemSumPriceInRow = By.cssSelector("span[id^='basket-item-sum-price']");
    private By deleteIconInRow = By.className("basket-item-block-actions");
    private By totalPrice = By.className("basket-coupon-block-total-price-current");
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
     * Returns true if item amount was changed by clicking plus button.
     * Returns false if maximum amount is already got
     */
    public boolean clickAmountButtonPlus(String itemName) throws NoSuchItemException {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(StaleElementReferenceException.class);

        Optional<WebElement> pickedItem = getCartItemRowsSet()
                .parallelStream().filter(e -> e.findElement(itemNameInRow).getText().equals(itemName))
                .findAny();

        if (pickedItem.isPresent()) {
            pickedItem.get().findElement(amountButtonPlusInRow).click();

            /*
             * TimeoutException in this context means that amount was not changed
             * i.e. maximum is got
             */
            try {
                /*
                 * Cart item row is deleted and inserted again during amount item changing
                 * So we have to wait this row presence before interacting with it again
                 */
                wait.until(ExpectedConditions.stalenessOf(pickedItem.get()));
                return true;
            } catch (TimeoutException e) {
                return false;
            }
        } else {
            throw new NoSuchItemException("No such item to click amount button plus");
        }
    }

    public void clickDeleteIcon(String itemName) throws NoSuchItemException {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(StaleElementReferenceException.class);

        List<WebElement> itemList = getCartItemRowsSet();

        Optional<WebElement> pickedItem = getCartItemRowsSet()
                .parallelStream().filter(e -> e.findElement(itemNameInRow).getText().equals(itemName))
                .findAny();

        if (pickedItem.isPresent()) {
            pickedItem.get().findElement(deleteIconInRow).click();

            /*
             * Cart item row is deleted as follows:
             * exactly the same item is added before current (get element and wait it invisibility)
             * current item is deleted (wait for staleness of this element)
             * there are some calculations in JS
             * new added item is deleted
             */
            wait.until(ExpectedConditions.stalenessOf(pickedItem.get()));

            List<WebElement> extraItemInList = getCartItemRowsSet();
            extraItemInList.removeAll(itemList);
            if (extraItemInList.size() == 1) {
                wait.until(ExpectedConditions.invisibilityOf(extraItemInList.get(0)));
            }
        } else {
            throw new NoSuchItemException("No such item to click delete icon");
        }
    }

    public BigDecimal getCartItemAmount(String itemName) throws NoSuchItemException {
        Optional<WebElement> pickedItem = getCartItemRowsSet()
                .parallelStream().filter(e -> e.findElement(itemNameInRow).getText().equals(itemName))
                .findAny();
        if (pickedItem.isPresent()) {
            return new BigDecimal(
                    pickedItem.get().findElement(itemAmountFieldInRow).getAttribute("value"));
        } else {
            throw new NoSuchItemException("No such item to get amount");
        }
    }

    /*
     * Returns list of items in full cart
     */
    public List<Item> getCartItemsInfo() {
        List<Item> cartItemsList = new ArrayList<>();

        for (WebElement cartItemWE : getCartItemRowsSet()) {
            String priceStr;
            String priceTitleStr;
            String sumPriceStr;
            Item cartItem = new Item();
            cartItem.setName(cartItemWE.findElement(itemNameInRow).getText());

            /* Price format on the page is 4.24 руб. So lets get only decimal price part */
            priceStr = cartItemWE.findElement(itemPriceInRow).getText();
            cartItem.setPrice(new BigDecimal(priceStr.split(" ")[0]));

            priceTitleStr = cartItemWE.findElement(itemPriceTitleInRow).getText();
            cartItem.setQuantityUnit(new BigDecimal(priceTitleStr.split(" ")[2]));
            cartItem.setQuantityMeasure(priceTitleStr.split(" ")[3]);

            cartItem.setAmount(new BigDecimal(
                    cartItemWE.findElement(itemAmountFieldInRow).getAttribute("value")));

            sumPriceStr = cartItemWE.findElement(itemSumPriceInRow).getText();
            cartItem.setSumPrice(new BigDecimal(
                    sumPriceStr.split(" р")[0].replace(" ", "")));
            cartItemsList.add(cartItem);
        }
        return cartItemsList;
    }

    public List<String> getCartItemsNames() {
        return getCartItemRowsSet()
                .parallelStream()
                .map(e -> e.findElement(itemNameInRow).getText()).collect(Collectors.toList());
    }

    public String getCartPageHeader() {
        return driver.findElement(cartPageHeader).getText();
    }

    public String getEmptyCartText() {
        return driver.findElement(emptyCartText).getText();
    }

    public BigDecimal getTotalPrice() {
        String totalPriceStr = driver.findElement(totalPrice).getText();
        return new BigDecimal(
                totalPriceStr.split(" р")[0].replace(" ", ""));
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
