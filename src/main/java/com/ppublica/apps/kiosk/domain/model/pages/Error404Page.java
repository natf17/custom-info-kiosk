package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;


/*
 * Represents an Error 404 Page with the following:
 *  - page title
 *  - error description
 *  - show redirect link boolean
 *  - a redirect link
 */
public class Error404Page extends KioskPage {
    public static final String REDIRECT_URL_FIELD_NAME = "Url";
    public static final String REDIRECT_DISPLAY_TEXT_FIELD_NAME = "DisplayText";
    public static final String REDIRECT_DESCRIPTION_FIELD_NAME = "Description";
    public static final String SHOW_REDIRECT_LINK_OPTION_FIELD_NAME = "ShowRedirectLink";
    public static final String ERROR_DESCR__FIELD_NAME = "ErrorDescription";

    protected static final String REDIRECT_URL_CONTAINER_NAME = "redirectUrlFC";
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.ERROR;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    private static final String PAGE_NAME = "error_page";

    private Page errorPageRep;
    private FieldContainer mainFC;
    private FieldContainer redirectUrlFC;

    private Error404Page(Page errorPageRep) {
        this.errorPageRep = errorPageRep;

        this.mainFC = errorPageRep.getFieldContainers().get(0);
        this.redirectUrlFC = mainFC.getChildContainers().get(0);
    }

    public String getRedirectUrl() {
        return redirectUrlFC.getUrlFields().get(0).getFieldValue();
    }

    public String getRedirectDisplayText() {
        List<RegularTextLongDescriptionField> fields = redirectUrlFC.getRegularTextLongDescriptionFields();
        
        for(RegularTextLongDescriptionField field : fields) {
            if(field.getFieldName().equals(REDIRECT_DISPLAY_TEXT_FIELD_NAME)) {
                return field.getFieldValue();
            }
        }

        return null;
    }

    public String getRedirectDescription() {
        List<RegularTextLongDescriptionField> fields = redirectUrlFC.getRegularTextLongDescriptionFields();
        
        for(RegularTextLongDescriptionField field : fields) {
            if(field.getFieldName().equals(REDIRECT_DESCRIPTION_FIELD_NAME)) {
                return field.getFieldValue();
            }
        }

        return null;
    }

    public String getErrorDescription() {
        return mainFC.getRegularTextLongDescriptionFields().get(0).getFieldValue();
    }

    public Boolean shouldShowRedirectLink() {
        return mainFC.getButtonFields().get(0).getFieldValue();
    } 

    @Override
    protected Page getPageRep() {
        return errorPageRep;
    }

    public static Error404Page fromPage(Page errorPageRep) {
        // TO ADD: a validator

        return new Error404Page(errorPageRep);
    }


    public static class Builder extends KioskPage.Builder<Builder, Error404Page> {

        private String errorDescription;
        private boolean showRedirectLink;
        private String redirectUrl;
        private String redirectDisplayText;
        private String redirectDescription;

        public Builder() {
            super(KIOSK_PAGE_TYPE, PAGE_NAME);
        }

        public Builder errorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
            return self();
        }

        public Builder showRedirectLink(boolean showRedirectLink) {
            this.showRedirectLink = showRedirectLink;
            return self();
        }

        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return self();
        }

        public Builder redirectDisplayText(String redirectDisplayText) {
            this.redirectDisplayText = redirectDisplayText;
            return self();
        }

        public Builder redirectDescription(String redirectDescription) {
            this.redirectDescription = redirectDescription;
            return self();
        }


        @Override
        protected void validateAndPrepareChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected Error404Page buildChild(Page.Builder pageRepBuilder) {

            FieldContainer redirectLinkFC = new FieldContainer.Builder()
                                                            .addUrlField(new UrlField(REDIRECT_URL_FIELD_NAME, redirectUrl))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(REDIRECT_DISPLAY_TEXT_FIELD_NAME, redirectDisplayText))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(REDIRECT_DESCRIPTION_FIELD_NAME, redirectDescription))
                                                            .fieldContainerName(REDIRECT_URL_CONTAINER_NAME)
                                                            .build();


            FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addButtonField(new ButtonField(SHOW_REDIRECT_LINK_OPTION_FIELD_NAME, showRedirectLink))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(ERROR_DESCR__FIELD_NAME, errorDescription))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .addChildContainer(redirectLinkFC)
                                                            .build();

            List<FieldContainer> fieldContainers = new ArrayList<>();
            fieldContainers.add(mainFC);

            pageRepBuilder.fieldContainers(fieldContainers);

            return new Error404Page(pageRepBuilder.build());
        }
        
    }
    
}
