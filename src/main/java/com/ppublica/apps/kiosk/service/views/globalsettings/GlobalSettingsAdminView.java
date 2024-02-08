package com.ppublica.apps.kiosk.service.views.globalsettings;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;

public record GlobalSettingsAdminView(List<LocalizedField> kioskTitle, List<LocalizedField> venueName, List<String> localizations) {}

