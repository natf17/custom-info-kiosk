package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.validator.ImageContainerValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.KioskPageFieldTypeValidator;

/*
 * Represents an About Page with the following:
 *  - rich description
 *  - feature image (image container)
 */
public class AboutPage extends KioskPage {

    public static final String FEATURE_IMAGE_FIELD_TYPE = "FeatImg";
    protected static final String FEATURE_IMAGE_FIELD_NAME_DEFAULT = "FeatImg";
    public static final String IMAGE_ALTERNATIVE_TEXT_FIELD_TYPE = "alternativeText";
    protected static final String IMAGE_ALTERNATIVE_TEXT_FIELD_NAME_DEFAULT = "alternativeText";
    public static final String RICH_DESCRIPTION_TEXT_FIELD_TYPE = "RichDescription";
    protected static final String RICH_DESCRIPTION_TEXT_FIELD_NAME_DEFAULT = "RichDescription";

    // for page rep
    protected static final String IMAGE_CONTAINER_NAME = "featureImageFC";
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.ABOUT;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    protected static final String PAGE_NAME_DEFAULT = "about_page";

    private ImageContainer imageContainer;
    private KioskPageField<String> richDescription;


    private AboutPage(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, 
                String pageName, ImageContainer imageContainer, KioskPageField<String> richDescriptionField) {
        super(pageRep, pageTitleField, pageMetadata, kioskPageType, pageName);
        this.imageContainer = imageContainer;
        this.richDescription = richDescriptionField;
    }

    public ImageContainer getImageContainer() {
        return this.imageContainer;
    }

    public KioskPageField<String> getRichDescrField() {
        return this.richDescription;
    }


    @Override
    protected Page buildPageRep(Page.Builder pageRepBuilder) {
        FieldContainer featureImageFC = this.imageContainer.toFieldContainer(IMAGE_CONTAINER_NAME, cmsConverter);

        FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addRichTextLongDescriptionField(cmsConverter.toRichTextLongDescr(this.richDescription))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .addChildContainer(featureImageFC)
                                                            .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);

        pageRepBuilder.fieldContainers(fieldContainers);

        return pageRepBuilder.build();

    }




    public static class Builder extends KioskPage.Builder<Builder, AboutPage> {
        private ImageContainerValidator imageContainerValidator;
        private ImageContainer imageContainer;
        private EditedImageContainer editedImageContainer;

        private KioskPageField<String> richDescriptionField;
        private EditedPageFieldPayload<String> editedRichDescriptionField;

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

        public Builder imageContainer(EditedImageContainer editedImageContainer) {
            this.editedImageContainer = editedImageContainer;
            return self();
        }

        public Builder richDescriptionField(EditedPageFieldPayload<String> richDescriptionField) {
            this.editedRichDescriptionField = richDescriptionField;
            return self();
        }

        @Override
        protected void validateAndPrepareChild() {

            if(super.pageRep != null) {
                // process pageRep, overriding everything else
                FieldContainer mainFC = pageRep.getFieldContainers().get(0);
                FieldContainer imageFC = mainFC.getChildContainers().get(0);

                imageContainer = ImageContainer.fromFieldContainer(imageFC, super.toKioskPageFieldConverter);

                RichTextLongDescriptionField richTextField = mainFC.getRichTextLongDescriptionFields().get(0);
                richDescriptionField = super.toKioskPageFieldConverter.toStringField(richTextField);

            }

            if(imageContainer == null) {
                if(editedImageContainer == null) {
                    editedImageContainer = EditedImageContainer.empty(FEATURE_IMAGE_FIELD_NAME_DEFAULT, IMAGE_ALTERNATIVE_TEXT_FIELD_NAME_DEFAULT);
                }
                imageContainer = editedImageContainer.toImageContainer(FEATURE_IMAGE_FIELD_TYPE, IMAGE_ALTERNATIVE_TEXT_FIELD_TYPE);
            }

            if(richDescriptionField == null) {
                if(editedRichDescriptionField == null) {
                    editedRichDescriptionField = new EditedPageFieldPayload<String>(RICH_DESCRIPTION_TEXT_FIELD_NAME_DEFAULT, null);
                }

                richDescriptionField = editedRichDescriptionField.toKioskPageField(RICH_DESCRIPTION_TEXT_FIELD_TYPE, true);
            }

            // validate objects
            imageContainerValidator = new ImageContainerValidator(FEATURE_IMAGE_FIELD_TYPE, IMAGE_ALTERNATIVE_TEXT_FIELD_TYPE);
            
            if(!imageContainerValidator.isValid(imageContainer)) {
                throw new RuntimeException("Invalid field type fin the ImageContainer");
            }

            if (!KioskPageFieldTypeValidator.isValid(richDescriptionField, RICH_DESCRIPTION_TEXT_FIELD_TYPE)) {
                throw new RuntimeException("RichTextLongDescriptionField fieldType does not match");
            }

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected AboutPage buildChild() {

            return new AboutPage(super.pageRep, super.kioskPageTitleField, super.pageMetadata, super.kioskPageType, super.pageName, imageContainer, richDescriptionField);
        }
    }

}
