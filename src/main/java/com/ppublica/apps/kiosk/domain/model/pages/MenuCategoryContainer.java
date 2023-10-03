package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MenuItemRegFieldSelector;

public class MenuCategoryContainer {
    private IdFieldContainerNameGenerator menuItemFCNameGenerator = new IdFieldContainerNameGenerator();
    private KioskPageField<String> title;
    private List<MenuItem> menuItems;

    public MenuCategoryContainer(KioskPageField<String> title, List<MenuItem> menuItems) {
        this.title = title;
        this.menuItems = menuItems;
    }

    public KioskPageField<String> getTitleField() {
        return this.title;
    }

    public List<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    /*
     * Expects a fieldContainer with a RegularTextLongDescriptionField and child containers representing MenuItems
     */
    public static MenuCategoryContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, MenuItemRegFieldSelector menuItemRegFieldSelector) {
        RegularTextLongDescriptionField cmsTitleField = fieldContainer.getRegularTextLongDescriptionFields().get(0);
        KioskPageField<String> titleField = converter.toStringField(cmsTitleField);

        List<FieldContainer> menuItemFCs = fieldContainer.getChildContainers();
        List<MenuItem> menuItems = new ArrayList<>();
        for(FieldContainer menuItemFC : menuItemFCs) {
            menuItems.add(MenuItem.fromFieldContainer(menuItemFC, converter, menuItemRegFieldSelector));
        }
  
        return new MenuCategoryContainer(titleField, menuItems);
    }


    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {
        List<FieldContainer> menuItemFCs = new ArrayList<>();
        String menuItemFCName;
        // convert each MenuItem to a FieldContainer
        for (MenuItem menuItem : menuItems) {
            menuItemFCName = menuItemFCNameGenerator.generateFieldContainerName(menuItem);
            menuItemFCs.add(menuItem.toFieldContainer(menuItemFCName, converter));
        }

        FieldContainer imageFC = new FieldContainer.Builder()
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(title))
            .childContainers(menuItemFCs)
            .fieldContainerName(containerName)
            .build();

        return imageFC;
    }
    
}
