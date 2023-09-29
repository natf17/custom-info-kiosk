package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.selector.RedirectUrlContainerRegFieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.validator.KioskPageFieldTypeValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.RedirectUrlContainerValidator;


/*
 * Represents an Error 404 Page with the following:
 *  - error description
 *  - show redirect link boolean
 *  - a redirect link (redirect link container)
 */
public class Error404Page extends KioskPage {
    public static final String REDIRECT_URL_FIELD_TYPE = "Url";
    public static final String REDIRECT_URL_FIELD_NAME_DEFUALT = "Url";
    public static final String REDIRECT_DISPLAY_TEXT_FIELD_TYPE = "DisplayText";
    public static final String REDIRECT_DISPLAY_TEXT_FIELD_NAME_DEFAULT = "DisplayText";
    public static final String REDIRECT_DESCRIPTION_FIELD_TYPE = "Description";
    public static final String REDIRECT_DESCRIPTION_FIELD_NAME_DEFAULT = "Description";
    public static final String SHOW_REDIRECT_LINK_OPTION_FIELD_TYPE = "ShowRedirectLink";
    public static final String SHOW_REDIRECT_LINK_OPTION_FIELD_NAME_DEFAULT = "ShowRedirectLink";
    public static final String ERROR_DESCR_FIELD_TYPE = "ErrorDescription";
    public static final String ERROR_DESCR_FIELD_NAME_DEFAULT = "ErrorDescription";

    protected static final String REDIRECT_URL_CONTAINER_NAME = "redirectUrlFC";
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.ERROR;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    private static final String PAGE_NAME_DEFAULT = "error_page";

    private KioskPageField<String> errorDescription;
    private KioskPageField<Boolean> shouldShowRedirectLink;
    private RedirectUrlContainer redirectUrlContainer;

    private Error404Page(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, 
                String pageName, KioskPageField<String> errorDescription, KioskPageField<Boolean> shouldShowRedirectLink, RedirectUrlContainer redirectUrlContainer) {
        super(pageRep, pageTitleField, pageMetadata, kioskPageType, pageName);
        this.errorDescription = errorDescription;
        this.shouldShowRedirectLink = shouldShowRedirectLink;
        this.redirectUrlContainer = redirectUrlContainer;
    }

    public KioskPageField<String> getErrorDescriptionField() {
        return this.errorDescription;
    }

    public KioskPageField<Boolean> shouldShowRedirectLink() {
        return this.shouldShowRedirectLink;
    }

    public RedirectUrlContainer getRedirectUrlContainer() {
        return this.redirectUrlContainer;
    }
    

    @Override
    protected Page buildPageRep(Page.Builder pageRepBuilder) {
        FieldContainer redirectContainer = this.redirectUrlContainer.toFieldContainer(REDIRECT_URL_CONTAINER_NAME, cmsConverter);

        FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addRegularTextLongDescriptionField(cmsConverter.toRegTextLongDescr(this.errorDescription))
                                                            .addButtonField(cmsConverter.toButtonField(this.shouldShowRedirectLink))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .addChildContainer(redirectContainer)
                                                            .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);

        pageRepBuilder.fieldContainers(fieldContainers);

