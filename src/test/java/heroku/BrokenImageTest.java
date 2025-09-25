package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.Objects;

public class BrokenImageTest {
    @Test
    void verifyBrokenImage(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/broken_images");
        driver.findElements(By.xpath("//div[@class='example']/img"))
                .forEach(image ->{
                    System.out.println(image.getAttribute("src"));
//                    System.out.println(image.getDomProperty("naturalHeight"));
//                    System.out.println(image.getDomProperty("naturalWidth"));
                    //Kiem tra naturalHeight va naturalWidth trong Properties
                    if(Objects.equals(image.getDomProperty("naturalHeight"), "0") && Objects.equals(image.getDomProperty("naturalWidth"), "0")){
                        System.out.println("image not found");
                    } else System.out.println("found image");

                });
    }
}
