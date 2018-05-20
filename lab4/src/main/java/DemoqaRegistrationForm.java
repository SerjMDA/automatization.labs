import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

public class DemoqaRegistrationForm {
    WebDriver driver;

    @BeforeClass
    public void connectToWebSite() {
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        driver = new FirefoxDriver();
        driver.get("http://demoqa.com/registration/");
    }

    @AfterClass
    public void closeWebBrowser() throws InterruptedException {
        Thread.sleep(5_000);
        driver.findElement(By.name("pie_submit")).click();
        Thread.sleep(5_000);
        driver.quit();
    }

    @DataProvider
    public Object[][] getNames() {
        return new Object[][]{{"Mihail", "Dovbenco"}};

    }

    @Test(dataProvider = "getNames")
    public void FormFirstNameAndLastNameInputs(String name, String surname) {
        WebElement firstName = driver.findElement(By.id("name_3_firstname"));
        firstName.sendKeys(name);
        WebElement lastName = driver.findElement(By.id("name_3_lastname"));
        lastName.sendKeys(surname);
    }

    @Test
    public void FormCheckBoxAndRadioButton() {
        WebElement danceCheckBox = driver.findElement(By.cssSelector("input[value='dance']"));
        danceCheckBox.click();
        WebElement readingCheckBox = driver.findElement(By.cssSelector("input[value='reading']"));
        readingCheckBox.click();
        WebElement singleRadioButton = driver.findElement(By.cssSelector("input[value='single']"));
        singleRadioButton.click();
    }

    @Test
    public void FormDropDown() {
        Select countryDropDown = new Select(driver.findElement(By.id("dropdown_7")));
        countryDropDown.selectByValue("Moldova");

        Select monthDropDown = new Select(driver.findElement(By.id("mm_date_8")));
        monthDropDown.selectByValue("10");

        Select dayDropDown = new Select(driver.findElement(By.id("dd_date_8")));
        dayDropDown.selectByValue("17");

        Select yearDropDown = new Select(driver.findElement(By.id("yy_date_8")));
        yearDropDown.selectByValue("1996");
    }

    @Test
    public void FormFileInput() {
        driver.findElement(By.id("profile_pic_10")).sendKeys("/Users/mikedovbenco/logo.jpeg");
    }

    @Test
    public void FormInputs() {
        WebElement phoneNumber = driver.findElement(By.id("phone_9"));
        phoneNumber.sendKeys("061345672");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("sergheev");
        WebElement eMail = driver.findElement(By.id("email_1"));
        eMail.sendKeys("mihail.d@gmail.com");
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("good communication skills,creativity,adaptability,time management");
        WebElement password = driver.findElement(By.id("password_2"));
        password.sendKeys("123456qaz");
        WebElement confirmPassword = driver.findElement(By.id("confirm_password_password_2"));
        confirmPassword.sendKeys("123456qaz");
    }

}