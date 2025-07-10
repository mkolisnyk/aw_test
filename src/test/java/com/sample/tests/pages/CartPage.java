package com.sample.tests.pages;

import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.ScrollTo;
import com.sample.framework.ui.controls.Control;

public class CartPage extends Page {

	@FindBy(locator = "//android.widget.TextView[@text=\"YOUR CART\"]")
	public Control textTitle;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CHECKOUT\"]",
			excludeFromSearch = true,
			scrollTo = "CHECKOUT",
			scrollDirection = ScrollTo.BOTTOM_ONLY)
	public Control buttonCheckout;

	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CONTINUE SHOPPING\"]",
			excludeFromSearch = true)
	public Control buttonContinueShopping;	
	
	public CartPage(WebDriver driverValue) {
		super(driverValue);
		// TODO Auto-generated constructor stub
	}

	
}
