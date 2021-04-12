package org.example.catalog;

import org.example.base.BaseTests;
import org.example.exceptions.NoSuchCategoryException;
import org.example.pages.CatalogCategoryPage;
import org.example.pages.CatalogPage;
import org.example.settings.ProjectURI;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.*;
import static org.testng.Assert.assertEquals;

/**
 * Contains different catalog tests
 */
public class CatalogTests extends BaseTests {

    @Test(groups = "Catalog tests",
          description = "Validate catalog URI")
    public void testCatalogURI() {
        homePage.clickCatalogBtn();
        assertEquals(getWindowManager().getCurrentURL(), ProjectURI.BASE + ProjectURI.CATALOG_PATH);
    }

    @Test(groups = "Catalog tests",
          description = "Validate catalog header")
    public void testCatalogHeader() {
        CatalogPage catalogPage = homePage.clickCatalogBtn();
        assertEquals(catalogPage.getCatalogPageHeader(), "Каталог рыбы");
    }

    @Test(groups = "Catalog tests",
          description = "Validate backToMainPage link works correctly")
    public void testBackToMainPageLink() {
        CatalogPage catalogPage = homePage.clickCatalogBtn();
        catalogPage.clickBackToMainPageLink();
        assertEquals(getWindowManager().getCurrentURL(), ProjectURI.BASE + ProjectURI.DEFAULT_PATH);
    }

    @Test(groups = "Catalog tests",
          description = "Validate catalog categories and subcategories presence")
    // TODO: Create full expectedCategorySubcategoryMap and replace map comparing with Map.equals()
    public void testCatalogCategoriesSubcategoriesPresence() {
        Set<String> expectedChilledFishSubcategories = new HashSet<>(Arrays.asList(
                "Лосось", "Семга", "Форель", "Палтус", "Сибас"));
        Set<String> expectedFrozenFishSubcategories = new HashSet<>(Arrays.asList(
                "Бротола", "Горбуша", "Гренадер", "Дори", "Камбала", "Кета", "Килька", "Конгрио",
                "Лосось", "Минтай", "Навага", "Окунь", "Палтус", "Пангасиус", "Пикша", "Сайда",
                "Салака", "Сельдь", "Сибас", "Скумбрия", "Тилапия", "Треска", "Тунец", "Угольная рыба",
                "Фарш рыбный", "Мойва", "Фландер", "Форель", "Хек", "Щука"));
        Map<String, Set<String>> expectedCategorySubcategoryMap = new HashMap<>();
        Map<String, Set<String>> actualCategorySubcategoryMap;

        expectedCategorySubcategoryMap.put("Охлажденная рыба", expectedChilledFishSubcategories);
        expectedCategorySubcategoryMap.put("Замороженная рыба", expectedFrozenFishSubcategories);

        CatalogPage catalogPage = homePage.clickCatalogBtn();
        actualCategorySubcategoryMap = catalogPage.getCategorySubcategoryMap();

        /*
         * Lazy comparing actual and expected categorySubcategoryMaps
         * Replace with Map.equal() after creating full expectedCategorySubcategoryMap
         */
        for (Map.Entry<String, Set<String>> categoryWithSubs : expectedCategorySubcategoryMap.entrySet()) {
            assertEquals(actualCategorySubcategoryMap.get(categoryWithSubs.getKey()),
                         categoryWithSubs.getValue());
        }
    }

    @Test(groups = "Catalog tests",
          description = "Validate category links work correctly")
    public void testCategoryLinks() {
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();

        /*
         * Loop through categories names to click every category name
         * and validate right category page header
         */
        for (String categoryName : catalogPage.getCategoriesNames()) {
            try {
                CatalogCategoryPage categoryPage = catalogPage.clickCategoryByName(categoryName);
                softAssert.assertEquals(categoryPage.getCategoryPageHeader(),
                        categoryName,
                        "Category link name does not correspond to category page header");
                getWindowManager().goBack();
            } catch (NoSuchCategoryException e) {
                softAssert.fail(e.getMessage() + ": " + categoryName);
            }
        }
        softAssert.assertAll();
    }

    @Test(groups = "Catalog tests",
          description = "Validate category items count")
    public void testCategoryItemsCount() {
        SoftAssert softAssert = new SoftAssert();

        CatalogPage catalogPage = homePage.clickCatalogBtn();

        /*
         * Loop through categories names to click every category name
         * and validate items count
         */
        for (String categoryName : catalogPage.getCategoriesNames()) {
            try {
                int catalogCategoryItemsCount;

                catalogCategoryItemsCount = catalogPage.getCategoryItemsCount(categoryName);
                CatalogCategoryPage categoryPage = catalogPage.clickCategoryByName(categoryName);
                categoryPage.showAllCategoryItems();
                softAssert.assertEquals(categoryPage.getItemCardsCount(),
                        catalogCategoryItemsCount,
                        " items in " + categoryName);
                getWindowManager().goBack();
            } catch (NoSuchCategoryException e) {
                softAssert.fail(e.getMessage() + ": " + categoryName);
            }
        }
        softAssert.assertAll();
    }
}
