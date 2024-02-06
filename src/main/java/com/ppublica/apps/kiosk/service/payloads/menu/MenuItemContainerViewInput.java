package com.ppublica.apps.kiosk.service.payloads.menu;

import java.util.List;

public record MenuItemContainerViewInput(String title, List<MenuItemViewInput> menuItems) {}
