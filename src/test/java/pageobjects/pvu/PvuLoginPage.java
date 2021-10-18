package pageobjects.pvu;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import setup.SetupWebdriver;
import utils.Utils;

public class PvuLoginPage extends Utils {

    //LOCATORS - ID
    private static final String INPUT_PASS_ID = "password";

    //LOCATORS - XPATH
    private static final String BUTTON_LOGIN_XPATH = "//*[contains(text(),'Log in with MetaMask')]";
    private static final String TAB_MOTHER_TREE_XPATH = "//*[text()='Mother Tree']";
    private static final String BUTTON_FARM_TAB_XPATH = "//div[contains(@class,'hidden')]//a[@href='#/farm']";

    public PvuLoginPage(SetupWebdriver setupWebdriver) {
        super(setupWebdriver);
    }

    public PvuLoginPage(SetupWebdriver setupWebdriver, Integer explicitWait) {
        super(setupWebdriver, explicitWait);
    }

    //CHECKS
    public boolean isDashBoardDisplayed() {
        return isElementVisible(By.xpath(TAB_MOTHER_TREE_XPATH));
    }


    //ACTIONS
    @Step("Redirect Pvu Farm")
    public void goToPvuFarm() {
        driver.get("https://marketplace.plantvsundead.com/farm#/login");
        waitForJSandJqueryFinish();
    }

    @Step("Login Pvu Farm")
    public void loginPvuFarm() {
        waitForClickable(By.xpath(BUTTON_LOGIN_XPATH));
        getLoginButton().click();
        waitForJSandJqueryFinish();
    }

    @Step("Click on Farm tab")
    public void clickFarmTab() {
        waitForClickable(By.xpath(BUTTON_FARM_TAB_XPATH));
        getFarmTabButton().click();
        waitForJSandJqueryFinish();
    }

    //AUX METHODS

    //WEBELEMENTS
    private WebElement getPassInput() {
        return driver.findElement(By.id(INPUT_PASS_ID));
    }

    private WebElement getLoginButton() {
        return driver.findElement(By.xpath(BUTTON_LOGIN_XPATH));
    }

    private WebElement getFarmTabButton() {
        return driver.findElement(By.xpath(BUTTON_FARM_TAB_XPATH));
    }

}
