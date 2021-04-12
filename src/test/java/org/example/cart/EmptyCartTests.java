package org.example.cart;

import org.example.base.BaseTests;
import org.example.pages.CartPage;
import org.example.settings.ProjectURI;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * Contains empty cart tests
 */
public class EmptyCartTests extends BaseTests {

    @Test(groups = "Cart tests",
          description = "Validate cart URI")
    public void testCartURI() {
        homePage.clickCartIcon();
        assertEquals(getWindowManager().getCurrentURL(), ProjectURI.BASE + ProjectURI.CART_PATH);
    }

    @Test(groups = "Empty cart on first visit",
          description = "Validate cart items number and price on first visit")
    public void testEmptyCartIconOverlayAndPrice() {
        assertFalse(homePage.isCartItemsIconOverlayExist());
        assertFalse(homePage.isCartPriceTextExist());
    }

    @Test(groups = "Empty cart on first visit",
          description = "Validate empty cart appearance")
    public void testEmptyCartAppearance() {
        CartPage cartPage = homePage.clickCartIcon();
        assertEquals(cartPage.getCartPageHeader(), "Моя корзина");
        assertEquals(cartPage.getEmptyCartText(), "Ваша корзина пуста");
    }

    @Test(groups = "Empty cart on first visit",
          description = "Validate backToCatalog link works correctly")
    public void testBackToCatalogLink() {
        CartPage cartPage = homePage.clickCartIcon();
        cartPage.clickBackToCatalogLink();
        assertEquals(getWindowManager().getCurrentURL(), ProjectURI.BASE + ProjectURI.CATALOG_PATH);
    }

    @Test(groups = "Empty cart on first visit",
          description = "Validate cart linked icon works correctly")
    public void testCartLinkedIconClick() {
        CartPage cartPage = homePage.clickCartIcon();
        cartPage.clickCartLinkedImage();
        assertEquals(getWindowManager().getCurrentURL(), ProjectURI.BASE + ProjectURI.CATALOG_PATH);
    }
}
