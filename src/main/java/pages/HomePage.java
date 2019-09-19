package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Log;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private String BASE_URL = "https://www.utest.com/";

    private static final By SIGN_UP_MENU = By.cssSelector("div.container-fluid a[ui-sref='signup.personal']");

    public void navigateToPage() throws Exception {
        try {
            Log.debug("Navigate to HomePage");
            navigateToURL(BASE_URL);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickSignUpForFreeBtn() throws Exception {
        try {
            Log.debug("Click Sign in Button on Menu");
            waitForElementIsVisible(SIGN_UP_MENU);
            clickElement(SIGN_UP_MENU);
        } catch (Exception e) {
            throw e;
        }
    }
}
