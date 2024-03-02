package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public interface KioskFieldToBuilderAction<T, K> {
        public void callOnBuilder(K builder, KioskCollectionField<T> field);

}
