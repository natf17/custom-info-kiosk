package com.ppublica.apps.kiosk.service.payloads;

public record LocalizedInputField<T>(String locale, T value) {}
