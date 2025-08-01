package com.sample.framework.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.reflections.Reflections;

import com.sample.framework.Configuration;
import com.sample.framework.Driver;
import com.sample.framework.ui.controls.Control;

public class Page {
    private static final long TINY_TIMEOUT = 1;
    private static final long SHORT_TIMEOUT = 2;
    private static final int SCROLL_TOP_PART = 9;
    private static final int SCROLL_TOTAL_PARTS = 10;
    private static final long TIMEOUT = Configuration.timeout();
    private static ConcurrentHashMap<String, Page> currentPages = new ConcurrentHashMap<String, Page>();

    private WebDriver driver;

    public Page(WebDriver driverValue) {
        this.driver = driverValue;
    }
    public static Page screen(String name) throws Exception {
        return screen(name, Configuration.get("pages_package"));
    }
    public static Page screen(String name, String pagePackage) throws Exception {
        Reflections reflections = new Reflections(pagePackage);
        Set<Class<? extends Page>> subTypes = reflections.getSubTypesOf(Page.class);
        for (Class<? extends Page> type : subTypes) {
            Alias annotation = type.getAnnotation(Alias.class);
            if (annotation != null && annotation.value().equals(name)) {
                return PageFactory.init(Driver.current(), type);
            }
        }
        return null;
    }
    public static Page getCurrent() {
        return currentPages.get(Driver.getThreadName());
    }
    public static void setCurrent(Page newPage) {
        currentPages.put(Driver.getThreadName(), newPage);
    }
    public WebDriver getDriver() {
        return driver;
    }
    public Page navigate() {
        return this;
    }
    public boolean isTextPresent(String text) {
        String locator = String.format("//*[@text='%s' or contains(@text, '%s')]", text, text);
        Control element = new Control(this, By.xpath(locator));
        return element.exists();
    }
    public Page assertTextPresent(String text) {
        Assertions.assertTrue(isTextPresent(text), "Unable to find text: '" + text + "'");
        return this;
    }
    public byte[] captureScreenShot() throws IOException {
        WebDriver augmentedDriver = new Augmenter().augment(this.getDriver());
        byte[] data = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
        return data;
    }
    public File captureScreenShot(String destination) throws IOException {
        WebDriver augmentedDriver = new Augmenter().augment(this.getDriver());
        File srcFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        File output = new File(destination);
        FileUtils.copyFile(srcFile, output);
        return output;
    }
    public String getSource() {
        return this.getDriver().getPageSource();
    }
    public Control getScrollable() {
        Control scrollable = new Control(this,
                By.xpath("(//*[@scrollable='true'])[1]"));
        return scrollable;
    }
    public Control getTextControl(String message) {
        Control text = null;
        String locator = "";
            locator = "//*[@text=\"" + message
                    + "\" or contains(@text,\"" + message
//                    + "\")"
//                    + " or contains(text(),\"" + message
//                    + "\") or text()=\"" + message
//                    + "\" or contains(@content-desc,\"" + message
                    + "\")]";
        text = new Control(this, By.xpath(locator));
        return text;
    }
    private static Rectangle getScreenSize() {
        Dimension size = Driver.current().manage().window().getSize();
        Rectangle area = new Rectangle(0, 0, size.getWidth(), size.getHeight());
        return area;
    }
    public boolean swipeScreen(boolean vertical, boolean leftTop, boolean once) {
        return swipeScreen(vertical, leftTop, once, 2);
    }
    public boolean swipeScreen(boolean vertical, boolean leftTop, boolean once, int seconds) {
        Control scrollable = getScrollable();
        if (!scrollable.exists(SHORT_TIMEOUT)) {
            return false;
        }
        Rectangle elementArea = scrollable.getRect();
        Rectangle screenArea = Page.getScreenSize();
        Rectangle area = new Rectangle(
        	Math.max(elementArea.x, screenArea.x),
        	Math.max(elementArea.y, screenArea.y),
        	Math.min(elementArea.width, screenArea.width - elementArea.x),
        	Math.min(elementArea.height, screenArea.height - elementArea.y)
		);

        int startX = area.x + area.width / 2;
        int startY = 0;
        int endX = area.x + area.width / 2;
        int endY = 0;
        if (vertical) {
            startX = area.x + area.width / 2;
            endX = area.x + area.width / 2;
            if (leftTop) {
                startY = area.y + area.height / SCROLL_TOTAL_PARTS;
                endY = area.y + SCROLL_TOP_PART * area.height / SCROLL_TOTAL_PARTS;
            } else {
                startY = area.y + SCROLL_TOP_PART * area.height / SCROLL_TOTAL_PARTS;
                endY = area.y + area.height / SCROLL_TOTAL_PARTS;
            }
        } else {
            startY = area.y + area.height / 2;
            endY = area.y + area.height / 2;
            if (leftTop) {
                startX = area.x + SCROLL_TOP_PART * area.width / SCROLL_TOTAL_PARTS;
                endX = area.x + area.width / SCROLL_TOTAL_PARTS;
            } else {
                startX = area.x + area.width / SCROLL_TOTAL_PARTS;
                endX = area.x + SCROLL_TOP_PART * area.width / SCROLL_TOTAL_PARTS;
            }
        }
        String prevState = "";
        String currentState = this.getSource();
        int times = 0;
        final int maxTries = 50;
        while (!currentState.equals(prevState)) {
        	
//            ((AppiumDriver) this.getDriver()).swipe(startX, startY, endX, endY,
//                    seconds * 1000);
        	
        	doSwipe(new Point(startX, startY), new Point(endX, endY));
        	
            if (once || times > maxTries) {
                break;
            }
            times++;
            prevState = currentState;
            currentState = this.getSource();
        }
        return true;
    }

