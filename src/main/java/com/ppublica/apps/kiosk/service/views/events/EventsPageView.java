package com.ppublica.apps.kiosk.service.views.events;

public record EventsPageView(String pageTitle, String pageDescription, String eventLangPickerLabel, GeneralStringsView general, 
                                EventSectionView sectionREG, EventSectionView sectionCACO, EventSectionView sectionCABR, EventSectionView sectionOTHER) {}
