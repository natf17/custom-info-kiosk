package com.ppublica.apps.kiosk.service.payloads.data.eventseason;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

public record EventSeasonInput(String type, Integer durationDays, List<LocalizedInputField<String>> theme, Integer serviceYear, List<LocalizedInputField<String>> durationText) {}

