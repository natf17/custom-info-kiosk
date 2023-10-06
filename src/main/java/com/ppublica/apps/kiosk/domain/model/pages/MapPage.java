package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.selector.AmenityContainerSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.FieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MapContainerSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MapViewConfigContainerFieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.TapWidgetContainerFieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.validator.AmenityContainerValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.KioskPageFieldTypeValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.MapContainerValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.MapViewConfigContainerValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.TapWidgetContainerValidator;

/*
 * Represents an Events Page with the following:
 *  - regular description
 *  - an eventLangPickerLabel
 *  - strings for buttons/labels (general string container)
 *  - text for different event types (event section container)
 */
public class MapPage extends KioskPage {
    public static final String PAGE_DESCR_FIELD_TYPE = "PageDescription";
    protected static final String PAGE_DESCR_FIELD_NAME_DEFAULT = "PageDescription";

    // for page rep
    protected static final String TAP_WIDGET_CONTAINER_NAME = "tapWidgetFC";
    protected static final String MAP_VIEW_CONFIG_CONTAINER_NAME = "mapViewConfigFC";
    protected static final String BATHROOM_AMENITY_CONTAINER_NAME = "bathroomAmenityFC";
    protected static final String WATER_FOUNTAIN_AMENITY_CONTAINER_NAME = "waterFountainAmenityFC";
    protected static final String FIRSTAID_AMENITY_CONTAINER_NAME = "firstAidAmenityFC";
    protected static final String DONATION_AMENITY_CONTAINER_NAME = "donationAmenityFC";
    protected static final String MAPS_CONTAINER_NAME = "mapsFC";
    /*
    protected static final String MAPS_DEFAULT_CONTAINER_NAME = "defaultMapFC";
    protected static final String MAPS_BATHROOMS_CONTAINER_NAME = "bathroomsMapFC";
    protected static final String MAPS_WATERFOUNTAINS_CONTAINER_NAME = "waterFountainsMapFC";
    protected static final String MAPS_FIRSTAID_CONTAINER_NAME = "firstAidMapFC";
    protected static final String MAPS_DONATIONS_CONTAINER_NAME = "donationsMapFC"; */
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    protected static final String TAP_WIDGET_INSTRUCTIONS_FIELD_TYPE = "Instructions";
    protected static final String TAP_WIDGET_INSTRUCTIONS_FIELD_NAME_DEFAULT = "Instructions";
    protected static final String TAP_WIDGET_BRLABEL_FIELD_TYPE = "Br_label";
    protected static final String TAP_WIDGET_BRLABEL_FIELD_NAME_DEFAULT = "Br_label";
    protected static final String TAP_WIDGET_WATERLABEL_FIELD_TYPE = "Water_label";
    protected static final String TAP_WIDGET_WATERLABEL_FIELD_NAME_DEFAULT = "Water_label";
    protected static final String TAP_WIDGET_FIRSTAIDLABEL_FIELD_TYPE = "Firstaid_label";
    protected static final String TAP_WIDGET_FIRSTAIDLABEL_FIELD_NAME_DEFAULT = "Firstaid_label";
    protected static final String TAP_WIDGET_DONATIONSLABEL_FIELD_TYPE = "Donations_label";
    protected static final String TAP_WIDGET_DONATIONSLABEL_FIELD_NAME_DEFAULT = "Donations_label";
    protected static final String TAP_WIDGET_NORESULTSFOUND_FIELD_TYPE = "NoResultsFound";
    protected static final String TAP_WIDGET_NORESULTSFOUND_FIELD_NAME_DEFAULT = "NoResultsFound";
    protected static final String MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_TYPE = "EnableFsCustomMaps";
    protected static final String MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_NAME_DEFAULT = "EnableFsCustomMaps";
    protected static final String MAPVCONFIG_CLEARRESULTS_FIELD_TYPE = "ClearResults";
    protected static final String MAPVCONFIG_CLEARRESULTS_FIELD_NAME_DEFAULT = "ClearResults";
    protected static final String MAPVCONFIG_LEVELSELECT_FIELD_TYPE = "LevelSelect";
    protected static final String MAPVCONFIG_LEVELSELECT_FIELD_NAME_DEFAULT = "LevelSelect";
    protected static final String MAPVCONFIG_MAPNOTAVAILABLE_FIELD_TYPE = "MapNotAvailable";
    protected static final String MAPVCONFIG_MAPNOTAVAILABLE_FIELD_NAME_DEFAULT = "MapNotAvailable";
    protected static final String AMENITY_WIDGETLABEL_FIELD_TYPE = "WidgetLabel";
    protected static final String AMENITY_WIDGETLABEL_FIELD_NAME_DEFAULT = "WidgetLabel";
    protected static final String AMENITY_HEADINGLABEL_FIELD_TYPE = "HeadingLabel";
    protected static final String AMENITY_HEADINGLABEL_FIELD_NAME_DEFAULT = "HeadingLabel";
    protected static final String MAP_DEFAULT_FIELD_TYPE = "Default";
    protected static final String MAP_DEFAULT_FIELD_NAME_DEFAULT = "Default";
    protected static final String MAP_BATHROOMS_FIELD_TYPE = "Bathrooms";
    protected static final String MAP_BATHROOMS_FIELD_NAME_DEFAULT = "Bathrooms";
    protected static final String MAP_WFOUNTAINS_FIELD_TYPE = "WaterFountains";
    protected static final String MAP_WFOUNTAINS_FIELD_NAME_DEFAULT = "WaterFountains";
    protected static final String MAP_FIRSTAID_FIELD_TYPE = "FirstAid";
    protected static final String MAP_FIRSTAID_FIELD_NAME_DEFAULT = "FirstAid";
    protected static final String MAP_DONATIONS_FIELD_TYPE = "Donations";
    protected static final String MAP_DONATIONS_FIELD_NAME_DEFAULT = "Donations";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.MAP;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    protected static final String PAGE_NAME_DEFAULT = "map_page";

