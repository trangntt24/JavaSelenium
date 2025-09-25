package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerTest {
    @Test
    void verifySelectDepartDateSuccessfully(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.vietnamairlines.com/vn/vi/home");

        driver.findElement(By.id("cookie-agree")).click();
        driver.findElement(By.id("roundtrip-date-depart")).click();

        LocalDate today = LocalDate.now();
        LocalDate next2Week = LocalDate.now().plusWeeks(2);

        String next2WeekStr = next2Week.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(next2Week.getDayOfMonth());
        WebElement departMonthTable;
        try {
            if(today.getMonthValue() < next2Week.getMonthValue()){
                departMonthTable = driver
                        .findElements(By.className("ui-datepicker-calendar"))
                        .get(1);
            }else {
                departMonthTable = driver
                        .findElements(By.className("ui-datepicker-calendar"))
                        .getFirst();
            }

            departMonthTable.findElements(By.xpath("./tbody/tr/td/a"))
                    .forEach(dateCell -> {
                        if(dateCell.getText().contains(String.valueOf(next2Week.getDayOfMonth())))
                            dateCell.click();
                    });
        }
        catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }

        String departDate = driver.findElement(By.id("roundtrip-date-depart")).getAttribute("value"); //gtri value trong Accessibility
        Assert.assertEquals(departDate,next2WeekStr);

        driver.quit();
    }
}
