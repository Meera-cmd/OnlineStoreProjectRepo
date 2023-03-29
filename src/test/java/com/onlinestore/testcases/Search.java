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
import com.onlinestore.pages.HomePage;
import com.onlinestore.pages.LoginPage;
import com.onlinestore.pages.SearchPage;
import com.onlinestore.utility.Utilities;

public class Search extends Base{
	
	SearchPage searchPage;
	HomePage homePage;
	
	//invoking the Base class constructor
	public Search() {
		super();
	}
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver=initializeBrowserAndOpenApp(prop.getProperty("browser"));
		homePage=new HomePage(driver);
		LoginPage loginPage = homePage.navigateToLoginPage();
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifySearchWithValidProduct() {
		
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("validProduct"));
		searchPage = homePage.clickOnSearchButton();
		
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid product HP is not displayed");
	}
	
	@Test(priority=2)
	public void verifySearchWithInvalidProduct() {
		
		searchPage=homePage.searchForAProduct(dataProp.getProperty("validProduct"));
		
		String actualSearchMessage=searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,dataProp.getProperty("NoProductMessageText"),"No product in search results is not displayed");

	}

	
}