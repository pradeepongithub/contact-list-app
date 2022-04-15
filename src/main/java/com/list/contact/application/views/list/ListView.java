package com.list.contact.application.views.list;

import com.list.contact.application.data.model.Contact;
import com.list.contact.application.data.service.ContactService;
import com.list.contact.application.pagination.PaginatedGrid;
import com.list.contact.application.views.MainLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import javax.annotation.security.PermitAll;

@SpringComponent
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Contact List")
@PermitAll
public class ListView extends VerticalLayout {

    private final PaginatedGrid<Contact> grid;
    private final TextField filterText;

    private final ContactService service;

    public ListView(ContactService service) {
        this.service = service;
        grid = new PaginatedGrid<>();
        filterText = new TextField();

        grid.setItems(this.service.getPageableContacts(0,10).getContent());

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        FlexLayout content = new FlexLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();
        add(getToolbar(), content);
        updateList();

    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.addColumn(Contact::getName).setHeader("Name").setSortable(true);
        grid.addComponentColumn(contact -> new Image(contact.getPhotoUrl(), "contact photo")).setHeader("Photo");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        // Sets the max number of items to be rendered on the grid for each page
        //grid.setPageSize(16);

        // Sets how many pages should be visible on the pagination before and/or after the current selected page
        //grid.setPaginatorSize(5);

        //If you want a specific page to be selected on the pagination:
        //grid.setPage(1);
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.getAllContactsByNamePrefix(filterText.getValue()).getContent());
    }

}
