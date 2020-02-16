package com.ts.app.views.transportsoftware;

import java.util.List;

import com.ts.app.backend.BackendService;
import com.ts.app.backend.Employee;
import com.ts.app.backend.LongToStringEncoder;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.Encode;
import com.vaadin.flow.templatemodel.TemplateModel;

import com.ts.app.MainView;
import com.ts.app.views.transportsoftware.TransportSoftwareView.TransportSoftwareViewModel;

@Route(value = "transport_software", layout = MainView.class)
@PageTitle("TransportSoftware")
@JsModule("./src/views/transportsoftware/transport-software-view.js")
@Tag("transport-software-view")
public class TransportSoftwareView extends PolymerTemplate<TransportSoftwareViewModel>
        implements AfterNavigationObserver {

    // This is the Java companion file of a design
    // You can find the design in /frontend/src/views/src/views/transportsoftware/transport-software-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    /**
     * All data we send to the client (template). Implementation is generated by the
     * framework.
     */
    public static interface TransportSoftwareViewModel extends TemplateModel {
        @Encode(value = LongToStringEncoder.class, path = "id")
        public void setPersons(List<Employee> items);
    }

    private BackendService service;

    public TransportSoftwareView() {
        service = new BackendService();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user

        // In this class we use the TemplateModel to populate data since we don't need
        // to reference the Grid in Java code. For that, please see MasterDetailView
        getModel().setPersons(service.getEmployees());
    }
}
