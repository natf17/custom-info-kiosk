package com.ppublica.apps.kiosk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.EventsPageService;
import com.ppublica.apps.kiosk.service.views.events.EventSectionView;
import com.ppublica.apps.kiosk.service.views.events.EventsPageView;
import com.ppublica.apps.kiosk.service.views.events.GeneralStringsView;

@GraphQlTest(controllers=EventsPageController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class EventsPageControllerTest {
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private EventsPageService service;

    Map<String,Object> payload;

    @BeforeEach
    public void setup() {
        // set up input
        Map<String,Object> generalStringsInput = new HashMap<>();
        generalStringsInput.put("eventThemeLabel", "sample_url");
        generalStringsInput.put("durationLabel", "durationLabel");
        generalStringsInput.put("yearsShowingLabel", "yearsShowingLabel");
        generalStringsInput.put("dateLabel", "dateLabel");
        generalStringsInput.put("eventLangLabel", "eventLangLabel");
        generalStringsInput.put("noEventsFound", "noEventsFound");

        Map<String,Object> eventSectionREGInput = new HashMap<>();
        eventSectionREGInput.put("title", "titleREG");
        eventSectionREGInput.put("btn_text", "btn_textREG");

        Map<String,Object> eventSectionCACOInput = new HashMap<>();
        eventSectionCACOInput.put("title", "titleCACO");
        eventSectionCACOInput.put("btn_text", "btn_textCACO");

        Map<String,Object> eventSectionCABRInput = new HashMap<>();
        eventSectionCABRInput.put("title", "titleCACBR");
        eventSectionCABRInput.put("btn_text", "btn_textCACBR");

        Map<String,Object> eventSectionOTHERInput = new HashMap<>();
        eventSectionOTHERInput.put("title", "titleOTHER");
        eventSectionOTHERInput.put("btn_text", "btn_textOTHER");

        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "pageTitle");
        input.put("pageDescription", "pageDescription");
        input.put("eventLangPickerLabel", "eventLangPickerLabel");
        input.put("general", generalStringsInput);
        input.put("sectionREG", eventSectionREGInput);
        input.put("sectionCACO", eventSectionCACOInput);
        input.put("sectionCABR", eventSectionCABRInput);
        input.put("sectionOTHER", eventSectionOTHERInput);

        payload = new HashMap<>();

        payload.put("data", input);
    }

    @Test
    public void GET_eventsPage_returns_page() {

        // set up mock
        GeneralStringsView generalView = new GeneralStringsView("eventThemeLabel", "durationLabel", "yearsShowingLabel", "dateLabel", "eventLangLabel", "noEventsFound");
        EventSectionView sectionREGView = new EventSectionView("titleREG", "btn_textREG");
        EventSectionView sectionCACOView = new EventSectionView("titleCACO", "btn_textCACO");
        EventSectionView sectionCABRView = new EventSectionView("titleCABR", "btn_textCABR");
        EventSectionView sectionOTHERView = new EventSectionView("titleOTHER", "btn_textOTHER");
        EventsPageView eventsPageView = new EventsPageView("pageTitle", "pageDescription", "eventLangPickerLabel", generalView, sectionREGView, sectionCACOView, sectionCABRView, sectionOTHERView);
       
        when(service.getEventsPage("en")).thenReturn(Optional.of(eventsPageView));


        graphqlTester.documentName("eventsPage")
            .variable("locale", "en")
            .execute()
            .path("eventsPage", eventsPage -> { eventsPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("pageDescription").entity(String.class).isEqualTo("pageDescription")
                .path("eventLangPickerLabel").entity(String.class).isEqualTo("eventLangPickerLabel")
                .path("general", featImg -> { featImg
                    .path("eventThemeLabel").entity(String.class).isEqualTo("eventThemeLabel")
                    .path("durationLabel").entity(String.class).isEqualTo("durationLabel")
                    .path("yearsShowingLabel").entity(String.class).isEqualTo("yearsShowingLabel")
                    .path("dateLabel").entity(String.class).isEqualTo("dateLabel")
                    .path("eventLangLabel").entity(String.class).isEqualTo("eventLangLabel")
                    .path("noEventsFound").entity(String.class).isEqualTo("noEventsFound");

                })
                .path("sectionREG", sectionReg -> { sectionReg
                    .path("title").entity(String.class).isEqualTo("titleREG")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textREG");
                })
                .path("sectionCACO", sectionCACO -> { sectionCACO
                    .path("title").entity(String.class).isEqualTo("titleCACO")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textCACO");
                })
                .path("sectionCABR", sectionCABR -> { sectionCABR
                    .path("title").entity(String.class).isEqualTo("titleCABR")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textCABR");
                })
                .path("sectionOTHER", sectionOther -> { sectionOther
                    .path("title").entity(String.class).isEqualTo("titleOTHER")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textOTHER");
                });

        });
    }

    @Test
    public void POST_eventsPage_returns_page() {

        // set up mocks
        GeneralStringsView generalView = new GeneralStringsView("eventThemeLabel", "durationLabel", "yearsShowingLabel", "dateLabel", "eventLangLabel", "noEventsFound");
        EventSectionView sectionREGView = new EventSectionView("titleREG", "btn_textREG");
        EventSectionView sectionCACOView = new EventSectionView("titleCACO", "btn_textCACO");
        EventSectionView sectionCABRView = new EventSectionView("titleCABR", "btn_textCABR");
        EventSectionView sectionOTHERView = new EventSectionView("titleOTHER", "btn_textOTHER");
        EventsPageView eventsPageView = new EventsPageView("pageTitle", "pageDescription", "eventLangPickerLabel", generalView, sectionREGView, sectionCACOView, sectionCABRView, sectionOTHERView);

        when(service.createEventsPage(any(), any())).thenReturn(eventsPageView);

        graphqlTester.documentName("eventsPageMutationPost")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("createEventsPage", eventsPage -> { eventsPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("pageDescription").entity(String.class).isEqualTo("pageDescription")
                .path("eventLangPickerLabel").entity(String.class).isEqualTo("eventLangPickerLabel")
                .path("general", featImg -> { featImg
                    .path("eventThemeLabel").entity(String.class).isEqualTo("eventThemeLabel")
                    .path("durationLabel").entity(String.class).isEqualTo("durationLabel")
                    .path("yearsShowingLabel").entity(String.class).isEqualTo("yearsShowingLabel")
                    .path("dateLabel").entity(String.class).isEqualTo("dateLabel")
                    .path("eventLangLabel").entity(String.class).isEqualTo("eventLangLabel")
                    .path("noEventsFound").entity(String.class).isEqualTo("noEventsFound");

                })
                .path("sectionREG", sectionReg -> { sectionReg
                    .path("title").entity(String.class).isEqualTo("titleREG")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textREG");
                })
                .path("sectionCACO", sectionCACO -> { sectionCACO
                    .path("title").entity(String.class).isEqualTo("titleCACO")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textCACO");
                })
                .path("sectionCABR", sectionCABR -> { sectionCABR
                    .path("title").entity(String.class).isEqualTo("titleCABR")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textCABR");
                })
                .path("sectionOTHER", sectionOther -> { sectionOther
                    .path("title").entity(String.class).isEqualTo("titleOTHER")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textOTHER");
                });
            });

        }

    @Test
    public void PUT_eventsPage_returns_page() {
    
        // set up mocks
        GeneralStringsView generalView = new GeneralStringsView("eventThemeLabel", "durationLabel", "yearsShowingLabel", "dateLabel", "eventLangLabel", "noEventsFound");
        EventSectionView sectionREGView = new EventSectionView("titleREG", "btn_textREG");
        EventSectionView sectionCACOView = new EventSectionView("titleCACO", "btn_textCACO");
        EventSectionView sectionCABRView = new EventSectionView("titleCABR", "btn_textCABR");
        EventSectionView sectionOTHERView = new EventSectionView("titleOTHER", "btn_textOTHER");
        EventsPageView eventsPageView = new EventsPageView("pageTitle", "pageDescription", "eventLangPickerLabel", generalView, sectionREGView, sectionCACOView, sectionCABRView, sectionOTHERView);

        when(service.updateEventsPage(any(), any())).thenReturn(eventsPageView);

        graphqlTester.documentName("eventsPageMutationPut")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("updateEventsPage", eventsPage -> { eventsPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("pageDescription").entity(String.class).isEqualTo("pageDescription")
                .path("eventLangPickerLabel").entity(String.class).isEqualTo("eventLangPickerLabel")
                .path("general", featImg -> { featImg
                    .path("eventThemeLabel").entity(String.class).isEqualTo("eventThemeLabel")
                    .path("durationLabel").entity(String.class).isEqualTo("durationLabel")
                    .path("yearsShowingLabel").entity(String.class).isEqualTo("yearsShowingLabel")
                    .path("dateLabel").entity(String.class).isEqualTo("dateLabel")
                    .path("eventLangLabel").entity(String.class).isEqualTo("eventLangLabel")
                    .path("noEventsFound").entity(String.class).isEqualTo("noEventsFound");

                })
                .path("sectionREG", sectionReg -> { sectionReg
                    .path("title").entity(String.class).isEqualTo("titleREG")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textREG");
                })
                .path("sectionCACO", sectionCACO -> { sectionCACO
                    .path("title").entity(String.class).isEqualTo("titleCACO")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textCACO");
                })
                .path("sectionCABR", sectionCABR -> { sectionCABR
                    .path("title").entity(String.class).isEqualTo("titleCABR")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textCABR");
                })
                .path("sectionOTHER", sectionOther -> { sectionOther
                    .path("title").entity(String.class).isEqualTo("titleOTHER")
                    .path("btn_text").entity(String.class).isEqualTo("btn_textOTHER");
                });
            });

    }

    @Test
    public void DELETE_eventsPage_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("locale", "en");

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("eventsPageMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteEventsPage", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the eventsPage in en");
            });
        
    }
    
}
