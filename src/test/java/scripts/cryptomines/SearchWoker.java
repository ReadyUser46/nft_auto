package scripts.cryptomines;

import baseobjects.CryptominesBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pageobjects.cryptomines.MarketPage;
import pageobjects.metamask.MetamaskPage;
import pageobjects.pvu.PvuFarmPage;

public class SearchWoker extends CryptominesBase {

    private static final String testCaseName = "Search Worker";
    private static final String targetUrl = "https://play.cryptomines.app/marketplace/workers";
    private static final String STARS = "4";
    private static final Integer explicitWait = 1;
    private PvuFarmPage pvuFarmPage;

    public SearchWoker() {
        super(testCaseName);
    }

    @Test
    public void searchWorker() {
        MetamaskPage metamaskPage = new MetamaskPage(getSetupWebDriverObject());
        MarketPage cmmarket = new MarketPage(getSetupWebDriverObject());

        //login metamask
        metamaskPage.loginMetamask();

        //login CryptoMines
        cmmarket.goToUrl(targetUrl);

        /*connect metamask
        metamaskPage.connectMetamask();*/

        //check login ok
        utils.assertTrue(cmmarket.isMarketDisplayed(), "Verifying login succesfully done");

        driver.manage().deleteAllCookies();
        //select stars
        cmmarket.selectStars(STARS);

        //check filter stars ok
        utils.assertTrue(cmmarket.isFilterOK(STARS), "Verifying login succesfully done");

        //MAIN LOOOP
        //cmmarket.loopPages();

        for (int i = 0; i <= 100; i++) {
            cmmarket.loopWorkers4();
        }


    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void tearDown() {
        super.tearDown();
    }
}
