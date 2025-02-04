package browsers.customs;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class CustomFirefox {
    private static final FirefoxOptions firefoxOptions = new FirefoxOptions();
    Proxy proxy = new Proxy();

    public CustomFirefox(String proxyPac, String node) {
        firefoxOptions.setCapability("browserName", "firefox");
        firefoxOptions.setCapability("platform", Platform.ANY);
        proxy.setProxyAutoconfigUrl(proxyPac);
        firefoxOptions.setCapability(CapabilityType.PROXY, proxy);
        if (!node.isEmpty()) {
            firefoxOptions.setCapability("applicationName", node);
        }
    }
}
