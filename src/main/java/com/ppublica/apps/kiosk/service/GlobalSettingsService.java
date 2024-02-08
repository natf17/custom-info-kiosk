package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import com.ppublica.apps.kiosk.service.payloads.globalsettings.GlobalSettingsInput;
import com.ppublica.apps.kiosk.service.views.globalsettings.GlobalSettingsAdminView;
import com.ppublica.apps.kiosk.service.views.globalsettings.GlobalSettingsView;

public class GlobalSettingsService {
    public Optional<GlobalSettingsView> getGlobalSettings(String locale) {
        throw new UnsupportedOperationException();
    }

    public Optional<GlobalSettingsAdminView> getGlobalSettingsAdmin() {
        throw new UnsupportedOperationException();
    }

    public GlobalSettingsAdminView createGlobalSettings(GlobalSettingsInput data) {
        throw new UnsupportedOperationException();
    }

    public GlobalSettingsAdminView updateGlobalSettings(GlobalSettingsInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteGlobalSettings() {
        throw new UnsupportedOperationException();
    }
}
