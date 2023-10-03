package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MenuItemRegFieldSelector;


public class MenuItem implements IdKioskContainer{
    private KioskPageField<Long> id;
    private KioskPageField<Boolean> isVisible;
    private KioskPageField<String> label;
    private KioskPageField<String> url;
    private KioskPageField<Image> image;

    public MenuItem(KioskPageField<Long> id, KioskPageField<Boolean> isVisible, KioskPageField<String> label, KioskPageField<String> url,
                        KioskPageField<Image> image) {
        this.id = id;
        this.isVisible = isVisible;
        this.label = label;
        this.url = url;
        this.image = image;
    }

    public KioskPageField<Long> getId() {
        return this.id;
    }

    public KioskPageField<Boolean> isVisible() {
        return this.isVisible;
    }

    public KioskPageField<String> getLabel() {
        return this.label;
    }

    public KioskPageField<String> getUrl() {
        return this.url;
    }

    public KioskPageField<Image> getImage() {
        return this.image;
    }

    /*
     * Expects a fieldContainer with 
     *  - two RegularTextLongDescriptionFields (one of which has a number only)
     *  - a UrlField
     *  - a ButtonField
     *  - an ImageField
     */
    public static MenuItem fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, MenuItemRegFieldSelector menuItemRegFieldSelector) {

        RegularTextLongDescriptionField cmsIdField = menuItemRegFieldSelector.selectIdField(fieldContainer);
        RegularTextLongDescriptionField cmsLabelField = menuItemRegFieldSelector.selectLabelField(fieldContainer);
        UrlField cmsUrlField = fieldContainer.getUrlFields().get(0);
        ButtonField cmsIsVisibleField = fieldContainer.getButtonFields().get(0);
        ImageField cmsImageField = fieldContainer.getImageFields().get(0);
        
        Long id = Long.valueOf(cmsIdField.getFieldValue());
        KioskPageField<Long> idField = new KioskPageField<Long>(cmsIdField.getFieldType(), cmsIdField.getFieldName(), id, false);
        KioskPageField<String> labelField = converter.toStringField(cmsLabelField);
        KioskPageField<String> urlField = converter.toStringField(cmsUrlField);
        KioskPageField<Boolean> isVisible = converter.toBooleanField(cmsIsVisibleField);
        KioskPageField<Image> imageField = converter.toImageField(cmsImageField);

        return new MenuItem(idField, isVisible, labelField, urlField, imageField);
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {
        FieldContainer menuItemFC = new FieldContainer.Builder()
            .addButtonField(converter.toButtonField(isVisible))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(label))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(url))
            .addImageField(converter.toImageField(image))
            .fieldContainerName(containerName)
            .build();

        return menuItemFC;
    }

}
