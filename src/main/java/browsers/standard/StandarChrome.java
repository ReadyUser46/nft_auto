package browsers.standard;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class StandarChrome implements Browser {
  private final ChromeOptions chromeOptions = new ChromeOptions();

  public StandarChrome() {
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

  public WebDriver getRemoteWebdriver(String remoteWebDriverURl) throws MalformedURLException {
    return new RemoteWebDriver(new URL(remoteWebDriverURl), chromeOptions);
  }
}
