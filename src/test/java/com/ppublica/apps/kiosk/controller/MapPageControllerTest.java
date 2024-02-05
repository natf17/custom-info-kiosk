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

import com.ppublica.apps.kiosk.service.MapPageService;
import com.ppublica.apps.kiosk.service.views.map.LocationAmenityView;
import com.ppublica.apps.kiosk.service.views.map.MapImageView;
import com.ppublica.apps.kiosk.service.views.map.MapImagesView;
import com.ppublica.apps.kiosk.service.views.map.MapPageView;
import com.ppublica.apps.kiosk.service.views.map.MapViewConfigView;
import com.ppublica.apps.kiosk.service.views.map.TapWidgetView;

@GraphQlTest(controllers=MapPageController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class MapPageControllerTest {

    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private MapPageService service;

    Map<String,Object> payload;

    MapPageView mapPageView;
    TapWidgetView tapWidgetView;
    MapViewConfigView mapViewConfigView;
    MapImagesView mapImagesView;

    @BeforeEach
    public void setup() {
        // set up input
        Map<String,Object> tapWidgetInput = new HashMap<>();
        tapWidgetInput.put("instructions", "instructions");
        tapWidgetInput.put("br_label", "br_label");
        tapWidgetInput.put("water_label", "water_label");
        tapWidgetInput.put("firstaid_label", "firstaid_label");
        tapWidgetInput.put("donations_label", "donations_label");
        tapWidgetInput.put("noResultsFound", "noResultsFound");

        Map<String,Object> mapViewConfigInput = new HashMap<>();
        mapViewConfigInput.put("enableFsCustomMaps", true);
        mapViewConfigInput.put("clearResults", "clearResults");
        mapViewConfigInput.put("levelSelect", "levelSelect");
        mapViewConfigInput.put("mapNotAvailable", "mapNotAvailable");

        Map<String,Object> bathroomAmenityInput = new HashMap<>();
        bathroomAmenityInput.put("widgetLabel", "widgetLabelBr");
        bathroomAmenityInput.put("headingLabel", "headingLabelBr");

        Map<String,Object> waterFountainAmenityInput = new HashMap<>();
        waterFountainAmenityInput.put("widgetLabel", "widgetLabelW");
        waterFountainAmenityInput.put("headingLabel", "headingLabelW");

        Map<String,Object> firstAidAmenityInput = new HashMap<>();
        firstAidAmenityInput.put("widgetLabel", "widgetLabelFa");
        firstAidAmenityInput.put("headingLabel", "headingLabelFa");

        Map<String,Object> donationAmenityInput = new HashMap<>();
        donationAmenityInput.put("widgetLabel", "widgetLabelDon");
        donationAmenityInput.put("headingLabel", "headingLabelDon");

        Map<String,Object> defaultMapInput = new HashMap<>();
        defaultMapInput.put("width", 1);
        defaultMapInput.put("height", 2);
        defaultMapInput.put("url", "url_defaultMap");

        Map<String,Object> bathroomsMapInput = new HashMap<>();
        bathroomsMapInput.put("width", 3);
        bathroomsMapInput.put("height", 4);
        bathroomsMapInput.put("url", "url_bathroomsMap");

        Map<String,Object> waterFountainsMapInput = new HashMap<>();
        waterFountainsMapInput.put("width", 5);
        waterFountainsMapInput.put("height", 6);
        waterFountainsMapInput.put("url", "url_waterFountainsMap");

        Map<String,Object> firstAidMapInput = new HashMap<>();
        firstAidMapInput.put("width", 7);
        firstAidMapInput.put("height", 8);
        firstAidMapInput.put("url", "url_firstAidMap");

        Map<String,Object> donationsMapInput = new HashMap<>();
        donationsMapInput.put("width", 9);
        donationsMapInput.put("height", 10);
        donationsMapInput.put("url", "url_donationsMap");
        
        Map<String,Object> mapImagesInput = new HashMap<>();
        mapImagesInput.put("defaultMap", defaultMapInput);
        mapImagesInput.put("bathrooms", bathroomsMapInput);
        mapImagesInput.put("waterFountains", waterFountainsMapInput);
        mapImagesInput.put("firstAid", firstAidMapInput);
        mapImagesInput.put("donations", donationsMapInput);


        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "pageTitle");
        input.put("pageDescription", "pageDescription");
        input.put("tapWidget", tapWidgetInput);
        input.put("mapViewConfig", mapViewConfigInput);
        input.put("bathroomAmenity", bathroomAmenityInput);
        input.put("waterFountainAmenity", waterFountainAmenityInput);
        input.put("firstAidAmenity", firstAidAmenityInput);
        input.put("donationAmenity", donationAmenityInput);
        input.put("maps", mapImagesInput);

        payload = new HashMap<>();

        payload.put("data", input);


        this.tapWidgetView = new TapWidgetView("instructions", "br_label", "water_label", "firstaid_label", "donations_label", "noResultsFound");
        this.mapViewConfigView = new MapViewConfigView(true, "clearResults", "levelSelect", "mapNotAvailable");
        this.mapImagesView = new MapImagesView(new MapImageView(1, 2, "url_defaultMap"),
                                                        new MapImageView(3, 4, "url_bathroomsMap"),
                                                        new MapImageView(5, 6, "url_waterFountainsMap"),
                                                        new MapImageView(7, 8, "url_firstAidMap"),
                                                        new MapImageView(9, 10, "url_donationsMap"));
        this.mapPageView = new MapPageView("pageTitle",
                                                "pageDescription",
                                                tapWidgetView,
                                                mapViewConfigView,
                                                new LocationAmenityView("widgetLabelBr", "headingLabelBr"),
                                                new LocationAmenityView("widgetLabelW", "headingLabelW"),
                                                new LocationAmenityView("widgetLabelFa", "headingLabelFa"),
                                                new LocationAmenityView("widgetLabelDon", "headingLabelDon"),
                                                mapImagesView);
    }

    @Test
    public void GET_mapPage_returns_page() {

        // set up mock
   

        when(service.getMapPage("en")).thenReturn(Optional.of(mapPageView));


        graphqlTester.documentName("mapPage")
            .variable("locale", "en")
            .execute()
            .path("mapPage", mapPage -> { mapPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("pageDescription").entity(String.class).isEqualTo("pageDescription")
                .path("tapWidget").entity(TapWidgetView.class).isEqualTo(tapWidgetView)
                .path("mapViewConfig").entity(MapViewConfigView.class).isEqualTo(mapViewConfigView)
                .path("bathroomAmenity", bathroomAmenity -> { bathroomAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelBr")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelBr");
                })
                .path("waterFountainAmenity", waterFountainAmenity -> { waterFountainAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelW")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelW");
                })
                .path("firstAidAmenity", firstAidAmenity -> { firstAidAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelFa")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelFa");
                })
                .path("donationAmenity", donationAmenity -> { donationAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelDon")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelDon");
                })
                .path("maps", donationAmenity -> { donationAmenity
                    .path("default", defaultMap -> { defaultMap
                        .path("width").entity(Integer.class).isEqualTo(1)
                        .path("height").entity(Integer.class).isEqualTo(2)
                        .path("url").entity(String.class).isEqualTo("url_defaultMap");
                    })
                    .path("bathrooms", bathroomsMap -> { bathroomsMap
                        .path("width").entity(Integer.class).isEqualTo(3)
                        .path("height").entity(Integer.class).isEqualTo(4)
                        .path("url").entity(String.class).isEqualTo("url_bathroomsMap");
                    })
                    .path("waterFountains", waterFountainsMap -> { waterFountainsMap
                        .path("width").entity(Integer.class).isEqualTo(5)
                        .path("height").entity(Integer.class).isEqualTo(6)
                        .path("url").entity(String.class).isEqualTo("url_waterFountainsMap");
                    })
                    .path("firstAid", firstAidMap -> { firstAidMap
                        .path("width").entity(Integer.class).isEqualTo(7)
                        .path("height").entity(Integer.class).isEqualTo(8)
                        .path("url").entity(String.class).isEqualTo("url_firstAidMap");
                    })
                    .path("donations", donationsMap -> { donationsMap
                        .path("width").entity(Integer.class).isEqualTo(9)
                        .path("height").entity(Integer.class).isEqualTo(10)
                        .path("url").entity(String.class).isEqualTo("url_donationsMap");
                    });
                });

        });
    }

    @Test
    public void POST_mapPage_returns_page() {

        when(service.createMapPage(any(), any())).thenReturn(mapPageView);

        graphqlTester.documentName("mapPageMutationPost")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("createMapPage", mapPage -> { mapPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("pageDescription").entity(String.class).isEqualTo("pageDescription")
                .path("tapWidget").entity(TapWidgetView.class).isEqualTo(tapWidgetView)
                .path("mapViewConfig").entity(MapViewConfigView.class).isEqualTo(mapViewConfigView)
                .path("bathroomAmenity", bathroomAmenity -> { bathroomAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelBr")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelBr");
                })
                .path("waterFountainAmenity", waterFountainAmenity -> { waterFountainAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelW")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelW");
                })
                .path("firstAidAmenity", firstAidAmenity -> { firstAidAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelFa")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelFa");
                })
                .path("donationAmenity", donationAmenity -> { donationAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelDon")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelDon");
                })
                .path("maps", donationAmenity -> { donationAmenity
                    .path("default", defaultMap -> { defaultMap
                        .path("width").entity(Integer.class).isEqualTo(1)
                        .path("height").entity(Integer.class).isEqualTo(2)
                        .path("url").entity(String.class).isEqualTo("url_defaultMap");
                    })
                    .path("bathrooms", bathroomsMap -> { bathroomsMap
                        .path("width").entity(Integer.class).isEqualTo(3)
                        .path("height").entity(Integer.class).isEqualTo(4)
                        .path("url").entity(String.class).isEqualTo("url_bathroomsMap");
                    })
                    .path("waterFountains", waterFountainsMap -> { waterFountainsMap
                        .path("width").entity(Integer.class).isEqualTo(5)
                        .path("height").entity(Integer.class).isEqualTo(6)
                        .path("url").entity(String.class).isEqualTo("url_waterFountainsMap");
                    })
                    .path("firstAid", firstAidMap -> { firstAidMap
                        .path("width").entity(Integer.class).isEqualTo(7)
                        .path("height").entity(Integer.class).isEqualTo(8)
                        .path("url").entity(String.class).isEqualTo("url_firstAidMap");
                    })
                    .path("donations", donationsMap -> { donationsMap
                        .path("width").entity(Integer.class).isEqualTo(9)
                        .path("height").entity(Integer.class).isEqualTo(10)
                        .path("url").entity(String.class).isEqualTo("url_donationsMap");
                    });
                });
            });

    }

    @Test
    public void PUT_mapPage_returns_page() {
    
        // set up mocks

        when(service.updateMapPage(any(), any())).thenReturn(mapPageView);

        graphqlTester.documentName("mapPageMutationPut")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("updateMapPage", mapPage -> { mapPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("pageDescription").entity(String.class).isEqualTo("pageDescription")
                .path("tapWidget").entity(TapWidgetView.class).isEqualTo(tapWidgetView)
                .path("mapViewConfig").entity(MapViewConfigView.class).isEqualTo(mapViewConfigView)
                .path("bathroomAmenity", bathroomAmenity -> { bathroomAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelBr")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelBr");
                })
                .path("waterFountainAmenity", waterFountainAmenity -> { waterFountainAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelW")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelW");
                })
                .path("firstAidAmenity", firstAidAmenity -> { firstAidAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelFa")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelFa");
                })
                .path("donationAmenity", donationAmenity -> { donationAmenity
                    .path("widgetLabel").entity(String.class).isEqualTo("widgetLabelDon")
                    .path("headingLabel").entity(String.class).isEqualTo("headingLabelDon");
                })
                .path("maps", donationAmenity -> { donationAmenity
                    .path("default", defaultMap -> { defaultMap
                        .path("width").entity(Integer.class).isEqualTo(1)
                        .path("height").entity(Integer.class).isEqualTo(2)
                        .path("url").entity(String.class).isEqualTo("url_defaultMap");
                    })
                    .path("bathrooms", bathroomsMap -> { bathroomsMap
                        .path("width").entity(Integer.class).isEqualTo(3)
                        .path("height").entity(Integer.class).isEqualTo(4)
                        .path("url").entity(String.class).isEqualTo("url_bathroomsMap");
                    })
                    .path("waterFountains", waterFountainsMap -> { waterFountainsMap
                        .path("width").entity(Integer.class).isEqualTo(5)
                        .path("height").entity(Integer.class).isEqualTo(6)
                        .path("url").entity(String.class).isEqualTo("url_waterFountainsMap");
                    })
                    .path("firstAid", firstAidMap -> { firstAidMap
                        .path("width").entity(Integer.class).isEqualTo(7)
                        .path("height").entity(Integer.class).isEqualTo(8)
                        .path("url").entity(String.class).isEqualTo("url_firstAidMap");
                    })
                    .path("donations", donationsMap -> { donationsMap
                        .path("width").entity(Integer.class).isEqualTo(9)
                        .path("height").entity(Integer.class).isEqualTo(10)
                        .path("url").entity(String.class).isEqualTo("url_donationsMap");
                    });
                });
            });

    }

    @Test
    public void DELETE_homePage_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("locale", "en");

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("mapPageMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteMapPage", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the mapPage in en");
            });
        
    }
    
}
