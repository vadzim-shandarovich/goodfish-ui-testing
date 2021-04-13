package org.example.cart;

import org.example.base.BaseTests;
import org.example.objects.Item;
import org.example.pages.CartPage;
import org.testng.annotations.Test;

/**
 * Contains tests on cart items calculations
 */
public class CartCalculationsTests extends BaseTests {

    @Test(groups = { "Cart calculations tests" },
          description = "Validate initial cart item calculations (sumPrice, amount)")
    public void testCartItemCalculations() {
        CartPage cartPage = homePage.clickCartIcon();
        for (Item item : cartPage.getCartItemsInfo()) {
            System.out.println(item);
        }
    }
}
