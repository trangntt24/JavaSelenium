package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NestedFrameTest {
    @Test
    void verifyNestedFrameContent(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        String content = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(content.contains("LEFT"));

        driver.switchTo().parentFrame(); //frame-top
        driver.switchTo().frame("frame-middle");
        content = driver.findElement(By.id("content")).getText();
        Assert.assertTrue(content.contains("MIDDLE"));

        driver.switchTo().parentFrame(); //frame-top
        driver.switchTo().frame("frame-right");
        content = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(content.contains("RIGHT"));

        driver.switchTo().defaultContent(); //ra cai goc cua no
        driver.switchTo().frame("frame-bottom");
        content = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(content.contains("BOTTOM"));

        driver.quit();
    }
}
