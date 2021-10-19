package pageobjects.cryptomines;

import io.qameta.allure.Step;
import setup.SetupWebdriver;
import utils.Utils;

public class MarketPage extends Utils {
    public MarketPage(SetupWebdriver instance) {
        super(instance);
    }

    //LOCATORS - ID

    //LOCATORS - XPATH

    //CHECKS

    //ACTIONS
    @Step("Redirect CryptoMines app")
    public void goToPvuFarm(String url) {
        driver.get(url);
        waitForJSandJqueryFinish();
    }

    /*@Step("Login Cryptomines Market")
    public void loginCMines() {
        waitForClickable(By.xpath(BUTTON_LOGIN_XPATH));
        getLoginButton().click();
        waitForJSandJqueryFinish();
    }*/


    //AUX METHODS

    //WEBELEMENTS
}
