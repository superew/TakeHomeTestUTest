package tdd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import pages.PageController;
import utilities.DriverManager;
import utilities.JSWaiter;
import utils.DesiredCapsManager;

import java.io.IOException;
import java.net.MalformedURLException;

public class TestBase {
    protected WebDriver driver;
    private DesiredCapsManager desiredCapsManager = new DesiredCapsManager();
    public PageController webApp;
    protected JSWaiter waiter;

    //Do the test setup
    @BeforeTest
    @Parameters(value = {"browser", "platform", "driverPath"})
    public void setupTest(String browser, String platform, String driverPath) throws MalformedURLException, IOException, InterruptedException, Exception {
        System.out.println("Browser: " + browser);
        System.out.println("Platform: " + platform);
        System.out.println("driverPath: " + driverPath);

        //Get DesiredCapabilities
        DesiredCapabilities capabilities = desiredCapsManager.getDesiredCapabilities(browser, platform);
        //Create Driver with capabilities
        driver = new DriverManager(capabilities, browser, driverPath).createDriver();
        //Send driver object to JSWaiter Class
        waiter = new JSWaiter(driver);
        //Maximize Window
        driver.manage().window().maximize();
//        Dimension d = new Dimension(1400,1080);
//        driver.manage().window().setSize(d);
        webApp = new PageController(driver);
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }

    public PageController getWebApp() {
        return webApp;
    }

    public void setWebApp(PageController webApp) {
        this.webApp = webApp;
    }
}
