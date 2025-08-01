package com.sample.framework.ui.controls;

import java.time.Duration;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sample.framework.Configuration;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.PageFactory;
import com.sample.framework.ui.ScrollTo;
import com.sample.framework.ui.SubItem;

public class Control {
    protected static final long TIMEOUT = Configuration.timeout();
    private Page parent;
    private By locator;
    private String locatorText = "";
    private String itemLocatorText = "";
    private HashMap<String, SubItem> subItemsMap;
    private String scrollTo;
    private ScrollTo scrollDirection;
    private String format;
    private boolean excludeFromSearch = false;
  
    public Control(Page parentValue, By locatorValue) {
        this.parent = parentValue;
        this.locator = locatorValue;
        this.locatorText = this.locator.toString().replaceFirst("^By\\.(\\S+): ", "");
        subItemsMap = new HashMap<String, SubItem>();
        System.out.println(String.format("New Control with locator: %s", this.locatorText));
    }
    public WebDriver getDriver() {
        return parent.getDriver();
    }
    public Page getParent() {
		return parent;
	}
    public <T extends Page> T getParent(Class<T> pageClass) {
		return (T) parent;
	}
	public By getLocator() {
		return locator;
	}
   public String getLocatorText() {
        return locatorText;
    }

    public String getItemLocatorText() {
        return itemLocatorText;
    }

    public void setItemLocatorText(String subItemLocatorText) {
        this.itemLocatorText = subItemLocatorText;
    }

    public String getScrollTo() {
        return scrollTo;
    }
    public void setScrollTo(String scrollTo) {
        this.scrollTo = scrollTo;
    }
    public ScrollTo getScrollDirection() {
        return scrollDirection;
    }
    public void setScrollDirection(ScrollTo scrollDirection) {
        this.scrollDirection = scrollDirection;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public boolean isExcludeFromSearch() {
        return excludeFromSearch;
    }
    public void setExcludeFromSearch(boolean excludeFromSearch) {
        this.excludeFromSearch = excludeFromSearch;
    }
    public void addSubItems(SubItem[] items) {
        for (SubItem item : items) {
            this.subItemsMap.put(item.name(), item);
        }
    }
    
    public HashMap<String, SubItem> getSubItemsMap() {
        return subItemsMap;
    }

	public WebElement element() {
        return getDriver().findElement(locator);
    }
	public WebElement element(int index) {
        return getDriver().findElements(locator).get(index);
    }
    public boolean waitUntil(ExpectedCondition<?> condition, long timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        try {
            wait.until(condition);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
    public boolean exists(long timeout) {
    	try {
	        this.scrollTo();
	        return waitUntil(ExpectedConditions.presenceOfElementLocated(locator), timeout);
    	} catch (WebDriverException e) {
    		return false;
    	}
    }
    public boolean exists() {
        return exists(TIMEOUT);
    }
    public boolean disappears(long timeout) {
        return waitUntil(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)), timeout);
    }
    public boolean disappears() {
        return disappears(TIMEOUT);
    }
    public boolean visible(long timeout) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(locator), timeout);
    }
    public boolean visible() {
        Assertions.assertTrue(
        		exists(),
                "Unable to find element: " + this.locator.toString()
                );
        return visible(TIMEOUT);
    }
    public boolean invisible(long timeout) {
        return waitUntil(ExpectedConditions.invisibilityOfElementLocated(locator), timeout);
    }
    public boolean invisible() {
        Assertions.assertTrue(
        		exists(),
                "Unable to find element: " + this.locator.toString()
                );
        return invisible(TIMEOUT);
    }
    public boolean enabled(long timeout) {
        return waitUntil(ExpectedConditions.elementToBeClickable(locator), timeout);
    }
    public boolean enabled() {
        return enabled(TIMEOUT);
    }
    public boolean disabled(long timeout) {
        return waitUntil(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(locator)), timeout);
    }
    public boolean disabled() {
        return enabled(TIMEOUT);
    }
    public void click() {
        Assertions.assertTrue(
        		exists(),
            "Unable to find element: " + this.locator.toString()
            );
        this.element().click();
    }
    public <T extends Page> T click(Class<T> pageClass) throws Exception {
    	this.click();
    	T page = PageFactory.init(this.getDriver(), pageClass);
    	Assertions.assertTrue(page.isCurrent(),
    			String.format("The page of %s class didn't appear during specified timeout", pageClass.getName())
                );
    	return page;
    }
    public String getText() {
		Assertions.assertTrue(this.exists(),
				"Unable to find element with locator: " + this.getLocator()
				);
		return this.element().getText();
    }
    public Control assertText(String expected) {
    	Assertions.assertEquals(expected, getText(), "Element has unexpected text");
    	return this;
    }
    public String getValue() {
        return this.getText();
    }
    public Rectangle getRect() {
        this.exists();
//        Rectangle rect = new Rectangle();
//        Point location = this.element().getLocation();
//        Dimension size = this.element().getSize();
//        rect.x = location.x;
//        rect.y = location.y;
//        rect.width = size.width;
//        rect.height = size.height;
        return this.element().getRect();
    }
    public void scrollTo() {
        if (this.getScrollTo() != null && !this.getScrollTo().trim().equals("")) {
            this.getParent().scrollTo(this.getScrollTo(),
                    this.getScrollDirection());
        }
    }
}
