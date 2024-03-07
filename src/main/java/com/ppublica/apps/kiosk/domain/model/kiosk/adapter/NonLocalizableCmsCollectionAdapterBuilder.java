package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionType;

/*
 * The non localizable equivalent of CmsCollectionAdapterBuilder
 */
public abstract class NonLocalizableCmsCollectionAdapterBuilder<B extends NonLocalizableCmsCollectionAdapterBuilder<B,M,S>, M extends NonLocalizableKioskCollectionType, S extends NonLocalizableKioskCollectionTypeBaseAdapter> {
    protected CollectionSharedProperties sharedCmsPiece;
    protected M kioskCollection;

    public B sharedCmsPiece(CollectionSharedProperties sharedCmsPiece) {
        this.kioskCollection = null;
        this.sharedCmsPiece = sharedCmsPiece;
        return self();
    }

    public B kioskCollection(M kioskCollection) {
        this.sharedCmsPiece = null;
        this.kioskCollection = kioskCollection;
        return self();
    }

    public S build() {

        if(sharedCmsPiece != null) {
            validateCmsPiece();

            return buildChild();
        }

        if(kioskCollection != null) {
            return buildChild();
        }

        throw new RuntimeException("The cms piece and the kiosk type cannot both be null.");
    }

    protected abstract S buildChild();

    protected abstract B self();

    protected void validateCmsPiece() {}
    
}
