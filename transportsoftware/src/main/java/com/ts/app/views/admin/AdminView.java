package com.ts.app.views.admin;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.io.InputStream;
import com.ts.app.MainView;
import com.ts.app.views.admin.AdminView.AdminViewModel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route(value = "admin", layout = MainView.class)
@PageTitle("Admin")
@JsModule("./src/views/admin/admin-view.js")
@Tag("admin-view")
public class AdminView extends PolymerTemplate<AdminViewModel> implements
        AfterNavigationObserver {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

  
	@Id("h1")
	private H1 h1;


	@Id("vaadinVerticalLayout")
	private VerticalLayout vaadinVerticalLayout;

	@Id("verticalLayoutUploadDocks")
	private VerticalLayout verticalLayoutUploadDocks;

	@Id("verticalLayoutUploadOrders")
	private VerticalLayout verticalLayoutUploadOrders;
	
	MemoryBuffer memoryBuffer;

    public AdminView() {
    	
    	MemoryBuffer memoryBuffer = new MemoryBuffer();
    	

    	Upload uploaderDocks = new Upload(memoryBuffer);
    	uploaderDocks.addFinishedListener(e -> {
    	    InputStream inputStream = memoryBuffer.getInputStream();
    	    //CSVReader.readCsv(inputStream);
    	    
    	});
    	
    	Upload uploaderOrders = new Upload(memoryBuffer);
    	uploaderOrders.addFinishedListener(e -> {
    	    InputStream inputStream = memoryBuffer.getInputStream();
    	    //CSVReader.readCsv(inputStream);
    	});
       
    	
    	verticalLayoutUploadDocks.add(uploaderDocks);
    	verticalLayoutUploadOrders.add(uploaderOrders);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

   
    }
    public static interface AdminViewModel extends TemplateModel {
    	
    }
}
