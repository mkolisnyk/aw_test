package com.sample.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.ScrollTo;
import com.sample.framework.ui.SubItem;
import com.sample.framework.ui.controls.Control;
import com.sample.framework.ui.controls.TableView;
import com.sample.tests.controls.AddRemoveToggleButton;

public class ProductsPage extends Page {
	public static final String ADD_REMOVE = "Add/Remove";

	@FindBy(locator = "//android.widget.TextView[@text=\"PRODUCTS\"]")
	public Control textTitle;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-Modal Selector Button\"]//android.widget.ImageView")
	public Control buttonSortBy;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc='test-Toggle']/android.widget.ImageView")
	public Control buttonToggleView;
	
	@FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-Cart\"]")
	public Control buttonCart;
	
	@FindBy(locator="//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]")
	@SubItem(locator = "//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\" "
			+ "or @content-desc=\"test-REMOVE\" ]",
			name = ADD_REMOVE,
			controlType = AddRemoveToggleButton.class)
	public TableView tableProducts;
	
	public ProductsPage(WebDriver driverValue) {
		super(driverValue);
		// TODO Auto-generated constructor stub
	}
	public ProductsPage toggleProductSelection(int index) throws Exception {
		this.tableProducts.getSubItem(ADD_REMOVE, index).click();
		return this;
	}
	public ProductsPage selectProduct(String name) throws Exception {
		this.scrollTo(name, ScrollTo.BOTTOM_TOP);
		Control button = new Control(
				this,
				By.xpath(
					"//android.widget.TextView[@content-desc=\"test-Item title\" and contains(@text, \"" + name + "\")]"
					+ "/../android.view.ViewGroup[@content-desc=\"test-ADD TO CART\" "
					+ "or @content-desc=\"test-REMOVE\" ]"
				)
		);
		button.click();
		return this;
	}
}
