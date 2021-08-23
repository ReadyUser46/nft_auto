package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import setup.SetupWebdriver;
import utils.Utils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PvuFarmPage extends Utils {

    private static final int cellStart = 593;
    private static final int cellLimit = 500;
    private static final int waterLimit = 90;
    private int plantsCounter = 0;


    public PvuFarmPage(SetupWebdriver setupWebdriver, Integer explicitWait) {
        super(setupWebdriver, explicitWait);
    }

    //LOCATORS - ID
    private static final String LOADING_PAGE_ID = "loader-1";

    //LOCATORS - XPATH
    private static final String BUTTON_MAP_XPATH = "//*[@src='/_nuxt/img/map@3x.8210b63.png']";
    private static final String CELLS_MAP_XPATH = "//*[@class='farm-map-cell']";
    private static final String BUTTON_VISIT_XPATH = "//div[@class='tw-inline-block button_div']//button[text()='Visit']";
    private static final String TEXT_NUM_WATERS_XPATH = "//img[@src='/_nuxt/img/water@3x.d5ca50d.png']//following-sibling::div[@class='plant-attr-number']//span[@class='small']";
    private static final String BUTTON_USE_XPATH = "//img[@src='/_nuxt/img/water@3x.f04d397.png']//parent::*//following-sibling::div/button";
    private static final String TEXT_NUM_PAGES_XPATH = "//p[contains(text(),'of')]";
    private static final String BUTTON_NEXT_PAGE_XPATH = "//*[@src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzIiIGhlaWdodD0iMzIiIHZpZXdCb3g9IjAgMCAzMiAzMiIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xNy42MDk1IDcuMzkwNTJDMTcuMDg4OCA2Ljg2OTgzIDE2LjI0NDUgNi44Njk4MyAxNS43MjM4IDcuMzkwNTJDMTUuMjAzMSA3LjkxMTIyIDE1LjIwMzEgOC43NTU0NCAxNS43MjM4IDkuMjc2MTRMMjAuNDQ3NyAxNEg3LjVDNi42NzE1NyAxNCA2IDE0LjY3MTYgNiAxNS41QzYgMTYuMzI4NCA2LjY3MTU3IDE3IDcuNSAxN0gyMC43ODFMMTUuNzIzOCAyMi4wNTcyQzE1LjIwMzEgMjIuNTc3OSAxNS4yMDMxIDIzLjQyMjEgMTUuNzIzOCAyMy45NDI4QzE2LjI0NDUgMjQuNDYzNSAxNy4wODg4IDI0LjQ2MzUgMTcuNjA5NSAyMy45NDI4TDI0Ljk0MjggMTYuNjA5NUMyNS40NjM1IDE2LjA4ODggMjUuNDYzNSAxNS4yNDQ2IDI0Ljk0MjggMTQuNzIzOUwxNy42MDk1IDcuMzkwNTJaIiBmaWxsPSJ3aGl0ZSIvPgo8L3N2Zz4K']";
    private static final String POPUP_SINGLE_CELL_XPATH = "//*[@src='/_nuxt/img/land_3d.34549cc.svg']";
    private static final String TEXT_OWNER_TITLE_XPATH = "//*[@class='owner-title']";

    //Constructors
    public PvuFarmPage(SetupWebdriver setupWebdriver) {
        super(setupWebdriver);
    }

    //CHECKS

    private static void waitForUserInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Exception Thrown %s. Script safe to continue?: ");
                String line = scanner.nextLine();
                System.out.printf("User input was: %s%n", line);
            }
        } catch (IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
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

    //ACTIONS
    @Step("Click on map")
    public void clickMap() {
        try {
            waitForClickable(By.xpath(BUTTON_MAP_XPATH));
            getMapButton().click();
            waitForJSandJqueryFinish();
        } catch (ElementClickInterceptedException e) {
            LOGGER.severe(String.format(" MAP MAP MAP MAP MAP\nclick intercepeted in --> %s", "click map"));
            espera(8); //time to solve the issue before script tears down
            getMapButton().click();
        }

    }

    @Step("Loop through cells")
    public void loopCells() {
        List<WebElement> cells;
        int numCells = driver.findElements(By.xpath(CELLS_MAP_XPATH)).size();

        try {
            for (int i = cellStart; i > cellLimit; i--) {
                LOGGER.info(String.format("Looping through CELL --> %s/%s ", i, numCells));
                cells = driver.findElements(By.xpath(CELLS_MAP_XPATH));
                cells.get(i).click();
                waitPvuLoads();
                waitForVisible(By.xpath(POPUP_SINGLE_CELL_XPATH));
                if (isElementLocated(By.xpath(BUTTON_VISIT_XPATH), 1)) {
                    clickVisit();
                    waitPvuLoads();
                    checkPages();
                } else {
                    driver.navigate().refresh();
                }
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.severe(String.format(" = stale element reference in --> %s", "loop cells"));

        }
    }

    private void checkPages() {
        LOGGER.info("Number of pages detected = " + getNumPages());
        for (int j = 0; j <= getNumPages() - 1; j++) {
            LOGGER.info(String.format("Looping through PAGE --> %s/%s ", j + 1, getNumPages()));
            checkWater();
            if (j == getNumPages() || getNumPages() == 1) { /*dont click next page if current page is last one or there's only one page*/
                break;
            } else clickNextPage();
        }
        clickMap();
    }

    private void checkWater() {
        List<WebElement> waters = driver.findElements(By.xpath(TEXT_NUM_WATERS_XPATH));
        LOGGER.info(String.format("Number of PLANTS detected in current page =  %s", waters.size()));

        try {
            for (int k = 0; k < waters.size(); k++) {
                LOGGER.info(String.format("Looping through PLANT %s/%s with WATER = %s", k + 1, waters.size(), waters.get(k).getText()));
                plantsCounter++;

                if (Integer.parseInt(waters.get(k).getText()) <= PvuFarmPage.waterLimit) {
                    LOGGER.warning(String.format("[ATENTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!]\n" +
                            "[POSITIVE] WATER = %s in PLANT %s| Under limite = %s", waters.get(k).getText(), k, waterLimit));
                    scrollToElement(waters.get(k));
                    waters.get(k).click();
                    clickUseWater();
                    waitForUserInput(); //todo manually click background when congrants windows is displayed
                }
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.severe(String.format(" = stale element reference in --> %s", "loop plants"));

        }
    }

    public void clickNextPage() {
        try {
            waitForClickable(By.xpath(BUTTON_NEXT_PAGE_XPATH));
            getNextPageButton().click();
            waitForJSandJqueryFinish(); //todo wait invisible loading
            waitPvuLoads();
        } catch (ElementClickInterceptedException e) {
            LOGGER.severe(String.format(" click intercepeted in --> %s", "click next"));

        }
    }

    //AUX METHODS
    public int getNumPages() {
        waitForVisible(By.xpath(TEXT_OWNER_TITLE_XPATH));
        if (isElementVisibleMS(By.xpath(TEXT_NUM_PAGES_XPATH), 500)) {
            return Integer.parseInt(getNumPagesText().getText().replace("of ", ""));
        } else return 1;
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

    public int getPlantsCounter() {
        return plantsCounter;
    }


}
