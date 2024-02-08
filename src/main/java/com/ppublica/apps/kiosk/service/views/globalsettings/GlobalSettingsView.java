package com.ppublica.apps.kiosk.service.views.globalsettings;

import java.util.List;

public record GlobalSettingsView(String kioskTitle, String venueName, String locale, List<LocalizationInfo> localizations) {}

