package com.sample.tests.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.sample.tests.helpers.AppHelper;
import com.sample.tests.pages.LoginPage;

public class TestCommon {
    public LoginPage pageLogin;
    // public static Process process;

	public TestCommon() {
		// TODO Auto-generated constructor stub
	}
    @BeforeAll
    public static void beforeSuite() throws Exception {
        AppHelper.uninstallApp();
        // process = SystemUtils.startProcessMetricsCommand(new File("output.txt"));
    }
    public void setUp(boolean reset) throws Exception {
        this.pageLogin = AppHelper.startApp(reset);
    }
	@BeforeEach
	public void setUp() throws Exception {
		setUp(true);
	}
	@AfterEach
	public void tearDown() {
		AppHelper.stopApp();
		// TestCommon.process.destroy();
	}

}
