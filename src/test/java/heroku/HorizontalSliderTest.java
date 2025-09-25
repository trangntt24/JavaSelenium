package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class HorizontalSliderTest {
    @Test
    void verifyAbleSlider(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        Actions actions = new Actions(driver);
        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
        for (int i = 0; i < 10; i++) {
            actions
                    .clickAndHold(slider)
                    .moveByOffset(5,0)
                    .perform();
        }

    }
}
