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
import com.onlinestore.pages.AccountSuccessPage;
import com.onlinestore.pages.HomePage;
import com.onlinestore.pages.RegisterPage;
import com.onlinestore.utility.Utilities;

public class Register extends Base{
	
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	//invoking the Base class constructor
	public Register() {
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver=initializeBrowserAndOpenApp(prop.getProperty("browser"));
		HomePage homePage=new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();

	
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifyRegisteringAccountWithMandatoryFields() {
		
		String firstName=dataProp.getProperty("firstName");
		String lastName=dataProp.getProperty("lastName");
		String telephoneNumber=dataProp.getProperty("telephoneNumber");
		String validPassword=dataProp.getProperty("validPassword");
		String enterEmailAddress=Utilities.generateRandomEmailAddress();

		
		accountSuccessPage=registerPage.registerWithMandatoryFields(firstName, lastName, enterEmailAddress, telephoneNumber, validPassword);
		
		String actualSuccessHeading=accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account success page is not displayed");


	}
	
	@Test
	public void verifyRegisteringAccountWithExistingEmailAddress() {
		
		String firstName=dataProp.getProperty("firstName");
		String lastName=dataProp.getProperty("lastName");
		String telephoneNumber=dataProp.getProperty("telephoneNumber");
		String validPassword=dataProp.getProperty("validPassword");
		String enterEmailAddress=Utilities.generateRandomEmailAddress();

		
		accountSuccessPage=registerPage.registerWithAllFields(firstName, lastName, enterEmailAddress, telephoneNumber, validPassword);		
		
		String actualWarning=registerPage.retrieveDuplicateEmailAddressWarning();
		Assert.assertTrue(actualWarning.contains(dataProp.getProperty("accountSuccessfullyCreatedHeading")),"Account success page is not displayed");


	}

}