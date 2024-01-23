package com.ppublica.apps.kiosk.service.payloads.data.eventseason;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;

public record EventSeasonInput(String type, Integer durationDays, List<LocalizedInput> theme, Integer serviceYear, List<LocalizedInput> durationText) {}

