package com.ppublica.apps.kiosk.service.payloads.menu;

public record MenuItemViewInput(Long id, Boolean isVisible, String label, String url, ImageViewInput image) {}

