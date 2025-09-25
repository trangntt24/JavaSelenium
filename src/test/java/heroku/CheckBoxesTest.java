package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckBoxesTest {
    /** TC02: Checkboxes : Check to a box*/
    @Test(priority = 0) //sap xep thu tu chay testcase dung priority
    void VerifySelectCheckboxesSuccessfully(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.cssSelector("form#checkboxes input:nth-child(1)")); //nth-child(...) doi tuong dua con thu may cua tag name form
        //WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]")); //1 dau '/' tuc la tim con ruot cua form

        if (!checkbox1.isSelected()) checkbox1.click();
        if (!checkbox2.isSelected()) checkbox2.click();

        Assert.assertTrue(checkbox1.isSelected());
        Assert.assertTrue(checkbox2.isSelected());

        driver.quit();
    }

    @Test(priority = 1)
    void VerifyDeselectCheckboxesSuccessfully(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]")); //1 dau '/' tuc la tim con ruot cua form

        if (checkbox1.isSelected()) checkbox1.click();
        if (checkbox2.isSelected()) checkbox2.click();

        Assert.assertFalse(checkbox1.isSelected());
        Assert.assertFalse(checkbox2.isSelected());

        driver.quit();
    }
}
