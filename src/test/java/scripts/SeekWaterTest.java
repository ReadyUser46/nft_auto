package scripts;

import baseobjects.BaseTest;
import org.testng.annotations.Test;
import pageobjects.MetamaskPage;
import pageobjects.PvuFarmPage;
import pageobjects.PvuLoginPage;

public class SeekWaterTest extends BaseTest {

    private static final String testCaseName = "Check Status";
    private static final String targetUrl = "https://marketplace.plantvsundead.com/farm#/farm";
    private static final Integer explicitWait = 1;

    public SeekWaterTest() {
        super(testCaseName);
    }

    @Test
    public void seekWater() {
        MetamaskPage metamaskPage = new MetamaskPage(getSetupWebDriverObject());
        PvuLoginPage pvuLoginPage = new PvuLoginPage(getSetupWebDriverObject());
        PvuFarmPage pvuFarmPage = new PvuFarmPage(getSetupWebDriverObject());

        //login metamask
        metamaskPage.loginMetamask();

        //login pvu farm
        pvuLoginPage.goToPvuFarm();
        pvuLoginPage.loginPvuFarm();

        //connect metamask
        metamaskPage.connectMetamask();

        //check login puv ok
        utils.assertTrue(pvuLoginPage.isDashBoardDisplayed(), "Verifying login succesfully done");

        //pvu farm
        pvuLoginPage.clickFarmTab();

        //pvu map
        pvuFarmPage.clickMap();
        pvuFarmPage.loopCells();

        System.out.println("tork");
    }
}
