package browsers.standard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class StandarFirefox implements Browser {
  private final FirefoxOptions firefoxOptions = new FirefoxOptions();

  public StandarFirefox() {

  }

  @Override
  public String getBrowserName() {
    return "firefox";
  }

  @Override
  public WebDriver getLocalDriver() {
    System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\drivers\\geckodriver.exe");
    return new FirefoxDriver(firefoxOptions);
  }
}
