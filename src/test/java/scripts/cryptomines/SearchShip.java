package scripts.cryptomines;

import baseobjects.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pageobjects.cryptomines.MarketPage;
import pageobjects.metamask.MetamaskPage;
import pageobjects.pvu.PvuFarmPage;
import pageobjects.pvu.PvuLoginPage;

public class SearchShip extends BaseTest {

    private static final String testCaseName = "Search Ship";
    private static final String targetUrl = "https://play.cryptomines.app/";
    private static final Integer explicitWait = 1;
    private PvuFarmPage pvuFarmPage;

    public SearchShip() {
        super(testCaseName);
    }

    @Test
    public void searchShip() {
        MetamaskPage metamaskPage = new MetamaskPage(getSetupWebDriverObject());
        MarketPage cmmarket = new MarketPage(getSetupWebDriverObject());
        PvuLoginPage pvuLoginPage = new PvuLoginPage(getSetupWebDriverObject());
        pvuFarmPage = new PvuFarmPage(getSetupWebDriverObject());

        //login metamask
        metamaskPage.loginMetamask();

        //login CryptoMines
        cmmarket.goToPvuFarm(targetUrl);

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
