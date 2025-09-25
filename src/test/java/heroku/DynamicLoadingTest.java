package heroku;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class DynamicLoadingTest {
    @Test
    void verifyContentLoading() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.xpath("//button[.='Start']")).click();
        //set thoi gian cho cung, mac dinh trong bao lau
//        Thread.sleep(10000);
//        String content = driver.findElement(By.id("finish")).getText();

        //set thoi gian cho linh hoat
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        String content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish"))).getText(); //doi tuong xuat hien nhung minh ko thay (xuat hien o DOM tuc la HTML)
        String content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish"))).getText();  //cho den khi nao doi tuong xuat hien (minh co the thay)

        Assert.assertTrue(content.contains("Hello World!"));
        driver.quit();
    }

    @Test
    void verifyTakesScreenshot() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        driver.findElement(By.xpath("//button[.='Start']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish"))).getText();
        Assert.assertTrue(content.contains("Hello World!"));

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(String.format("target/screenshot-%s-%s.png", "context-menu", System.currentTimeMillis()));
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
}
