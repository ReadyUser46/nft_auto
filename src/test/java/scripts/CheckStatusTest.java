package scripts;

import baseobjects.BaseTest;
import org.testng.annotations.Test;
import pageobjects.MetamaskPage;
import pageobjects.PvuFarmPage;

public class CheckStatusTest extends BaseTest {

    private static final String testCaseName = "Check Status";
    private static final String targetUrl = "https://marketplace.plantvsundead.com/farm#/farm";
    private static final Integer explicitWait = 1;

    public CheckStatusTest() {
        super(testCaseName);
    }

    @Test
    public void checkStatus() {
        MetamaskPage metamaskPage = new MetamaskPage(getSetupWebDriverObject());
        PvuFarmPage pvuFarmPage = new PvuFarmPage(getSetupWebDriverObject());

        //login metamask
        metamaskPage.loginMetamask();

        //login pvu farm
        pvuFarmPage.goToPvuFarm();
        pvuFarmPage.loginPvuFarm();

        //connect metamask
        metamaskPage.connectMetamask();

        //check login puv ok
        utils.assertTrue(pvuFarmPage.isDashBoardDisplayed(), "Verifying login succesfully done");

        //pvu farm
        pvuFarmPage.clickFarmTab();

    }
}
