package pageobjects.metamask;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import setup.SetupWebdriver;
import utils.Utils;

public class MetamaskPage extends Utils {

    //LOCATORS - ID
    private static final String INPUT_PASS_ID = "password";

    //LOCATORS - XPATH
    private static final String BUTTON_UNLOCK_XPATH = "//button[text()='Desbloquear']";
    private static final String BUTTON_NEXT_XPATH = "//button[text()='Siguiente']";
    private static final String BUTTON_CONNECT_XPATH = "//button[text()='Conectar']";
    private static final String BUTTON_SIGN_XPATH = "//button[text()='Firmar']";

    public MetamaskPage(SetupWebdriver setupWebdriver) {
        super(setupWebdriver);
    }

    public MetamaskPage(SetupWebdriver setupWebdriver, Integer explicitWait) {
        super(setupWebdriver, explicitWait);
    }


    //CHECKS

    //ACTIONS
    @Step("Login Metamask")
    public void loginMetamask() {
        waitForVisible(By.id(INPUT_PASS_ID));
        getPassInput().sendKeys(readProperty("metaMask_pass"));

        waitForClickable(By.xpath(BUTTON_UNLOCK_XPATH));
        getUnlockButton().click();
        waitForJSandJqueryFinish();
    }

    @Step("Connect Metamask wallet")
    public void connectMetamask() {
        String win = driver.getWindowHandle();
        waitForNumberOfWindows(2);
        changeToNextWindow();
        //select account
        selectAccount();
        //connect
        connectWallet();
        //sign
        signWallet();
        //return to pvu farm win
        waitForNumberOfWindows(1);
        driver.switchTo().window(win);
    }

    private void selectAccount() {
        if (isElementVisibleAngular(By.xpath(BUTTON_NEXT_XPATH), 1)) {
            getNextButton().click();
        } else LOGGER.info("Account already selected");
    }

    private void connectWallet() {
        if (isElementVisibleAngular(By.xpath(BUTTON_CONNECT_XPATH), 1)) {
            getConnectButton().click();
        } else LOGGER.info("Wallet already connected");
    }

    private void signWallet() {
        if (isElementVisibleAngular(By.xpath(BUTTON_SIGN_XPATH), 2)) {
            getSignButton().click();
            LOGGER.info("Wallet successfully connected");
        } else LOGGER.info("Wallet already signed");
    }

    //AUX METHODS

    //WEBELEMENTS
    public WebElement getPassInput() {
        return driver.findElement(By.id(INPUT_PASS_ID));
    }

    private WebElement getUnlockButton() {
        return driver.findElement(By.xpath(BUTTON_UNLOCK_XPATH));
    }

    private WebElement getNextButton() {
        return driver.findElement(By.xpath(BUTTON_NEXT_XPATH));
    }

    private WebElement getConnectButton() {
        return driver.findElement(By.xpath(BUTTON_CONNECT_XPATH));
    }

    private WebElement getSignButton() {
        return driver.findElement(By.xpath(BUTTON_SIGN_XPATH));
    }
}
