package com.ppublica.apps.kiosk.service.views.error;


public record Error404PageView(String pageTitle, String errorDescription, Boolean showRedirectLink, RedirectLinkView redirectLink) {}