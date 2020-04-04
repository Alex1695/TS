package com.ts.app.views.admin;

import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.ts.app.views.utils.Notifications;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.login.LoginForm;
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
	 */
	public AdminLoginView(){
		
		//LoginForm component = new LoginForm();
		component = new LoginOverlay();
		component.setTitle("SGM - DHL");
		component.setDescription("Acceso al panel de administración");
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setAdditionalInformation("Default credentials: admin / admin");
		component.setI18n(i18n);
		//LoginOverlay Click listener:
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
		this.getUI().ifPresent(ui ->
         ui.navigate("admin"));
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
