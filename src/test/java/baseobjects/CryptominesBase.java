package baseobjects;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import setup.SetupWebdriver;
import utils.Utils;

public class CryptominesBase extends SetupWebdriver {

    private static final String browerName = "pluginChrome";
    private static final String mtmskUrl = "chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/popup.html#unlock";
    private String targetUrl;
    protected Utils utils;

    /**
     * Contructor for metamask login
     */
    public CryptominesBase(String testCaseName) {
        super();
        setBrowserName(browerName);
        setTestCaseName(testCaseName);
        setTargetUrl(mtmskUrl);
    }

    /**
     * Default login
     */
    public CryptominesBase(String testCaseName, String targetUrl) {
        super();
        setBrowserName(browerName);
        setTestCaseName(testCaseName);
        setTargetUrl(targetUrl);
    }

    @BeforeMethod(alwaysRun = true)
    @Override
    public void setup() {
        super.setup();
        utils = new Utils(this);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void tearDown() {
        super.tearDown();
    }
}
