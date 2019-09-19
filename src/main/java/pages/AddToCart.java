package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Log;

public class AddToCart extends BasePage {
    public AddToCart(WebDriver driver) {
        super(driver);
    }

    private static final By CONFIRM_MSG = By.cssSelector("div#layer_cart .layer_cart_product  h2");
    private static final By QTY_ITEM = By.cssSelector("#layer_cart #layer_cart_product_quantity");
    private static final By TOTAL_ITEM = By.cssSelector("#layer_cart #layer_cart_product_price");
    private static final By CLOSE_CART_BTN = By.cssSelector("#layer_cart .cross");
    private static final By CONTINUE_SHOPPING_BTN = By.cssSelector("#layer_cart .layer_cart_cart span[title='Continue shopping']");
    private static final By PROCEED_TO_CHECKOUT_BTN = By.cssSelector("#layer_cart .layer_cart_cart a[title='Proceed to checkout']");
    private static final By CART_DROPDOWN_LIST = By.cssSelector("a[title='View my shopping cart']");


    public String getTextConfirmMsg() {
        try {
            Log.debug("get Text Confirm Msg");
            waitForElementIsVisible(CONFIRM_MSG);
            return readText(CONFIRM_MSG);
        } catch (Exception e) {
            throw e;
        }
    }

    public Double getTextQtyItem() {
        try {
            Log.debug("get Text Item Quantity");
            waitForElementIsVisible(QTY_ITEM);
            return Double.parseDouble(readText(QTY_ITEM));
        } catch (Exception e) {
            throw e;
        }
    }

    public Double getTextTotalItem() {
        try {
            Log.debug("get Text Item Total");
            waitForElementIsVisible(TOTAL_ITEM);
            return Double.parseDouble(readText(TOTAL_ITEM).replace("$", ""));
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickCloseCart() {
        try {
            Log.debug("Click Close Cart");
            waitForElementIsVisible(CLOSE_CART_BTN);
            clickElement(CLOSE_CART_BTN);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickContinueShopping() {
        try {
            Log.debug("Click Continue Shopping");
            waitForElementIsVisible(CONTINUE_SHOPPING_BTN);
            clickElement(CONTINUE_SHOPPING_BTN);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clickProceedCheckOut() {
        try {
            Log.debug("Click Proceed to Checkout");
            waitForElementIsVisible(PROCEED_TO_CHECKOUT_BTN);
            clickElement(PROCEED_TO_CHECKOUT_BTN);
            waitForElementIsVisible(PROCEED_TO_CHECKOUT_BTN);
        } catch (Exception e) {
            throw e;
        }
    }

    public void hoverCartDropdownList() {
        try {
            Log.debug("Hover to Cart Dropdown list");
            waitForElementIsVisible(CART_DROPDOWN_LIST);
            moveMouseToElement(CART_DROPDOWN_LIST);
        } catch (Exception e) {
            throw e;
        }
    }

    public void convertPriceToInt() {
        try {
            Log.debug("convert price To Int");
            getTextTotalItem();
        } catch (Exception e) {
            throw e;
        }
    }


}
