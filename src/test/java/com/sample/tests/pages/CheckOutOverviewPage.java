package com.sample.tests.pages;

import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.ScrollTo;
import com.sample.framework.ui.controls.Control;

public class CheckOutOverviewPage extends Page {

	@FindBy(locator = "//android.widget.TextView[@text=\"CHECKOUT: OVERVIEW\"]")
	public Control textTitle;

	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CANCEL\"]",
			excludeFromSearch = true, scrollTo = "CANCEL", scrollDirection = ScrollTo.BOTTOM_ONLY)
	public Control buttonCancel;

	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-FINISH\"]",
			excludeFromSearch = true, scrollTo = "FINISH", scrollDirection = ScrollTo.BOTTOM_ONLY)
	public Control buttonFinish;

	
	public CheckOutOverviewPage(WebDriver driverValue) {
		super(driverValue);
		// TODO Auto-generated constructor stub
	}

}
