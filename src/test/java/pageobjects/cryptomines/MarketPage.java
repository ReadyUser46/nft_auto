package pageobjects.cryptomines;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import setup.SetupWebdriver;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MarketPage extends Utils {

    private static final double MAX_SHIP_PRICE = 1.5;
    private static final int INIT_PAGE = 0;
    private static final int END_PAGE = 500;
    private static final int MIN_MP = 113;

    //LOCATORS - ID
    private static final String TOGGLE_VIDEO_ID = "video-toggle";
    private static final String CHECK_3STAR_WORKER_XPATH = "(//div[@class='absolute right-0 bottom-8 grid grid-flow-col gap-1.5  mr-1'][count(./span/*) = %s])[1]";
    private static final String TEXT_MARKETID_WORKER_XPATH = "//span//b[text()='MarketId: ']/parent::span";

    public MarketPage(SetupWebdriver instance) {
        super(instance);
    }

    //LOCATORS - XPATH
    private static final String DIV_BACKGROUND_XPATH = "//div[@class='header-wrapper m-0 row']";
    private static final String BUTTON_STARS_XPATH = "//button[contains(@class,'px-3 py-2 self-center rounded-sm')][text()='%s']";
    private static final String ICON_1STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][1]//span[@style='visibility: hidden;']";
    private static final String ICON_2STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][2]//span[@style='visibility: hidden;']";
    private static final String ICON_3STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][3]//span[@style='visibility: hidden;']";
    private static final String ICON_4STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][4]//span[@style='visibility: hidden;']";
    private static final String ICON_5STAR_XPATH = "(//div[@class='card'])[1]//span[@style='cursor: inherit; display: inline-block; position: relative;'][5]//span[@style='visibility: hidden;']";
    private static final String BUTTON_PRICE_XPATH = "(//div[@class='card'])[%s]//button";
    private static final String BUTTON_NEXTPAGE_XPATH = "//a[@rel='next']";
    private static final String CARDS_XPATHS = "//div[contains(@class,'group relative min')]";
    private static final String CARDS_OK_XPATHS = "(//div[@class='product-box '])[%s]";
    private static final String TEXT_UID_XPATH = "(//div[@class='card'])[%s]//*[@class='ribbon ribbon']//b[text()='UID: ']";
    private static final String ALREADY_SOLD_XPATH = "//div[contains(@class,'group relative min')]//*[@fill-rule='evenodd']";
    private static final String WORKER_AVAILABLE_XPATH = "//div[contains(@class,'group relative min')]//*[contains(@class,'relative mx-auto flex justify-center undefined')]";
    ArrayList<String> listStarXpaths;

    //CHECKS
    public boolean isMarketDisplayed() {
        return isElementVisibleAngularMS(By.xpath(CARDS_XPATHS), 10);
    }

    public boolean isFilterOK(String stars) {
        return isElementVisibleAngularMS(By.xpath(String.format(CHECK_3STAR_WORKER_XPATH, stars)), 10);
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
        clickByJS(getStarButton(stars));
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
            String marketId = getMarketIdText().getText(); //first worker
            loopWorkers(i);
            clickByJS(getNextPageButton());
            espera(1);
            //waitMarketLoad(marketId);
            waitMarketLoad2();
        }
    }

    private void waitMarketLoad(String oldMarketId) {
        String newMarketId;
        newMarketId = getMarketIdText().getText();
        int tries = 5;
        for (int i = 0; i <= tries; i++) {
            if (newMarketId.equals(oldMarketId)) {
                sleepms(1000);
                if (i == 5) clickByJS(getNextPageButton());
            } else {
                return;
            }
        }
        /*while (newMarketId.equals(oldMarketId)) {
            sleepms(1500);
            newMarketId = getMarketIdText().getText();
        }*/
    }

    private void waitMarketLoad2() {
        List<WebElement> elements = driver.findElements(By.xpath(CARDS_XPATHS));
        int intento = 0;
        while (elements.size() < 8) {
            sleepms(1000);
            intento++;
            if (intento == 5) {
                clickByJS(getNextPageButton());
                return;
            }
        }
    }

    @Step("Loop cards")
    public void loopCards(int page) {

        int prices = driver.findElements(By.xpath(CARDS_XPATHS)).size();


        for (int i = 1; i <= prices; i++) {
            if (isElementVisible(By.xpath(String.format(CARDS_OK_XPATHS, i)), 1)) {
                System.out.printf("Ship %s ENABLED! in PAGE %s\n", i, page);
                printShipPrice(i, page);
            } else {
                System.out.printf("Ship %s DISABLED in PAGE %s\n", i, page);
            }
        }
    }

    @Step("Loop workers")
    public void loopWorkers(int page) {

        String buynow_xpath = "//button[text()='Buy Now']";
        String mp_xpath_plural = "//*[text()=' MP ']/parent::div//div[@class='self-center text-lg font-bold']";
        String mp_xpath = "(//*[text()=' MP ']/parent::div//div[@class='self-center text-lg font-bold'])[%s]";
        String price_xpath = "//div[contains(@class,'group relative min')]//*[contains(@class,'relative mx-auto flex justify-center undefined')]";
        //int numAvailables = driver.findElements(By.xpath(WORKER_AVAILABLE_XPATH)).size();

        if (isElementLocated(By.xpath(mp_xpath_plural), 1)) {
            int numAvailables = driver.findElements(By.xpath(mp_xpath_plural)).size();
            List<WebElement> mps = driver.findElements(By.xpath(mp_xpath_plural));
            List<WebElement> prices = driver.findElements(By.xpath(price_xpath));

            for (WebElement element : mps) {
                int worker_mp = getMP(element);
                if (worker_mp >= MIN_MP) {
                    //System.out.println("stop");
                    String winmarket = driver.getWindowHandle();
                    clickByJS(prices.get(mps.indexOf(element)));
                    String worker_price = prices.get(mps.indexOf(element)).getText();
                    clickByJS(driver.findElement(By.xpath(buynow_xpath)));
                    /*waitForNumberOfWindows(2);
                    changeToNextWindow();
                    waitForJSandJqueryFinish();
                    //rechazar
                    if (isElementVisibleAngularMS(By.xpath("//div[text()='Fondos insuficientes.']"),3)) {

                        System.out.println("fondos insufi");
                        clickByJS(driver.findElement(By.xpath("//button[text()='Rechazar']")));
                        driver.close();
                        driver.switchTo().window(winmarket);
                        clickByJS(getNextPageButton());

                    }else {*/
                    System.out.println("comprar MP = " + worker_mp);
                    System.out.println("Comprar?");
                    //driver.navigate().refresh();
                    //waitForJSandJqueryFinish();
                    //selectStars("3");
                    //loopPages();


                }
            }

        }
    }

    @Step("Loop workers")
    public void loopWorkers4() {

        String buynow_xpath = "//button[text()='Buy Now']";
        String mp_xpath_plural = "//*[text()=' MP ']/parent::div//div[@class='self-center text-lg font-bold']";
        String mp_xpath = "(//*[text()=' MP ']/parent::div//div[@class='self-center text-lg font-bold'])[%s]";
        String price_xpath = "//div[contains(@class,'group relative min')]//*[contains(@class,'relative mx-auto flex justify-center undefined')]";
        //int numAvailables = driver.findElements(By.xpath(WORKER_AVAILABLE_XPATH)).size();

        if (isElementLocated(By.xpath(mp_xpath_plural), 1)) {
            int numAvailables = driver.findElements(By.xpath(mp_xpath_plural)).size();
            List<WebElement> mps = driver.findElements(By.xpath(mp_xpath_plural));
            List<WebElement> prices = driver.findElements(By.xpath(price_xpath));

            for (WebElement element : mps) {
                int worker_mp = getMP(element);
                if (worker_mp >= 180) {
                    //System.out.println("stop");
                    String winmarket = driver.getWindowHandle();
                    clickByJS(prices.get(mps.indexOf(element)));
                    String worker_price = prices.get(mps.indexOf(element)).getText();
                    clickByJS(driver.findElement(By.xpath(buynow_xpath)));

                    System.out.println("comprar MP = " + worker_mp);
                    System.out.println("Comprar?");

                }
                driver.navigate().refresh();
                espera();
                waitForJSandJqueryFinish();
                selectStars("4");
                espera();
            }

        }
    }

    private int getMP(WebElement element) {
        int mp = 0;
        try {
            String mptext = element.getText(); //100 MP
            mp = Integer.parseInt(mptext.replace("MP", "").trim());
        } catch (StaleElementReferenceException e) {
            System.out.println("staleeeeee for " + mp);
        }

        return mp;
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

    private WebElement getStarsButton_deprecated(String stars) {
        return driver.findElement(By.xpath(String.format(BUTTON_STARS_XPATH, stars)));
    }

    private WebElement getAnimToggle() {
        return driver.findElement(By.id(TOGGLE_VIDEO_ID));
    }

    private WebElement getNextPageButton() {
        return driver.findElement(By.xpath(BUTTON_NEXTPAGE_XPATH));
    }

    private WebElement getStarButton(String stars) {
        return driver.findElement(By.xpath(String.format(BUTTON_STARS_XPATH, stars)));
    }

    private WebElement getMarketIdText() {
        return driver.findElement(By.xpath(TEXT_MARKETID_WORKER_XPATH));
    }
}
