package heroku;

import base.BaseTest;
import heroku.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static common.Browser.*;

public class LoginTest extends BaseTest {
    /** TC01: Form Authentication : Login successful with valid credentials*/
    LoginPage loginPage;
    @BeforeMethod
    void openPage(){
        loginPage = new LoginPage();
        loginPage.open();
    }

    @DataProvider
    Object[][] testData(){
        return new Object[][]{
                {"","SuperSecretPassword!","https://the-internet.herokuapp.com/login","error","Your username is invalid!"},
                {"tomsmith","","https://the-internet.herokuapp.com/login","error","Your password is invalid!"},
                {"aland","SuperSecretPassword!","https://the-internet.herokuapp.com/login","error","Your username is invalid!"},
                {"tomsmith","Tranggg9090","https://the-internet.herokuapp.com/login","error","Your password is invalid!"},
        };
    }

//    @Test
//    void successfullyWithValidCredential() {
//        loginPage.login("tomsmith","SuperSecretPassword!");
//        Assert.assertEquals(getCurrentUrl(),"https://the-internet.herokuapp.com/secure");
//
//        String successMessage = loginPage.getFlashMessage("success");
//        Assert.assertTrue(successMessage.contains("You logged into a secure area!"));
//    }

    @Test(dataProvider = "testData") //ten trong ngoac phai giong o tren
    void errorWithInvalidCredential(String userName, String password, String expectedUrl, String expectedMessageType, String expectedMessageContent){
        loginPage.login(userName,password);
        Assert.assertEquals(getCurrentUrl(),expectedUrl);

        String errorMessage = loginPage.getFlashMessage(expectedMessageType);
        Assert.assertTrue(errorMessage.contains(expectedMessageContent));
    }
}
