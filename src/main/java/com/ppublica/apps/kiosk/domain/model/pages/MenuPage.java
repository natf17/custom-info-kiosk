package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.selector.FieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MenuItemRegFieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.validator.MenuCategoryContainerValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.MenuItemValidator;

/*
 * Represents a Menu Page with the following types of objects in the menu:
 *  - directory (directory container)
 *  - events (events container)
 *  - about (about container)
 */
public class MenuPage extends KioskPage {

    protected static final String DIRECTORY_CONTAINER_NAME = "directoryFC";
    protected static final String EVENTS_CONTAINER_NAME = "eventsFC";
    protected static final String ABOUT_CONTAINER_NAME = "aboutFC";
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    protected static final String MENU_CAT_TITLE_FIELD_TYPE = "Title";
    protected static final String MENU_CAT_TITLE_FIELD_NAME_DEFAULT = "Title";
    protected static final String MENU_ITEM_ID_FIELD_TYPE = "Id";
    protected static final String MENU_ITEM_ID_FIELD_NAME_DEFAULT = "Id";
    protected static final String MENU_ITEM_IS_VISIBLE_FIELD_TYPE = "IsVisible";
    protected static final String MENU_ITEM_IS_VISIBLE_FIELD_NAME_DEFAULT = "IsVisible";
    protected static final String MENU_ITEM_LABEL_FIELD_TYPE = "Label";
    protected static final String MENU_ITEM_LABEL_FIELD_NAME_DEFAULT = "Label";
    protected static final String MENU_ITEM_URL_FIELD_TYPE = "Url";
    protected static final String MENU_ITEM_URL_FIELD_NAME_DEFAULT = "Url";
    protected static final String MENU_ITEM_IMAGE_FIELD_TYPE = "Image";
    protected static final String MENU_ITEM_IMAGE_FIELD_NAME_DEFAULT = "Image";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.MENU;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    protected static final String PAGE_NAME_DEFAULT = "menu_page";

    private MenuCategoryContainer directoryContainer;
    private MenuCategoryContainer eventsContainer;
    private MenuCategoryContainer aboutContainer;

    private MenuPage(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, 
                String pageName, MenuCategoryContainer directoryContainer, MenuCategoryContainer eventsContainer, MenuCategoryContainer aboutContainer) {
        super(pageRep, pageTitleField, pageMetadata, kioskPageType, pageName);
        this.directoryContainer = directoryContainer;
        this.eventsContainer = eventsContainer;
        this.aboutContainer = aboutContainer;

    }

    public MenuCategoryContainer getDirectoryContainer() {
        return this.directoryContainer;
    }

    public MenuCategoryContainer getEventsContainer() {
        return this.eventsContainer;
    }

    public MenuCategoryContainer getAboutContainer() {
        return this.aboutContainer;
    }

    @Override
    protected Page buildPageRep(Page.Builder pageRepBuilder) {
        List<FieldContainer> menuCategoryFCs = new ArrayList<>();

        FieldContainer directoryFC = this.directoryContainer.toFieldContainer(DIRECTORY_CONTAINER_NAME, cmsConverter);
        FieldContainer eventsFC = this.eventsContainer.toFieldContainer(EVENTS_CONTAINER_NAME, cmsConverter);
        FieldContainer aboutFC = this.aboutContainer.toFieldContainer(ABOUT_CONTAINER_NAME, cmsConverter);

        menuCategoryFCs.add(directoryFC);
        menuCategoryFCs.add(eventsFC);
        menuCategoryFCs.add(aboutFC);


        FieldContainer mainFC = new FieldContainer.Builder()
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .childContainers(menuCategoryFCs)
                                                            .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);

        pageRepBuilder.fieldContainers(fieldContainers);

