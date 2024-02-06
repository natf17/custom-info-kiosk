package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.MenuCategoryContainer;
import com.ppublica.apps.kiosk.domain.model.pages.MenuItem;
import com.ppublica.apps.kiosk.domain.model.pages.MenuPage;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.payloads.menu.MenuPageInput;
import com.ppublica.apps.kiosk.service.views.menu.ImageView;
import com.ppublica.apps.kiosk.service.views.menu.MenuItemContainerView;
import com.ppublica.apps.kiosk.service.views.menu.MenuItemView;
import com.ppublica.apps.kiosk.service.views.menu.MenuPageView;

public class MenuPageService {
    @Autowired
    private PageRepository repo;

    public Optional<MenuPageView> getMenuPage(String locale) {

        Optional<Page> menuPageOpt = repo.findByPageTypeAndKioskLocale(MenuPage.PAGE_TYPE, locale);
        
        if (menuPageOpt.isEmpty()) {
            return Optional.empty();
        }

        MenuPage menuPage = new MenuPage.Builder(menuPageOpt.get())
                                .build();

        return Optional.of(transformToView(menuPage));

    }

    public MenuPageView createMenuPage(String locale, MenuPageInput input) {
        throw new UnsupportedOperationException();
    }

    public MenuPageView updateMenuPage(String locale, MenuPageInput input) {
        throw new UnsupportedOperationException();
    }

    public void deleteMenuPage(String locale) {
        throw new UnsupportedOperationException();
    }

    private MenuPageView transformToView(MenuPage menuPage) {

        String pageTitle = menuPage.getPageTitleField().getFieldValue();

        MenuItemContainerView directory = transformToView(menuPage.getDirectoryContainer());
        MenuItemContainerView events = transformToView(menuPage.getEventsContainer());
        MenuItemContainerView about = transformToView(menuPage.getAboutContainer());

        return new MenuPageView(pageTitle, directory, events, about);

    }

    private MenuItemContainerView transformToView(MenuCategoryContainer container) {
        String title = container.getTitleField().getFieldValue();

        List<MenuItemView> menuItems = container.getMenuItems().stream()
                                                                .map(i -> transformToView(i))
                                                                .collect(Collectors.toList());

        return new MenuItemContainerView(title, menuItems);
    
    }

    private MenuItemView transformToView(MenuItem menuItem) {
        Long id = menuItem.getId().getFieldValue();
        Boolean isVisible = menuItem.isVisible().getFieldValue();
        String label = menuItem.getLabel().getFieldValue();
        String url = menuItem.getUrl().getFieldValue();

        ImageView imageView = transformToView(menuItem.getImage().getFieldValue());

        return new MenuItemView(id, isVisible, label, url, imageView);
    }

    private ImageView transformToView(Image image) {
        String url = image.location();
        Integer width = image.width();
        Integer height = image.height();

        return new ImageView(url, width, height);
    }
}
