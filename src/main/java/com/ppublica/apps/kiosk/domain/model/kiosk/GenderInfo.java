package com.ppublica.apps.kiosk.domain.model.kiosk;

import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;

public record GenderInfo(KioskCollectionField<String> gender) implements GenderAware {}