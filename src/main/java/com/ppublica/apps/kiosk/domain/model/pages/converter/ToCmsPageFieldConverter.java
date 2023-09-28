package com.ppublica.apps.kiosk.domain.model.pages.converter;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;
import com.ppublica.apps.kiosk.domain.model.pages.KioskPageField;

public class ToCmsPageFieldConverter {

    public RegularTextLongDescriptionField toRegTextLongDescr(KioskPageField<String> kioskPageField) {
        return new RegularTextLongDescriptionField(kioskPageField.getFieldType(), kioskPageField.getFieldName(), kioskPageField.getFieldValue());
    }

    public ImageField toImageField(KioskPageField<Image> kioskPageField) {
        return new ImageField(kioskPageField.getFieldType(), kioskPageField.getFieldName(), kioskPageField.getFieldValue());
    }

    public RichTextLongDescriptionField toRichTextLongDescr(KioskPageField<String> kioskPageField) {
        return new RichTextLongDescriptionField(kioskPageField.getFieldType(), kioskPageField.getFieldName(), kioskPageField.getFieldValue());
    }

    public PageTitleField toPageTitleField(KioskPageField<String> kioskPageField) {
        return new PageTitleField(kioskPageField.getFieldType(), kioskPageField.getFieldName(), kioskPageField.getFieldValue());
    }

    public UrlField toUrlField(KioskPageField<String> kioskPageField) {
        return new UrlField(kioskPageField.getFieldType(), kioskPageField.getFieldName(), kioskPageField.getFieldValue());
    }

    public ButtonField toButtonField(KioskPageField<Boolean> kioskPageField) {
        return new ButtonField(kioskPageField.getFieldType(), kioskPageField.getFieldName(), kioskPageField.getFieldValue());
    }

    

    
}
