package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;

public class MenuPageTest {

    @Test
    public void givenValidMenuPage_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();
        Image sampleImageDirectory = new Image("location", 1,  2);
        Image sampleImageEventCO = new Image("locationCO", 2,  3);
        Image sampleImageEventBR = new Image("locationBR", 3,  4);

        List<EditedMenuItem> menuItemsDirectory = new ArrayList<>();
        menuItemsDirectory.add(new EditedMenuItem(new EditedPageFieldPayload<Long>("map_id", 10L),
                                        new EditedPageFieldPayload<Boolean>("map_isVisible", true),
                                        new EditedPageFieldPayload<String>("map_label", "map_label_value"),
                                        new EditedPageFieldPayload<String>("map_url", "map_url_value"),
                                        new EditedPageFieldPayload<Image>("map_image", sampleImageDirectory))
                            );

        List<EditedMenuItem> menuItemsEvents = new ArrayList<>();
        menuItemsEvents.add(new EditedMenuItem(new EditedPageFieldPayload<Long>("CO_id", 1L),
                                        new EditedPageFieldPayload<Boolean>("CO_isVisible", true),
                                        new EditedPageFieldPayload<String>("CO_label", "co_label_value"),
                                        new EditedPageFieldPayload<String>("CO_url", "co_url_value"),
                                        new EditedPageFieldPayload<Image>("CO_image", sampleImageEventCO))
                            );

        menuItemsEvents.add(new EditedMenuItem(new EditedPageFieldPayload<Long>("BR_id", 1L),
                                        new EditedPageFieldPayload<Boolean>("BR_isVisible", true),
                                        new EditedPageFieldPayload<String>("BR_label", "br_label_value"),
                                        new EditedPageFieldPayload<String>("BR_url", "br_url_value"),
                                        new EditedPageFieldPayload<Image>("BR_image", sampleImageEventBR))
                            );

        

        MenuPage menuPage = new MenuPage.Builder()
                                        .createdOn(createdOn)
                                        .lastModified(lastModified)
                                        .pageStatus(PageStatus.DRAFT)
                                        .pageName("sample")
                                        .pageTitle("page_title")
                                        .withLocaleId(5L)
                                        .directoryContainer(new EditedMenuCategoryContainer(new EditedPageFieldPayload<String>("directory_name", "directory_value"), 
                                                                                            menuItemsDirectory))
                                        .eventsContainer(new EditedMenuCategoryContainer(new EditedPageFieldPayload<String>("events_name", "events_value"), 
                                                                                            menuItemsEvents))
                                        .build();

        Assertions.assertEquals("page_title", menuPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals("sample", menuPage.getPageName());
        Assertions.assertEquals(KioskPageType.MENU, menuPage.getPageType());
        
        MenuCategoryContainer menuContainerDirectory = menuPage.getDirectoryContainer();
        Assertions.assertEquals("directory_value", menuContainerDirectory.getTitleField().getFieldValue());
        List<MenuItem> menuItemsDirectoryReturned = menuContainerDirectory.getMenuItems();
        Assertions.assertEquals(1, menuItemsDirectoryReturned.size());
        Assertions.assertEquals(10L, menuItemsDirectoryReturned.get(0).getId().getFieldValue());
        Assertions.assertTrue(menuItemsDirectoryReturned.get(0).isVisible().getFieldValue());
        Assertions.assertEquals("map_label_value", menuItemsDirectoryReturned.get(0).getLabel().getFieldValue());
        Assertions.assertEquals("map_url_value", menuItemsDirectoryReturned.get(0).getUrl().getFieldValue());
        Assertions.assertEquals(sampleImageDirectory, menuItemsDirectoryReturned.get(0).getImage().getFieldValue());

        MenuCategoryContainer menuContainerEvents = menuPage.getEventsContainer();
        Assertions.assertEquals(2, menuContainerEvents.getMenuItems().size());

        MenuCategoryContainer menuContainerAbout = menuPage.getAboutContainer();
        Assertions.assertTrue(menuContainerAbout.getMenuItems().isEmpty());


    }

    @Test
    public void givenValidPageRep_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        Image sampleImageDirectory = new Image("location", 1,  2);
        Image sampleImageEventCO = new Image("locationCO", 2,  3);
        Image sampleImageEventBR = new Image("locationBR", 3,  4);


