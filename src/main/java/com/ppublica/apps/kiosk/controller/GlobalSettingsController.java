package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.GlobalSettingsService;
import com.ppublica.apps.kiosk.service.payloads.globalsettings.GlobalSettingsPayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.globalsettings.GlobalSettingsAdminView;
import com.ppublica.apps.kiosk.service.views.globalsettings.GlobalSettingsView;

@Controller
public class GlobalSettingsController {
    @Autowired
    private GlobalSettingsService service;

    
    @QueryMapping
    public GlobalSettingsView globalSetting(@Argument String locale) {
        GlobalSettingsView globalSettingsView = service.getGlobalSettings(locale).get();

        return globalSettingsView;
    }

    @QueryMapping
    public GlobalSettingsAdminView globalSettingsAdmin() {
        GlobalSettingsAdminView globalSettingsAdminView = service.getGlobalSettingsAdmin().get();

        return globalSettingsAdminView;
    }

    @MutationMapping
    public GlobalSettingsAdminView createGlobalSettings(@Argument GlobalSettingsPayload input) {
        GlobalSettingsAdminView globalSettingsAdminView = service.createGlobalSettings(input.data());

        return globalSettingsAdminView;
    }

    @MutationMapping
    public GlobalSettingsAdminView updateGlobalSettings(@Argument GlobalSettingsPayload input) {
        GlobalSettingsAdminView globalSettingsAdminView = service.updateGlobalSettings(input.data());

        return globalSettingsAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteGlobalSettings() {
        service.deleteGlobalSettings();

        return new MessageResponse("Deleted the app's global settings");
    }

}