        return pageRepBuilder.build();

    }


    public static class Builder extends KioskPage.Builder<Builder, MenuPage> {
        private MenuCategoryContainerValidator menuCategoryContainerValidator;
        private MenuItemValidator menuItemValidator;
        private MenuCategoryContainer directoryContainer;
        private EditedMenuCategoryContainer editedDirectoryContainer;

        private MenuCategoryContainer eventsContainer;
        private EditedMenuCategoryContainer editedEventsContainer;
        
        private MenuCategoryContainer aboutContainer;
        private EditedMenuCategoryContainer editedAboutContainer;


        public Builder(String pageName) {
            super(KIOSK_PAGE_TYPE);
            super.pageName = pageName;
        }
        
        public Builder() {
            super(KIOSK_PAGE_TYPE);
            super.pageName = PAGE_NAME_DEFAULT;
        }

        public Builder(Page pageRep) {
            super(KIOSK_PAGE_TYPE, pageRep);
        }

        public Builder directoryContainer(EditedMenuCategoryContainer editedDirectoryContainer) {
            this.editedDirectoryContainer = editedDirectoryContainer;
            return self();
        }

        public Builder eventsContainer(EditedMenuCategoryContainer editedEventsContainer) {
            this.editedEventsContainer = editedEventsContainer;
            return self();
        }

        public Builder aboutContainer(EditedMenuCategoryContainer editedAboutContainer) {
            this.editedAboutContainer = editedAboutContainer;
            return self();
        }

        @Override
        protected void validateAndPrepareChild() {
            if(super.pageRep != null) {
                // process pageRep, overriding everything else
                FieldContainer mainFC = pageRep.getFieldContainers().get(0);
                FieldSelector fieldSelector = new FieldSelector(mainFC);
                
                FieldContainer directoryFC = fieldSelector.selectChildContainer(DIRECTORY_CONTAINER_NAME);
                FieldContainer eventsFC = fieldSelector.selectChildContainer(EVENTS_CONTAINER_NAME);
                FieldContainer aboutFC = fieldSelector.selectChildContainer(ABOUT_CONTAINER_NAME);

                MenuItemRegFieldSelector menuItemRegFieldSelector = new MenuItemRegFieldSelector(MENU_ITEM_ID_FIELD_TYPE, MENU_ITEM_LABEL_FIELD_TYPE);
                directoryContainer = MenuCategoryContainer.fromFieldContainer(directoryFC, super.toKioskPageFieldConverter, menuItemRegFieldSelector);
                eventsContainer = MenuCategoryContainer.fromFieldContainer(eventsFC, super.toKioskPageFieldConverter, menuItemRegFieldSelector);
                aboutContainer = MenuCategoryContainer.fromFieldContainer(aboutFC, super.toKioskPageFieldConverter, menuItemRegFieldSelector);

            }

            if(directoryContainer == null) {
                if(editedDirectoryContainer == null) {
                    editedDirectoryContainer = EditedMenuCategoryContainer.empty(MENU_CAT_TITLE_FIELD_TYPE);
                }
                directoryContainer = editedDirectoryContainer.toMenuCategoryContainer(MENU_CAT_TITLE_FIELD_TYPE, MENU_ITEM_ID_FIELD_TYPE, MENU_ITEM_IS_VISIBLE_FIELD_TYPE, 
                                                                                MENU_ITEM_LABEL_FIELD_TYPE, MENU_ITEM_URL_FIELD_TYPE, MENU_ITEM_IMAGE_FIELD_TYPE);
            }

            if(eventsContainer == null) {
                if(editedEventsContainer == null) {
                    editedEventsContainer = EditedMenuCategoryContainer.empty(MENU_CAT_TITLE_FIELD_NAME_DEFAULT);
                }
                eventsContainer = editedEventsContainer.toMenuCategoryContainer(MENU_CAT_TITLE_FIELD_TYPE, MENU_ITEM_ID_FIELD_TYPE, MENU_ITEM_IS_VISIBLE_FIELD_TYPE, 
                                                                                MENU_ITEM_LABEL_FIELD_TYPE, MENU_ITEM_URL_FIELD_TYPE, MENU_ITEM_IMAGE_FIELD_TYPE);
            }

            if(aboutContainer == null) {
                if(editedAboutContainer == null) {
                    editedAboutContainer = EditedMenuCategoryContainer.empty(MENU_CAT_TITLE_FIELD_NAME_DEFAULT);
                }
                aboutContainer = editedAboutContainer.toMenuCategoryContainer(MENU_CAT_TITLE_FIELD_TYPE, MENU_ITEM_ID_FIELD_TYPE, MENU_ITEM_IS_VISIBLE_FIELD_TYPE, 
                                                                                MENU_ITEM_LABEL_FIELD_TYPE, MENU_ITEM_URL_FIELD_TYPE, MENU_ITEM_IMAGE_FIELD_TYPE);
            }

            // validate objects
            menuItemValidator = new MenuItemValidator(MENU_ITEM_ID_FIELD_TYPE, MENU_ITEM_IS_VISIBLE_FIELD_TYPE, MENU_ITEM_LABEL_FIELD_TYPE, MENU_ITEM_URL_FIELD_TYPE, 
                                                        MENU_ITEM_IMAGE_FIELD_TYPE);
            menuCategoryContainerValidator = new MenuCategoryContainerValidator(MENU_CAT_TITLE_FIELD_TYPE, menuItemValidator);
            
            if(!menuCategoryContainerValidator.isValid(directoryContainer)) {
                throw new RuntimeException("Invalid field type in the MenuCategoryContainer for the directory");
            }

            if(!menuCategoryContainerValidator.isValid(eventsContainer)) {
                throw new RuntimeException("Invalid field type in the MenuCategoryContainer for events");
            }

            if(!menuCategoryContainerValidator.isValid(aboutContainer)) {
                throw new RuntimeException("Invalid field type in the MenuCategoryContainer for the about category");
            }

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected MenuPage buildChild() {

            return new MenuPage(super.pageRep, super.kioskPageTitleField, super.pageMetadata, super.kioskPageType, super.pageName, directoryContainer, eventsContainer, aboutContainer);
        }
    }

    
}
