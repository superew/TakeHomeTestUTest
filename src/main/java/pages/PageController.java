package pages;

import org.openqa.selenium.WebDriver;

public class PageController extends BasePage {

    public PageController(WebDriver driver) {
        super(driver);
        setHomePage();
        setSignUpPage();
    }

    //	Pages definition
    HomePage homePage;
    SignUpPage signUpPage;

    public SignUpPage getSignUpPage() {
        return signUpPage;
    }

    public void setSignUpPage() {
        this.signUpPage = new SignUpPage(driver);
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage() {
        homePage = new HomePage(driver);
    }

}
