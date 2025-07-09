package com.sample.tests.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.PageFactory;
import com.sample.framework.ui.controls.Control;
import com.sample.framework.ui.controls.Edit;

public class LoginPage extends Page {

	@FindBy(locator = "//android.widget.EditText[@content-desc='test-Username']")
	public Edit editUsername;
	
	@FindBy(locator = "//android.widget.EditText[@content-desc=\"test-Password\"]")
	public Edit editPassword;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]")
	public Control buttonLogin;
	
	public LoginPage(WebDriver driverValue) {
		super(driverValue);
	}

	public <T extends Page> T login(String userName, String password, Class<T> pageClass) throws Exception {
		this.editUsername.setText(userName);
		this.editPassword.setText(password);
		this.buttonLogin.click();
		T expectedPage = PageFactory.init(getDriver(), pageClass);
		Assertions.assertTrue(expectedPage.isCurrent(),
				String.format("Expected page of the '%s' class wasn't found", pageClass.getSimpleName()));
		return expectedPage;
	}
}