        return pageRepBuilder.build();
    }


    public static class Builder extends KioskPage.Builder<Builder, Error404Page> {
        private RedirectUrlContainerValidator redirectUrlContainerValidator;

        private KioskPageField<String> errorDescriptionField;
        private EditedPageFieldPayload<String> editedErrorDescriptionField;

        private KioskPageField<Boolean> shouldShowRedirectLink;
        private EditedPageFieldPayload<Boolean> editedShouldShowRedirectLinkField;

        private RedirectUrlContainer redirectUrlContainer;
        private EditedRedirectUrlContainer editedRedirectUrlContainer;
        

        public Builder(String pageName) {
            super(KIOSK_PAGE_TYPE);
            super.pageName = pageName;
        }
        
        public Builder() {
            super(KIOSK_PAGE_TYPE);
            super.pageName = PAGE_NAME_DEFAULT;
        }

        public Builder(Page pageRep) {
            super(KIOSK_PAGE_TYPE, pageRep);
        }

        public Builder errorDescriptionField(EditedPageFieldPayload<String> editedErrorDescriptionField) {
            this.editedErrorDescriptionField = editedErrorDescriptionField;
            return self();
        }

        public Builder showRedirectLinkField(EditedPageFieldPayload<Boolean> editedShouldShowRedirectLinkField) {
            this.editedShouldShowRedirectLinkField = editedShouldShowRedirectLinkField;
            return self();
        }

        public Builder redirectUrlContainer(EditedRedirectUrlContainer editedRedirectUrlContainer) {
            this.editedRedirectUrlContainer = editedRedirectUrlContainer;
            return self();
        }



        @Override
        protected void validateAndPrepareChild() {
            if(super.pageRep != null) {
                FieldContainer mainFC = pageRep.getFieldContainers().get(0);
                FieldContainer redirectFC = mainFC.getChildContainers().get(0);

                RegularTextLongDescriptionField cmsErrorDescriptionField = mainFC.getRegularTextLongDescriptionFields().get(0);
                errorDescriptionField = toKioskPageFieldConverter.toStringField(cmsErrorDescriptionField);
                
                ButtonField cmsShouldShowRedirectLinkField = mainFC.getButtonFields().get(0);
                shouldShowRedirectLink = toKioskPageFieldConverter.toBooleanField(cmsShouldShowRedirectLinkField);

                redirectUrlContainer = RedirectUrlContainer.fromFieldContainer(redirectFC, toKioskPageFieldConverter,
                    new RedirectUrlContainerRegFieldSelector(REDIRECT_DISPLAY_TEXT_FIELD_TYPE, REDIRECT_DESCRIPTION_FIELD_TYPE));
            }

            if(errorDescriptionField == null) {
                if(editedErrorDescriptionField == null) {
                    editedErrorDescriptionField = new EditedPageFieldPayload<String>(ERROR_DESCR_FIELD_NAME_DEFAULT, null);
                }

                errorDescriptionField = editedErrorDescriptionField.toKioskPageField(ERROR_DESCR_FIELD_TYPE, true);
            }

            if(shouldShowRedirectLink == null) {
                if(editedShouldShowRedirectLinkField == null) {
                    editedShouldShowRedirectLinkField = new EditedPageFieldPayload<Boolean>(SHOW_REDIRECT_LINK_OPTION_FIELD_NAME_DEFAULT, false);
                }

                shouldShowRedirectLink = editedShouldShowRedirectLinkField.toKioskPageField(SHOW_REDIRECT_LINK_OPTION_FIELD_TYPE, false);
            }

            if(redirectUrlContainer == null) {
                if(editedRedirectUrlContainer == null) {
                    editedRedirectUrlContainer = EditedRedirectUrlContainer.empty(REDIRECT_URL_FIELD_NAME_DEFUALT, REDIRECT_DISPLAY_TEXT_FIELD_NAME_DEFAULT, REDIRECT_DESCRIPTION_FIELD_NAME_DEFAULT);
                }

                redirectUrlContainer = editedRedirectUrlContainer.toRedirectUrlContainer(REDIRECT_URL_FIELD_TYPE, REDIRECT_DISPLAY_TEXT_FIELD_TYPE, REDIRECT_DESCRIPTION_FIELD_TYPE);
            }

            // validate objects
            redirectUrlContainerValidator = new RedirectUrlContainerValidator(REDIRECT_URL_FIELD_TYPE, REDIRECT_DISPLAY_TEXT_FIELD_TYPE, REDIRECT_DESCRIPTION_FIELD_TYPE);

            if(!redirectUrlContainerValidator.isValid(redirectUrlContainer)) {
                throw new RuntimeException("Invalid field type in the RedirectUrlFieldContainer");
            }

            if (!KioskPageFieldTypeValidator.isValid(errorDescriptionField, ERROR_DESCR_FIELD_TYPE)) {
                throw new RuntimeException("The field type for the error description does not match");
            }

            if (!KioskPageFieldTypeValidator.isValid(shouldShowRedirectLink, SHOW_REDIRECT_LINK_OPTION_FIELD_TYPE)) {
                throw new RuntimeException("The field type for the shouldShowRedirectLink does not match");
            }

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected Error404Page buildChild() {


            return new Error404Page(super.pageRep, super.kioskPageTitleField, super.pageMetadata, super.kioskPageType, super.pageName, 
                        errorDescriptionField, shouldShowRedirectLink, redirectUrlContainer);
        }
        
    }
    
}
