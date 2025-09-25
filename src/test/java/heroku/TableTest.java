package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableTest {
    //cách 1
    /**@Test
    void verifyLargestDuePerson(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tables");
        List<WebElement> cells = driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td"));  //lay all cac ô

        List<Double> dueValue = driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td[4]")) //lay gia tri cot Due
                .stream()
                .map(WebElement::getText)  //lay text ($50.00, $51.00, $100.00, $50.00)
                .map(d ->Double.parseDouble(d.replace("$","")))  //bo ky tu dau tien lay ky tu so de lay max
                .toList();
        Double maxDue = dueValue.stream().max(Comparator.naturalOrder()).get();  //lay gtri max
        List<String> actualLargeDuePerson = new ArrayList<>(); //tao 1 ds trong
        for (int i = 0; i <dueValue.size(); i++){
            if(dueValue.get(i).equals(maxDue)){
                String maxDuePersonFirstNameLocator = String.format("//table[@id='table1']/tbody/tr[%d]/td[2]",i + 1);
                String maxDuePersonLastNameLocator = String.format("//table[@id='table1']/tbody/tr[%d]/td[1]",i + 1);
                String firstName = driver.findElement(By.xpath(maxDuePersonFirstNameLocator)).getText().trim();
                String lastName = driver.findElement(By.xpath(maxDuePersonLastNameLocator)).getText().trim();
                actualLargeDuePerson.add(String.format("%s %s",firstName,lastName));  //add ten nguoi co gtri max vao bang
            }
        }
        Assert.assertEquals(actualLargeDuePerson, List.of("Jason Doe"));

        driver.quit();

    }

    @Test
    void verifyLeastDuePerson(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tables");
        List<WebElement> cells = driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td"));
        List<Double> dueValue = driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td[4]"))
                .stream()
                .map(WebElement::getText)
                .map(d ->Double.parseDouble(d.replace("$","")))
                .toList();
        Double minDue = dueValue.stream().min(Comparator.naturalOrder()).get();
        List<String> actuaLeastDuePerson = new ArrayList<>();
        for (int i = 0; i < dueValue.size(); i++){
            if (dueValue.get(i).equals(minDue)){
                String minDuePersonFirstNameLocator = String.format("//table[@id='table1']/tbody/tr[%d]/td[2]", i + 1);
                String minDuePersonLastNameLocator = String.format("//table[@id='table1']/tbody/tr[%d]/td[1]", i + 1);
                String firstName = driver.findElement(By.xpath(minDuePersonFirstNameLocator)).getText().trim();
                String lastName = driver.findElement(By.xpath(minDuePersonLastNameLocator)).getText().trim();
                actuaLeastDuePerson.add(String.format("%s %s",firstName,lastName));
            }
        }
        Assert.assertEquals(actuaLeastDuePerson, List.of("John Smith", "Tim Conway"));

        driver.quit();

    }**/

    //cách 2
    List<Person> table1 = new ArrayList<>();
    @BeforeClass
    void getTable1(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/tables");
        //tim doi tuong trong doi tuong
        driver.findElements(By.xpath("//table[@id='table1']/tbody/tr"))
        .forEach(row -> {
            String firstName = row.findElement(By.xpath("./td[2]")).getText();
            String lastName = row.findElement(By.xpath("./td[1]")).getText();
            String due = row.findElement(By.xpath("./td[4]")).getText();
            table1.add(new Person(firstName, lastName, due));
        });
        driver.quit();
    }

    @Test
    void verifyLargestDuePerson() {
        double maxDue = table1.stream().max(Comparator.comparing(Person::getDue)).get().getDue();
        List<String> maxDuePerson = table1.stream().filter(person -> person.getDue() == maxDue).map(Person::getFullName).toList();
        Assert.assertEquals(maxDuePerson, List.of("Jason Doe"));
    }

    @Test
    void verifyMinDuePerson() {
        double minDue = table1.stream().min(Comparator.comparing(Person::getDue)).get().getDue();
        List<String> minDuePerson = table1.stream().filter(person -> person.getDue() == minDue).map(Person::getFullName).toList();
        Assert.assertEquals(minDuePerson, List.of("John Smith","Tim Conway"));
    }
}
