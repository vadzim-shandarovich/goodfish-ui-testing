package org.example.base;

import org.example.pages.HomePage;
import org.example.utils.CookieManager;
import org.example.utils.ProjectURI;
import org.example.utils.WindowManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterTest;
import java.util.concurrent.TimeUnit;

/**
 * Sets up project base settings and implements before/after tests annotations
 */
public class BaseTests {
    protected HomePage homePage;
    private WebDriver driver;
    private static final String CHROME_DRIVER_PATH = "resources/drivers/chromedriver.exe";
    private static final String CHROME_PROFILE_PATH = "resources/web browsers user profiles/chrome/Profile 1";

    @BeforeTest(alwaysRun = true)
    public void setup() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        /* Disable infobar "Браузером управляет автоматизированное ПО" */
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches",
                new String[]{ "enable-automation" });

        /* Set chrome profile directory */
        //chromeOptions.addArguments("--user-data-dir=".concat(CHROME_PROFILE_PATH));

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(ProjectURI.BASE);

        homePage = new HomePage(driver);
    }

    @BeforeGroups({ "Empty cart on first visit" })
    public void simulateCustomerFirstVisit() {
        getCookieManager().deleteAllCookies();
        getWindowManager().refresh();
    }

    @BeforeMethod(groups = { "Empty cart on first visit" })
    public void goToMainPage() {
        getWindowManager().goTo(ProjectURI.BASE);
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