    private void doSwipe(Point start, Point end) {
    	final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    	var swipe = new Sequence(finger, 1);
    	swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
    	    PointerInput.Origin.viewport(), start.getX(), start.getY()));
    	swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    	swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
    	    PointerInput.Origin.viewport(), end.getX(), end.getY()));
    	swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    	((RemoteWebDriver) this.getDriver()).perform(Arrays.asList(swipe));
    }
    
    public boolean scrollTo(Control control, boolean up) {
        if (control.exists(TINY_TIMEOUT)) {
            return true;
        }
        Control scrollable = getScrollable();
        if (!scrollable.exists(TINY_TIMEOUT)) {
            return false;
        }
        String prevState = "";
        String currentState = this.getSource();
        while (!currentState.equals(prevState) && this.swipeScreen(true, up, true)) {
        	System.out.println("Waiting for element: " + control.getLocatorText());
            if (control.exists(TINY_TIMEOUT)) {
                System.out.println("Here!!!");
                return true;
            }
            System.out.println("Here!!!");
            prevState = currentState;
            currentState = this.getSource();
        }
        return false;
    }
    public boolean scrollTo(boolean up) throws Exception {
        return swipeScreen(true, up, false);
    }
    public boolean scrollTo(Control control, ScrollTo scrollDirection) {
        switch (scrollDirection) {
            case TOP_ONLY:
                return scrollTo(control, true);
            case TOP_BOTTOM:
                return scrollTo(control, true) || scrollTo(control, false);
            case BOTTOM_ONLY:
                return scrollTo(control, false);
            case BOTTOM_TOP:
                return scrollTo(control, false) || scrollTo(control, true);
            default:
                return scrollTo(control, true) || scrollTo(control, false);
        }
    }
    public boolean scrollTo(Control control) throws Exception {
        return scrollTo(control, ScrollTo.TOP_BOTTOM);
    }
    public boolean scrollTo(String text, boolean up) throws Exception {
        Control control = this.getTextControl(text);
        return this.scrollTo(control, up);
    }
    public boolean scrollTo(String text, ScrollTo scrollDirection) {
        Control control = this.getTextControl(text);
        return scrollTo(control, scrollDirection);
    }
    public boolean scrollTo(String text) {
        return scrollTo(text, ScrollTo.TOP_BOTTOM);
    }
    
    public Page hideKeyboard() {
        try {
            // ((AppiumDriver) this.getDriver()).hideKeyboard();
        	((HidesKeyboard) this.getDriver()).hideKeyboard();
        } catch (Exception e) {
        }
        return this;
    }
    public <T extends Page> T hideKeyboard(Class<T> pageClass) {
        try {
            // ((AppiumDriver) this.getDriver()).hideKeyboard();
        	((HidesKeyboard) this.getDriver()).hideKeyboard();
        } catch (Exception e) {
        }
        return (T) this;
    }
    public boolean isCurrent(long timeout) throws Exception {
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            if (Control.class.isAssignableFrom(field.getType())) {
                Control control = (Control) field.get(this);
                if (!control.isExcludeFromSearch() && !control.exists(timeout)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isCurrent() throws Exception {
        return isCurrent(TIMEOUT);
    }
    protected boolean allElementsAre(Control[] elements, String state) throws Exception {
        for (Control element : elements) {
            if (!(Boolean) (element.getClass().getMethod(state).invoke(element))) {
                return false;
            }
        }
        return true;
    }
    protected boolean anyOfElementsIs(Control[] elements, String state) throws Exception {
        for (Control element : elements) {
            if ((Boolean) element.getClass().getMethod(state, long.class).invoke(element, 1)) {
                return true;
            }
        }
        return false;
    }
    public boolean allElementsExist(Control[] elements) throws Exception {
        return allElementsAre(elements, "exists");
    }
    public boolean allElementsDoNotExist(Control[] elements) throws Exception {
        return allElementsAre(elements, "disappears");
    }
    public boolean allElementsAreVisible(Control[] elements) throws Exception {
        return allElementsAre(elements, "visible");
    }
    public boolean allElementsAreInvisible(Control[] elements) throws Exception {
        return allElementsAre(elements, "invisible");
    }
    public boolean allElementsAreEnabled(Control[] elements) throws Exception {
        return allElementsAre(elements, "enabled");
    }
    public boolean allElementsAreDisabled(Control[] elements) throws Exception {
        return allElementsAre(elements, "disabled");
    }
    
    public boolean anyOfElementsExist(Control[] elements) throws Exception {
        return anyOfElementsIs(elements, "exists");
    }
    public boolean anyOfElementsDoNotExist(Control[] elements) throws Exception {
        return anyOfElementsIs(elements, "disappears");
    }
    public boolean anyOfElementsIsVisible(Control[] elements) throws Exception {
        return anyOfElementsIs(elements, "visible");
    }
    public boolean anyOfElementsIsInvisible(Control[] elements) throws Exception {
        return anyOfElementsIs(elements, "invisible");
    }
    public boolean anyOfElementsIsEnabled(Control[] elements) throws Exception {
        return anyOfElementsIs(elements, "enabled");
    }
    public boolean anyOfElementsIsDisabled(Control[] elements) throws Exception {
        return anyOfElementsIs(elements, "disabled");
    }
    public Control onPage(String name) throws Exception {
        for (Field field : this.getClass().getFields()) {
            if (Control.class.isAssignableFrom(field.getType())) {
                Alias alias = field.getAnnotation(Alias.class);
                if (alias != null && name.equals(alias.value())) {
                    return (Control) field.get(this);
                }
            }
        }
        return null;
    }
}
