package com.ppublica.apps.kiosk.service.payloads.locations;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

public record LocationInput(List<LocalizedInputField<String>> fullname, Integer level_num, List<LocalizedInputField<String>> level_name, List<LocalizedInputField<MapImageInput>> map) {}
