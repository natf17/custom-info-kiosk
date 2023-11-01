package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.EventSectionContainer;
import com.ppublica.apps.kiosk.domain.model.pages.EventsPage;
import com.ppublica.apps.kiosk.domain.model.pages.GeneralEventStringsContainer;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.views.events.EventSectionView;
import com.ppublica.apps.kiosk.service.views.events.EventsPageView;
import com.ppublica.apps.kiosk.service.views.events.GeneralStringsView;

public class EventsPageService {

    @Autowired
    private PageRepository repo;

    public Optional<EventsPageView> getEventsPage(String locale) {

        Optional<Page> eventsPageOpt = repo.findByPageTypeAndKioskLocale(EventsPage.PAGE_TYPE, locale);
        
        if (eventsPageOpt.isEmpty()) {
            return Optional.empty();
        }

        EventsPage eventsPage = new EventsPage.Builder(eventsPageOpt.get())
                                .build();

        return Optional.of(transformToView(eventsPage));

    }



    private EventsPageView transformToView(EventsPage eventsPage) {

        GeneralEventStringsContainer genEventStringCont = eventsPage.getGeneralStringsContainer();
        String eventThemeLabel = genEventStringCont.getEventThemeLabelField().getFieldValue();
        String durationLabel = genEventStringCont.getDurationLabelField().getFieldValue();
        String yearsShowingLabel = genEventStringCont.getYearsShowingLabelField().getFieldValue();
        String dateLabel = genEventStringCont.getDateLabel().getFieldValue();
        String eventLangLabel = genEventStringCont.getEventLangLabelField().getFieldValue();
        String noEventsFound = genEventStringCont.getNoEventsFoundField().getFieldValue();

        GeneralStringsView general = new GeneralStringsView(eventThemeLabel, durationLabel, yearsShowingLabel, dateLabel, eventLangLabel, noEventsFound);
        
        EventSectionView sectionREG = transformToView(eventsPage.getRegSectionContainer());
        EventSectionView sectionCACO = transformToView(eventsPage.getCoSectionContainer());
        EventSectionView sectionCABR = transformToView(eventsPage.getBrSectionContainer());
        EventSectionView sectionOTHER = transformToView(eventsPage.getOtherSectionContainer());

        String title = eventsPage.getPageTitleField().getFieldValue();
        String pageDescription = eventsPage.getPageDescriptionField().getFieldValue();
        String eventLangPickerLabel = eventsPage.getEventLangPickerLabelField().getFieldValue();

        return new EventsPageView(title, pageDescription, eventLangPickerLabel, general, sectionREG, sectionCACO, sectionCABR, sectionOTHER);

    }

    private EventSectionView transformToView(EventSectionContainer evContainer) {
        String title = evContainer.getTitleField().getFieldValue();
        String btn_text = evContainer.getBtnTextField().getFieldValue();

        return new EventSectionView(title, btn_text);
    }
}
