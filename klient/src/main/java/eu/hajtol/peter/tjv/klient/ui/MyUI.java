package eu.hajtol.peter.tjv.klient.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        
        //dropdown selects
        ComboBox<Adresa> eventAddressSelect = new ComboBox("Adresa", addressList);
        ComboBox<Adresa> userAddressSelect = new ComboBox("Adresa", addressList);
       
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
        
        Grid<Akcia> userEventGrid = new Grid<>();
        userEventGrid.addColumn(Akcia::getNazov).setCaption("Názov");
        userEventGrid.addColumn(Akcia::getDatumcas).setCaption("Dátum a čas");
       
        Label userLabel = new Label("Užívatelia (celkom: " + uzivCl.countREST() + ")");
        
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
        FormLayout userDetailForm = new FormLayout();
        TextField userField1 = new TextField("ID");
        userField1.setEnabled(false);
        TextField userField2 = new TextField("Meno");
        TextField userField3 = new TextField("Priezvisko");
        TextField userField4 = new TextField("Username");
        TextField userField5 = new TextField("Telefón");
        TextField userField6 = new TextField("E-mail");
        Button userEditBtn = new Button("Upraviť");
        Button userDeleteBtn = new Button("Zmazať");
        Button userAddBtn = new Button("Vytvoriť");
        userEditBtn.setVisible(false);
        userDeleteBtn.setVisible(false);
        userAddBtn.setVisible(true);
        userDetailForm.addComponents(userField1, userField2, userField3, userField4, userField5, userField6, userAddressSelect, new HorizontalLayout(userEditBtn, userDeleteBtn, userAddBtn));
        
        //add actions to buttons and grid selection
        userEditBtn.addClickListener(e -> {
            Uzivatel user = new Uzivatel();
            user.setId(Integer.parseInt(userField1.getValue()));
            user.setMeno(userField2.getValue());
            user.setMeno(userField2.getValue());
            user.setPriezvisko(userField3.getValue());
            user.setNick(userField4.getValue());
            user.setTelefon(userField5.getValue());
            user.setMail(userField6.getValue());
            user.setAdresa(userAddressSelect.getValue());
            uzivCl.edit_XML(user, user.getId().toString());
            userGrid.setItems(uzivCl.findAll());
        });
        userDeleteBtn.addClickListener(e -> {
            uzivCl.remove(userField1.getValue());
            userGrid.setItems(uzivCl.findAll());
            userLabel.setValue("Užívatelia (celkom: " + uzivCl.countREST() + ")");
        });
        userAddBtn.addClickListener(e -> {
            Uzivatel user = new Uzivatel();
            user.setMeno(userField2.getValue());
            user.setPriezvisko(userField3.getValue());
            user.setNick(userField4.getValue());
            user.setTelefon(userField5.getValue());
            user.setMail(userField6.getValue());
            user.setAdresa(userAddressSelect.getValue());
            uzivCl.create_XML(user);
            userGrid.setItems(uzivCl.findAll());
            userLabel.setValue("Užívatelia (celkom: " + uzivCl.countREST() + ")");
            
            userField1.setValue("");
            userField2.setValue("");
            userField3.setValue("");
            userField4.setValue("");
            userField5.setValue("");
            userField6.setValue("");
            userAddressSelect.setSelectedItem(null);
        });
        userGrid.addSelectionListener((SelectionEvent<Uzivatel> e) -> {
            Set<Uzivatel> selected = e.getAllSelectedItems();
            
            if (selected.size() > 0) {
                Uzivatel user = selected.stream().findFirst().get();
                
                userEditBtn.setVisible(true);
                userDeleteBtn.setVisible(true);
                userAddBtn.setVisible(false);
                
                userField1.setValue(user.getId().toString());
                userField2.setValue(user.getMeno());
                userField3.setValue(user.getPriezvisko());
                userField4.setValue(user.getNick());
                userField5.setValue(user.getTelefon());
                userField6.setValue(user.getMail());
                userAddressSelect.setSelectedItem(user.getAdresa());
                userEventGrid.setItems(user.getAkcie());
            } else {
                userEditBtn.setVisible(false);
                userDeleteBtn.setVisible(false);
                userAddBtn.setVisible(true);
                
                userField1.setValue("");
                userField2.setValue("");
                userField3.setValue("");
                userField4.setValue("");
                userField5.setValue("");
                userField6.setValue("");
                userAddressSelect.setSelectedItem(null);
                userEventGrid.setItems(new ArrayList<Akcia>());
            }            
        });
        
        VerticalLayout userForms = new VerticalLayout();
        userForms.addComponents(userLabel, userSearchForm, userDetailForm);
        
        
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
        
        Label addressLabel = new Label("Adresy (celkom: " + adrCl.countREST() + ")");
        
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
        FormLayout addressDetailForm = new FormLayout();
        TextField addressField1 = new TextField("ID");
        addressField1.setEnabled(false);
        TextField addressField2 = new TextField("Ulica");
        TextField addressField3 = new TextField("Číslo domu");
        TextField addressField4 = new TextField("PSČ");
        TextField addressField5 = new TextField("Mesto");
        TextField addressField6 = new TextField("Krajina");
        Button addressEditBtn = new Button("Upraviť");
        Button addressDeleteBtn = new Button("Zmazať");
        Button addressAddBtn = new Button("Vytvoriť");
        addressEditBtn.setVisible(false);
        addressDeleteBtn.setVisible(false);
        addressAddBtn.setVisible(true);
        addressDetailForm.addComponents(addressField1, addressField2, addressField3, addressField4, addressField5, addressField6, 
                new HorizontalLayout(addressEditBtn, addressDeleteBtn, addressAddBtn)
        );
        
        //add actions to buttons and grid selection
        addressEditBtn.addClickListener(e -> {
            Adresa address = new Adresa();
            address.setId(Integer.parseInt(addressField1.getValue()));
            address.setUlica(addressField2.getValue());
            address.setCislo(Integer.parseInt(addressField3.getValue()));
            address.setPsc(addressField4.getValue());
            address.setMesto(addressField5.getValue());
            address.setKrajina(addressField6.getValue());
            uzivCl.edit_XML(address, address.getId().toString());
            List<Adresa> addresses = adrCl.findAll();
            addressGrid.setItems(addresses);
            userAddressSelect.setItems(addresses);
            eventAddressSelect.setItems(addresses);
        });
        addressDeleteBtn.addClickListener(e -> {
            adrCl.remove(addressField1.getValue());
            List<Adresa> addresses = adrCl.findAll();
            addressGrid.setItems(addresses);
            userAddressSelect.setItems(addresses);
            eventAddressSelect.setItems(addresses);
            addressLabel.setValue("Adresy (celkom: " + adrCl.countREST() + ")");
        });
        addressAddBtn.addClickListener(e -> {
            Adresa address = new Adresa();
            address.setUlica(addressField2.getValue());
            address.setCislo(Integer.parseInt(addressField3.getValue()));
            address.setPsc(addressField4.getValue());
            address.setMesto(addressField5.getValue());
            address.setKrajina(addressField6.getValue());
            adrCl.create_XML(address);
            List<Adresa> addresses = adrCl.findAll();
            addressGrid.setItems(addresses);
            userAddressSelect.setItems(addresses);
            eventAddressSelect.setItems(addresses);
            addressLabel.setValue("Adresy (celkom: " + adrCl.countREST() + ")");
            
            addressField1.setValue("");
            addressField2.setValue("");
            addressField3.setValue("");
            addressField4.setValue("");
            addressField5.setValue("");
            addressField6.setValue("");
        });
        addressGrid.addSelectionListener(e -> {
            Set<Adresa> selected = e.getAllSelectedItems();
            
            if (selected.size() > 0) {
                Adresa address = selected.stream().findFirst().get();
                
                addressEditBtn.setVisible(true);
                addressDeleteBtn.setVisible(true);
                addressAddBtn.setVisible(false);
                
                addressField1.setValue(address.getId().toString());
                addressField2.setValue(address.getUlica());
                addressField3.setValue(address.getCislo().toString());
                addressField4.setValue(address.getPsc());
                addressField5.setValue(address.getMesto());
                addressField6.setValue(address.getKrajina());
            } else {
                addressEditBtn.setVisible(false);
                addressDeleteBtn.setVisible(false);
                addressAddBtn.setVisible(true);
                
                addressField1.setValue("");
                addressField2.setValue("");
                addressField3.setValue("");
                addressField4.setValue("");
                addressField5.setValue("");
                addressField6.setValue("");
            }            
        });
        
        VerticalLayout addressForms = new VerticalLayout();
        addressForms.addComponents(addressLabel, addressSearchForm, addressDetailForm);
        
        
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
        eventGrid.addColumn(Akcia::getAdresa).setCaption("Adresa");
        
        Label eventLabel = new Label("Akcie (celkom: " + akcCl.countREST() + ")");
        
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
        FormLayout eventDetailForm = new FormLayout();
        TextField eventField1 = new TextField("ID");
        eventField1.setEnabled(false);
        TextField eventField2 = new TextField("Názov");
        TextField eventField3 = new TextField("Dátum a čas");
        Button eventEditBtn = new Button("Upraviť");
        Button eventDeleteBtn = new Button("Zmazať");
        Button eventAddBtn = new Button("Vytvoriť");
        eventEditBtn.setVisible(false);
        eventDeleteBtn.setVisible(false);
        eventAddBtn.setVisible(true);
        eventDetailForm.addComponents(eventSearchForm, eventLabel, eventField1, eventField2, eventField3, eventAddressSelect, new HorizontalLayout(eventEditBtn, eventDeleteBtn, eventAddBtn));
        
        //add actions to buttons and grid selection
        eventEditBtn.addClickListener(e -> {
            Akcia event = new Akcia();
            event.setId(Integer.parseInt(eventField1.getValue()));
            event.setNazov(eventField2.getValue());
            event.setDatumcas(eventField3.getValue());
            event.setAdresa(eventAddressSelect.getValue());
            akcCl.edit_XML(event, event.getId().toString());
            eventGrid.setItems(akcCl.findAll());
        });
        eventDeleteBtn.addClickListener(e -> {
            akcCl.remove(eventField1.getValue());
            eventGrid.setItems(akcCl.findAll());
            eventLabel.setValue("Akcie (celkom: " + akcCl.countREST() + ")");
        });
        eventAddBtn.addClickListener(e -> {
            Akcia event = new Akcia();
            event.setNazov(eventField2.getValue());
            event.setDatumcas(eventField3.getValue());
            event.setAdresa(eventAddressSelect.getValue());
            akcCl.create_XML(event);
            eventGrid.setItems(akcCl.findAll());
            eventLabel.setValue("Akcie (celkom: " + akcCl.countREST() + ")");
            
            eventField1.setValue("");
            eventField2.setValue("");
            eventField3.setValue("");
        });
        eventGrid.addSelectionListener(e -> {
            Set<Akcia> selected = e.getAllSelectedItems();
            
            if (selected.size() > 0) {
                Akcia event = selected.stream().findFirst().get();
                
                eventEditBtn.setVisible(true);
                eventDeleteBtn.setVisible(true);
                eventAddBtn.setVisible(false);
                
                eventField1.setValue(event.getId().toString());
                eventField2.setValue(event.getNazov());
                eventField3.setValue(event.getDatumcas());
                eventAddressSelect.setSelectedItem(event.getAdresa());
            } else {
                eventEditBtn.setVisible(false);
                eventDeleteBtn.setVisible(false);
                eventAddBtn.setVisible(true);
                
                eventField1.setValue("");
                eventField2.setValue("");
                eventField3.setValue("");
                eventAddressSelect.setSelectedItem(null);
            }            
        });
        
        VerticalLayout eventForms = new VerticalLayout();
        eventForms.addComponents(eventLabel, eventSearchForm, eventDetailForm);
        
        
        
        eventSection.addComponents(eventGrid, eventForms);
        addressSection.addComponents(addressGrid, addressForms);
        userSection.addComponents(userGrid, userForms, userEventGrid);
        
        layout.addComponents(userSection, addressSection, eventSection);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
