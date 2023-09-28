package com.ppublica.apps.kiosk.domain.model.pages.converter;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;
import com.ppublica.apps.kiosk.domain.model.pages.KioskPageField;

public class ToKioskPageFieldConverter {

    public KioskPageField<Image> toImageField(ImageField cmsImageField) {
        return new KioskPageField<Image>(cmsImageField.getFieldType(), cmsImageField.getFieldName(), cmsImageField.getFieldValue(), cmsImageField.isLocalized());
    }

    public KioskPageField<String> toStringField(RegularTextLongDescriptionField regTextField) {
        return new KioskPageField<String>(regTextField.getFieldType(), regTextField.getFieldName(), regTextField.getFieldValue(), regTextField.isLocalized());
    }

    public KioskPageField<String> toStringField(PageTitleField pageTitleField) {
        return new KioskPageField<String>(pageTitleField.getFieldType(), pageTitleField.getFieldName(), pageTitleField.getFieldValue(), pageTitleField.isLocalized());
    }

    public KioskPageField<String> toStringField(RichTextLongDescriptionField richTextField) {
        return new KioskPageField<String>(richTextField.getFieldType(), richTextField.getFieldName(), richTextField.getFieldValue(), richTextField.isLocalized());
    }

    public KioskPageField<String> toStringField(UrlField urlField) {
        return new KioskPageField<String>(urlField.getFieldType(), urlField.getFieldName(), urlField.getFieldValue(), urlField.isLocalized());
    }

    public KioskPageField<Boolean> toBooleanField(ButtonField buttonField) {
        return new KioskPageField<Boolean>(buttonField.getFieldType(), buttonField.getFieldName(), buttonField.getFieldValue(), buttonField.isLocalized());
    }
    
}
