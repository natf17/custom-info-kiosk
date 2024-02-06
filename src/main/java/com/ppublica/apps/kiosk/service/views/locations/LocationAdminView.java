package com.ppublica.apps.kiosk.service.views.locations;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;

public record LocationAdminView(String id, List<LocalizedField> fullname, Integer level_num, List<LocalizedField> level_name, List<LocalizedView<MapImage>> map) {}
