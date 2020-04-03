package com.ts.app.views.utils;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;

public class Notifications {

	public static int DEFAULTDURATION = 30000;
	public static String ERRORCOLOR = "red";
	public static String SUCCESSCOLOR = "green";
	
	/**
	 * Custom notification to set message, color and time dynamically from java.
	 * @param message
	 * @param time
	 * @param color
	 */
	public static void customNotify(String message, int time, String color) {
		Div content = new Div();
		content.addClassName("my-style");
		content.setText("");

		Notification custom = new Notification(content);
		custom.setDuration(time);

		// @formatter:off
		String styles = ".my-style { "
		        + "  color:"+color+";"
		        + " }";
		// @formatter:on

		/*
		 * The code below register the style file dynamically. Normally you
		 * use @StyleSheet annotation for the component class. This way is
		 * chosen just to show the style file source code.
		 */
		StreamRegistration resource = UI.getCurrent().getSession()
		        .getResourceRegistry()
		        .registerResource(new StreamResource("styles.css", () -> {
		            byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
		            return new ByteArrayInputStream(bytes);
		        }));
		UI.getCurrent().getPage().addStyleSheet(
		        "base://" + resource.getResourceUri().toString());

		custom.open();
	}
}
