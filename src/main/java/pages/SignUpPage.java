package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Log;

import java.util.List;

public class SignUpPage extends BasePage {
    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    private String BASE_URL = "https://www.utest.com/";

    private static final By FIRST_NAME_TXT = By.cssSelector("input#firstName");
    private static final By LAST_NAME_TXT = By.cssSelector("input#lastName");
    private static final By EMAIL_ADDRESS_TXT = By.cssSelector("input#email");

    private static final By DATE_DDL = By.cssSelector("div[placeholder='Day'] span.form-control");
    private static final By DATE_LIST = By.cssSelector("div[name='birthDay'] ul.dropdown-menu div[id*='ui']");

    private static final By MONTH_DDL = By.cssSelector("div[placeholder='Month'] span.form-control");
    private static final By MONTH_LIST = By.cssSelector("div[name='birthMonth'] ul.dropdown-menu div[id*='ui']");

    private static final By YEAR_DDL = By.cssSelector("div[placeholder='Year'] span.form-control");
    private static final By YEAR_LIST = By.cssSelector("div[name='birthYear'] ul.dropdown-menu div[id*='ui']");

    private static final By GENDER_DDL = By.cssSelector("div[placeholder='Select a gender'] span.form-control");
    private static final By GENDER_LIST = By.cssSelector("div#genderCode ul.dropdown-menu div[id*='ui']");

    private static final By NEXT_BTN = By.cssSelector("div.text-right a span");

    private static final By EMAIL_ERROR_MSG = By.cssSelector("span#emailError");

    private static final By STEP2_PAGE = By.cssSelector("h1.step-title span.sub-title");

    String firstName = "Andrew";
    String lastName = "Nguyen";
    String emailAdd = randomEmailAddress(5);
    String invalidEmailAdd = "abc";

    public void inputFirstName() throws Exception {
        try {
            Log.debug("Input First Name");
            waitForElementIsVisible(FIRST_NAME_TXT);
            writeText(FIRST_NAME_TXT, firstName);
        } catch (Exception e) {
            throw e;
        }
    }

    public void inputLastName() throws Exception {
        try {
            Log.debug("Input Last Name");
            waitForElementIsVisible(LAST_NAME_TXT);
            writeText(LAST_NAME_TXT, lastName);
        } catch (Exception e) {
            throw e;
        }
    }

    public void inputEmailAddress() throws Exception {
        try {
            Log.debug("Input Email Address");
            waitForElementIsVisible(EMAIL_ADDRESS_TXT);
            writeText(EMAIL_ADDRESS_TXT, emailAdd);
        } catch (Exception e) {
            throw e;
        }
    }

    public void inputInvalidEmailAddress() throws Exception {
        try {
            Log.debug("Input Email Address");
            waitForElementIsVisible(EMAIL_ADDRESS_TXT);
            writeText(EMAIL_ADDRESS_TXT, invalidEmailAdd);
            Thread.sleep(1000);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getErrorMsgEmailAddress() throws Exception {
        try {
            Log.debug("Get Error Message Email Address");
            waitForElementIsVisible(EMAIL_ERROR_MSG);
            return readText(EMAIL_ERROR_MSG);
        } catch (Exception e) {
            throw e;
        }
    }

    public void selectDay() throws Exception {
        try {
            Log.debug("Select Day");
            waitForElementIsVisible(DATE_DDL);
            clickElement(DATE_DDL);
            Thread.sleep(1000);
            waitForElementIsVisible(DATE_LIST);
            List<WebElement> lst = driver.findElements(DATE_LIST);
            lst.get(4).click();
        } catch (Exception e) {
            throw e;
        }
    }

    public void selectMonth() throws Exception {
        try {
            Log.debug("Select Month");
            waitForElementIsVisible(MONTH_DDL);
            clickElement(MONTH_DDL);
            Thread.sleep(1000);
            waitForElementIsVisible(MONTH_LIST);
            List<WebElement> lst = driver.findElements(MONTH_LIST);
            lst.get(4).click();
        } catch (Exception e) {
            throw e;
        }
    }

    public void selectYear() throws Exception {
        try {
            Log.debug("Select Year");
            waitForElementIsVisible(YEAR_DDL);
            clickElement(YEAR_DDL);
            Thread.sleep(1000);
            waitForElementIsVisible(YEAR_LIST);
            List<WebElement> lst = driver.findElements(YEAR_LIST);
            lst.get(4).click();
        } catch (Exception e) {
            throw e;
        }
    }

    public void selectGender() throws Exception {
        try {
            Log.debug("Select Gender");
            waitForElementIsVisible(GENDER_DDL);
            clickElement(GENDER_DDL);
            Thread.sleep(1000);
            waitForElementIsVisible(GENDER_LIST);
            List<WebElement> lst = driver.findElements(GENDER_LIST);
            lst.get(1).click();
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickNextBtn() throws Exception {
        try {
            Log.debug("Click Next Button to Step 2");
            waitForElementIsVisible(NEXT_BTN);
            clickElement(NEXT_BTN);
        } catch (Exception e) {
            throw e;
        }
    }

    public String getTextStep2() throws Exception {
        try {
            Log.debug("Get text Step 2");
            waitForElementIsVisible(STEP2_PAGE);
            return readText(STEP2_PAGE);
        } catch (Exception e) {
            throw e;
        }
    }
}
