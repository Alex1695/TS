package com.ts.app;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import com.ts.app.views.tft.TFTView;
import com.ts.app.backend.BBDD_Conection;
import com.ts.app.views.admin.AdminLoginView;
import com.ts.app.views.admin.AdminView;
import com.ts.app.views.reserva.ReservaView;
import com.ts.app.views.transportsoftware.TransportSoftwareView;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "TransportSoftware", shortName = "TransportSoftware")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainView extends AppLayout {

    private final Tabs menu;
    Connection conexion;
    //Language diccionary
    Properties lang_dicc;

    public MainView() {
    	
    	init();
    	
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, new DrawerToggle());
        menu = createMenuTabs();
        addToDrawer(menu);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("TFT", TFTView.class));
        tabs.add(createTab("Admin", AdminView.class));
        tabs.add(createTab("Reserva", ReservaView.class));
        tabs.add(createTab("TransportSoftware", TransportSoftwareView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title,
            Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink
                    && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }
    
    private void init() {
    	//BBDD Conection
    	try {
    		conexion = BBDD_Conection.getConexionInstance();
    	}catch(Exception e) {
    		System.out.println("ERROR AL ESTABLECER UNA CONEXION CON EL SERVIDOR MYSQL");
    		e.printStackTrace();
    	}
    	
//    	//cargamos el diccionario de la app
//    	try {
//			app_language("lang_ES");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//		}
    }
    	
    /**
     * Method to load selected laguage diccionary from src/main/resources
     */
    private void app_language(String lang) throws IOException {
        //Importante agregar el resource directorie en el .pom
        lang_dicc = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream(lang+".properties")) {
            lang_dicc.load(resourceStream);
        }
    }
    
}
