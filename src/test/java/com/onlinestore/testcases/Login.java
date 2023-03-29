package com.onlinestore.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.onlinestore.base.Base;
import com.onlinestore.pages.AccountPage;
import com.onlinestore.pages.HomePage;
import com.onlinestore.pages.LoginPage;
import com.onlinestore.utility.Utilities;

public class Login extends Base{
	
	LoginPage loginPage;
	//invoking the Base class constructor
	public Login() {
		super();
	}
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver=initializeBrowserAndOpenApp(prop.getProperty("browser"));
		HomePage homePage=new HomePage(driver);
		loginPage = homePage.navigateToLoginPage();
	
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1,dataProvider="validCredentials")
	public void verifyLoginWithValidCredentials(String email,String password) {
		
		AccountPage accountPage = loginPage.loginWithCredentials(email,password);
		
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit your account information option is not displayed");
	}

	@Test(priority=2)
	public void verifyLoginWithInvalidCredentials() {
		
		loginPage.loginWithCredentials(Utilities.generateRandomEmailAddress(), dataProp.getProperty("invalidPassword"));
	
		String actualWarningMessage=loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage=dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected warning message is not displayed");
		
	}
	
	@Test(priority=3)
	public void verifyLoginWithInvalidEmailandValidPassword() {

		loginPage.loginWithCredentials(Utilities.generateRandomEmailAddress(), dataProp.getProperty("validPassword"));
		
		String actualWarningMessage=loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage=dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected warning message is not displayed");
		
	}
	
	@Test(priority=4)
	public void verifyLoginWithValidEmailAndInvalidPassword() {

		loginPage.loginWithCredentials(dataProp.getProperty("validEmailAddress"), dataProp.getProperty("invalidPassword"));
		
		String actualWarningMessage=loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage=dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected warning message is not displayed");
		
	}
	
	@Test(priority=5)
	public void verifyLoginWithoutProvidingEmailAndPassword() {

		loginPage.loginWithCredentials("","");
		
		
		String actualWarningMessage=loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage=dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected warning message is not displayed");
		
	}
	
	
	@DataProvider(name="validCredentials")
	public Object[][] supplyTestData(){
		Object[][] data=Utilities.getTestDataFromExcel("Login");
		return data;
	}
}
