package com.sample.tests.junit;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static com.sample.tests.pages.LoginPage.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sample.framework.ui.Page;
import com.sample.tests.pages.LoginPage;
import com.sample.tests.pages.ProductsPage;

public class LoginTest extends TestCommon {
	
	public static Stream<Arguments> inputDataProvider() {
		return Stream.of(
			arguments(STANDARD_USER, PASSWORD, ProductsPage.class, "PRODUCTS"),
			arguments(STANDARD_USER, "wrong_password", LoginPage.class, "Username and password do not match any user in this service"),
			arguments(LOCKED_OUT_USER, PASSWORD, LoginPage.class, "Sorry, this user has been locked out"),
			arguments(PROBLEM_USER, PASSWORD, ProductsPage.class, "PRODUCTS")
		);
	}
	
	@ParameterizedTest
	@MethodSource("inputDataProvider")
	void testAppLogin(String userName, String password, Class<? extends Page> pageClass, String expectedText) throws Exception {
		this.pageLogin.login(userName, password, pageClass)
			.assertTextPresent(expectedText);
	}
}
