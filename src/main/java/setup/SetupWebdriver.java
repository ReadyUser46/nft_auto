package setup;


import browsers.customs.PluginChrome;
import browsers.standard.Browser;
import browsers.standard.StandarChrome;
import browsers.standard.StandarEdge;
import browsers.standard.StandarFirefox;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SetupWebdriver {

    private static final String REMOTE_WEBDRIVER_URL = "http://localhost:4444/wd/hub";
    private static final int pageTimeout = 300000; // The Timeout in milliseconds when a load page expectation is called
    private static final int implicitTimeout = 20000; // The Timeout in milliseconds when an implicit expectation is called
    private static final int scriptTimeout = 600000; // The script Timeout in milliseconds when a load script expectation is called
    protected WebDriver driver;
    private String testCaseName;
    private String browserName = null;
    private String targetUrl = null;
    private Logger logger;

    public SetupWebdriver getSetupWebDriverObject() {
        return this;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger customLog() {
        return logger;
    }

    public void skipTest(String reason) {
        throw new SkipException(reason);
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        HashMap<String, Browser> browsersPool = new HashMap<>();
        browsersPool.put("pluginChrome", new PluginChrome());
        browsersPool.put("chrome", new StandarChrome());
        browsersPool.put("firefox", new StandarFirefox());
        browsersPool.put("edge", new StandarEdge());


        Browser browser = browsersPool.get(browserName);
        driver = browser.getLocalDriver();

        /*manage timeouts*/
        driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(pageTimeout, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(scriptTimeout, TimeUnit.MILLISECONDS);

        /*Logger*/
        System.setProperty("java.util.logging.SimpleFormatter.format", //[2021-08-23][20:52:19] INFO: info
                "[%1$tF][%1$tT] %4$s: %5$s%n");
        setLogger(Logger.getLogger("setup"));
        customLog().info(String.format("\n[INIT] Test case = '%s' will be executed\n" +
                        "[INIT] Browser = '%s' | Target url = %s\n" +
                        "[INIT] Webdriver initialized",
                testCaseName, browserName, targetUrl));


        /*go to target url*/
        driver.get(targetUrl);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            //todo takeRemoteScreenshot(customWebDriver(), customWebDriver().getCurrentUrl());
            driver.quit();
            customLog().log(Level.INFO, String.format("Webdriver Released for: '%s'", testCaseName));
        } catch (Exception e) {
            customLog().log(Level.SEVERE, String.format("Error closing Webdriver for: '%s'", testCaseName));
        }
    }


}
