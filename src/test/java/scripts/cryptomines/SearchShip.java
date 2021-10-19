package scripts.cryptomines;

import baseobjects.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pageobjects.metamask.MetamaskPage;
import pageobjects.pvu.PvuFarmPage;
import pageobjects.pvu.PvuLoginPage;

public class SearchShip extends BaseTest {

    private static final String testCaseName = "Check Status";
    private static final String targetUrl = "";
    private static final Integer explicitWait = 1;
    private PvuFarmPage pvuFarmPage;

    public SearchShip() {
        super(testCaseName);
    }

    @Test
    public void seekWater() {
        MetamaskPage metamaskPage = new MetamaskPage(getSetupWebDriverObject());
        PvuLoginPage pvuLoginPage = new PvuLoginPage(getSetupWebDriverObject());
        pvuFarmPage = new PvuFarmPage(getSetupWebDriverObject());

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
        pvuFarmPage.loopCells(Integer.parseInt(utils.readProperty("lastCell")));

        System.out.println("tork");
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void tearDown() {
        super.tearDown();
        System.out.println(pvuFarmPage.getPlantsCounter() + " Plants checked!");
        System.out.println("Last cell checked = " + pvuFarmPage.getLastCell());
        utils.setProperty("lastCell", String.valueOf(pvuFarmPage.getLastCell()));
    }
}
