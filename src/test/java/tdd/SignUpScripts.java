package tdd;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.PageController;
import utilities.DriverManager;
import utilities.JSWaiter;
import utils.DesiredCapsManager;

public class SignUpScripts {
    protected WebDriver driver;
    private DesiredCapsManager desiredCapsManager = new DesiredCapsManager();
    public PageController webApp;

    String emailMsg = "Enter valid email";
    String step2Title = "Add your address";
    protected JSWaiter waiter;

    public SignUpScripts() throws Exception {
    }

    @BeforeTest

    @Parameters(value = {"browser", "platform", "driverPath"})
    public void setupTest(String browser, String platform, String driverPath) throws Exception {
        System.out.println("Browser: " + browser);
        System.out.println("Platform: " + platform);
        System.out.println("driverPath: " + driverPath);

        //Get DesiredCapabilities
        DesiredCapabilities capabilities = desiredCapsManager.getDesiredCapabilities(browser, platform);
        //Create Driver with capabilities
        driver = new DriverManager(capabilities, browser, driverPath).createDriver();
        //Send driver object to JSWaiter Class
        waiter = new JSWaiter(driver);

        if (platform.contains("mac")) {
            Point targetPoint = new Point(0, 0);
            driver.manage().window().setPosition(targetPoint);
            Dimension d = new Dimension(1250, 900);
            driver.manage().window().setSize(d);
        }
        if (platform.contains("windows")) {
            driver.manage().window().maximize();
//        } else {
//            driver.manage().window().fullscreen();
        }
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


    @Test
    public void TC_01_CreatAccountWithInvalidEmail() throws Exception {
        webApp.getHomePage().navigateToPage();
        webApp.getHomePage().clickSignUpForFreeBtn();
        webApp.getSignUpPage().inputFirstName();
        webApp.getSignUpPage().inputLastName();
        webApp.getSignUpPage().inputInvalidEmailAddress();
        String errorMsg = webApp.getSignUpPage().getErrorMsgEmailAddress().trim();
        Assert.assertEquals(errorMsg, emailMsg);
    }

    @Test
    public void TC_02_CreatAccountWithValidEmail() throws Exception {
        webApp.getHomePage().navigateToPage();
        webApp.getHomePage().clickSignUpForFreeBtn();
        webApp.getSignUpPage().inputFirstName();
        webApp.getSignUpPage().inputLastName();
        webApp.getSignUpPage().inputEmailAddress();
        webApp.getSignUpPage().selectMonth();
        webApp.getSignUpPage().selectDay();
        webApp.getSignUpPage().selectYear();
        webApp.getSignUpPage().selectGender();
        webApp.getSignUpPage().clickNextBtn();
        String step2Title = webApp.getSignUpPage().getTextStep2().trim();
        Assert.assertEquals(step2Title, step2Title);
    }

}