    private KioskPageField<String> pageDescription;
    private TapWidgetContainer tapWidgetContainer;
    private MapViewConfigContainer mapViewConfigContainer;
    private AmenityContainer bathroomAmenityContainer;
    private AmenityContainer waterFountainAmenityContainer;
    private AmenityContainer firstAidAmenityContainer;
    private AmenityContainer donationAmenityContainer;
    private MapContainer mapContainer;


    private MapPage(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, 
                String pageName, KioskPageField<String> pageDescription, TapWidgetContainer tapWidgetContainer, MapViewConfigContainer mapViewConfigContainer,
                AmenityContainer bathroomAmenityContainer, AmenityContainer waterFountainAmenityContainer, AmenityContainer firstAidAmenityContainer, AmenityContainer donationAmenityContainer,
                MapContainer mapContainer) {
        super(pageRep, pageTitleField, pageMetadata, kioskPageType, pageName);
        this.pageDescription = pageDescription;
        this.tapWidgetContainer = tapWidgetContainer;
        this.mapViewConfigContainer = mapViewConfigContainer;
        this.bathroomAmenityContainer = bathroomAmenityContainer;
        this.waterFountainAmenityContainer = waterFountainAmenityContainer;
        this.firstAidAmenityContainer = firstAidAmenityContainer;
        this.donationAmenityContainer = donationAmenityContainer;
        this.mapContainer = mapContainer;
    }


    public KioskPageField<String> getPageDescriptionField() {
        return this.pageDescription;
    }

    public TapWidgetContainer getTapWidgetContainer() {
        return this.tapWidgetContainer;
    }

    public MapViewConfigContainer getMapViewConfigContainer() {
        return this.mapViewConfigContainer;
    }

    public AmenityContainer getBathroomAmenityContainer() {
        return this.bathroomAmenityContainer;
    }
    public AmenityContainer getWaterFountainAmenityContainer() {
        return this.waterFountainAmenityContainer;
    }
    public AmenityContainer getFirstAidAmenityContainer() {
        return this.firstAidAmenityContainer;
    }

    public AmenityContainer getDonationAmenityContainer() {
        return this.donationAmenityContainer;
    }

    public MapContainer getMapContainer() {
        return this.mapContainer;
    }

