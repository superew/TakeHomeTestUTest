package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import utilities.JSWaiter;
import utilities.Log;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BasePage {
    public WebDriver driver;
    public WebDriverWait wait;
    Wait<WebDriver> fluentWait;
    public final static int SERVER_TIMEOUT = 20;
    public final static int MAX_TIMEOUT = 45;
    public final static int DURATION_OF_POLING = 3;
    public JSWaiter waiter;
    private String browser = System.getenv("browser");

    //Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, SERVER_TIMEOUT);
        waiter = new JSWaiter(driver);
        fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(SERVER_TIMEOUT, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public void navigateToURL(String url) {
        Log.debug("Navigated to  " + url);
        driver.navigate().to(url);
    }

    public void navigateToNewURL(String url) {
        Log.debug("Navigated to  " + url);
        driver.get(url);
    }

    public void clickElement(By elementLocation) {
        Log.debug("Click to element by BY: " + elementLocation);
        try {
            waitForElementIsVisible(elementLocation);
            driver.findElement(elementLocation).click();
        } catch (Exception e) {
            Log.error("Cannot click element by By: " + e.getMessage());
        }
    }

    public void clickElement(WebElement element) {
        Log.debug("Click to element by WebElement: " + element);
        try {
            waitForElementIsVisible(element);
            element.click();
        } catch (Exception e) {
            Log.error("Cannot click element by WebElement: " + e.getMessage());
        }
    }

    public void clickElementByJS(By elementLocation) throws Exception {
        Log.debug("Click to element by BY JS: " + elementLocation);
        try {
            waitForElementIsVisible(elementLocation);
            clickJavaScriptElement(elementLocation);
        } catch (Exception e) {
            Log.error("Cannot click element by BY JS: " + elementLocation);
            throw e;
        }
    }

    public <T> void clickJavaScriptElement(T elementAttr) {
        waiter.waitForJQueryLoad();
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementAttr);
            waiter.waitForJQueryLoad();
        } catch (StaleElementReferenceException e) {
            Log.error("Element is not attached to the page document " + e.getStackTrace());
        } catch (NoSuchElementException e) {
            Log.error("Element was not found in DOM " + e.getStackTrace());
        } catch (Exception e) {
            Log.error("Unable to click on element " + e.getStackTrace());
        }
    }

    public void select(By elementLocation, String string) {
        Select drpDay = new Select(driver.findElement(elementLocation));
        drpDay.selectByVisibleText(string);
    }

    public void writeText(By elementLocation, String text) {
        driver.findElement(elementLocation).sendKeys(Keys.CLEAR);
        driver.findElement(elementLocation).sendKeys(text);
    }


    public void deleteText(By elementLocation) {
        driver.findElement(elementLocation).clear();
    }


    public String readText(By elementLocation) {
        return driver.findElement(elementLocation).getText();
    }

    public String readText(WebElement element) {
        return element.getText();
    }

    public String readIndexAfterSplitText(WebElement element, String regex, int index) {
        String a = readText(element);
        String[] b = a.split(regex);
        return b[index];
    }

    public void verifyText(String actual, String expected) {
        Assert.assertEquals(actual, expected);
    }

    public <T> boolean isElementExist(T element) {
        boolean result = false;
        try {
            if (element.getClass().getName().contains("By")) {
                wait.until(ExpectedConditions.presenceOfElementLocated((By) element));
                result = driver.findElement((By) element).isDisplayed();
            } else {
                wait.until(ExpectedConditions.visibilityOf((WebElement) element));
                result = ((WebElement) element).isDisplayed();
            }
            return result;
        } catch (Exception ex) {
            Log.error(ex.getMessage());
            return result;
        }
    }

    public <T> void waitForElementIsVisible(T element) {
        Log.debug("Explicit wait to element is visible");
        Wait wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(MAX_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(DURATION_OF_POLING))
                .ignoring(java.util.NoSuchElementException.class);
        Function<WebDriver, Boolean> elementIsDisplayed = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (element.getClass().getName().contains("By"))
                        driver.findElement((By) element).isDisplayed();
                    else
                        ((WebElement) element).isDisplayed();
                    return true;
                } catch (NoSuchElementException e) {
                    return false;
                } catch (StaleElementReferenceException f) {
                    return false;
                }
            }
        };

        wait.until(elementIsDisplayed);
    }

    public <T> void waitToElementIsNotVisible(T element) {
        Log.debug("Explicit wait to element is not visible");
        Wait wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(MAX_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(DURATION_OF_POLING))
                .ignoring(NoSuchElementException.class);

        Function<WebDriver, Boolean> elementIsDisplayed = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    if (element.getClass().getName().contains("By"))
                        driver.findElement((By) element).isDisplayed();
                    else
                        ((WebElement) element).isDisplayed();
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                } catch (StaleElementReferenceException f) {
                    return true;
                }
            }
        };

        wait.until(elementIsDisplayed);
    }

    public String getCurrentURL() {
        String currentURL = driver.getCurrentUrl();
        return currentURL;
    }

    public String getpageTitle() {
        String currentTitle = driver.getTitle();
        return currentTitle;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    //Generate a random string
    public String randomAlphaNumeric(int count) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ._0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    //Get Current Time of System
    public long getCurrentTime() {
        Date date = new Date();
        long time = date.getTime();
        return time;
    }

    //Generate a random email base on the random string and current time
    public String randomEmailAddress(int count) {
        String newEmail = randomAlphaNumeric(count);
        long currentTime = getCurrentTime();
        newEmail = newEmail + currentTime + "@gmail.com";
        return newEmail;
    }

    public void selectIndexOnDropBox(By elementLocation, int num) {
        Select optionSelect = new Select(driver.findElement(elementLocation));
        optionSelect.selectByIndex(num);
    }

    public void selectValueOnDropBox(By elementLocation, String text) {
        Select optionSelect = new Select(driver.findElement(elementLocation));
        optionSelect.selectByValue(text);
    }

    public void selectVisibleTextOnDropBox(By elementLocation, String text) {
        Select optionSelect = new Select(driver.findElement(elementLocation));
        optionSelect.selectByVisibleText(text);
    }

    //Get number of items
    public int getNumberOfItems(By elementLocation) {
        List<WebElement> items = null;
        try {
            items = driver.findElements(elementLocation);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return items.size();
    }

    //Get item
    public WebElement getItems(By elementLocation, int index) {
        List<WebElement> items = null;
        try {
            items = driver.findElements(elementLocation);
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return items.get(index);
    }

    //Get list of items
    public List<WebElement> getListItems(By elementLocation) {
        List<WebElement> items = null;
        try {
            items = driver.findElements(elementLocation);
        } catch (Exception e) {
            throw e;
        }
        return items;
    }

    public void outOfFocus(By elementLocation) {
        driver.findElement(elementLocation).sendKeys(Keys.TAB);
    }

    public void moveMouseOverProduct(By elementLocation) {
        Actions act = new Actions(driver);
        //waitAnElementDisplay(elementLocation);
        WebElement ele = driver.findElement(elementLocation);
        act.moveToElement(ele).build().perform();
    }

    public void moveMouseToElement(By elementLocation) {
        Actions act = new Actions(driver);
        waitForElementIsVisible(elementLocation);
        WebElement ele = driver.findElement(elementLocation);
        act.moveToElement(ele).perform();
    }

    public String getDisplayedValueElement(By elementLocation) {
        String valueElement = driver.findElement(elementLocation).getAttribute("value");
        return valueElement;
    }

    public String getAttributeElement(By elementLocation, String attribute) {
        String valueElement = driver.findElement(elementLocation).getAttribute(attribute);
        return valueElement;
    }

    public double parsePriceStringToDouble(String price) {
        //Delete the $ sign from the string
        StringBuilder tempString = new StringBuilder(price);
        tempString.deleteCharAt(0);
        double doublePrice = Double.parseDouble(tempString.toString());
        return doublePrice;
    }

    public double parsePercentStringToDouble(String percent) {
        //Delete % sign from the string
        int index = percent.indexOf("%");
        StringBuilder tempString = new StringBuilder(percent);
        tempString.deleteCharAt(index);
        double doublePercent = Double.parseDouble(tempString.toString());
        return doublePercent;
    }

    public void Tap() {
        JavascriptExecutor jxc = (JavascriptExecutor) driver;
        Map<String, Object> BackspaceKeyevent = new HashMap<>();
        BackspaceKeyevent.put("key", "4");
        jxc.executeScript("mobile:key:event", BackspaceKeyevent);
    }

    public boolean isMobileAndroid() {
        return browser.contains("mobile-android");
    }

    public boolean isNotMobileOrBrowserStack() {
        return !(browser.contains("android") || browser.contains("browserstack"));
    }

    public void waitPageLoad() {
        waiter.waitForPageLoaded();
    }

    public static boolean checkSort(ArrayList<String> arraylist) {
        Log.debug("Check Sort List");
        try {
            boolean isSorted = true;
            for (int i = 1; i < arraylist.size(); i++) {
                if (arraylist.get(i - 1).compareTo(arraylist.get(i)) > 0) {
                    isSorted = false;
                    break;
                }
            }
            return isSorted;
        } catch (Exception e) {
            throw e;
        }
    }


    public void switchToFrame(By element) {
        driver.switchTo().frame(driver.findElement(element));
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }


}