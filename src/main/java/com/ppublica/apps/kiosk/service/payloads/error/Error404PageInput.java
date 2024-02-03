package com.ppublica.apps.kiosk.service.payloads.error;

public record Error404PageInput(String pageTitle, String errorDescription, Boolean showRedirectLink, RedirectLinkInput redirectLink) {}
