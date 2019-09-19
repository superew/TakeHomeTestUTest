package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    public DesiredCapabilities caps;
    public String browser = null;
    public WebDriver driver = null;
    private String driverPath;
    private static String SERVER_URL = ":9515";
    private static String USERNAME_BROWSERSTACK;
    private static String AUTOMATE_KEY_BROWSERSTACK;
    private static String EXTENSION_BROWSERSTACK;
    private static String URL_BROWSERSTACK;
    String hubURL = "http://172.16.254.107:4444/wd/hub";

    public DriverManager(DesiredCapabilities capabilities, String browser, String driverPath) {
        this.caps = capabilities;
        this.browser = browser;
        this.driverPath = driverPath;
    }

    public WebDriver createDriver() throws MalformedURLException, Exception {

//        if (browser == "macos") {
//            System.setProperty("webdriver.chrome.driver", this.driverPath + "chromedriver");
//            driver = new ChromeDriver(getChromeOptions());
//        } else{

        switch (browser.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", this.driverPath + "geckodriver.exe");
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                driver = new FirefoxDriver(getFirefoxOptions());
//                driver = new RemoteWebDriver(new URL(hubURL), caps);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", this.driverPath + "chromedriver");
                driver = new ChromeDriver(getChromeOptions());
//                driver = new RemoteWebDriver(new URL(hubURL), caps);
                break;
            case "internet explorer":
                System.setProperty("webdriver.ie.driver", this.driverPath + "IEDriverServer.exe");
                driver = new InternetExplorerDriver(getIEOptions());
//                driver = new RemoteWebDriver(new URL(hubURL), caps);
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", this.driverPath + "MicrosoftWebDriver.exe");
                driver = new EdgeDriver(new EdgeOptions());
//                driver = new RemoteWebDriver(new URL(hubURL), caps);
                break;
            case "mobile-android":
                this.driverPath = "http://localhost";
                driver = new RemoteWebDriver(new URL(driverPath + SERVER_URL), caps);
                break;
            case "browserstack-safari-mobile":
            case "browserstack-safari-mac":
            case "browserstack-chrome-mobile":
            case "browserstack-chrome-windows":

                URL_BROWSERSTACK = "https://" + USERNAME_BROWSERSTACK + ":" + AUTOMATE_KEY_BROWSERSTACK + EXTENSION_BROWSERSTACK;
                Log.debug("URL_BROWSERSTACK: " + URL_BROWSERSTACK);
                driver = new RemoteWebDriver(new URL(URL_BROWSERSTACK), caps);
                break;
            default:
                Assert.assertTrue(false, "There is a problem on browser selection! Please check testng xml file!");
                break;

        }
        return driver;
    }

    private FirefoxOptions getFirefoxOptions() {
        Log.info("---------------Firefox Driver---------------------");
        ProfilesIni profilesIni = new ProfilesIni();
        FirefoxProfile profile = profilesIni.getProfile("default");
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        profile.setPreference("general.useragent.override", "Any UserAgent String");
        //Use No Proxy Settings
        profile.setPreference("network.proxy.type", 0);
        profile.setPreference("intl.accept_languages", "en-us");
        profile.setPreference("app.update.enabled", false);
        profile.setPreference("javascript.enabled", true);
        profile.setPreference("-private", true);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        return options;
    }

    private ChromeOptions getChromeOptions() {
        Log.info("---------------Chrome Driver---------------------");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("disable-popup-blocking");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("disable-translate");
        options.addArguments("disable-infobars");
        options.addArguments("start-maximized");
        return options;
    }

    private InternetExplorerOptions getIEOptions() {
        Log.info("---------------Internet Explorer Driver---------------------");
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
        options.setCapability("requireWindowFocus", true);
        return options;
    }
}
