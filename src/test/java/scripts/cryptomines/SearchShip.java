package scripts.cryptomines;

import baseobjects.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pageobjects.cryptomines.MarketPage;
import pageobjects.metamask.MetamaskPage;

public class SearchShip extends BaseTest {

    private static final String testCaseName = "Search Ship";
    private static final String targetUrl = "https://play.cryptomines.app/marketplace/spaceships/cryptomines";
    private static final String STARS = "2";
    private static final Integer explicitWait = 1;

    public SearchShip() {
        super(testCaseName);
    }

    @Test
    public void searchShip() {
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
        cmmarket.disableAnimations();

        //select stars
        cmmarket.selectStars(STARS);

        //check filter stars ok
        //utils.assertTrue(cmmarket.isFilterOK(Integer.parseInt(STARS)), "Verifying login succesfully done");

        utils.espera(3);

        //MAIN LOOOP
        cmmarket.loopPages();

    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void tearDown() {
        super.tearDown();
    }
}
