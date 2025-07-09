package com.sample.tests.junit;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sample.framework.ui.Page;
import com.sample.tests.pages.LoginPage;

public class LoginTest extends TestCommon {
	
	public static Stream<Arguments> inputDataProvider() {
		return Stream.of(
			// arguments("standard_user", "secret_secure", LoginPage.class),
			arguments("locked_out_user", "secret_secure", LoginPage.class)
			// arguments("problem_user", "secret_secure", LoginPage.class)
		);
	}
	
	@ParameterizedTest
	@MethodSource("inputDataProvider")
	void testAppLogin(String userName, String password, Class<? extends Page> pageClass) throws Exception {
		this.pageLogin.login(userName, password, pageClass);
	}
}
