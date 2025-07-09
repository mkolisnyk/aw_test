package com.sample.tests.helpers;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sample.framework.Configuration;
import com.sample.framework.Driver;
import com.sample.framework.ui.PageFactory;
import com.sample.framework.utils.SystemUtils;
import com.sample.tests.pages.LoginPage;

public class AppHelper {

    public static LoginPage startApp(boolean reset) throws Exception {
        Configuration.load();
        Configuration.print();
        DesiredCapabilities cap = new DesiredCapabilities();
        // cap.setCapability(CapabilityType.BROWSER_NAME, Configuration.get("browser"));
        // cap.setCapability("platformVersion", Configuration.get("platformVersion"));
        cap.setCapability("platformName", "Android");
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:app", new File(Configuration.get("app_path")).getAbsolutePath());
        // cap.setCapability("udid", Configuration.get("udid"));
        // cap.setCapability("deviceName", Configuration.get("deviceName"));
        cap.setCapability("appium:commandTimeout", Configuration.get("commandTimeout"));
        //cap.setCapability("appActivity", Configuration.get("appActivity"));
        //cap.setCapability("appPackage", Configuration.get("appPackage"));
        
//        cap.setCapability("appWaitActivity", Configuration.get("appActivity"));
//        cap.setCapability("appWaitPackage", Configuration.get("appPackage"));
//        

        cap.setCapability("appium:fullReset", false);
        cap.setCapability("appium:dontStopAppOnReset", true);
        cap.setCapability("appium:appWaitForLaunch", false);
        if (Configuration.platform().isAndroidNative() && reset) {
            SystemUtils.setSystemTime();
            SystemUtils.resetAppData();
        }
        WebDriver driver = Driver.init("http://127.0.0.1:" + Configuration.get("port"),
                Configuration.platform(), cap);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Driver.add(driver);
        if (Configuration.platform().isWeb()) {
                Driver.current().get(Configuration.get("url"));
        }
        return PageFactory.init(Driver.current(),LoginPage.class);
    }
    public static void stopApp() {
        Driver.current().quit();
    }
    public static void uninstallApp() throws IOException {
        Configuration.load();
        if (Configuration.platform().isAndroidNative()) {
            SystemUtils.uninstallApp(Configuration.get("appPackage"));
        }
    }
}
