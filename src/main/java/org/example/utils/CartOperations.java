package org.example.utils;

import org.example.exceptions.NoSuchCategoryException;
import org.example.exceptions.NoSuchItemException;
import org.example.objects.Item;
import org.example.pages.CatalogCategoryPage;
import org.example.pages.CatalogPage;
import org.example.pages.popups.CatalogBasketPopup;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Operates with cart e.g. adds random items to cart
 */
public class CartOperations {
    private Logger log;
    private WindowManager windowManager;

    public CartOperations(WebDriver driver) {
        windowManager = new WindowManager(driver);

        /* Instantiate logger to log necessary info */
        log = Logger.getLogger("");
    }

    /*
     * Adds random items to cart starting from catalog page
     * and goes to catalog page back
     */
    public List<Item> addRandomItemsToCart(CatalogPage catalogPage, int itemsCount) {
        int pickedItemsCount = 0;
        String randomCategoryName;
        String randomItemName = "";
        List<String> categoriesNames;
        List<String> availableItemsNames;
        Item pickedItem;
        List<Item> itemsList = new ArrayList<>();

        categoriesNames = catalogPage.getCategoriesNames();

        /* Try to find category with available for purchase items */
        do {

            /* Choose random category to click */
            randomCategoryName = categoriesNames.get(
                    ThreadLocalRandom.current().nextInt(categoriesNames.size()));
            try {
                CatalogCategoryPage categoryPage = catalogPage.clickCategoryByName(randomCategoryName);
                categoryPage.showAllCategoryItems();
                availableItemsNames = categoryPage.getAvailableItemsNames();

                /* If there is no available for purchase items pick the new category */
                if (!availableItemsNames.isEmpty()) {
                    String basketPopupTitle;
                    CatalogBasketPopup catalogBasketPopup;

                    /* Choose random item for adding to cart */
                    randomItemName = availableItemsNames.get(
                            ThreadLocalRandom.current().nextInt(availableItemsNames.size()));
                    pickedItem = categoryPage.clickAddAvailableItemToCart(randomItemName);

                    catalogBasketPopup = categoryPage.getCatalogBasketPopup();
                    basketPopupTitle = catalogBasketPopup.getTitleBarText();

                    /* Product item could be available for purchase but do not have necessary amount */
                    if (basketPopupTitle.equals("Товар добавлен в корзину")) {
                        catalogBasketPopup.clickContinueButton();
                        log.log(Level.INFO, "{0} was added to cart", pickedItem);
                        itemsList.add(pickedItem);
                        pickedItemsCount++;
                    } else {
                        catalogBasketPopup.clickCloseButton();
                    }
                }
                windowManager.goBack();
            } catch (NoSuchItemException e) {
                log.severe(e.getMessage() + ": " + randomItemName);
            } catch (NoSuchCategoryException e) {
                log.severe(e.getMessage() + ": " + randomCategoryName);
            }
        } while (pickedItemsCount < itemsCount);
        return itemsList;
    }

    /*
     * Adds all available items to cart from category
     * starting from catalog page and goes to catalog page back
     */
    public List<Item> addAllCategoryItemsToCart(CatalogPage catalogPage, String categoryName) {
        String catchingExcItemName = "";
        List<String> availableItemsNames;
        Item pickedItem;
        List<Item> itemsList = new ArrayList<>();

        try {
            CatalogCategoryPage categoryPage = catalogPage.clickCategoryByName(categoryName);
            categoryPage.showAllCategoryItems();
            availableItemsNames = categoryPage.getAvailableItemsNames();

            /* Add all available items to cart */
            for (String itemName : availableItemsNames) {
                String basketPopupTitle;
                CatalogBasketPopup catalogBasketPopup;

                catchingExcItemName = itemName;
                pickedItem = categoryPage.clickAddAvailableItemToCart(itemName);
                catalogBasketPopup = categoryPage.getCatalogBasketPopup();
                basketPopupTitle = catalogBasketPopup.getTitleBarText();

                /* Product item could be available for purchase but do not have necessary amount */
                if (basketPopupTitle.equals("Товар добавлен в корзину")) {
                    catalogBasketPopup.clickContinueButton();
                    log.log(Level.INFO, "{0} was added to cart", pickedItem);
                    itemsList.add(pickedItem);
                } else {
                    catalogBasketPopup.clickCloseButton();
                }
            }
            windowManager.goBack();
        } catch (NoSuchItemException e) {
            log.severe(e.getMessage() + ": " + catchingExcItemName + " in " + categoryName);
        } catch (NoSuchCategoryException e) {
            log.severe(e.getMessage() + ": " + categoryName);
        }
        return itemsList;
    }
}
