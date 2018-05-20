import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
public class ParkingCalculationForm {
    WebDriver driver;

    @BeforeClass
    public void connectToWebSite() {
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        driver = new FirefoxDriver();
        driver.get("http://adam.goucher.ca/parkcalc/index.php");
    }

    @AfterClass
    public void closeWebBrowser() throws InterruptedException {
        Thread.sleep(5_000);
        driver.findElement(By.name("Submit")).click();
        Thread.sleep(3_000);
        driver.quit();
    }

    @DataProvider
    public Object[][] getHours() {
        return new Object[][]{{"4:20","16/5/2018", "7:00","20/5/2018"}};
    }

    @Test(dataProvider = "getHours")
    public void CalculationTimeInputs(String from,String fromDate, String to, String toDate) {
        WebElement begin = driver.findElement(By.id("EntryTime"));
        begin.clear();
        begin.sendKeys(from);
        WebElement beginDate = driver.findElement(By.id("EntryDate"));
        beginDate.clear();
        beginDate.sendKeys(fromDate);
        WebElement end = driver.findElement(By.id("ExitTime"));
        end.clear();
        end.sendKeys(to);
        WebElement endDate = driver.findElement(By.id("ExitDate"));
        endDate.clear();
        endDate.sendKeys(toDate);
    }

    @Test
    public void CalculationCheckBoxAndRadioButton() {
        WebElement entryRadio = driver.findElement(By
                .cssSelector("input[name='EntryTimeAMPM'][value='AM']"));
        entryRadio.click();
    }

    @Test
    public void CalculationDropDown() {
        Select lotDropDown = new Select(driver.findElement(By.id("Lot")));
        lotDropDown.selectByValue("LTG");
    }
}