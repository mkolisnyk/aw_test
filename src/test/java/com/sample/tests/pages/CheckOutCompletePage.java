package com.sample.tests.pages;

import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.controls.Control;

public class CheckOutCompletePage extends Page {

	@FindBy(locator = "//android.widget.TextView[@text=\"CHECKOUT: COMPLETE!\"]")
	public Control textTitle;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-BACK HOME\"]")
	public Control buttonBackHome;
	
	public CheckOutCompletePage(WebDriver driverValue) {
		super(driverValue);
		// TODO Auto-generated constructor stub
	}
	
}
