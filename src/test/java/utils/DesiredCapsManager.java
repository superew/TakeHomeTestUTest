package utils;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DesiredCapsManager {
    public final static String ANDROID_CHROME_PACK = "com.android.chrome";


    public DesiredCapabilities getDesiredCapabilities(String browser, String platform) throws Exception {
        //Set DesiredCapabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //Set Platform
        capabilities.setCapability("platform", platform);
        //Set BrowserName
        capabilities.setCapability("browserName", browser);
        //Return Capabilities
        return capabilities;
    }

    //Set Firefox Capabilities
    public void setFirefoxCaps(DesiredCapabilities capabilities) {
        FirefoxOptions opt = new FirefoxOptions();
        TemporaryFilesystem.getDefaultTmpFS().deleteTemporaryFiles();
        opt.addArguments("-private");
        capabilities.setCapability("-marionette", true);
        opt.merge(capabilities);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, opt);

    }

    //Set Chrome Options
    public void setChromeCaps(DesiredCapabilities capabilities) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("disable-translate");
        options.addArguments("disable-infobars");
        options.addArguments("start-maximized");
        options.addArguments("disable-popup-blocking");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    }
}
