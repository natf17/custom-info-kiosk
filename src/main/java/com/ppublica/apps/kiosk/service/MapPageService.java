package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.AmenityContainer;
import com.ppublica.apps.kiosk.domain.model.pages.MapContainer;
import com.ppublica.apps.kiosk.domain.model.pages.MapPage;
import com.ppublica.apps.kiosk.domain.model.pages.MapViewConfigContainer;
import com.ppublica.apps.kiosk.domain.model.pages.TapWidgetContainer;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.views.map.LocationAmenityView;
import com.ppublica.apps.kiosk.service.views.map.MapImageView;
import com.ppublica.apps.kiosk.service.views.map.MapImagesView;
import com.ppublica.apps.kiosk.service.views.map.MapPageView;
import com.ppublica.apps.kiosk.service.views.map.MapViewConfigView;
import com.ppublica.apps.kiosk.service.views.map.TapWidgetView;

public class MapPageService {
    @Autowired
    private PageRepository repo;

    public Optional<MapPageView> getMapPage(String locale) {

        Optional<Page> mapPageOpt = repo.findByPageTypeAndKioskLocale(MapPage.PAGE_TYPE, locale);
        
        if (mapPageOpt.isEmpty()) {
            return Optional.empty();
        }

        MapPage mapPage = new MapPage.Builder(mapPageOpt.get())
                                .build();

        return Optional.of(transformToView(mapPage));

    }

    private MapPageView transformToView(MapPage mapPage) {

        String pageTitle = mapPage.getPageTitleField().getFieldValue();
        String pageDescription = mapPage.getPageDescriptionField().getFieldValue();

        TapWidgetView tapWidgetView = transformToView(mapPage.getTapWidgetContainer());
        MapViewConfigView mapViewConfigView = transformToView(mapPage.getMapViewConfigContainer());
        LocationAmenityView bathroomAmenityView = transformToView(mapPage.getBathroomAmenityContainer());
        LocationAmenityView waterFountainAmenityView = transformToView(mapPage.getWaterFountainAmenityContainer());
        LocationAmenityView firstAidAmenityView = transformToView(mapPage.getFirstAidAmenityContainer());
        LocationAmenityView donationAmenityView = transformToView(mapPage.getDonationAmenityContainer());
        MapImagesView mapsView = transformToView(mapPage.getMapContainer());

        return new MapPageView(pageTitle, pageDescription, tapWidgetView, mapViewConfigView, bathroomAmenityView, waterFountainAmenityView, firstAidAmenityView, donationAmenityView, mapsView);

    }

    private TapWidgetView transformToView(TapWidgetContainer container) {
        String instructions = container.getInstructionsField().getFieldValue();
        String br_label = container.getBrLabelField().getFieldValue();
        String water_label = container.getWaterLabelField().getFieldValue();
        String firstaid_label = container.getFirstAidLabelField().getFieldValue();
        String donations_label = container.getDonationsLabelField().getFieldValue();
        String noResultsFound = container.getNoResultsFoundField().getFieldValue();

        return new TapWidgetView(instructions, br_label, water_label, firstaid_label, donations_label, noResultsFound);
    }

    private MapViewConfigView transformToView(MapViewConfigContainer container) {
        Boolean enableFsCustomMaps = container.enableFsCustomMapsField().getFieldValue();
        String clearResults = container.getClearResultsField().getFieldValue();
        String levelSelect = container.getLevelSelectField().getFieldValue();
        String mapNotAvailable = container.getMapNotAvailableField().getFieldValue();

        return new MapViewConfigView(enableFsCustomMaps, clearResults, levelSelect, mapNotAvailable);
    }

    private LocationAmenityView transformToView(AmenityContainer container) {
        String widgetLabel = container.getWidgetLabelField().getFieldValue();
        String headingLabel = container.getHeadingLabelField().getFieldValue();

        return new LocationAmenityView(widgetLabel, headingLabel);
    }

    private MapImagesView transformToView(MapContainer container) {
        MapImageView defaultMap = transformToView(container.getDefaultMapField().getFieldValue());
        MapImageView bathrooms = transformToView(container.getDefaultMapField().getFieldValue());
        MapImageView waterFountains = transformToView(container.getDefaultMapField().getFieldValue());
        MapImageView firstAid = transformToView(container.getDefaultMapField().getFieldValue());
        MapImageView donations = transformToView(container.getDefaultMapField().getFieldValue());

        return new MapImagesView(defaultMap, bathrooms, waterFountains, firstAid, donations);
    }

    private MapImageView transformToView(Image image) {
        Integer width = image.width();
        Integer height = image.height();
        String url = image.location();

        return new MapImageView(width, height, url);
    }
}
