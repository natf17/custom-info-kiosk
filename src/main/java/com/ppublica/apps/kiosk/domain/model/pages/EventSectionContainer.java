package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.EventSectionFieldSelector;

public class EventSectionContainer {

    private KioskPageField<String> title;
    private KioskPageField<String> btnText;

    public EventSectionContainer(KioskPageField<String> title, KioskPageField<String> btnText) {
        this.title = title;
        this.btnText = btnText;
    }

    public KioskPageField<String> getTitleField() {
        return this.title;
    }

    public KioskPageField<String> getBtnTextField() {
        return this.btnText;
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {
        FieldContainer eventSection = new FieldContainer.Builder()
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(title))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(btnText))
                            .fieldContainerName(containerName)
                            .build();

        return eventSection;
    }

    /*
     * Expects a fieldContainer with two RegularTextLongDescriptionFields
     */
    public static EventSectionContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, EventSectionFieldSelector fieldSelector) {
        

        RegularTextLongDescriptionField cmsTitleField = fieldSelector.titleField(fieldContainer);
        RegularTextLongDescriptionField cmsBtnTextField = fieldSelector.btnTextField(fieldContainer);

        KioskPageField<String> titleField = converter.toStringField(cmsTitleField);
        KioskPageField<String> btnTextField = converter.toStringField(cmsBtnTextField);
  
        return new EventSectionContainer(titleField, btnTextField);
    }
    
}
