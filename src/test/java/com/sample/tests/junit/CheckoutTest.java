package com.sample.tests.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sample.tests.pages.CartPage;
import com.sample.tests.pages.CheckOutCompletePage;
import com.sample.tests.pages.CheckOutOverviewPage;
import com.sample.tests.pages.CheckOutPage;
import com.sample.tests.pages.LoginPage;
import com.sample.tests.pages.ProductsPage;

public class CheckoutTest extends TestCommon {

	private final String checkoutFirstName = "Test";
	private final String checkoutLastName = "User";
	private final String checkoutZipCode = "W6 9NT";

	@DisplayName("Complete checkout flow for standard user buying single product should complete successfully")
	@Test
	public void testStandardUserBasicCheckout() throws Exception {
		this.pageLogin.login()
			.tableProducts.getSubItem(ProductsPage.ADD_REMOVE, 0).assertText("ADD TO CART")
			.getParent(ProductsPage.class)
			.toggleProductSelection(0)
			.tableProducts.getSubItem(ProductsPage.ADD_REMOVE, 0).assertText("REMOVE")
			.getParent(ProductsPage.class)
			.buttonCart.click(CartPage.class)
			.buttonCheckout.click(CheckOutPage.class)
			.fillFormData(checkoutFirstName, checkoutLastName, checkoutZipCode)
			.buttonContinue.click(CheckOutOverviewPage.class)
			.buttonFinish.click(CheckOutCompletePage.class)
			.buttonBackHome.click(ProductsPage.class);
	}

	@DisplayName("Complete checkout flow for standard user buying multiple products should complete successfully")
	@Test
	public void testStandardUserMultipleCheckout() throws Exception {
		this.pageLogin.login()
			.selectProduct("Sauce Labs Backpack")
			.selectProduct("Sauce Labs Fleece Jacket")
			.selectProduct("Sauce Labs Onesie")
			.buttonCart.click(CartPage.class)
			.buttonCheckout.click(CheckOutPage.class)
			.fillFormData(checkoutFirstName, checkoutLastName, checkoutZipCode)
			.buttonContinue.click(CheckOutOverviewPage.class)
			.buttonFinish.click(CheckOutCompletePage.class)
			.buttonBackHome.click(ProductsPage.class);
	}

	@DisplayName("Standard user should go back to products page from the checkout")
	@Test
	public void testStandardUserBackNavigation() throws Exception {
		this.pageLogin.login()
			.tableProducts.getSubItem(ProductsPage.ADD_REMOVE, 0).assertText("ADD TO CART")
			.getParent(ProductsPage.class)
			.toggleProductSelection(0)
			.tableProducts.getSubItem(ProductsPage.ADD_REMOVE, 0).assertText("REMOVE")
			.getParent(ProductsPage.class)
			.buttonCart.click(CartPage.class)
			.buttonCheckout.click(CheckOutPage.class)
			.fillFormData(checkoutFirstName, checkoutLastName, checkoutZipCode)
			.buttonCancel.click(ProductsPage.class);
	}

	@DisplayName("Complete checkout flow for problem user buying single product should complete successfully")
	@Test
	public void testProblemUserBasicCheckout() throws Exception {
		this.pageLogin.login(LoginPage.PROBLEM_USER, LoginPage.PASSWORD, ProductsPage.class)
			.tableProducts.getSubItem(ProductsPage.ADD_REMOVE, 0).assertText("ADD TO CART")
			.getParent(ProductsPage.class)
			.toggleProductSelection(0)
			.tableProducts.getSubItem(ProductsPage.ADD_REMOVE, 0).assertText("REMOVE")
			.getParent(ProductsPage.class)
			.buttonCart.click(CartPage.class)
			.buttonCheckout.click(CheckOutPage.class)
			.fillFormData(checkoutFirstName, checkoutLastName, checkoutZipCode)
			.buttonContinue.click(CheckOutOverviewPage.class)
			.buttonFinish.click(CheckOutCompletePage.class)
			.buttonBackHome.click(ProductsPage.class);
	}

}
