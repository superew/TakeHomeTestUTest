package configuration;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.BasePage;
import pages.PageController;
import utilities.DriverManager;
import utilities.JSWaiter;
import utilities.Log;
import utils.DesiredCapsManager;


public class TestConfiguration {

    WebDriver driver;
    JSWaiter waiter;
    public PageController webApp;
    private DesiredCapsManager desiredCapsManager = new DesiredCapsManager();
    public BasePage basePage;

    @Before
    public void configTestEnvironment() throws Exception {
        try {
            System.out.println("============================================================================================");


            String browser = System.getenv("browser");
            String platform = System.getenv("platform");
            String driverPath = System.getenv("driverPath");
            Log.debug("    Config Test Environment. Browser is: " + browser);
            Log.debug("    Config Test Environment. Platform is: " + platform);
            //Get DesiredCapabilities
            DesiredCapabilities capabilities = desiredCapsManager.getDesiredCapabilities(browser, platform);
            //Create Driver with capabilities
            driver = new DriverManager(capabilities, browser, driverPath).createDriver();
            //Maximize Window
            if (!browser.contains("browserstack")) {
                Dimension d = new Dimension(1024, 768);
                driver.manage().window().setSize(d);
            }
            if (platform.contains("mac")) {
                Point targetPoint = new Point(0, 0);
                driver.manage().window().setPosition(targetPoint);
                Dimension d = new Dimension(1250, 900);
                driver.manage().window().setSize(d);
            }
            if (platform.contains("windows")) {
                driver.manage().window().maximize();
            } else {
                driver.manage().window().fullscreen();
            }
            basePage = new BasePage(driver);
            waiter = new JSWaiter(driver);
        } catch (Exception e) {
            throw e;
        }
    }

    @After
    public void tearDown() throws Exception {
        try {

            driver.quit();
        } catch (Exception e) {
            throw e;
        }
    }

}
