package com.sample.tests.pages;

import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.controls.Control;
import com.sample.framework.ui.controls.Edit;

public class CheckOutPage extends Page {

	@FindBy(locator = "//android.widget.EditText[@content-desc=\"test-First Name\"]")
	public Edit editFirstName;

	@FindBy(locator = "//android.widget.EditText[@content-desc=\"test-Last Name\"]")
	public Edit editLastName;

	@FindBy(locator = "//android.widget.EditText[@content-desc=\"test-Zip/Postal Code\"]")
	public Edit editZipCode;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CANCEL\"]")
	public Control buttonCancel;

	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CONTINUE\"]")
	public Control buttonContinue;

	public CheckOutPage(WebDriver driverValue) {
		super(driverValue);
		// TODO Auto-generated constructor stub
	}

}
