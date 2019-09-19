package objects;

public class Browsers {


    private static String browser;

    public Browsers() {
        browser = null;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getCurrentBrowser() {
        return System.getenv("browser");
    }


}
