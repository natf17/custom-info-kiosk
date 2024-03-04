package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultLocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

public class LocationViewsConverterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;

    LocationType locationTypeEn;
    LocationType locationTypeEs;
    LocationView locationViewEn;
    LocationView locationViewEs;
    LocationAdminView locationAdminView;

    LocationViewsConverter converter = new LocationViewsConverter();


    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);

        locationTypeEn = new DefaultLocationType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Location", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .map(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .level_num(new KioskCollectionField<Integer>(6, false))
                                            .fullName(new KioskCollectionField<String>("fullName_fieldValue", false))
                                            .level_name(new KioskCollectionField<String>("levelName_fieldValue", true))
                                            .build();

        this.locationViewEn = new LocationView("1", "fullName_fieldValue", 6, "levelName_fieldValue", new MapImage(1, 2, "url"));

        locationTypeEs = new DefaultLocationType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Location", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(2L, "es", Status.PUBLISHED, testDate, testDateTime))
                                            .map(new KioskCollectionField<KioskImage>(new KioskImage("url_es", 1, 2), true))
                                            .level_num(new KioskCollectionField<Integer>(6, false))
                                            .fullName(new KioskCollectionField<String>("fullName_fieldValue_es", false))
                                            .level_name(new KioskCollectionField<String>("levelName_fieldValue_es", true))
                                            .build();



        locationAdminView = new LocationAdminView("1",
                                                    List.of(new LocalizedField("en", "fullName_fieldValue"),
                                                            new LocalizedField("es", "fullName_fieldValue_es")),
                                                    6,
                                                    List.of(new LocalizedField("en", "levelName_fieldValue"),
                                                            new LocalizedField("es", "levelName_fieldValue_es")),
                                                    List.of(new LocalizedView<>("en", new MapImage(1, 2, "url")),
                                                            new LocalizedView<>("es", new MapImage(1, 2, "url_es")))
                                                );

    }
    
    @Test
    public void givenLocationType_buildView_returnsLocationView() {
        LocationView viewActual = converter.buildView(locationTypeEn);

        Assertions.assertEquals(locationViewEn, viewActual);
    }

    @Test
    public void givenLocationTypeList_buildAdminView_returnsLocationAdminView() {
        LocationAdminView adminViewActual = converter.buildAdminView(List.of(locationTypeEn, locationTypeEs));

        Assertions.assertEquals(locationAdminView, adminViewActual);

    }
}
