package com.onlinestore.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	//Elements
		@FindBy(id="input-email")
		private WebElement emailAddressField;
		
		@FindBy(id="input-password")
		private WebElement passwordField;
		
		@FindBy(xpath="//input[@value"
				+ "='Login']")
		private WebElement loginButton;
		
		@FindBy(xpath="//div[contains(@class,'alert-dismissible')]")
		private WebElement emailPasswordNotMatchingWarning;
		
		
		public LoginPage(WebDriver driver) {
			this.driver=driver;
			PageFactory.initElements(driver, this);
		}
		
		public AccountPage loginWithCredentials(String emailText,String password) {
			emailAddressField.sendKeys(emailText);
			passwordField.sendKeys(password);
			loginButton.click();
			return new AccountPage(driver);
			
			
		}
		
		public String retrieveEmailPasswordNotMatchingWarningMessageText() {
			
			String warningText=emailPasswordNotMatchingWarning.getText();
			return warningText;
			
		}
}
