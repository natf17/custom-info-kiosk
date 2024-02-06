package com.ppublica.apps.kiosk.service.payloads.locations;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

public record LocationInput(List<LocalizedInput> fullname, Integer level_num, List<LocalizedInput> level_name, List<LocalizedInputField<MapImageInput>> map) {}
