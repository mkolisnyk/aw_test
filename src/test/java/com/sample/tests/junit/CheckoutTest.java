package com.sample.tests.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.sample.tests.pages.CartPage;
import com.sample.tests.pages.CheckOutCompletePage;
import com.sample.tests.pages.CheckOutOverviewPage;
import com.sample.tests.pages.CheckOutPage;
import com.sample.tests.pages.LoginPage;
import com.sample.tests.pages.ProductsPage;

public class CheckoutTest extends TestCommon {

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
			.editFirstName.setText("Test").getParent(CheckOutPage.class)
			.editLastName.setText("User").getParent(CheckOutPage.class)
			.editZipCode.setText("W6 9NT").getParent(CheckOutPage.class)
			.hideKeyboard(CheckOutPage.class)
			.buttonContinue.click(CheckOutOverviewPage.class)
			.buttonFinish.click(CheckOutCompletePage.class)
			.buttonBackHome.click(ProductsPage.class);
	}
	
	@Test
	public void testStandardUserMultipleCheckout() throws Exception {
		this.pageLogin.login()
			.selectProduct("Sauce Labs Backpack")
			.selectProduct("Sauce Labs Fleece Jacket")
			.selectProduct("Sauce Labs Onesie")
			.buttonCart.click(CartPage.class)
			.buttonCheckout.click(CheckOutPage.class)
			.editFirstName.setText("Test").getParent(CheckOutPage.class)
			.editLastName.setText("User").getParent(CheckOutPage.class)
			.editZipCode.setText("W6 9NT").getParent(CheckOutPage.class)
			.hideKeyboard(CheckOutPage.class)
			.buttonContinue.click(CheckOutOverviewPage.class)
			.buttonFinish.click(CheckOutCompletePage.class)
			.buttonBackHome.click(ProductsPage.class);
	}
	
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
			.editFirstName.setText("Test").getParent(CheckOutPage.class)
			.editLastName.setText("User").getParent(CheckOutPage.class)
			.editZipCode.setText("W6 9NT").getParent(CheckOutPage.class)
			.hideKeyboard(CheckOutPage.class)
			.buttonCancel.click(ProductsPage.class);
	}
	
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
			.editFirstName.setText("Test").getParent(CheckOutPage.class)
			.editLastName.setText("User").getParent(CheckOutPage.class)
			.editZipCode.setText("W6 9NT").getParent(CheckOutPage.class)
			.hideKeyboard(CheckOutPage.class)
			.buttonContinue.click(CheckOutOverviewPage.class)
			.buttonFinish.click(CheckOutCompletePage.class)
			.buttonBackHome.click(ProductsPage.class);
	}

}
