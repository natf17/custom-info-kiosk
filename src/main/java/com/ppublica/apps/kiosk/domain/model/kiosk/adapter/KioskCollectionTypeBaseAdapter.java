package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.KioskCollectionTypeConverter;

public class KioskCollectionTypeBaseAdapter implements KioskCollectionType, CollectionLocalizedProperties, CollectionSharedProperties {

    private KioskCollectionType baseKioskCollection;
    private CmsPieces cmsPieces;

    private KioskCollectionTypeConverter baseConverter = new KioskCollectionTypeConverter();


    public KioskCollectionTypeBaseAdapter(KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this.baseKioskCollection = baseKioskCollection;

        if(localizedCmsPiece != null && sharedCmsPiece != null) {
            this.cmsPieces = new CmsPieces(sharedCmsPiece, localizedCmsPiece);
        }
    }
    @Override
    public CollectionSharedProperties withId(Long id) {
        return getSharedCmsPiece().withId(id);
    }

    @Override
    public String type() {
        return getSharedCmsPiece().type();
    }

    @Override
    public String subType() {
        return getSharedCmsPiece().subType();
    }

    @Override
    public CollectionSharedInternals collectionSharedInternals() {
        return getSharedCmsPiece().collectionSharedInternals();
    }

    @Override
    public List<LinkedCollectionField> linkedCollectionFields() {
        return getSharedCmsPiece().linkedCollectionFields();
    }

    @Override
    public Long id() {
        return getSharedCmsPiece().id();
    }

    @Override
    public CollectionNameField collectionNameField() {
        return getSharedCmsPiece().collectionNameField();
    }

    @Override
    public List<TextField> textFields() {
        return getSharedCmsPiece().textFields();
    }

    @Override
    public List<NumericField> numericFields() {
        return getSharedCmsPiece().numericFields();
    }

    @Override
    public List<BooleanField> booleanFields() {
        return getSharedCmsPiece().booleanFields();
    }

    @Override
    public List<ImageField> imageFields() {
        return getSharedCmsPiece().imageFields();
    }

    @Override
    public Long parentId() {
        return getLocalizedCmsPiece().parentId();
    }

    @Override
    public CollectionLocalizedProperties locWithId(Long id) {
        return getLocalizedCmsPiece().locWithId(id);
    }
    @Override
    public Long locId() {
        return getLocalizedCmsPiece().locId();
    }
    @Override
    public CollectionNameField locCollectionNameField() {
        return getLocalizedCmsPiece().locCollectionNameField();
    }
    @Override
    public List<TextField> locTextFields() {
       return getLocalizedCmsPiece().locTextFields();
    }
    @Override
    public List<NumericField> locNumericFields() {
        return getLocalizedCmsPiece().locNumericFields();
    }
    @Override
    public List<BooleanField> locBooleanFields() {
        return getLocalizedCmsPiece().locBooleanFields();
    }
    @Override
    public List<ImageField> locImageFields() {
        return getLocalizedCmsPiece().locImageFields();
    }
    @Override
    public CollectionInternals locCollectionInternals() {
        return getLocalizedCmsPiece().locCollectionInternals();
    }

    @Override
    public Long collectionId() {
        return getBaseKioskCollection().collectionId();
    }

    @Override
    public CollectionType kioskCollectionType() {
        return getBaseKioskCollection().kioskCollectionType();
    }

    @Override
    public KioskCollectionField<String> kioskCollectionNameField() {
        return getBaseKioskCollection().kioskCollectionNameField();
    }

    @Override
    public KioskCollectionMetadata kioskCollectionMetadata() {
        return getBaseKioskCollection().kioskCollectionMetadata();
    }

    public final KioskCollectionType getBaseKioskCollection() {
        if(this.baseKioskCollection == null) {
            buildAndSetBaseKioskCollection();
        }

        return this.baseKioskCollection;
    }

    /*
     * Extending classes should override this method, and call it to allow this class to build the base kiosk collection.
     */
    protected void buildAndSetBaseKioskCollection() {
        this.baseKioskCollection = baseConverter.convert(this.cmsPieces.sharedCmsPiece(), this.cmsPieces.localizedCmsPiece());
    }

    public final CollectionLocalizedProperties getLocalizedCmsPiece() {
        if(this.cmsPieces == null) {
            buildAndSetCmsPieces();
        }

        return this.cmsPieces.localizedCmsPiece();
    }

    // for extending classes
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {}

    public final CollectionSharedProperties getSharedCmsPiece() {
        if(this.cmsPieces == null) {
            buildAndSetCmsPieces();
        }

        return this.cmsPieces.sharedCmsPiece();
    }

    protected final void buildAndSetCmsPieces() {
        CollectionSharedPropertiesImpl.Builder sharedCmsPieceBuilder = new CollectionSharedPropertiesImpl.Builder(this.baseKioskCollection.kioskCollectionType().toString());
        CollectionLocalizedPropertiesImpl.Builder localizedCollectionBuilder = new CollectionLocalizedPropertiesImpl.Builder(this.baseKioskCollection.collectionId());

        baseConverter.transferKioskRepToCmsBuilders(sharedCmsPieceBuilder, localizedCollectionBuilder, baseKioskCollection);
    
        processCmsBuilders(sharedCmsPieceBuilder, localizedCollectionBuilder);

        this.cmsPieces = new CmsPieces(sharedCmsPieceBuilder.build(), localizedCollectionBuilder.build());

    }


    record CmsPieces(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        CmsPieces {
            if(sharedCmsPiece == null || localizedCmsPiece == null) {
                throw new IllegalArgumentException("CollectionSharedProperties and CollectionLocalizedProperties must be non-null");
            }
        }
    }


    
}
