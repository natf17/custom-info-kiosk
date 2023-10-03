package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

/*
 * An empty MenuItem does not exist
 */
public class EditedMenuItem {
    private final EditedPageFieldPayload<Long> id;
    private final EditedPageFieldPayload<Boolean> isVisible;
    private final EditedPageFieldPayload<String> label;
    private final EditedPageFieldPayload<String> url;
    private final EditedPageFieldPayload<Image> image;

    public EditedMenuItem(EditedPageFieldPayload<Long> id, EditedPageFieldPayload<Boolean> isVisible, EditedPageFieldPayload<String> label,
                            EditedPageFieldPayload<String> url, EditedPageFieldPayload<Image> image) {
        this.id = id;
        this.isVisible = isVisible;
        this.label = label;
        this.url = url;
        this.image = image;
    }

    public EditedPageFieldPayload<Long> getId() {
        return this.id;
    }

    public EditedPageFieldPayload<Boolean> isVisible() {
        return this.isVisible;
    }

    public EditedPageFieldPayload<String> getLabel() {
        return this.label;
    }

    public EditedPageFieldPayload<String> getUrl() {
        return this.url;
    }

    public EditedPageFieldPayload<Image> getImage() {
        return this.image;
    }


    public MenuItem toMenuItem(String idFieldType, String isVisibleFieldType, String labelFieldType, String urlFieldType, String imageFieldType) {
        KioskPageField<Long> idField = id.toKioskPageField(idFieldType, false);
        KioskPageField<Boolean> isVisibleField = isVisible.toKioskPageField(isVisibleFieldType, true);
        KioskPageField<String> labelField = label.toKioskPageField(labelFieldType, true);
        KioskPageField<String> urlField = url.toKioskPageField(urlFieldType, true);
        KioskPageField<Image> imageField = image.toKioskPageField(imageFieldType, false);
        
        return new MenuItem(idField, isVisibleField, labelField, urlField, imageField);
    }
}
