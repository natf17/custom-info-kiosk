package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@Component
public class LocationViewsConverter {
    public LocationView buildView(LocationType location) {
        KioskImage map = location.map().fieldValue();
        MapImage image = map != null ? new MapImage(map.width(), map.height(), map.location()): null;

        return new LocationView(Long.toString(location.collectionId()), 
                                location.fullName().fieldValue(), 
                                location.level_num().fieldValue(),
                                location.level_name().fieldValue(),
                                image
                                );
    }

    // expects at least one element in the list
    public LocationAdminView buildAdminView(List<? extends LocationType> locations) {
        LocationType arbitraryLocation = locations.get(0);

        String id = arbitraryLocation.collectionId() != null ? Long.toString(arbitraryLocation.collectionId()): null;

        List<LocalizedField> fullNameLoc = new ArrayList<>();
        List<LocalizedField> levelNameLoc = new ArrayList<>();
        List<LocalizedView<MapImage>> mapLoc = new ArrayList<>();

        // process localizable fields
        for (LocationType location : locations) {
            String locale = location.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String fullName = location.fullName().fieldValue();
            if(fullName != null) {
                fullNameLoc.add(new LocalizedField(locale, fullName));
            }

            String levelName = location.level_name().fieldValue();
            if(levelName != null) {
                levelNameLoc.add(new LocalizedField(locale, levelName));
            }

            KioskImage mapImage = location.map().fieldValue();
            MapImage map = mapImage != null ? new MapImage(mapImage.width(), mapImage.height(), mapImage.location()): null;
            mapLoc.add(new LocalizedView<MapImage>(locale, map));

        }

        // process non-locale fields
        Integer levelNum = arbitraryLocation.level_num().fieldValue();

        return new LocationAdminView(id, fullNameLoc, levelNum, levelNameLoc, mapLoc);
    }
}
