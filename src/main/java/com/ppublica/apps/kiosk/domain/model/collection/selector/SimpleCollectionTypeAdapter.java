package com.ppublica.apps.kiosk.domain.model.collection.selector;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.collection.converter.ToCmsCollectionConverter;
import com.ppublica.apps.kiosk.domain.model.collection.converter.ToKioskCollectionConverter;

public abstract class SimpleCollectionTypeAdapter implements KioskCollectionType, SimpleCollectionType{
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    // cms-facing methods

    @Override
    public Long getId() {
        return getAndSetCmsRep().getId();        
    }

    @Override
    public String getType() {
        return getAndSetCmsRep().getType();
    }

    @Override
    public CollectionNameField getCollectionNameField() {
        return getAndSetCmsRep().getCollectionNameField();
    }

    @Override
    public List<TextField> getTextFields() {
        return getAndSetCmsRep().getTextFields();
    }

    @Override
    public List<NumericField> getNumericFields() {
        return getAndSetCmsRep().getNumericFields();
    }

    @Override
    public List<ImageField> getImageFields() {
        return getAndSetCmsRep().getImageFields();
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return getAndSetCmsRep().getCollectionInternals();
    }

    // kiosk-facing methods
    @Override
    public CollectionTypeName getKioskCollectionTypeName() {
        return getAndSetKioskRep().getKioskCollectionTypeName();
    }

    @Override
    public KioskCollectionField<String> getKioskCollectionNameField() {
        return getAndSetKioskRep().getKioskCollectionNameField();
    }

    @Override
    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return getAndSetKioskRep().getKioskCollectionMetadata();
    }
    
    protected abstract SimpleCollectionType getCmsRepInstance();
    protected abstract void processAndSetCmsRep(SimpleCollectionTypeImpl.Builder cmsRepBuilder);
    
    protected SimpleCollectionType getAndSetCmsRep() {
        SimpleCollectionType cmsRep = getCmsRepInstance();
        
        if(cmsRep != null) {
            return cmsRep;
        }

        // make use of "both cmsRep and kioskRep cannot be null" invariant
        KioskCollectionType kioskRep = getAndSetKioskRep();
        SimpleCollectionTypeImpl.Builder collectionBuilder = new SimpleCollectionTypeImpl.Builder()
                                                                .type(kioskRep.getKioskCollectionTypeName().toString())
                                                                .collectionInternals(kioskRep.getKioskCollectionMetadata().getCollectionInternals())
                                                                .collectionNameField(toCmsCollectionConverter.toCollectionNameField(kioskRep.getKioskCollectionNameField()));
    

        processAndSetCmsRep(collectionBuilder);
        return getCmsRepInstance();
    }


    protected abstract KioskCollectionType getKioskRepInstance();
    protected <B extends KioskCollectionTypeImpl.Builder<B,M>, M extends KioskCollectionTypeImpl> void processAndSetKioskRep(KioskCollectionTypeImpl.Builder<B, M> kioskRepBuilder) {
        SimpleCollectionType cmsRep = getCmsRepInstance();
        CollectionNameField collNameField = cmsRep.getCollectionNameField();

        kioskRepBuilder.collectionNameField(toKioskCollectionConverter.toStringField(collNameField));
        kioskRepBuilder.kioskCollectionMetadata(KioskCollectionMetadata.fromCollectionInternals(cmsRep.getCollectionInternals()));

    }
    
    
    protected abstract KioskCollectionType getAndSetKioskRep();

    
}
