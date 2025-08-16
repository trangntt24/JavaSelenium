package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Edge {
    @Test
    void openBrowserWithDefaultMode(){
        System.setProperty("webdriver.edge.driver", "E:\\Software\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");

        driver.quit();
    }
}
