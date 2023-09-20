package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;

/*
 * Represents an About Page with the following:
 *  - page title
 *  - rich description
 *  - feature image
 */
public class AboutPage extends KioskPage {

    public static final String FEATURE_IMAGE_FIELD_NAME = "FeatImg";
    public static final String IMAGE_ALTERNATIVE_TEXT_FIELD_NAME = "alternativeText";
    public static final String RICH_DESCRIPTION_TEXT_FIELD_NAME = "RichDescription";

    protected static final String IMAGE_CONTAINER_NAME = "featureImageFC";
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.ABOUT;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    private static final String PAGE_NAME = "about_page";

    private Page aboutPageRep;
    private FieldContainer mainFc;
    private FieldContainer featureImageFc;


    private AboutPage(Page aboutPageRep) {
        this.aboutPageRep = aboutPageRep;

        this.mainFc = aboutPageRep.getFieldContainers().get(0);
        this.featureImageFc = mainFc.getChildContainers().get(0); 

    }

    public String getImageAltText() {
        return featureImageFc.getRegularTextLongDescriptionFields().get(0).getFieldValue();

    }

    public Image getImage() {
        return featureImageFc.getImageFields().get(0).getFieldValue();
    }

    public String getRichDescription() {
        return mainFc.getRichTextLongDescriptionFields().get(0).getFieldValue();
    }

    @Override
    protected Page getPageRep() {
        return this.aboutPageRep;
    }


    public static AboutPage fromPage(Page aboutPageRep) {
        // TO ADD: a validator

        return new AboutPage(aboutPageRep);
    }

    public static class Builder extends KioskPage.Builder<Builder, AboutPage> {
        public Builder() {
            super(KIOSK_PAGE_TYPE, PAGE_NAME);
        }

        private Image featureImage;
        private String altText;
        private String richDescription;


        public Builder featureImage(Image image) {
            this.featureImage = image;

            return self();
        }

        public Builder featureImageAltText(String text) {
            this.altText = text;

            return self();
        }

        public Builder richDescription(String description) {
            this.richDescription = description;

            return self();
        }

        @Override
        protected void validateAndPrepareChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected AboutPage buildChild(Page.Builder pageRepBuilder) {

            FieldContainer featureImageFC = new FieldContainer.Builder()
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(IMAGE_ALTERNATIVE_TEXT_FIELD_NAME, altText))
                                                            .addImageField(new ImageField(FEATURE_IMAGE_FIELD_NAME, featureImage))
                                                            .fieldContainerName(IMAGE_CONTAINER_NAME)
                                                            .build();

            FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addRichTextLongDescriptionField(new RichTextLongDescriptionField(RICH_DESCRIPTION_TEXT_FIELD_NAME, richDescription))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .addChildContainer(featureImageFC)
                                                            .build();

            List<FieldContainer> fieldContainers = new ArrayList<>();
            fieldContainers.add(mainFC);

            pageRepBuilder.fieldContainers(fieldContainers);

            return new AboutPage(pageRepBuilder.build());
        }
    }

}
