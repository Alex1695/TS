package com.ts.app.views.admin;

import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.io.UnsupportedEncodingException;

import com.ts.app.views.utils.DictionaryManager;
import com.ts.app.views.utils.Notifications;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;

@Tag("login")
@Route(value = "login")
@RouteAlias(value = "/login")
@PageTitle("Admin Login")
public class AdminLoginView extends FlexLayout {

	
	private static final long serialVersionUID = 1L;
	
	private LoginOverlay component;
	
	/**
	 * Default contructor of LoginOverlay
	 * @throws UnsupportedEncodingException 
	 */
	public AdminLoginView() throws UnsupportedEncodingException{
		
		component = new LoginOverlay();
		component.setTitle("SGM - DHL");
		component.setDescription(DictionaryManager.translateField("adminlogin.description"));
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setAdditionalInformation("Default credentials: admin / admin");
		component.setI18n(i18n);
		//LoginOverlay Click listener:
		component.addLoginListener(e -> {
		    boolean isAuthenticated = authenticate(e);
		    if (isAuthenticated) {
		    	try {
					Notifications.customNotify(DictionaryManager.translateField("adminView.welcomeNotify")+e.getUsername(), 
							Notifications.DEFAULTDURATION,Notifications.SUCCESSCOLOR);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        navigateToMainPage(e);
		        
		    } else {
		    	try {
					Notifications.customNotify(DictionaryManager.translateField("adminView.invalidLoginNotify"), 
							Notifications.DEFAULTDURATION,Notifications.ERRORCOLOR );
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        component.setError(true);
		    }
		});
		component.setEnabled(true);
		component.setVisible(true);
		this.add(component);
	}
	
	
	/**
	 * Checks user & password from BBDD?
	 * @param event
	 * @return
	 */
	private boolean authenticate(LoginEvent event) {
		String adminUSR = "admin";//FOR TEST
		String passUSR = "admin";//FOR TEST
		if(event.getUsername().equals(adminUSR) && event.getPassword().equals(passUSR)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Method to navigate to Main view
	 */
	private void navigateToMainPage(LoginEvent event) {
		
		this.getUI().ifPresent(ui ->
         ui.navigate("main"));
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		component.setOpened(true);
	}
	
	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		component.setOpened(false);
	}
	
	
}
