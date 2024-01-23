package com.ppublica.apps.kiosk.service.views.data.eventseason;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;

public record EventSeasonAdminView(Long id, String type, Integer durationDays, List<LocalizedField> theme, Integer serviceYear, List<LocalizedField> durationText) {}

