package browsers.customs;

import browsers.standard.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class PluginChrome implements Browser {
    private final ChromeOptions chromeOptions = new ChromeOptions();

    public PluginChrome() {
        //chromeOptions.addExtensions(new File("src/main/resources/mtmsk_10_0_2_0.crx"));
        chromeOptions.addArguments("user-data-dir=C:\\Users\\blackout\\AppData\\Local\\Google\\Chrome\\User Data");
        chromeOptions.addArguments("--profile-directory=Profile 2");

    }

    @Override
    public String getBrowserName() {
        return "chrome";
    }

    @Override
    public WebDriver getLocalDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\drivers\\chromedriver.exe");
        return new ChromeDriver(chromeOptions);
    }
}
