package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;

public abstract class CmsCollectionAdapterBuilder<B extends CmsCollectionAdapterBuilder<B,M,S>, M extends KioskCollectionType, S extends KioskCollectionTypeBaseAdapter> {
    protected CollectionLocalizedProperties localizedCmsPiece;
    protected CollectionSharedProperties sharedCmsPiece;
    protected M kioskCollection;

    public B localizedCmsPiece(CollectionLocalizedProperties localizedCmsPiece) {
        this.kioskCollection = null;
        this.localizedCmsPiece = localizedCmsPiece;
        return self();
    }

    public B sharedCmsPiece(CollectionSharedProperties sharedCmsPiece) {
        this.kioskCollection = null;
        this.sharedCmsPiece = sharedCmsPiece;
        return self();
    }

    public B kioskCollection(M kioskCollection) {
        this.localizedCmsPiece = null;
        this.sharedCmsPiece = null;
        this.kioskCollection = kioskCollection;
        return self();
    }

    public S build() {

        if(localizedCmsPiece != null) {
            if(sharedCmsPiece != null) {
                validateCmsPieces();

                return buildChild();
            }

            throw new RuntimeException("Both cms pieces must be provided.");
        }

        if(kioskCollection != null) {
            return buildChild();
        }

        throw new RuntimeException("The cms pieces and the bathroom type cannot both be null.");
    }

    protected abstract S buildChild();

    protected abstract B self();

    protected void validateCmsPieces() {}
    
}
