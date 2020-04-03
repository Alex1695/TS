package com.ts.app.views.admin;

import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.ts.app.views.utils.Notifications;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.login.LoginForm;

@Tag("login")
@Route(value = "login")
@RouteAlias(value = "/login")
@PageTitle("Admin Login")
public class AdminLoginView extends FlexLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminLoginView(){
		
		LoginForm component = new LoginForm();
		component.addLoginListener(e -> {
		    boolean isAuthenticated = authenticate(e);
		    
		    if (isAuthenticated) {
		    	Notifications.customNotify("Bienvenido "+e.getUsername(), 
		    			Notifications.DEFAULTDURATION,Notifications.SUCCESSCOLOR);
		        navigateToMainPage(e);
		        
		    } else {
		    	Notifications.customNotify("ERROR. Usuario o contraseña incorrectas.", 
		    			Notifications.DEFAULTDURATION,Notifications.ERRORCOLOR );
		        component.setError(true);
		    }
		});
	}
	
	/**
	 * Checks user & password from BBDD?
	 * @param event
	 * @return
	 */
	private boolean authenticate(LoginEvent event) {
		String adminUSR = "admin";
		String passUSR = "admin";
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
		//getUI().get().navigate("/admin");
		//event.forwardTo(TradesLightView.class);
	}
	
	
}
