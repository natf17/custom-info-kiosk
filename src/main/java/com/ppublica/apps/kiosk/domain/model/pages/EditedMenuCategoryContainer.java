package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

public class EditedMenuCategoryContainer {
    private final EditedPageFieldPayload<String> title;
    private final List<EditedMenuItem> editedMenuItems;

    public EditedMenuCategoryContainer(EditedPageFieldPayload<String> title) {
        this(title, new ArrayList<>());
    }

    public EditedMenuCategoryContainer(EditedPageFieldPayload<String> title, List<EditedMenuItem> menuItems) {
        this.title = title;
        this.editedMenuItems = menuItems;
    }

    public EditedPageFieldPayload<String> getTitleField() {
        return this.title;
    }

    public List<EditedMenuItem> getMenuItems() {
        return this.editedMenuItems;
    }

    public static EditedMenuCategoryContainer empty(String titleFieldName) {
        // empty, so no menu items
        return new EditedMenuCategoryContainer(new EditedPageFieldPayload<String>(titleFieldName, null));
    }

    public MenuCategoryContainer toMenuCategoryContainer(String menuCategoryTitleFieldType, String menuItemIdFieldType, String menuItemIsVisibleFieldType, 
                                                            String menuItemLabelFieldType, String menuItemUrlFieldType, String menuItemImageFieldType) {
        // since the MenuItems are in a list in the MenuCategoryContainer, we don't need an identifier (field type or container name)
        KioskPageField<String> titleField = title.toKioskPageField(menuCategoryTitleFieldType, true);
        List<MenuItem> menuItems = new ArrayList<>();

        for(EditedMenuItem editedMenuItem : editedMenuItems) {
            menuItems.add(editedMenuItem.toMenuItem(menuItemIdFieldType, menuItemIsVisibleFieldType, menuItemLabelFieldType, menuItemUrlFieldType, menuItemImageFieldType));
        }

        return new MenuCategoryContainer(titleField, menuItems);
    }
}
