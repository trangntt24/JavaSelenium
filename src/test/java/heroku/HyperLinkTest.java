package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HyperLinkTest {

    @Test
    void verifyNavigateHyperlinkSuccessfully(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/status_codes");

        //driver.findElement(By.linkText("200")).click();
        //driver.findElement(By.xpath("//a[.='200']/@href")).click();
        //Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes/200");

        String href = driver.findElement(By.linkText("200")).getAttribute("href"); //href de lay ra link
        driver.findElement(By.linkText("200")).click();
        Assert.assertEquals(driver.getCurrentUrl(), href);
        //driver.findElement(By.linkText("here")).click();
        driver.navigate().back(); //nut back

        href = driver.findElement(By.linkText("301")).getAttribute("href"); //href de lay ra link
        driver.findElement(By.linkText("301")).click();
        Assert.assertEquals(driver.getCurrentUrl(), href);
        driver.findElement(By.linkText("here")).click();

        href = driver.findElement(By.linkText("404")).getAttribute("href"); //href de lay ra link
        driver.findElement(By.linkText("404")).click();
        Assert.assertEquals(driver.getCurrentUrl(), href);
        driver.findElement(By.linkText("here")).click();

        href = driver.findElement(By.linkText("500")).getAttribute("href"); //href de lay ra link
        driver.findElement(By.linkText("500")).click();
        Assert.assertEquals(driver.getCurrentUrl(), href);
        driver.findElement(By.linkText("here")).click();

        driver.quit();
    }
}
