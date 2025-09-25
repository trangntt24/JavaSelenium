package browsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation; //lấy đúng với version chrome
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.model.ConnectionType;
import org.openqa.selenium.devtools.v138.performance.Performance;
import org.openqa.selenium.devtools.v138.performance.model.Metric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    //test case kiểm tra Responsive trên web
    @Test
    void mobileViewPortraitTest(){
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 344);
        deviceMetrics.put("height", 882);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //tcs chạy version chrome
    @Test
    void openBrowserWithOldVersion(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBrowserVersion("136");  //thay đổi giá trị tùy vào bản chrome muốn test

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //tcs giả lập tọa độ
    @Test
    void openBrowserWithFakeGeoLocation(){
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        // Mountain view
        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(37.386052),
                Optional.of(-122.083851),
                Optional.of(1),
                Optional.of(100),
                Optional.of(1),
                Optional.of(1),
                Optional.of(0)
        ));
        driver.get("https://the-internet.herokuapp.com/geolocation");
        driver.findElement(By.xpath("//button[.='Where am I?']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#lat-value")).getText(),"37.386052");
        Assert.assertEquals(driver.findElement(By.cssSelector("#long-value")).getText(),"-122.083851");

        driver.quit();
    }

    // giả lập Network
    @Test
    void simulate3GNetworkCondition(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        // Enable Network emulation
        devTools.send(Network.enable(Optional.of(100000000), Optional.empty(), Optional.empty(), Optional.empty()));

        // Set network conditions to emulate 3G or 4G
        devTools.send(Network.emulateNetworkConditions(
                false,
                100,
                75000,
                25000,
                Optional.of(ConnectionType.CELLULAR2G),
                Optional.of(0),
                Optional.of(0),
                Optional.of(false)
        ));
        driver.get("https://selenium.dev");
    }

    //bắt request gửi đi và response gửi về (API)
    @Test
    void interceptionNetwork(){
        WebDriver driver = new ChromeDriver();
        DevTools devTool = ((HasDevTools) driver).getDevTools();

        devTool.createSession();
        devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        devTool.addListener(Network.requestWillBeSent(), requestSent -> {
            System.out.println("Request URL => " + requestSent.getRequest().getUrl());
            System.out.println("Request Method => " + requestSent.getRequest().getMethod());
            System.out.println("Request Headers => " + requestSent.getRequest().getHeaders().toString());
            System.out.println("------------------------------------------------------");
        });

        devTool.addListener(Network.responseReceived(), responseReceived -> {
            System.out.println("Response Url => " + responseReceived.getResponse().getUrl());
            System.out.println("Response Status => " + responseReceived.getResponse().getStatus());
            System.out.println("Response Headers => " + responseReceived.getResponse().getHeaders().toString());
            System.out.println("Response MIME Type => " + responseReceived.getResponse().getMimeType().toString());
            System.out.println("------------------------------------------------------");
        });

        driver.get("https://selenium.dev");
        driver.quit();
        driver.quit();
    }

    //do performance metrics
    @Test
    void openSeleniumHomePageAndCapturePerformanceMetrics(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());
        driver.get("https://selenium.dev");

        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();

        for(Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }
    }

}