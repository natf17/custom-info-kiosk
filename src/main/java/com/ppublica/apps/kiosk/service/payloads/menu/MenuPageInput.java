package com.ppublica.apps.kiosk.service.payloads.menu;

public record MenuPageInput(String pageTitle, MenuItemContainerViewInput directory, MenuItemContainerViewInput events, MenuItemContainerViewInput about) {}
