package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.selector.FieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.validator.KioskPageFieldTypeValidator;

/*
 * Represents a the Home Page, which containts the following:
 *  - tap to continue prompt text
 *  - welcome text
 *  - boolean: should select from available locales
 */
public class HomePage extends KioskPage {
    public static final String TAP_TO_CONTINUE_TEXT_TYPE = "TapToContinuePrompt";
    public static final String TAP_TO_CONTINUE_TEXT_FIELD_NAME_DEFAULT = "TapToContinuePrompt";

    public static final String WELCOME_TEXT_TYPE = "WelcomeText";
    public static final String WELCOME_TEXT_FIELD_NAME_DEFAULT = "WelcomeText";

    public static final String SHOW_SELECT_FROM_AVAILABLE_LOCALES_TYPE = "ShowSelectFromAvailableLocales";
    public static final String SHOW_SELECT_FROM_AVAILABLE_LOCALES_FIELD_NAME_DEFAULT = "ShowSelectFromAvailableLocales";

    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.HOME;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    private static final String PAGE_NAME_DEFAULT = "Home Page";

    private KioskPageField<String> tapToContinueText;
    private KioskPageField<String> welcomeText;
    private KioskPageField<Boolean> shouldShowSelectFromAvailableLocales;

    private HomePage(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, 
                String pageName, KioskPageField<String> tapToContinueText, KioskPageField<String> welcomeText, KioskPageField<Boolean> shouldShowSelectFromAvailableLocales) {
        super(pageRep, pageTitleField, pageMetadata, kioskPageType, pageName);
        this.tapToContinueText = tapToContinueText;
        this.welcomeText = welcomeText;
        this.shouldShowSelectFromAvailableLocales = shouldShowSelectFromAvailableLocales;
    }

    public KioskPageField<String> getTapToContinueText() {
        return this.tapToContinueText;
    }

    public KioskPageField<String> getWelcomeText() {
        return this.welcomeText;
    }

    public KioskPageField<Boolean> shouldShowSelectFromAvailableLocales() {
        return this.shouldShowSelectFromAvailableLocales;
    }

    @Override
    protected Page buildPageRep(Page.Builder pageRepBuilder) {

        FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addRegularTextLongDescriptionField(cmsConverter.toRegTextLongDescr(this.tapToContinueText))
                                                            .addRegularTextLongDescriptionField(cmsConverter.toRegTextLongDescr(this.welcomeText))
                                                            .addButtonField(cmsConverter.toButtonField(this.shouldShowSelectFromAvailableLocales))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);

        pageRepBuilder.fieldContainers(fieldContainers);

        return pageRepBuilder.build();
    }

    public static class Builder extends KioskPage.Builder<Builder, HomePage> {
        private KioskPageField<String> tapToContinueTextField;
        private EditedPageFieldPayload<String> editedTapToContinueTextField;

        private KioskPageField<String> welcomeTextField;
        private EditedPageFieldPayload<String> editedWelcomeTextField;

        private KioskPageField<Boolean> shouldShowSelectFromAvailableLocalesField;
        private EditedPageFieldPayload<Boolean> editedShouldShowSelectFromAvailableLocalesField;
        

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

        public Builder tapToContinueTextField(EditedPageFieldPayload<String> editedTapToContinueTextField) {
            this.editedTapToContinueTextField = editedTapToContinueTextField;
            return self();
        }

        public Builder welcomeTextField(EditedPageFieldPayload<String> editedWelcomeTextField) {
            this.editedWelcomeTextField = editedWelcomeTextField;
            return self();
        }

        public Builder showSelectFromAvailableLocalesField(EditedPageFieldPayload<Boolean> editedShouldShowSelectFromAvailableLocalesField) {
            this.editedShouldShowSelectFromAvailableLocalesField = editedShouldShowSelectFromAvailableLocalesField;
            return self();
        }


        @Override
        protected void validateAndPrepareChild() {
            FieldSelector fieldSelector;
            
            if(super.pageRep != null) {
                FieldContainer mainFC = pageRep.getFieldContainers().get(0);
                fieldSelector = new FieldSelector(mainFC);

                RegularTextLongDescriptionField cmsTapToContinueField = fieldSelector.selectRegTextLongDescrField(TAP_TO_CONTINUE_TEXT_TYPE);
                tapToContinueTextField = toKioskPageFieldConverter.toStringField(cmsTapToContinueField);

                RegularTextLongDescriptionField cmsWelcomeTextField = fieldSelector.selectRegTextLongDescrField(WELCOME_TEXT_TYPE);
                welcomeTextField = toKioskPageFieldConverter.toStringField(cmsWelcomeTextField);
                
                ButtonField cmsShouldShowSelectFromAvailableLocalesField = mainFC.getButtonFields().get(0);
                shouldShowSelectFromAvailableLocalesField = toKioskPageFieldConverter.toBooleanField(cmsShouldShowSelectFromAvailableLocalesField);

            }

            if(tapToContinueTextField == null) {
                if(editedTapToContinueTextField == null) {
                    editedTapToContinueTextField = new EditedPageFieldPayload<String>(TAP_TO_CONTINUE_TEXT_FIELD_NAME_DEFAULT, null);
                }

                tapToContinueTextField = editedTapToContinueTextField.toKioskPageField(TAP_TO_CONTINUE_TEXT_TYPE, false);
            }

            if(welcomeTextField == null) {
                if(editedWelcomeTextField == null) {
                    editedWelcomeTextField = new EditedPageFieldPayload<String>(WELCOME_TEXT_FIELD_NAME_DEFAULT, null);
                }

                welcomeTextField = editedWelcomeTextField.toKioskPageField(WELCOME_TEXT_TYPE, true);
            }

            if(shouldShowSelectFromAvailableLocalesField == null) {
                if(editedShouldShowSelectFromAvailableLocalesField == null) {
                    editedShouldShowSelectFromAvailableLocalesField = new EditedPageFieldPayload<Boolean>(SHOW_SELECT_FROM_AVAILABLE_LOCALES_FIELD_NAME_DEFAULT, false);
                }

                shouldShowSelectFromAvailableLocalesField = editedShouldShowSelectFromAvailableLocalesField.toKioskPageField(SHOW_SELECT_FROM_AVAILABLE_LOCALES_TYPE, false);
            }

            // validate objects
            if (!KioskPageFieldTypeValidator.isValid(tapToContinueTextField, TAP_TO_CONTINUE_TEXT_TYPE)) {
                throw new RuntimeException("The field type for the tap to continue text field does not match");
            }

            if (!KioskPageFieldTypeValidator.isValid(welcomeTextField, WELCOME_TEXT_TYPE)) {
                throw new RuntimeException("The field type for the welcome text field does not match");
            }

            if (!KioskPageFieldTypeValidator.isValid(shouldShowSelectFromAvailableLocalesField, SHOW_SELECT_FROM_AVAILABLE_LOCALES_TYPE)) {
                throw new RuntimeException("The field type for the showSelectFromAvailableLocales field does not match");
            }

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected HomePage buildChild() {


            return new HomePage(super.pageRep, super.kioskPageTitleField, super.pageMetadata, super.kioskPageType, super.pageName, 
                        tapToContinueTextField, welcomeTextField, shouldShowSelectFromAvailableLocalesField);
        }
        
    }

}
