package com.ppublica.apps.kiosk.service.payloads.globalsettings;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;

public record GlobalSettingsInput(List<LocalizedInput> kioskTitle, List<LocalizedInput> venueName, List<String> localizations) {}