    @Override
    protected Page buildPageRep(Page.Builder pageRepBuilder) {
        FieldContainer tapWidgetFC = this.tapWidgetContainer.toFieldContainer(TAP_WIDGET_CONTAINER_NAME, cmsConverter);
        FieldContainer mapViewConfigFC = this.mapViewConfigContainer.toFieldContainer(MAP_VIEW_CONFIG_CONTAINER_NAME, cmsConverter);
        FieldContainer bathroomAmenityFC = this.bathroomAmenityContainer.toFieldContainer(BATHROOM_AMENITY_CONTAINER_NAME, cmsConverter);
        FieldContainer waterFountainAmenityFC = this.waterFountainAmenityContainer.toFieldContainer(WATER_FOUNTAIN_AMENITY_CONTAINER_NAME, cmsConverter);
        FieldContainer firstAidAmenityFC = this.firstAidAmenityContainer.toFieldContainer(FIRSTAID_AMENITY_CONTAINER_NAME, cmsConverter);
        FieldContainer donationAmenityFC = this.donationAmenityContainer.toFieldContainer(DONATION_AMENITY_CONTAINER_NAME, cmsConverter);
        FieldContainer mapFC = this.mapContainer.toFieldContainer(MAPS_CONTAINER_NAME, cmsConverter);

        FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addRegularTextLongDescriptionField(cmsConverter.toRegTextLongDescr(this.pageDescription))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .addChildContainer(tapWidgetFC)
                                                            .addChildContainer(mapViewConfigFC)
                                                            .addChildContainer(bathroomAmenityFC)
                                                            .addChildContainer(waterFountainAmenityFC)
                                                            .addChildContainer(firstAidAmenityFC)
                                                            .addChildContainer(donationAmenityFC)
                                                            .addChildContainer(mapFC)
                                                            .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);

        pageRepBuilder.fieldContainers(fieldContainers);

