package com.ppublica.apps.kiosk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.GlobalSettingsService;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.globalsettings.GlobalSettingsAdminView;
import com.ppublica.apps.kiosk.service.views.globalsettings.GlobalSettingsView;
import com.ppublica.apps.kiosk.service.views.globalsettings.LocalizationInfo;

@GraphQlTest(controllers=GlobalSettingsController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class GlobalSettingsControllerTest {
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private GlobalSettingsService service;

    Map<String,Object> input;


    GlobalSettingsAdminView globalSettingsAdminView;

    @BeforeEach
    public void setup() {

        // fields for input
        Map<String,Object> localizedFieldTitle_EN = new HashMap<>();
        localizedFieldTitle_EN.put("locale", "en");
        localizedFieldTitle_EN.put("value", "kioskTitle");

        Map<String,Object> localizedFieldTitle_ES = new HashMap<>();
        localizedFieldTitle_ES.put("locale", "es");
        localizedFieldTitle_ES.put("value", "kioskTitle_es");

        Map<String,Object> localizedFieldVenueName_EN = new HashMap<>();
        localizedFieldVenueName_EN.put("locale", "en");
        localizedFieldVenueName_EN.put("value", "venueName");

        Map<String,Object> localizedFieldVenueName_ES = new HashMap<>();
        localizedFieldVenueName_ES.put("locale", "es");
        localizedFieldVenueName_ES.put("value", "venueName_es");

        //input
        this.input = new HashMap<>();
        input.put("kioskTitle", List.of(localizedFieldTitle_EN, localizedFieldTitle_ES));
        input.put("venueName", List.of(localizedFieldVenueName_EN, localizedFieldVenueName_ES));
        input.put("localizations", List.of("en", "es"));


        // views
    
        globalSettingsAdminView = new GlobalSettingsAdminView(
                                                    List.of(new LocalizedField("en", "kioskTitle"), new LocalizedField("es", "kioskTitle_es")), 
                                                    List.of(new LocalizedField("en", "venueName"), new LocalizedField("es", "venueName_es")), 
                                                    List.of("en", "es"));
    }

    @Test
    public void GET_globalSettingGivenLocale_returns_globalSetting() {

        // set up mock
        GlobalSettingsView globalSettingsView = new GlobalSettingsView("kioskTitle", "venueName", "en", List.of(new LocalizationInfo("1", "en"), new LocalizationInfo("2", "es")));
       
        when(service.getGlobalSettings("en")).thenReturn(Optional.of(globalSettingsView));


        graphqlTester.documentName("globalSetting")
            .variable("locale", "en")
            .execute()
            .path("globalSetting").entity(GlobalSettingsView.class).isEqualTo(globalSettingsView);
    }

    @Test
    public void GET_globalSettingsAdmin_returns_globalSettings() {

        when(service.getGlobalSettingsAdmin()).thenReturn(Optional.of(globalSettingsAdminView));

        graphqlTester.documentName("globalSettingsAdmin")
            .execute()
            .path("globalSettingsAdmin").entity(GlobalSettingsAdminView.class).isEqualTo(globalSettingsAdminView);
    }


    @Test
    public void POST_globalSettings_returns_globalSettingsAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.createGlobalSettings(any())).thenReturn(globalSettingsAdminView);

        graphqlTester.documentName("globalSettingsMutationPost")
            .variable("input", payload)
            .execute()
            .path("createGlobalSettings")
            .entity(GlobalSettingsAdminView.class)
            .isEqualTo(globalSettingsAdminView);

    }


    @Test
    public void PUT_globalSettings_returns_globalSettingsAdmin() {        

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.updateGlobalSettings(any())).thenReturn(globalSettingsAdminView);

        graphqlTester.documentName("globalSettingsMutationPut")
            .variable("input", payload)
            .execute()
            .path("updateGlobalSettings")
            .entity(GlobalSettingsAdminView.class)
            .isEqualTo(globalSettingsAdminView);

    }
    
    @Test
    public void DELETE_globalSettings_returns_message() {

        graphqlTester.documentName("globalSettingsMutationDelete")
            .execute()
            .path("deleteGlobalSettings", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the app's global settings");
            });

    }
  
}
