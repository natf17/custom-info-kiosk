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

public class MapPageTest {
    @Test
    public void givenValidMapPage_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();
        Image defaultImage = new Image("def_location", 1,  2);
        Image brImage = new Image("br_location", 1,  2);
        Image wImage = new Image("w_location", 1,  2);
        Image fImage = new Image("f_location", 1,  2);
        Image dImage = new Image("w_location", 1,  2);


        MapPage mapPage = new MapPage.Builder()
                                        .createdOn(createdOn)
                                        .lastModified(lastModified)
                                        .pageStatus(PageStatus.DRAFT)
                                        .pageName("sample")
                                        .pageTitle("page_title")
                                        .withLocaleId(5L)
                                        .pageDescriptionField(new EditedPageFieldPayload<String>("pageDescr_fName", "pageDescr_fValue"))
                                        .tapWidgetContainer(new EditedTapWidgetContainer(
                                                                            new EditedPageFieldPayload<String>("instructions_fName", "instructions_fValue"),
                                                                            new EditedPageFieldPayload<String>("brLabel_fName", "brLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("waterLabel_fName", "waterLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("firstAidLabel_fName", "firstAidLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("donationsLabel_fName", "donationsLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("noResultsFound_fName", "noResultsFound_fValue")))
                                        .mapViewConfigContainer(new EditedMapViewConfigContainer(
                                                                    new EditedPageFieldPayload<Boolean>("enableFsCustomMaps_fName", true),
                                                                    new EditedPageFieldPayload<String>("clearResults_fName", "clearResults_fValue"),
                                                                    new EditedPageFieldPayload<String>("levelSelect_fName", "levelSelect_fValue"),
                                                                    new EditedPageFieldPayload<String>("mapNotAvailable_fName", "mapNotAvailable_fValue")))
                                        .bathroomAmenityContainer(new EditedAmenityContainer(
                                                                    new EditedPageFieldPayload<String>("br_widgetLabel_fName", "br_widgetLabel_fValue"),
                                                                    new EditedPageFieldPayload<String>("br_headingLabel_fName", "br_headingLabel_fValue")))
                                        .waterFountainAmenityContainer(new EditedAmenityContainer(
                                                                    new EditedPageFieldPayload<String>("w_widgetLabel_fName", "w_widgetLabel_fValue"),
                                                                    new EditedPageFieldPayload<String>("w_headingLabel_fName", "w_headingLabel_fValue")))
                                        .firstAidAmenityContainer(new EditedAmenityContainer(
                                                                    new EditedPageFieldPayload<String>("f_widgetLabel_fName", "f_widgetLabel_fValue"),
                                                                    new EditedPageFieldPayload<String>("f_headingLabel_fName", "f_headingLabel_fValue")))
                                        .donationAmenityContainer(new EditedAmenityContainer(
                                                                    new EditedPageFieldPayload<String>("d_widgetLabel_fName", "d_widgetLabel_fValue"),
                                                                    new EditedPageFieldPayload<String>("d_headingLabel_fName", "d_headingLabel_fValue")))
                                        .mapContainer(new EditedMapContainer(
                                                            new EditedPageFieldPayload<Image>("defaultMap_fName", defaultImage),
                                                            new EditedPageFieldPayload<Image>("bathroomsMap_fName", brImage),
                                                            new EditedPageFieldPayload<Image>("waterFountainsMap_fName", wImage),
                                                            new EditedPageFieldPayload<Image>("firstAidMap_fName", fImage),
                                                            new EditedPageFieldPayload<Image>("donationsMap_fName", dImage)))
                                        .build();

        Assertions.assertEquals("page_title", mapPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals("sample", mapPage.getPageName());
        Assertions.assertEquals(KioskPageType.MAP, mapPage.getPageType());
        
        Assertions.assertEquals("pageDescr_fValue", mapPage.getPageDescriptionField().getFieldValue());

        TapWidgetContainer tapWidgetContainer = mapPage.getTapWidgetContainer();
        Assertions.assertEquals("instructions_fValue", tapWidgetContainer.getInstructionsField().getFieldValue());
        Assertions.assertEquals("brLabel_fValue", tapWidgetContainer.getBrLabelField().getFieldValue());
        Assertions.assertEquals("waterLabel_fValue", tapWidgetContainer.getWaterLabelField().getFieldValue());
        Assertions.assertEquals("firstAidLabel_fValue", tapWidgetContainer.getFirstAidLabelField().getFieldValue());
        Assertions.assertEquals("donationsLabel_fValue", tapWidgetContainer.getDonationsLabelField().getFieldValue());
        Assertions.assertEquals("noResultsFound_fValue", tapWidgetContainer.getNoResultsFoundField().getFieldValue());


        MapViewConfigContainer mapViewConfigContainer = mapPage.getMapViewConfigContainer();
        Assertions.assertTrue(mapViewConfigContainer.enableFsCustomMapsField().getFieldValue());
        Assertions.assertEquals("clearResults_fValue", mapViewConfigContainer.getClearResultsField().getFieldValue());
        Assertions.assertEquals("levelSelect_fValue", mapViewConfigContainer.getLevelSelectField().getFieldValue());
        Assertions.assertEquals("mapNotAvailable_fValue", mapViewConfigContainer.getMapNotAvailableField().getFieldValue());

        AmenityContainer bathroomAmenityContainer = mapPage.getBathroomAmenityContainer();
        Assertions.assertEquals("br_widgetLabel_fValue", bathroomAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("br_headingLabel_fValue", bathroomAmenityContainer.getHeadingLabelField().getFieldValue());

        AmenityContainer waterFountainAmenityContainer = mapPage.getWaterFountainAmenityContainer();
        Assertions.assertEquals("w_widgetLabel_fValue", waterFountainAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("w_headingLabel_fValue", waterFountainAmenityContainer.getHeadingLabelField().getFieldValue());

        AmenityContainer firstAidAmenityContainer = mapPage.getFirstAidAmenityContainer();
        Assertions.assertEquals("f_widgetLabel_fValue", firstAidAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("f_headingLabel_fValue", firstAidAmenityContainer.getHeadingLabelField().getFieldValue());

        AmenityContainer donationAmenityContainer = mapPage.getDonationAmenityContainer();
        Assertions.assertEquals("d_widgetLabel_fValue", donationAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("d_headingLabel_fValue", donationAmenityContainer.getHeadingLabelField().getFieldValue());

        MapContainer mapContainer = mapPage.getMapContainer();
        Assertions.assertEquals(defaultImage, mapContainer.getDefaultMapField().getFieldValue());
        Assertions.assertEquals(brImage, mapContainer.getBathroomsMapField().getFieldValue());
        Assertions.assertEquals(wImage, mapContainer.getWaterFountainsMapField().getFieldValue());
        Assertions.assertEquals(fImage, mapContainer.getFirstAidMapField().getFieldValue());
        Assertions.assertEquals(dImage, mapContainer.getDonationsMapField().getFieldValue());
        
    }

    @Test
    public void givenValidPageRep_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();
        Image defaultImage = new Image("def_location", 1,  2);
        Image brImage = new Image("br_location", 1,  2);
        Image wImage = new Image("w_location", 1,  2);
        Image fImage = new Image("f_location", 1,  2);
        Image dImage = new Image("w_location", 1,  2);

        FieldContainer tapWidgetFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.TAP_WIDGET_INSTRUCTIONS_FIELD_TYPE, "instructions_fName", "instructions_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.TAP_WIDGET_BRLABEL_FIELD_TYPE, "brLabel_fName", "brLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.TAP_WIDGET_WATERLABEL_FIELD_TYPE, "waterLabel_fName", "waterLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.TAP_WIDGET_FIRSTAIDLABEL_FIELD_TYPE, "firstAidLabel_fName", "firstAidLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.TAP_WIDGET_DONATIONSLABEL_FIELD_TYPE, "donationsLabel_fName", "donationsLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.TAP_WIDGET_NORESULTSFOUND_FIELD_TYPE, "noResultsFound_fName", "noResultsFound_fValue"))
                                                .fieldContainerName(MapPage.TAP_WIDGET_CONTAINER_NAME)
                                                .build();

        FieldContainer mapViewConfigFC = new FieldContainer.Builder()
                                                .addButtonField(new ButtonField(MapPage.MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_TYPE, "enableFsCustomMaps_fName", true))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.MAPVCONFIG_CLEARRESULTS_FIELD_TYPE, "clearResults_fName", "clearResults_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.MAPVCONFIG_LEVELSELECT_FIELD_TYPE, "levelSelect_fName", "levelSelect_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.MAPVCONFIG_MAPNOTAVAILABLE_FIELD_TYPE, "mapNotAvailable_fName", "mapNotAvailable_fValue"))
                                                .fieldContainerName(MapPage.MAP_VIEW_CONFIG_CONTAINER_NAME)
                                                .build();
        
        FieldContainer brAmenityFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_WIDGETLABEL_FIELD_TYPE, "br_widgetLabel_fName", "br_widgetLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_HEADINGLABEL_FIELD_TYPE, "br_headingLabel_fName", "br_headingLabel_fValue"))
                                                .fieldContainerName(MapPage.BATHROOM_AMENITY_CONTAINER_NAME)
                                                .build();

        FieldContainer wAmenityFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_WIDGETLABEL_FIELD_TYPE, "w_widgetLabel_fName", "w_widgetLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_HEADINGLABEL_FIELD_TYPE, "w_headingLabel_fName", "w_headingLabel_fValue"))
                                                .fieldContainerName(MapPage.WATER_FOUNTAIN_AMENITY_CONTAINER_NAME)
                                                .build();

        FieldContainer fAmenityFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_WIDGETLABEL_FIELD_TYPE, "f_widgetLabel_fName", "f_widgetLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_HEADINGLABEL_FIELD_TYPE, "f_headingLabel_fName", "f_headingLabel_fValue"))
                                                .fieldContainerName(MapPage.FIRSTAID_AMENITY_CONTAINER_NAME)
                                                .build();

        FieldContainer dAmenityFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_WIDGETLABEL_FIELD_TYPE, "d_widgetLabel_fName", "d_widgetLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(MapPage.AMENITY_HEADINGLABEL_FIELD_TYPE, "d_headingLabel_fName", "d_headingLabel_fValue"))
                                                .fieldContainerName(MapPage.DONATION_AMENITY_CONTAINER_NAME)
                                                .build();

         FieldContainer mapFC = new FieldContainer.Builder()
                                                .addImageField(new ImageField(MapPage.MAP_DEFAULT_FIELD_TYPE, "defaultMap_fName", defaultImage))
                                                .addImageField(new ImageField(MapPage.MAP_BATHROOMS_FIELD_TYPE, "bathroomsMap_fName", brImage))
                                                .addImageField(new ImageField(MapPage.MAP_WFOUNTAINS_FIELD_TYPE, "waterFountainsMap_fName", wImage))
                                                .addImageField(new ImageField(MapPage.MAP_FIRSTAID_FIELD_TYPE, "firstAidMap_fName", fImage))
                                                .addImageField(new ImageField(MapPage.MAP_DONATIONS_FIELD_TYPE, "donationsMap_fName", dImage))
                                                .fieldContainerName(MapPage.MAPS_CONTAINER_NAME)
                                                .build();
        

        

        RegularTextLongDescriptionField pageDescriptionField = new RegularTextLongDescriptionField(EventsPage.PAGE_DESCR_FIELD_TYPE, MapPage.PAGE_DESCR_FIELD_NAME_DEFAULT, "pageDescr_fValue");


        FieldContainer mainFC = new FieldContainer.Builder()
                                                .fieldContainerName(MapPage.MAIN_CONTAINER_NAME)
                                                .addChildContainer(tapWidgetFC)
                                                .addChildContainer(mapViewConfigFC)
                                                .addChildContainer(brAmenityFC)
                                                .addChildContainer(wAmenityFC)
                                                .addChildContainer(fAmenityFC)
                                                .addChildContainer(dAmenityFC)
                                                .addChildContainer(mapFC)
                                                .addRegularTextLongDescriptionField(pageDescriptionField)
                                                .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_TYPE, KioskPage.PAGE_TITLE_FIELD_NAME_DEFAULT, "page_title"))
                                .pageType(KioskPageType.MAP.toString())
                                .pageName("sample")
                                .fieldContainers(fieldContainers)
                                .build();


        MapPage mapPage = new MapPage.Builder(pageRep)
                                .build();


        Assertions.assertEquals("page_title", mapPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals("sample", mapPage.getPageName());
        Assertions.assertEquals(KioskPageType.MAP, mapPage.getPageType());
        
        Assertions.assertEquals("pageDescr_fValue", mapPage.getPageDescriptionField().getFieldValue());

        TapWidgetContainer tapWidgetContainer = mapPage.getTapWidgetContainer();
        Assertions.assertEquals("instructions_fValue", tapWidgetContainer.getInstructionsField().getFieldValue());
        Assertions.assertEquals("brLabel_fValue", tapWidgetContainer.getBrLabelField().getFieldValue());
        Assertions.assertEquals("waterLabel_fValue", tapWidgetContainer.getWaterLabelField().getFieldValue());
        Assertions.assertEquals("firstAidLabel_fValue", tapWidgetContainer.getFirstAidLabelField().getFieldValue());
        Assertions.assertEquals("donationsLabel_fValue", tapWidgetContainer.getDonationsLabelField().getFieldValue());
        Assertions.assertEquals("noResultsFound_fValue", tapWidgetContainer.getNoResultsFoundField().getFieldValue());


        MapViewConfigContainer mapViewConfigContainer = mapPage.getMapViewConfigContainer();
        Assertions.assertTrue(mapViewConfigContainer.enableFsCustomMapsField().getFieldValue());
        Assertions.assertEquals("clearResults_fValue", mapViewConfigContainer.getClearResultsField().getFieldValue());
        Assertions.assertEquals("levelSelect_fValue", mapViewConfigContainer.getLevelSelectField().getFieldValue());
        Assertions.assertEquals("mapNotAvailable_fValue", mapViewConfigContainer.getMapNotAvailableField().getFieldValue());

        AmenityContainer bathroomAmenityContainer = mapPage.getBathroomAmenityContainer();
        Assertions.assertEquals("br_widgetLabel_fValue", bathroomAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("br_headingLabel_fValue", bathroomAmenityContainer.getHeadingLabelField().getFieldValue());

        AmenityContainer waterFountainAmenityContainer = mapPage.getWaterFountainAmenityContainer();
        Assertions.assertEquals("w_widgetLabel_fValue", waterFountainAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("w_headingLabel_fValue", waterFountainAmenityContainer.getHeadingLabelField().getFieldValue());

        AmenityContainer firstAidAmenityContainer = mapPage.getFirstAidAmenityContainer();
        Assertions.assertEquals("f_widgetLabel_fValue", firstAidAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("f_headingLabel_fValue", firstAidAmenityContainer.getHeadingLabelField().getFieldValue());

        AmenityContainer donationAmenityContainer = mapPage.getDonationAmenityContainer();
        Assertions.assertEquals("d_widgetLabel_fValue", donationAmenityContainer.getWidgetLabelField().getFieldValue());
        Assertions.assertEquals("d_headingLabel_fValue", donationAmenityContainer.getHeadingLabelField().getFieldValue());

        MapContainer mapContainer = mapPage.getMapContainer();
        Assertions.assertEquals(defaultImage, mapContainer.getDefaultMapField().getFieldValue());
        Assertions.assertEquals(brImage, mapContainer.getBathroomsMapField().getFieldValue());
        Assertions.assertEquals(wImage, mapContainer.getWaterFountainsMapField().getFieldValue());
        Assertions.assertEquals(fImage, mapContainer.getFirstAidMapField().getFieldValue());
        Assertions.assertEquals(dImage, mapContainer.getDonationsMapField().getFieldValue());
        
    }
}
