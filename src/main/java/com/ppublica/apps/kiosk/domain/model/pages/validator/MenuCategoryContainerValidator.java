package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.MenuCategoryContainer;
import com.ppublica.apps.kiosk.domain.model.pages.MenuItem;

public class MenuCategoryContainerValidator {
    
    private String titleFieldType;
    private MenuItemValidator menuItemValidator;


    public MenuCategoryContainerValidator(String titleFieldType, MenuItemValidator menuItemValidator) {
        this.titleFieldType = titleFieldType;
        this.menuItemValidator = menuItemValidator;
    }

    public boolean isValid(MenuCategoryContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.getTitleField(), titleFieldType)) {
            return false;
        }

        for (MenuItem menuItem : container.getMenuItems()) {
            if (!menuItemValidator.isValid(menuItem)) {
                return false;
            }
        }

        return true;
    }


}
