package com.ppublica.apps.kiosk.service.payloads.events;

import com.ppublica.apps.kiosk.service.views.events.EventSectionView;

public record EventsPageInput(String pageTitle, String pageDescription, String eventLangPickerLabel, GeneralStringsInput general, 
                                EventSectionView sectionREG, EventSectionInput sectionCACO, EventSectionView sectionCABR, EventSectionView sectionOTHER) {}
