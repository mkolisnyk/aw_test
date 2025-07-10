package com.sample.tests.controls;

import org.openqa.selenium.By;

import com.sample.framework.ui.Page;
import com.sample.framework.ui.controls.Control;

public class AddRemoveToggleButton extends Control {

	public AddRemoveToggleButton(Page parentValue, By locatorValue) {
		super(parentValue, locatorValue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getText() {
		Control label = new Control(this.getParent(),
				By.xpath(String.format("%s/android.widget.TextView", this.getLocatorText())));
		return label.getText();
	}

	
}
