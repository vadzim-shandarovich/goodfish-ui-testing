package org.example.cart;

import org.example.base.BaseTests;
import org.example.exceptions.NoSuchCategoryException;
import org.example.exceptions.NoSuchItemException;
import org.example.objects.Item;
import org.example.pages.CartPage;
import org.example.pages.CatalogCategoryPage;
import org.example.pages.CatalogPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Contains tests on adding items to cart
 */
public class CartAddingTests extends BaseTests {

    @Test(groups = { "Adding to cart tests", "Smoke" },
          description = "Validate adding to cart random category item (items count, name, price...)")
    public void testAddingRandomItem() {
        final int ITEMS_COUNT = 1;
        Item categoryItem;
        List<Item> cartItems;
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();
        categoryItem = cartOperations.addRandomItemsToCart(catalogPage, ITEMS_COUNT).get(0);
        CartPage cartPage = catalogPage.clickCartIcon();
        cartItems = cartPage.getCartItemsInfo();

        softAssert.assertEquals(cartItems.size(), ITEMS_COUNT, "Wrong items count");
        softAssert.assertEquals(cartItems.get(0), categoryItem,
                "Cart item description does not correspond to its category description");

        //log.info(cartItems.get(0).toString() + " was found in cart");
        softAssert.assertAll();
    }

    @Test(groups = { "Adding to cart tests" },
          description = "Validate adding to cart 10 random items (items count, order, name, price...)",
          dependsOnMethods = { "testAddingRandomItem" })
    public void testAdding10RandomItems() {
        final int ITEMS_COUNT = 10;
        List<Item> categoryItems;
        List<Item> cartItems;
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();
        categoryItems = cartOperations.addRandomItemsToCart(catalogPage, ITEMS_COUNT);
        CartPage cartPage = catalogPage.clickCartIcon();
        cartItems = cartPage.getCartItemsInfo();

        softAssert.assertEquals(cartItems.size(), ITEMS_COUNT, "Wrong items count");
        softAssert.assertEquals(cartItems, categoryItems,
                "Cart items do not correspond to picked category items");
        softAssert.assertAll();
    }

    @Test(groups = { "Adding to cart tests" },
          description = "Validate adding to cart 30 random items (items count, order, name, price...)",
          dependsOnMethods = { "testAddingRandomItem" })
    public void testAdding30RandomItems() {
        final int ITEMS_COUNT = 30;
        List<Item> categoryItems;
        List<Item> cartItems;
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();
        categoryItems = cartOperations.addRandomItemsToCart(catalogPage, ITEMS_COUNT);
        CartPage cartPage = catalogPage.clickCartIcon();
        cartItems = cartPage.getCartItemsInfo();

        softAssert.assertEquals(cartItems.size(), ITEMS_COUNT, "Wrong items count");
        softAssert.assertEquals(cartItems, categoryItems,
                "Cart items do not correspond to picked category items");
        softAssert.assertAll();
    }

    @Test(groups = { "Adding to cart tests" },
          description = "Validate adding to cart all available items from random category")
    public void testAddingAllItemsInCategory() {
        String randomCategoryName;
        List<Item> categoryItems;
        List<Item> cartItems;
        List<String> categoriesNames;
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();

        /* Choose random category to add its all available items to cart */
        categoriesNames = catalogPage.getCategoriesNames();
        do {
            randomCategoryName = categoriesNames.get(
                    ThreadLocalRandom.current().nextInt(categoriesNames.size()));

            categoryItems = cartOperations.addAllCategoryItemsToCart(catalogPage, randomCategoryName);
        } while (categoryItems.isEmpty());

        CartPage cartPage = catalogPage.clickCartIcon();

        /* Scroll to the bottom of the page to get all added items */
        cartPage.scrollToLastItem();
        cartItems = cartPage.getCartItemsInfo();

        softAssert.assertEquals(cartItems.size(), categoryItems.size(), "Wrong items count");
        softAssert.assertEquals(cartItems, categoryItems,
                "Cart items do not correspond to picked category items");
        softAssert.assertAll();
    }

    @Test(groups = { "Adding to cart tests" },
          description = "Validate adding to cart all available items",
          dependsOnMethods = { "testAddingAllItemsInCategory" })
    public void testAddingAllItems() {
        List<String> categoriesNames;
        List<Item> cartItems;
        List<Item> allItems = new ArrayList<>();
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();
        categoriesNames = catalogPage.getCategoriesNames();

        /* Loop through categories to add its all available items to cart */
        for (String categoryName : categoriesNames) {
            allItems.addAll(cartOperations.addAllCategoryItemsToCart(catalogPage, categoryName));
        }
        CartPage cartPage = catalogPage.clickCartIcon();

        /* Scroll to the bottom of the page to get all added items */
        cartPage.scrollToLastItem();
        cartItems = cartPage.getCartItemsInfo();

        softAssert.assertEquals(cartItems.size(), allItems.size(), "Wrong items count");
        softAssert.assertEquals(cartItems, allItems,
                "Cart items do not correspond to picked category items");
        softAssert.assertAll();
    }

    @Test(groups = { "Adding to cart tests", "Negative" },
          description = "Validate adding to cart out of stock item (shouldn't be added)")
    public void testAddingOutOfStockItem() {
        boolean isItemPicked = false;
        String randomCategoryName;
        String randomItemName = "";
        List<String> categoriesNames;
        List<Item> cartItems;
        List<String> notAvailableItemsNames;

        CatalogPage catalogPage = homePage.clickCatalogBtn();
        categoriesNames = catalogPage.getCategoriesNames();

        /* Try to find category with not available for purchase items */
        do {

            /* Choose random category to click */
            randomCategoryName = categoriesNames.get(
                    ThreadLocalRandom.current().nextInt(categoriesNames.size()));
            try {
                CatalogCategoryPage categoryPage = catalogPage.clickCategoryByName(randomCategoryName);
                categoryPage.showAllCategoryItems();
                notAvailableItemsNames = categoryPage.getNotAvailableItemsNames();

                /* If there is no not available for purchase items pick the new category */
                if (!notAvailableItemsNames.isEmpty()) {

                    /* Choose random not available item to click add to cart */
                    randomItemName = notAvailableItemsNames.get(
                            ThreadLocalRandom.current().nextInt(notAvailableItemsNames.size()));
                    categoryPage.clickAddNotAvailableItemToCart(randomItemName);
                    isItemPicked = true;
                }
                getWindowManager().goBack();
            } catch (NoSuchItemException e) {
                fail(e.getMessage() + ": " + randomItemName);
            } catch (NoSuchCategoryException e) {
                fail(e.getMessage() + ": " + randomCategoryName);
            }
        } while (!isItemPicked);
        CartPage cartPage = catalogPage.clickCartIcon();
        cartItems = cartPage.getCartItemsInfo();

        assertEquals(cartItems.size(), 0, "Wrong items count");
    }
}
