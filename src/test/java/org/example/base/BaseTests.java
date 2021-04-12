package org.example.base;

import org.example.pages.HomePage;
import org.example.utils.CookieManager;
import org.example.settings.ProjectURI;
import org.example.utils.PageEventListener;
import org.example.utils.WindowManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Sets up project base settings and implements before/after tests annotations
 */
public class BaseTests {
    private static final String CHROME_DRIVER_PATH = "resources/drivers/chromedriver.exe";
    private static final String CHROME_PROFILE_PATH = "resources/web browsers user profiles/chrome/Profile 1";
    protected HomePage homePage;
    protected Logger log;
    private EventFiringWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void setup() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        /* Disable infobar "Браузером управляет автоматизированное ПО" */
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches",
                new String[]{ "enable-automation" });

        /* Set chrome profile directory */
        //chromeOptions.addArguments("--user-data-dir=".concat(CHROME_PROFILE_PATH));

        /* Use EventFiringWebDriver to log click actions */
        driver = new EventFiringWebDriver(new ChromeDriver(chromeOptions));
        //driver.register(new PageEventListener());

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(ProjectURI.BASE);

        homePage = new HomePage(driver);

        /* Instantiate logger to log necessary info */
        log = Logger.getLogger("");
    }

    @BeforeGroups({ "Empty cart on first visit" })
    public void simulateCustomerFirstVisit() {
        getCookieManager().deleteAllCookies();
        getWindowManager().refresh();
        chooseMinskAsCustomersCity();
    }

    @BeforeGroups({ "Catalog tests" })
    public void chooseMinskAsCustomersCity() {
        homePage.getTownSelectPopup().clickBigTownsMinskButton();
    }

    @BeforeMethod(groups = { "Empty cart on first visit", "Catalog tests" })
    public void goToMainPage() {
        getWindowManager().goTo(ProjectURI.BASE);
    }

    @BeforeMethod(groups = { "Adding to cart tests" })
    public void goToMainPageAndSimulateFirstVisit() {
        goToMainPage();
        simulateCustomerFirstVisit();
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    public CookieManager getCookieManager() {
        return new CookieManager(driver);
    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }
}
