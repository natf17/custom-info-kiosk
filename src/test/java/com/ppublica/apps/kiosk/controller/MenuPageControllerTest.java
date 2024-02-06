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

import com.ppublica.apps.kiosk.service.MenuPageService;
import com.ppublica.apps.kiosk.service.views.menu.ImageView;
import com.ppublica.apps.kiosk.service.views.menu.MenuItemContainerView;
import com.ppublica.apps.kiosk.service.views.menu.MenuItemView;
import com.ppublica.apps.kiosk.service.views.menu.MenuPageView;

@GraphQlTest(controllers=MenuPageController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class MenuPageControllerTest {

    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private MenuPageService service;

    Map<String,Object> payload;


    MenuItemContainerView directoryView;
    MenuItemView directoryMenuItem1;
    MenuItemView directoryMenuItem2;

    MenuItemContainerView eventsView;
    MenuItemView eventsMenuItem1;
    
    MenuItemContainerView aboutView;

    MenuPageView menuPageView;

    @BeforeEach
    public void setup() {
        // set up input
        Map<String,Object> imageViewInputDirectory1 = new HashMap<>();
        imageViewInputDirectory1.put("url", "directory_img_url1");
        imageViewInputDirectory1.put("width", 1);
        imageViewInputDirectory1.put("height", 2);

        Map<String,Object> menuItemInputDirectory1 = new HashMap<>();
        menuItemInputDirectory1.put("id", 1L);
        menuItemInputDirectory1.put("isVisible", true);
        menuItemInputDirectory1.put("label", "directoryMenuItem1_label");
        menuItemInputDirectory1.put("url", "directoryMenuItem1_url");
        menuItemInputDirectory1.put("image", imageViewInputDirectory1);

        Map<String,Object> imageViewInputDirectory2 = new HashMap<>();
        imageViewInputDirectory1.put("url", "directory_img_url2");
        imageViewInputDirectory1.put("width", 3);
        imageViewInputDirectory1.put("height", 4);

        Map<String,Object> menuItemInputDirectory2 = new HashMap<>();
        menuItemInputDirectory1.put("id", 2L);
        menuItemInputDirectory1.put("isVisible", false);
        menuItemInputDirectory1.put("label", "directoryMenuItem2_label");
        menuItemInputDirectory1.put("url", "directoryMenuItem2_url");
        menuItemInputDirectory1.put("image", imageViewInputDirectory2);
        
        Map<String,Object> directoryContainerInput = new HashMap<>();
        directoryContainerInput.put("title", "title_directory");
        directoryContainerInput.put("menuItems", List.of(menuItemInputDirectory1, menuItemInputDirectory2));


        Map<String,Object> imageViewInputEvents = new HashMap<>();
        imageViewInputEvents.put("url", "events_img_url");
        imageViewInputEvents.put("width", 1);
        imageViewInputEvents.put("height", 2);

        Map<String,Object> menuItemInputEvents = new HashMap<>();
        menuItemInputDirectory1.put("id", 1L);
        menuItemInputDirectory1.put("isVisible", true);
        menuItemInputDirectory1.put("label", "eventsMenuItem_label");
        menuItemInputDirectory1.put("url", "eventsMenuItem_url");
        menuItemInputDirectory1.put("image", imageViewInputEvents);

        Map<String,Object> eventsContainerInput = new HashMap<>();
        directoryContainerInput.put("title", "title_events");
        directoryContainerInput.put("menuItems", List.of(menuItemInputEvents));

        Map<String,Object> aboutContainerInput = new HashMap<>();
        directoryContainerInput.put("title", "title_about");
        directoryContainerInput.put("menuItems", List.of());


        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "pageTitle");
        input.put("directory", directoryContainerInput);
        input.put("events", eventsContainerInput);
        input.put("about", aboutContainerInput);

        payload = new HashMap<>();

        payload.put("data", input);

        ImageView directoryMenuItem1Image = new ImageView("directory_img_url1", 1, 2);
        this.directoryMenuItem1 = new MenuItemView(1L, true, "directoryMenuItem1_label", "directoryMenuItem1_url", directoryMenuItem1Image);
        ImageView directoryMenuItem2Image = new ImageView("directory_img_url2", 3, 4);
        this.directoryMenuItem2 = new MenuItemView(2L, false, "directoryMenuItem2_label", "directoryMenuItem2_url", directoryMenuItem2Image);
        this.directoryView = new MenuItemContainerView("title_directory", List.of(directoryMenuItem1, directoryMenuItem2));


        ImageView eventsMenuItemImage = new ImageView("events_img_url", 1, 2);
        this.eventsMenuItem1 = new MenuItemView(1L, true, "eventsMenuItem_label", "eventsMenuItem_url", eventsMenuItemImage);
        this.eventsView = new MenuItemContainerView("title_events", List.of(eventsMenuItem1));

        this.aboutView = new MenuItemContainerView("title_about", List.of());



        this.menuPageView = new MenuPageView("pageTitle", directoryView, eventsView, aboutView);
    }

    @Test
    public void GET_menuPage_returns_page() {

        // set up mock
        when(service.getMenuPage("en")).thenReturn(Optional.of(menuPageView));

        graphqlTester.documentName("menuPage")
            .variable("locale", "en")
            .execute()
            .path("menuPage", menuPage -> { menuPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("directory", directory -> { directory
                    .path("title").entity(String.class).isEqualTo("title_directory")
                    .path("menuItems").entityList(MenuItemView.class).containsExactly(directoryMenuItem1, directoryMenuItem2);
                })
                .path("events", events -> { events
                    .path("title").entity(String.class).isEqualTo("title_events")
                    .path("menuItems").entityList(MenuItemView.class).containsExactly(eventsMenuItem1);
                })
                .path("about", about -> { about
                    .path("title").entity(String.class).isEqualTo("title_about")
                    .path("menuItems").entityList(MenuItemView.class).hasSize(0);
                });

        });
    }

    @Test
    public void POST_menuPage_returns_page() {

        when(service.createMenuPage(any(), any())).thenReturn(menuPageView);

        graphqlTester.documentName("menuPageMutationPost")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("createMenuPage", menuPage -> { menuPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("directory", directory -> { directory
                    .path("title").entity(String.class).isEqualTo("title_directory")
                    .path("menuItems").entityList(MenuItemView.class).containsExactly(directoryMenuItem1, directoryMenuItem2);
                })
                .path("events", events -> { events
                    .path("title").entity(String.class).isEqualTo("title_events")
                    .path("menuItems").entityList(MenuItemView.class).containsExactly(eventsMenuItem1);
                })
                .path("about", about -> { about
                    .path("title").entity(String.class).isEqualTo("title_about")
                    .path("menuItems").entityList(MenuItemView.class).hasSize(0);
                });
            });

    }

    @Test
    public void PUT_menuPage_returns_page() {
    
        // set up mocks

        when(service.updateMenuPage(any(), any())).thenReturn(menuPageView);

        graphqlTester.documentName("menuPageMutationPut")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("updateMenuPage", menuPage -> { menuPage
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("directory", directory -> { directory
                    .path("title").entity(String.class).isEqualTo("title_directory")
                    .path("menuItems").entityList(MenuItemView.class).containsExactly(directoryMenuItem1, directoryMenuItem2);
                })
                .path("events", events -> { events
                    .path("title").entity(String.class).isEqualTo("title_events")
                    .path("menuItems").entityList(MenuItemView.class).containsExactly(eventsMenuItem1);
                })
                .path("about", about -> { about
                    .path("title").entity(String.class).isEqualTo("title_about")
                    .path("menuItems").entityList(MenuItemView.class).hasSize(0);
                });
            });

    }

    @Test
    public void DELETE_menuPage_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("locale", "en");

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("menuPageMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteMenuPage", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the menuPage in en");
            });
        
    }
    
}