        return pageRepBuilder.build();

    }




    public static class Builder extends KioskPage.Builder<Builder, MapPage> {
        private KioskPageField<String> pageDescriptionField;
        private EditedPageFieldPayload<String> editedPageDescriptionField;

        private TapWidgetContainerValidator tapWidgetContainerValidator;
        private MapViewConfigContainerValidator mapViewConfigContainerValidator;
        private AmenityContainerValidator amenityContainerValidator;
        private MapContainerValidator mapContainerValidator;

        private TapWidgetContainer tapWidgetContainer;
        private EditedTapWidgetContainer editedTapWidgetContainer;

        private MapViewConfigContainer mapViewConfigContainer;
        private EditedMapViewConfigContainer editedMapViewConfigContainer;
        private AmenityContainer bathroomAmenityContainer;
        private EditedAmenityContainer editedBathroomAmenityContainer;
        private AmenityContainer waterFountainAmenityContainer;
        private EditedAmenityContainer editedWaterFountainAmenityContainer;
        private AmenityContainer firstAidAmenityContainer;
        private EditedAmenityContainer editedFirstAidAmenityContainer;
        private AmenityContainer donationAmenityContainer;
        private EditedAmenityContainer editedDonationAmenityContainer;
        private MapContainer mapContainer;
        private EditedMapContainer editedMapContainer;


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

        public Builder pageDescriptionField(EditedPageFieldPayload<String> pageDescriptionField) {
            this.editedPageDescriptionField = pageDescriptionField;
            return self();
        }

        public Builder tapWidgetContainer(EditedTapWidgetContainer tapWidgetContainer) {
            this.editedTapWidgetContainer = tapWidgetContainer;
            return self();
        }

        public Builder mapViewConfigContainer(EditedMapViewConfigContainer mapViewConfigContainer) {
            this.editedMapViewConfigContainer = mapViewConfigContainer;
            return self();
        }

        public Builder bathroomAmenityContainer(EditedAmenityContainer bathroomAmenityContainer) {
            this.editedBathroomAmenityContainer = bathroomAmenityContainer;
            return self();
        }

        public Builder waterFountainAmenityContainer(EditedAmenityContainer waterFountainAmenityContainer) {
            this.editedWaterFountainAmenityContainer = waterFountainAmenityContainer;
            return self();
        }

        public Builder firstAidAmenityContainer(EditedAmenityContainer firstAidAmenityContainer) {
            this.editedFirstAidAmenityContainer = firstAidAmenityContainer;
            return self();
        }

        public Builder donationAmenityContainer(EditedAmenityContainer donationAmenityContainer) {
            this.editedDonationAmenityContainer = donationAmenityContainer;
            return self();
        }

        public Builder mapContainer(EditedMapContainer mapContainer) {
            this.editedMapContainer = mapContainer;
            return self();
        }


        @Override
        protected void validateAndPrepareChild() {

            if(super.pageRep != null) {
                // process pageRep, overriding everything else
                FieldContainer mainFC = pageRep.getFieldContainers().get(0);

                FieldSelector fieldSelectorMainFc = new FieldSelector(mainFC);
                FieldContainer tapWidgetFC = fieldSelectorMainFc.selectChildContainer(TAP_WIDGET_CONTAINER_NAME);
                FieldContainer mapViewConfigFC = fieldSelectorMainFc.selectChildContainer(MAP_VIEW_CONFIG_CONTAINER_NAME);
                FieldContainer bathroomAmenityFC = fieldSelectorMainFc.selectChildContainer(BATHROOM_AMENITY_CONTAINER_NAME);
                FieldContainer waterFountainAmenityFC = fieldSelectorMainFc.selectChildContainer(WATER_FOUNTAIN_AMENITY_CONTAINER_NAME);
                FieldContainer firstAidAmenityFC = fieldSelectorMainFc.selectChildContainer(FIRSTAID_AMENITY_CONTAINER_NAME);
                FieldContainer donationAmenityFC = fieldSelectorMainFc.selectChildContainer(DONATION_AMENITY_CONTAINER_NAME);
                FieldContainer mapFC = fieldSelectorMainFc.selectChildContainer(MAPS_CONTAINER_NAME);

                TapWidgetContainerFieldSelector tapWidgetContainerFieldSelector = new TapWidgetContainerFieldSelector(TAP_WIDGET_INSTRUCTIONS_FIELD_TYPE, 
                                                                                                                    TAP_WIDGET_BRLABEL_FIELD_TYPE,
                                                                                                                    TAP_WIDGET_WATERLABEL_FIELD_TYPE,
                                                                                                                    TAP_WIDGET_FIRSTAIDLABEL_FIELD_TYPE,
                                                                                                                    TAP_WIDGET_DONATIONSLABEL_FIELD_TYPE,
                                                                                                                    TAP_WIDGET_NORESULTSFOUND_FIELD_TYPE);
                
                MapViewConfigContainerFieldSelector mapViewConfigContainerFieldSelector = new MapViewConfigContainerFieldSelector(MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_TYPE, 
                                                                                                                            MAPVCONFIG_CLEARRESULTS_FIELD_TYPE,
                                                                                                                            MAPVCONFIG_LEVELSELECT_FIELD_TYPE,
                                                                                                                            MAPVCONFIG_MAPNOTAVAILABLE_FIELD_TYPE);
                                                                        
                AmenityContainerSelector amenityContainerSelector = new AmenityContainerSelector(AMENITY_WIDGETLABEL_FIELD_TYPE, 
                                                                                                AMENITY_HEADINGLABEL_FIELD_TYPE);

                MapContainerSelector mapContainerSelector = new MapContainerSelector(MAP_DEFAULT_FIELD_TYPE, 
                                                                                    MAP_BATHROOMS_FIELD_TYPE,
                                                                                    MAP_WFOUNTAINS_FIELD_TYPE,
                                                                                    MAP_FIRSTAID_FIELD_TYPE,
                                                                                    MAP_DONATIONS_FIELD_TYPE);

                
                tapWidgetContainer = TapWidgetContainer.fromFieldContainer(tapWidgetFC, super.toKioskPageFieldConverter, tapWidgetContainerFieldSelector);
                
                mapViewConfigContainer = MapViewConfigContainer.fromFieldContainer(mapViewConfigFC, super.toKioskPageFieldConverter, mapViewConfigContainerFieldSelector);
                
                bathroomAmenityContainer = AmenityContainer.fromFieldContainer(bathroomAmenityFC, super.toKioskPageFieldConverter, amenityContainerSelector);
                waterFountainAmenityContainer = AmenityContainer.fromFieldContainer(waterFountainAmenityFC, super.toKioskPageFieldConverter, amenityContainerSelector);
                firstAidAmenityContainer = AmenityContainer.fromFieldContainer(firstAidAmenityFC, super.toKioskPageFieldConverter, amenityContainerSelector);
                donationAmenityContainer = AmenityContainer.fromFieldContainer(donationAmenityFC, super.toKioskPageFieldConverter, amenityContainerSelector);

                mapContainer = MapContainer.fromFieldContainer(mapFC, super.toKioskPageFieldConverter, mapContainerSelector);

                RegularTextLongDescriptionField cmsPageDescriptionField = fieldSelectorMainFc.selectRegTextLongDescrField(PAGE_DESCR_FIELD_TYPE);

                pageDescriptionField = super.toKioskPageFieldConverter.toStringField(cmsPageDescriptionField);
                

            }

            if(pageDescriptionField == null) {
                if(editedPageDescriptionField == null) {
                    editedPageDescriptionField =  new EditedPageFieldPayload<String>(PAGE_DESCR_FIELD_NAME_DEFAULT, null);
                }
                pageDescriptionField = editedPageDescriptionField.toKioskPageField(PAGE_DESCR_FIELD_TYPE, true);
            }

            if(tapWidgetContainer == null) {
                if(editedTapWidgetContainer == null) {
                    editedTapWidgetContainer = EditedTapWidgetContainer.empty(TAP_WIDGET_INSTRUCTIONS_FIELD_NAME_DEFAULT, 
                                                                                TAP_WIDGET_BRLABEL_FIELD_NAME_DEFAULT,
                                                                                TAP_WIDGET_WATERLABEL_FIELD_NAME_DEFAULT,
                                                                                TAP_WIDGET_FIRSTAIDLABEL_FIELD_NAME_DEFAULT,
                                                                                TAP_WIDGET_DONATIONSLABEL_FIELD_NAME_DEFAULT,
                                                                                TAP_WIDGET_NORESULTSFOUND_FIELD_NAME_DEFAULT);
                }
                tapWidgetContainer = editedTapWidgetContainer.toTapWidgetContainer(TAP_WIDGET_INSTRUCTIONS_FIELD_TYPE, 
                                                                                    TAP_WIDGET_BRLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_WATERLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_FIRSTAIDLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_DONATIONSLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_NORESULTSFOUND_FIELD_TYPE);
            }

            if(mapViewConfigContainer == null) {
                if(editedMapViewConfigContainer == null) {
                    editedMapViewConfigContainer = EditedMapViewConfigContainer.empty(MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_NAME_DEFAULT, 
                                                                                        MAPVCONFIG_CLEARRESULTS_FIELD_NAME_DEFAULT,
                                                                                        MAPVCONFIG_LEVELSELECT_FIELD_NAME_DEFAULT,
                                                                                        MAPVCONFIG_MAPNOTAVAILABLE_FIELD_NAME_DEFAULT);
                }

                mapViewConfigContainer = editedMapViewConfigContainer.toMapViewConfigContainer(MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_TYPE, 
                                                                                                MAPVCONFIG_CLEARRESULTS_FIELD_TYPE,
                                                                                                MAPVCONFIG_LEVELSELECT_FIELD_TYPE,
                                                                                                MAPVCONFIG_MAPNOTAVAILABLE_FIELD_TYPE);
            }

            if(bathroomAmenityContainer == null) {
                if(editedBathroomAmenityContainer == null) {
                    editedBathroomAmenityContainer =  EditedAmenityContainer.empty(AMENITY_WIDGETLABEL_FIELD_NAME_DEFAULT, 
                                                                                    AMENITY_HEADINGLABEL_FIELD_NAME_DEFAULT);
                }

                bathroomAmenityContainer = editedBathroomAmenityContainer.toAmenityContainer(AMENITY_WIDGETLABEL_FIELD_TYPE, 
                                                                                                AMENITY_HEADINGLABEL_FIELD_TYPE);
            }

            if(waterFountainAmenityContainer == null) {
                if(editedWaterFountainAmenityContainer == null) {
                    editedWaterFountainAmenityContainer =  EditedAmenityContainer.empty(AMENITY_WIDGETLABEL_FIELD_NAME_DEFAULT, 
                                                                                    AMENITY_HEADINGLABEL_FIELD_NAME_DEFAULT);
                }

                waterFountainAmenityContainer = editedWaterFountainAmenityContainer.toAmenityContainer(AMENITY_WIDGETLABEL_FIELD_TYPE, 
                                                                                                AMENITY_HEADINGLABEL_FIELD_TYPE);
            }

            if(firstAidAmenityContainer == null) {
                if(editedFirstAidAmenityContainer == null) {
                    editedFirstAidAmenityContainer =  EditedAmenityContainer.empty(AMENITY_WIDGETLABEL_FIELD_NAME_DEFAULT, 
                                                                                    AMENITY_HEADINGLABEL_FIELD_NAME_DEFAULT);
                }

                firstAidAmenityContainer = editedFirstAidAmenityContainer.toAmenityContainer(AMENITY_WIDGETLABEL_FIELD_TYPE, 
                                                                                                AMENITY_HEADINGLABEL_FIELD_TYPE);
            }
            
            if(donationAmenityContainer == null) {
                if(editedDonationAmenityContainer == null) {
                    editedDonationAmenityContainer =  EditedAmenityContainer.empty(AMENITY_WIDGETLABEL_FIELD_NAME_DEFAULT, 
                                                                                    AMENITY_HEADINGLABEL_FIELD_NAME_DEFAULT);
                }

                donationAmenityContainer = editedDonationAmenityContainer.toAmenityContainer(AMENITY_WIDGETLABEL_FIELD_TYPE, 
                                                                                                AMENITY_HEADINGLABEL_FIELD_TYPE);
            }

            if(mapContainer == null) {
                if(editedMapContainer == null) {
                    editedMapContainer =  EditedMapContainer.empty(MAP_DEFAULT_FIELD_TYPE, 
                                                                    MAP_BATHROOMS_FIELD_TYPE,
                                                                    MAP_WFOUNTAINS_FIELD_TYPE,
                                                                    MAP_FIRSTAID_FIELD_TYPE,
                                                                    MAP_DONATIONS_FIELD_TYPE);
                }

                mapContainer = editedMapContainer.toMapContainer(MAP_DEFAULT_FIELD_TYPE, 
                                                                MAP_BATHROOMS_FIELD_TYPE,
                                                                MAP_WFOUNTAINS_FIELD_TYPE,
                                                                MAP_FIRSTAID_FIELD_TYPE,
                                                                MAP_DONATIONS_FIELD_TYPE);
            }

            // validate objects
            if (!KioskPageFieldTypeValidator.isValid(pageDescriptionField, PAGE_DESCR_FIELD_TYPE)) {
                throw new RuntimeException("PageDescription fieldType does not match");
            }

            tapWidgetContainerValidator = new TapWidgetContainerValidator(TAP_WIDGET_INSTRUCTIONS_FIELD_TYPE, 
                                                                                    TAP_WIDGET_BRLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_WATERLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_FIRSTAIDLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_DONATIONSLABEL_FIELD_TYPE,
                                                                                    TAP_WIDGET_NORESULTSFOUND_FIELD_TYPE);
            
            if(!tapWidgetContainerValidator.isValid(tapWidgetContainer)) {
                throw new RuntimeException("Invalid field type in the TapWidgetContainer");
            }

            mapViewConfigContainerValidator = new MapViewConfigContainerValidator(MAPVCONFIG_ENABLEFSCUSTOMMAPS_FIELD_TYPE, 
                                                                        MAPVCONFIG_CLEARRESULTS_FIELD_TYPE,
                                                                        MAPVCONFIG_LEVELSELECT_FIELD_TYPE,
                                                                        MAPVCONFIG_MAPNOTAVAILABLE_FIELD_TYPE);

            if(!mapViewConfigContainerValidator.isValid(mapViewConfigContainer)) {
                throw new RuntimeException("Invalid field type in the MapViewConfigContainer");
            }

            amenityContainerValidator = new AmenityContainerValidator(AMENITY_WIDGETLABEL_FIELD_TYPE, 
                                                                        AMENITY_HEADINGLABEL_FIELD_TYPE);
            if(!amenityContainerValidator.isValid(bathroomAmenityContainer)) {
                throw new RuntimeException("Invalid field type in the bathroom AmenityContainer");
            }

            if(!amenityContainerValidator.isValid(waterFountainAmenityContainer)) {
                throw new RuntimeException("Invalid field type in the water fountain AmenityContainer");
            }

            if(!amenityContainerValidator.isValid(firstAidAmenityContainer)) {
                throw new RuntimeException("Invalid field type in the first aid AmenityContainer");
            }

            if(!amenityContainerValidator.isValid(donationAmenityContainer)) {
                throw new RuntimeException("Invalid field type in the donation AmenityContainer");
            }

            mapContainerValidator = new MapContainerValidator(MAP_DEFAULT_FIELD_TYPE, 
                                                                MAP_BATHROOMS_FIELD_TYPE,
                                                                MAP_WFOUNTAINS_FIELD_TYPE,
                                                                MAP_FIRSTAID_FIELD_TYPE,
                                                                MAP_DONATIONS_FIELD_TYPE);

            if(!mapContainerValidator.isValid(mapContainer)) {
                throw new RuntimeException("Invalid field type in the MapContainer");
            }
            
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected MapPage buildChild() {

            return new MapPage(super.pageRep, super.kioskPageTitleField, super.pageMetadata, super.kioskPageType, super.pageName,
                                    pageDescriptionField, tapWidgetContainer, mapViewConfigContainer, bathroomAmenityContainer, waterFountainAmenityContainer, 
                                    firstAidAmenityContainer, donationAmenityContainer, mapContainer);
        }
    }
}
