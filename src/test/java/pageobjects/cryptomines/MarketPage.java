package pageobjects.cryptomines;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import setup.SetupWebdriver;
import utils.Utils;

import java.util.ArrayList;

public class MarketPage extends Utils {

    private static final double MAX_SHIP_PRICE = 1.5;
    private static final int INIT_PAGE = 0;
    private static final int END_PAGE = 3;
    //LOCATORS - ID
    private static final String TOGGLE_VIDEO_ID = "video-toggle";


    public MarketPage(SetupWebdriver instance) {
        super(instance);
    }

    //LOCATORS - XPATH
    private static final String DIV_BACKGROUND_XPATH = "//div[@class='header-wrapper m-0 row']";
    private static final String BUTTON_STARS_XPATH = "//button[@class='btn btn-light '][text()='%s']";
    private static final String ICON_1STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][1]//span[@style='visibility: hidden;']";
    private static final String ICON_2STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][2]//span[@style='visibility: hidden;']";
    private static final String ICON_3STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][3]//span[@style='visibility: hidden;']";
    private static final String ICON_4STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][4]//span[@style='visibility: hidden;']";
    private static final String ICON_5STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][5]//span[@style='visibility: hidden;']";
    private static final String BUTTON_PRICE_XPATH = "(//div[@class='card'])[%s]//button";
    private static final String BUTTON_NEXTPAGE_XPATH = "//a[@rel='next']";
    private static final String CARDS_XPATHS = "//div[@class='card']";
    private static final String CARDS_OK_XPATHS = "(//div[@class='product-box '])[%s]";
    private static final String TEXT_UID_XPATH = "(//div[@class='card'])[%s]//*[@class='ribbon ribbon']//b[text()='UID: ']";
    ArrayList<String> listStarXpaths;

    //CHECKS
    public boolean isMarketDisplayed() {
        return isElementVisible(By.xpath(CARDS_XPATHS));
    }

    public boolean isFilterOK(int stars) {
        return isElementVisible(By.xpath(getStarXpath(stars)));
    }

    //ACTIONS
    @Step("Redirect CryptoMines app --> {0}")
    public void goToUrl(String url) {
        driver.get(url);
        waitForJSandJqueryFinish();
    }

    @Step("Select stars --> {0}")
    public void selectStars(String stars) {
        waitForClickable(By.xpath(String.format(BUTTON_STARS_XPATH, stars)));
        getStarsButton(stars).click();
        waitForJSandJqueryFinish();
    }

    @Step("Disable animations")
    public void disableAnimations() {
        waitForClickable(By.id(TOGGLE_VIDEO_ID));
        espera(4);
        getAnimToggle().click();
        waitForJSandJqueryFinish();
    }

    @Step("Loop pages")
    public void loopPages() {
        for (int i = INIT_PAGE; i <= END_PAGE - 1; i++) {
            loopCards(i);
            getNextPageButton().click();
            espera(1);
        }
    }

    @Step("Loop cards")
    public void loopCards(int page) {

        int cards = driver.findElements(By.xpath(CARDS_XPATHS)).size();

        for (int i = 1; i <= cards; i++) {
            if (isElementVisible(By.xpath(String.format(CARDS_OK_XPATHS, i)), 1)) {
                System.out.printf("Ship %s ENABLED! in PAGE %s\n", i, page);
                printShipPrice(i, page);
            } else {
                System.out.printf("Ship %s DISABLED in PAGE %s\n", i, page);
            }
        }
    }


    @Step("Get ship price")
    public void printShipPrice(int card, int page) {
        String price = getShipPriceButton(String.valueOf(card)).getText();
        double price_value = Double.parseDouble(price.replace("ETERNAL", "").trim());
        if (price_value <= MAX_SHIP_PRICE) {
            System.out.printf("Ship %s--> %s in PAGE %s = %s\n", card, getShipUid(card).getText(), page, price);
        }
    }

    //AUX METHODS
    private String getStarXpath(int stars) {
        listStarXpaths = new ArrayList<>();
        listStarXpaths.add(ICON_1STAR_XPATH);
        listStarXpaths.add(ICON_2STAR_XPATH);
        listStarXpaths.add(ICON_3STAR_XPATH);
        listStarXpaths.add(ICON_4STAR_XPATH);
        listStarXpaths.add(ICON_5STAR_XPATH);

        return listStarXpaths.get(stars);
    }

    //WEBELEMENTS
    private WebElement getShipPriceButton(String card) {
        return driver.findElement(By.xpath(String.format(BUTTON_PRICE_XPATH, card)));
    }

    private WebElement getShipUid(int card) {
        return driver.findElement(By.xpath(String.format(TEXT_UID_XPATH, card)));
    }

    private WebElement getBackgroundClick() {
        return driver.findElement(By.xpath(DIV_BACKGROUND_XPATH));
    }

    private WebElement getStarsButton(String stars) {
        return driver.findElement(By.xpath(String.format(BUTTON_STARS_XPATH, stars)));
    }

    private WebElement getAnimToggle() {
        return driver.findElement(By.id(TOGGLE_VIDEO_ID));
    }

    private WebElement getNextPageButton() {
        return driver.findElement(By.xpath(BUTTON_NEXTPAGE_XPATH));
    }
}
