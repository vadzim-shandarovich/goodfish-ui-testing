package org.example.cart;

import org.example.base.BaseTests;
import org.example.exceptions.NoSuchItemException;
import org.example.objects.Item;
import org.example.pages.CartPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Contains tests on cart items calculations
 */
public class CartCalculationsTests extends BaseTests {

    /*
     * Calculates and makes assertions on items quantity, sumPrice, totalPrice
     */
    public SoftAssert makeCartItemCalculations(CartPage cartPage, SoftAssert softAssert) {
        final BigDecimal EXP_DIVISION_REMAINDER = BigDecimal.ZERO;
        BigDecimal calculatedTotalPrice = BigDecimal.ZERO;

        /* Loop through all cart items and check correctness sumPrice and amount calculations */
        for (Item item : cartPage.getCartItemsInfo()) {
            BigDecimal quantityUnit = item.getQuantityUnit();
            BigDecimal amount = item.getAmount();
            BigDecimal price = item.getPrice();
            BigDecimal sumPrice = item.getSumPrice();
            BigDecimal divisionRemainder;
            BigDecimal quantity; // means how many quantityUnits are in amount

            divisionRemainder = amount.divideAndRemainder(quantityUnit)[1];

            /* Assert that amount is divided without remainder by quantity unit */
            softAssert.assertEquals(divisionRemainder.compareTo(EXP_DIVISION_REMAINDER),
                    0,
                    "Item amount is divided with remainder by its quantity unit in: "
                            + item.getName());

            quantity = amount.divide(quantityUnit);
            softAssert.assertEquals(price.multiply(quantity).compareTo(sumPrice), 0,
                    "SumPrice is not equal quantity * price in: " + item.getName());
            calculatedTotalPrice = calculatedTotalPrice.add(sumPrice);
        }
        softAssert.assertEquals(cartPage.getTotalPrice().compareTo(calculatedTotalPrice), 0,
                "Total price is not equal calculatedTotalPrice");
        return softAssert;
    }

    @Test(groups = { "Cart calculations tests" },
          description = "Validate initial cart item calculations (sumPrice, amount, totalPrice)")
    public void testCartItemCalculations() {
        CartPage cartPage = homePage.clickCartIcon();
        SoftAssert softAssert = new SoftAssert();
        softAssert = makeCartItemCalculations(cartPage, softAssert);
        softAssert.assertAll();
    }

    @Test(groups = { "Cart calculations tests" },
          description = "Validate cart item calculations during item amount is going to maximum")
    public void testItemCalculationsMaximumItemAmount() {
        String randomItemName;
        List<String> itemsNames;
        SoftAssert softAssert = new SoftAssert();
        CartPage cartPage = homePage.clickCartIcon();

        itemsNames = cartPage.getCartItemsNames();
        randomItemName = itemsNames.get(
                ThreadLocalRandom.current().nextInt(itemsNames.size()));
        try {
            log.info(randomItemName + " was picked to enlarge amount till maximum");

            /*
             * Enlarge item amount until maximum amount is got
             * and make calculations during incrementation
             */
            while (cartPage.clickAmountButtonPlus(randomItemName)) {
                softAssert = makeCartItemCalculations(cartPage, softAssert);
            }
        } catch (NoSuchItemException e) {
            softAssert.fail(e.getMessage());
        }
        softAssert.assertAll();
    }

    @Test(groups = { "Cart calculations tests" },
          description = "Validate cart item calculations after random item delete")
    public void testCalculationsAfterItemDelete() {
        String randomItemName;
        List<String> itemsNames;
        SoftAssert softAssert = new SoftAssert();
        CartPage cartPage = homePage.clickCartIcon();

        itemsNames = cartPage.getCartItemsNames();
        randomItemName = itemsNames.get(
                ThreadLocalRandom.current().nextInt(itemsNames.size()));
        try {
            log.info(randomItemName + " was picked to delete");
            cartPage.clickDeleteIcon(randomItemName);
            softAssert = makeCartItemCalculations(cartPage, softAssert);
        } catch (NoSuchItemException e) {
            softAssert.fail(e.getMessage());
        }
        softAssert.assertAll();
    }
}
