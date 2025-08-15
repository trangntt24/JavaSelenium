package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChromeTest {

    @Test
    public void normalModeTest(){
        //open browser
        WebDriver driver = new ChromeDriver();
        //navigate đến link web
        driver.get("https://www.selenium.dev/");
        //so sánh title web
        Assert.assertEquals(driver.getTitle(),"Selenium");
        //đóng browser
        driver.quit();
    }

    //chạy CI trên github
    @Test
    public void headlessModeTest(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }
}