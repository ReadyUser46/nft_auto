package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import setup.SetupWebdriver;
import utils.Utils;

import java.util.List;

public class PvuFarmPage extends Utils {

    private static final int cellLimit = 1000;
    private static final int humanWait = 30;
    private static final int waterLimit = 90;
    //LOCATORS - XPATH
    private static final String BUTTON_MAP_XPATH = "//*[@src='/_nuxt/img/map@3x.8210b63.png']";

    public PvuFarmPage(SetupWebdriver setupWebdriver, Integer explicitWait) {
        super(setupWebdriver, explicitWait);
    }


    //LOCATORS - ID
    private static final String CELLS_MAP_XPATH = "//*[@class='farm-map-cell']";
    private static final String BUTTON_VISIT_XPATH = "//div[@class='tw-inline-block button_div']//button[text()='Visit']";
    private static final String TEXT_NUM_WATERS_XPATH = "//img[@src='/_nuxt/img/water@3x.d5ca50d.png']//following-sibling::div[@class='plant-attr-number']//span[@class='small']";
    private static final String BUTTON_USE_XPATH = "//img[@src='/_nuxt/img/water@3x.f04d397.png']//parent::*//following-sibling::div/button";
    private static final String TEXT_NUM_PAGES_XPATH = "//p[contains(text(),'of')]";
    private static final String BUTTON_NEXT_PAGE_XPATH = "//*[@src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzIiIGhlaWdodD0iMzIiIHZpZXdCb3g9IjAgMCAzMiAzMiIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xNy42MDk1IDcuMzkwNTJDMTcuMDg4OCA2Ljg2OTgzIDE2LjI0NDUgNi44Njk4MyAxNS43MjM4IDcuMzkwNTJDMTUuMjAzMSA3LjkxMTIyIDE1LjIwMzEgOC43NTU0NCAxNS43MjM4IDkuMjc2MTRMMjAuNDQ3NyAxNEg3LjVDNi42NzE1NyAxNCA2IDE0LjY3MTYgNiAxNS41QzYgMTYuMzI4NCA2LjY3MTU3IDE3IDcuNSAxN0gyMC43ODFMMTUuNzIzOCAyMi4wNTcyQzE1LjIwMzEgMjIuNTc3OSAxNS4yMDMxIDIzLjQyMjEgMTUuNzIzOCAyMy45NDI4QzE2LjI0NDUgMjQuNDYzNSAxNy4wODg4IDI0LjQ2MzUgMTcuNjA5NSAyMy45NDI4TDI0Ljk0MjggMTYuNjA5NUMyNS40NjM1IDE2LjA4ODggMjUuNDYzNSAxNS4yNDQ2IDI0Ljk0MjggMTQuNzIzOUwxNy42MDk1IDcuMzkwNTJaIiBmaWxsPSJ3aGl0ZSIvPgo8L3N2Zz4K']";
    private static final String POPUP_SINGLE_CELL_XPATH = "//*[@src='/_nuxt/img/land_3d.34549cc.svg']";

    //Constructors
    public PvuFarmPage(SetupWebdriver setupWebdriver) {
        super(setupWebdriver);
    }

    //CHECKS

    //ACTIONS
    @Step("Click on map")
    public void clickMap() {
        waitForClickable(By.xpath(BUTTON_MAP_XPATH));
        getMapButton().click();
        waitForJSandJqueryFinish();
    }

    @Step("Visiting Land")
    public void clickVisit() {
        waitForClickable(By.xpath(BUTTON_VISIT_XPATH));
        getVisitButton().click();
        waitForJSandJqueryFinish();
    }

    @Step("Use Water")
    public void clickUseWater() {
        waitForClickable(By.xpath(BUTTON_USE_XPATH));
        getUseButton().click();
        waitForJSandJqueryFinish();
    }

    @Step("Go next page")
    public void clickNextPage() {
        waitForClickable(By.xpath(BUTTON_NEXT_PAGE_XPATH));
        getNextPageButton().click();
        waitForJSandJqueryFinish();
    }

    public void loopCells() {
        List<WebElement> cells = driver.findElements(By.xpath(CELLS_MAP_XPATH));

        for (int i = (cells.size() - 1); i > cellLimit; i--) {
            cells.get(i).click();
            waitForVisible(By.xpath(POPUP_SINGLE_CELL_XPATH));
            if (isElementLocated(By.xpath(BUTTON_VISIT_XPATH), 1)) {
                clickVisit();
                checkPages();
            }
        }
    }

    private void checkPages() {
        LOGGER.info("Number of pages detected = " + getNumPages());
        for (int j = 0; j < getNumPages(); j++) {
            LOGGER.info(String.format("Looping through PAGE --> %s/%s ", j + 1, getNumPages()));
            checkWater();
            clickNextPage();
        }
    }

    private void checkWater() {
        List<WebElement> waters = driver.findElements(By.xpath(TEXT_NUM_WATERS_XPATH));
        LOGGER.info(String.format("Number of PLANTS detected in current page =  %s", waters.size()));

        for (int k = 0; k < waters.size(); k++) {
            LOGGER.info(String.format("Looping through PLANT %s/%s with WATER = %s", k + 1, waters.size(), waters.get(k).getText()));

            if (Integer.parseInt(waters.get(k).getText()) <= PvuFarmPage.waterLimit) {
                LOGGER.info(String.format("[POSITIVE] WATER = %s | Under limite = %s", waters.get(k).getText(), waterLimit));
                waters.get(k).click();
                clickUseWater();
                espera(PvuFarmPage.humanWait);
                //todo capcha
            }
        }
    }


    //AUX METHODS
    public int getNumPages() {
        waitForVisible(By.xpath(TEXT_NUM_PAGES_XPATH));
        return Integer.parseInt(getNumPagesText().getText().replace("of ", ""));
    }

    //WEBELEMENTS
    private WebElement getVisitButton() {
        return driver.findElement(By.xpath(BUTTON_VISIT_XPATH));
    }

    private WebElement getMapButton() {
        return driver.findElement(By.xpath(BUTTON_MAP_XPATH));
    }

    private WebElement getUseButton() {
        return driver.findElement(By.xpath(BUTTON_USE_XPATH));
    }

    private WebElement getNumPagesText() {
        return driver.findElement(By.xpath(TEXT_NUM_PAGES_XPATH));
    }

    private WebElement getNextPageButton() {
        return driver.findElement(By.xpath(BUTTON_NEXT_PAGE_XPATH));
    }

    private WebElement getWaterNums() {
        return driver.findElement(By.xpath(TEXT_NUM_WATERS_XPATH));
    }

    private WebElement getMapCells() {
        return driver.findElement(By.xpath(CELLS_MAP_XPATH));
    }


}