        FieldContainer mapFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_ITEM_ID_FIELD_TYPE, "map_id", "10"))
                                                .addButtonField(new ButtonField(MenuPage.MENU_ITEM_IS_VISIBLE_FIELD_TYPE, "map_isVisible",true))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_ITEM_LABEL_FIELD_TYPE, "map_label", "map_label_value"))
                                                .addUrlField(new UrlField(MenuPage.MENU_ITEM_URL_FIELD_TYPE, "map_url", "map_url_value"))
                                                .addImageField(new ImageField(MenuPage.MENU_ITEM_IMAGE_FIELD_TYPE, "map_image", sampleImageDirectory))
                                                .fieldContainerName("---")
                                                .build();

        FieldContainer coFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_ITEM_ID_FIELD_TYPE, "CO_id", "1"))
                                                .addButtonField(new ButtonField(MenuPage.MENU_ITEM_IS_VISIBLE_FIELD_TYPE, "CO_isVisible",true))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_ITEM_LABEL_FIELD_TYPE, "CO_label", "co_label_value"))
                                                .addUrlField(new UrlField(MenuPage.MENU_ITEM_URL_FIELD_TYPE, "CO_url", "co_url_value"))
                                                .addImageField(new ImageField(MenuPage.MENU_ITEM_IMAGE_FIELD_TYPE, "CO_image", sampleImageEventCO))
                                                .fieldContainerName("---")
                                                .build();

        FieldContainer brFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_ITEM_ID_FIELD_TYPE, "BR_id", "1"))
                                                .addButtonField(new ButtonField(MenuPage.MENU_ITEM_IS_VISIBLE_FIELD_TYPE, "BR_isVisible",true))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_ITEM_LABEL_FIELD_TYPE, "BR_label", "br_label_value"))
                                                .addUrlField(new UrlField(MenuPage.MENU_ITEM_URL_FIELD_TYPE, "BR_url", "br_url_value"))
                                                .addImageField(new ImageField(MenuPage.MENU_ITEM_IMAGE_FIELD_TYPE, "BR_image", sampleImageEventBR))
                                                .fieldContainerName("---")
                                                .build();



        FieldContainer directoryFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_CAT_TITLE_FIELD_TYPE, "directory_name", "directory_value"))
                                                .addChildContainer(mapFC)
                                                .fieldContainerName(MenuPage.DIRECTORY_CONTAINER_NAME)
                                                .build();

        FieldContainer eventsFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_CAT_TITLE_FIELD_TYPE, "events_name", "events_value"))
                                                .addChildContainer(coFC)
                                                .addChildContainer(brFC)
                                                .fieldContainerName(MenuPage.EVENTS_CONTAINER_NAME)
                                                .build();

        FieldContainer aboutFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MenuPage.MENU_CAT_TITLE_FIELD_TYPE, MenuPage.MENU_CAT_TITLE_FIELD_NAME_DEFAULT, "about_title_value"))
                                                .fieldContainerName(MenuPage.ABOUT_CONTAINER_NAME)
                                                .build();

        FieldContainer mainFC = new FieldContainer.Builder()
                                                .fieldContainerName(MenuPage.MAIN_CONTAINER_NAME)
                                                .addChildContainer(directoryFC)
                                                .addChildContainer(eventsFC)
                                                .addChildContainer(aboutFC)
                                                .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_TYPE, KioskPage.PAGE_TITLE_FIELD_NAME_DEFAULT, "page_title"))
                                .pageType(KioskPageType.MENU.toString())
                                .pageName("sample")
                                .fieldContainers(fieldContainers)
                                .build();


        MenuPage menuPage = new MenuPage.Builder(pageRep)
                                .build();


        Assertions.assertEquals("page_title", menuPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals("sample", menuPage.getPageName());
        Assertions.assertEquals(KioskPageType.MENU, menuPage.getPageType());
        
        MenuCategoryContainer menuContainerDirectory = menuPage.getDirectoryContainer();
        Assertions.assertEquals("directory_value", menuContainerDirectory.getTitleField().getFieldValue());
        List<MenuItem> menuItemsDirectoryReturned = menuContainerDirectory.getMenuItems();
        Assertions.assertEquals(1, menuItemsDirectoryReturned.size());
        Assertions.assertEquals(10L, menuItemsDirectoryReturned.get(0).getId().getFieldValue());
        Assertions.assertTrue(menuItemsDirectoryReturned.get(0).isVisible().getFieldValue());
        Assertions.assertEquals("map_label_value", menuItemsDirectoryReturned.get(0).getLabel().getFieldValue());
        Assertions.assertEquals("map_url_value", menuItemsDirectoryReturned.get(0).getUrl().getFieldValue());
        Assertions.assertEquals(sampleImageDirectory, menuItemsDirectoryReturned.get(0).getImage().getFieldValue());

        MenuCategoryContainer menuContainerEvents = menuPage.getEventsContainer();
        Assertions.assertEquals(2, menuContainerEvents.getMenuItems().size());

        MenuCategoryContainer menuContainerAbout = menuPage.getAboutContainer();
        Assertions.assertTrue(menuContainerAbout.getMenuItems().isEmpty());
        
    }
    
}
