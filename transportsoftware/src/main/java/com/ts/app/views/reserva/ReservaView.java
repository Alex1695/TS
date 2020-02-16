package com.ts.app.views.reserva;

import com.ts.app.backend.Employee;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

import com.ts.app.MainView;
import com.ts.app.views.reserva.ReservaView.ReservaViewModel;

@Route(value = "reserva", layout = MainView.class)
@PageTitle("Reserva")
@JsModule("./src/views/reserva/reserva-view.js")
@Tag("reserva-view")
public class ReservaView extends PolymerTemplate<ReservaViewModel> {

    // This is the Java companion file of a design
    // You can find the design file in /frontend/src/views/src/views/reserva/reserva-view.js
    // The design can be easily edited by using Vaadin Designer (vaadin.com/designer)

    public static interface ReservaViewModel extends TemplateModel {

    }

    @Id
    private TextField firstname;
    @Id
    private TextField lastname;
    @Id
    private TextField email;
    @Id
    private TextArea notes;

    @Id
    private Button cancel;
    @Id
    private Button save;

    public ReservaView() {

        // Configure Form
        Binder<Employee> binder = new Binder<>(Employee.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> binder.readBean(null));
        save.addClickListener(e -> {
            Notification.show("Not implemented");
        });
    }
}
