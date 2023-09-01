package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

/*
 * Represents a redirect link "container" with the following page fields:
 *  - url
 *  - display text
 *  - description
 */
public class RedirectLinkContainer extends PageFieldContainer {
    private static final String URL_FIELD_NAME = "Url";
    private static final String DISPLAY_TEXT_FIELD_NAME = "DisplayText";
    private static final String DESCRIPTION_FIELD_NAME = "Description";

    private UrlField urlField;
    private DisplayTextField displayTextField;
    private SimpleDescriptionField simpleDescriptionField;

    @PersistenceCreator
    public RedirectLinkContainer(String containerName, UrlField urlField, DisplayTextField displayTextField, 
                                    SimpleDescriptionField simpleDescriptionField) {
        super(containerName);

        this.urlField = urlField;
        this.displayTextField = displayTextField;
        this.simpleDescriptionField = simpleDescriptionField;
    }

    public RedirectLinkContainer(String containerName, String urlFieldStr, String displayTextFieldStr, 
                                    String simpleDescriptionFieldStr) {
        super(containerName);

        this.urlField = new UrlField(URL_FIELD_NAME, urlFieldStr);
        this.displayTextField = new DisplayTextField(DISPLAY_TEXT_FIELD_NAME, displayTextFieldStr);
        this.simpleDescriptionField = new SimpleDescriptionField(DESCRIPTION_FIELD_NAME, simpleDescriptionFieldStr);
    }
}
