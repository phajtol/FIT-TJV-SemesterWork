package eu.hajtol.peter.tjv.klient.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import eu.hajtol.peter.tjv.klient.clients.AdresaClient;
import eu.hajtol.peter.tjv.klient.clients.AkciaClient;
import eu.hajtol.peter.tjv.klient.clients.UzivatelClient;
import eu.hajtol.peter.tjv.klient.entities.Adresa;
import eu.hajtol.peter.tjv.klient.entities.Akcia;
import eu.hajtol.peter.tjv.klient.entities.Uzivatel;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        HorizontalLayout userSection = new HorizontalLayout();
        HorizontalLayout addressSection = new HorizontalLayout();
        HorizontalLayout eventSection = new HorizontalLayout();
        
        //clients
        AdresaClient adrCl = new AdresaClient();
        AkciaClient akcCl = new AkciaClient();
        UzivatelClient uzivCl = new UzivatelClient();
        
        //data for grids
        List<Uzivatel> userList = uzivCl.findAll();
        List<Adresa> addressList = adrCl.findAll();
        List<Akcia> eventList = akcCl.findAll();
        
       
        //==========
        //USER PART
        //==========
        //grid
        Grid<Uzivatel> userGrid = new Grid<>();
        userGrid.setWidth("800px");
        userGrid.setItems(userList);
        userGrid.addColumn(Uzivatel::getId).setCaption("ID");
        userGrid.addColumn(Uzivatel::getMeno).setCaption("Meno");
        userGrid.addColumn(Uzivatel::getPriezvisko).setCaption("Priezvisko");
        userGrid.addColumn(Uzivatel::getNick).setCaption("Username");
        userGrid.addColumn(Uzivatel::getTelefon).setCaption("Telefón");
        userGrid.addColumn(Uzivatel::getMail).setCaption("E-mail");
        userGrid.addColumn(Uzivatel::getAdresa).setCaption("Adresa");
       
        //search form
        HorizontalLayout userSearchForm = new HorizontalLayout();
        TextField userSearchText = new TextField();
        Button userSearchBtn = new Button("Hľadať");
        userSearchBtn.addClickListener(e -> {
            if (userSearchText.getValue().equals("")) {
                userGrid.setItems(uzivCl.findAll());
            } else {
                List<Uzivatel> res = uzivCl.find(userSearchText.getValue());
                userGrid.setItems(res);
            }
        });
        userSearchForm.addComponents(userSearchText, userSearchBtn);
       
        //add/edit form
        Label userLabel = new Label("Užívatelia (celkom: " + uzivCl.countREST() + ")");
        FormLayout userForms = new FormLayout();
        TextField userField1 = new TextField("ID");
        userField1.setEnabled(false);
        TextField userField2 = new TextField("Meno");
        TextField userField3 = new TextField("Priezvisko");
        TextField userField4 = new TextField("Username");
        TextField userField5 = new TextField("Telefón");
        TextField userField6 = new TextField("E-mail");
        Button userBtn1 = new Button("Upraviť");
        Button userBtn2 = new Button("Zmazať");
        Button userBtn3 = new Button("Vytvoriť");
        
        userForms.addComponents(userSearchForm, userLabel, userField1, userField2, userField3, userField4, userField5, userField6, new HorizontalLayout(userBtn1, userBtn2, userBtn3));
        
        
        //==========
        //ADDREESS PART
        //==========
        //grid
        Grid<Adresa> addressGrid = new Grid<>();
        addressGrid.setWidth("800px");
        addressGrid.setItems(addressList);
        addressGrid.addColumn(Adresa::getId).setCaption("ID");
        addressGrid.addColumn(Adresa::getUlica).setCaption("Ulica");
        addressGrid.addColumn(Adresa::getCislo).setCaption("Číslo");
        addressGrid.addColumn(Adresa::getPsc).setCaption("PSČ");
        addressGrid.addColumn(Adresa::getMesto).setCaption("Mesto");
        addressGrid.addColumn(Adresa::getKrajina).setCaption("Krajina");
        
        //search form
        HorizontalLayout addressSearchForm = new HorizontalLayout();
        TextField addressSearchText = new TextField();
        Button addressSearchBtn = new Button("Hľadať");
        addressSearchBtn.addClickListener(e -> {
            if (addressSearchText.getValue().equals("")) {
                addressGrid.setItems(adrCl.findAll());
            } else {
                List<Adresa> res = adrCl.find(addressSearchText.getValue());
                addressGrid.setItems(res);
            }
        });
        addressSearchForm.addComponents(addressSearchText, addressSearchBtn);
        
        //add/edit form
        Label addressLabel = new Label("Adresy (celkom: " + adrCl.countREST() + ")");
        FormLayout addressForms = new FormLayout();
        TextField addressField1 = new TextField("ID");
        addressField1.setEnabled(false);
        TextField addressField2 = new TextField("Ulica");
        TextField addressField3 = new TextField("Číslo domu");
        TextField addressField4 = new TextField("PSČ");
        TextField addressField5 = new TextField("Mesto");
        TextField addressField6 = new TextField("Krajina");
        Button addressBtn1 = new Button("Upraviť");
        Button addressBtn2 = new Button("Zmazať");
        Button addressBtn3 = new Button("Vytvoriť");
        
        addressForms.addComponents(addressSearchForm, addressLabel, addressField1, addressField2, addressField3, addressField4, addressField5, addressField6, 
                new HorizontalLayout(addressBtn1, addressBtn2, addressBtn3)
        );
        
        
        //==========
        //EVENT PART
        //==========
        //grid
        Grid<Akcia> eventGrid = new Grid<>();
        eventGrid.setWidth("800px");
        eventGrid.setItems(eventList);
        eventGrid.addColumn(Akcia::getId).setCaption("ID");
        eventGrid.addColumn(Akcia::getNazov).setCaption("Názov");
        eventGrid.addColumn(Akcia::getDatumcas).setCaption("Dátum a čas");
        
        //search form
        HorizontalLayout eventSearchForm = new HorizontalLayout();
        TextField eventSearchText = new TextField();
        Button eventSearchBtn = new Button("Hľadať");
        eventSearchBtn.addClickListener(e -> {
            if (eventSearchText.getValue().equals("")) {
                eventGrid.setItems(akcCl.findAll());
            } else {
                List<Akcia> res = akcCl.find(eventSearchText.getValue());
                eventGrid.setItems(res);
            }
        });
        eventSearchForm.addComponents(eventSearchText, eventSearchBtn);
        
        //add/edit form
        Label eventLabel = new Label("Akcie (celkom: " + akcCl.countREST() + ")");
        FormLayout eventForm = new FormLayout();
        TextField eventField1 = new TextField("ID");
        eventField1.setEnabled(false);
        TextField eventField2 = new TextField("Názov");
        TextField eventField3 = new TextField("Dátum a čas");
        Button eventBtn1 = new Button("Upraviť");
        Button eventBtn2 = new Button("Zmazať");
        Button eventBtn3 = new Button("Vytvoriť");
        
        eventForm.addComponents(eventSearchForm, eventLabel, eventField1, eventField2, eventField3, new HorizontalLayout(eventBtn1, eventBtn2, eventBtn3));
        
        
        
        eventSection.addComponents(eventGrid, eventForm);
        addressSection.addComponents(addressGrid, addressForms);
        userSection.addComponents(userGrid, userForms);
        
        layout.addComponents(userSection, addressSection, eventSection);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
