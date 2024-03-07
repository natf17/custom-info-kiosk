package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultSeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventAdminView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

public class SeasonalEventViewsConverterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;

    LocalDate eventDate;

    SeasonalEventType seasonalEventType;
    SeasonalEventView seasonalEventView;
    SeasonalEventAdminView seasonalEventAdminView;

    SeasonalEventViewsConverter converter = new SeasonalEventViewsConverter();


    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.eventDate = LocalDate.of(2024, 3, 6);

        seasonalEventType = new DefaultSeasonalEventType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Event season", false))
                                            .kioskCollectionMetadata(new NonLocalizableKioskCollectionMetadata(Status.PUBLISHED, testDate, testDateTime))
                                            .startDate(new KioskCollectionField<>(LocalDate.of(2024, 3, 6), false))
                                            .eventLanguage(new KioskCollectionField<>("eventLang", false))
                                            .seasonId(new KioskCollectionField<>(10L, false))
                                            .build();

        this.seasonalEventView = new SeasonalEventView(1L, eventDate.toString(), "eventLang", 10L);


        this.seasonalEventAdminView = new SeasonalEventAdminView(1L, eventDate.toString(),"eventLang", 10L);

    }
    
    @Test
    public void givenSeasonalEventType_buildView_returnsSeasonalEventView() {
        SeasonalEventView viewActual = converter.buildView(seasonalEventType);

        Assertions.assertEquals(seasonalEventView, viewActual);
    }

    // only testing with one vew
    @Test
    public void givenSeasonalEventTypeList_buildAdminView_returnsSeasonalEventAdminView() {
        SeasonalEventAdminView adminViewActual = converter.buildAdminView(seasonalEventType);

        Assertions.assertEquals(seasonalEventAdminView, adminViewActual);

    }
}
