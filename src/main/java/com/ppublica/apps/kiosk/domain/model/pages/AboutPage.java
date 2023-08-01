package com.ppublica.apps.kiosk.domain.model.pages;

/*
 * Represents an About Page with the following:
 *  - page title
 *  - rich description
 *  - feature image
 */
public class AboutPage extends KioskPage {
    private static final String FEATURE_IMG_FIELD_NAME = "FeatImg";
    private static final String RICH_DESCR_FIELD_NAME = "RichDescription";

    private ImageField featureImageField;
    private RDescriptionField richDescriptionField;


    public AboutPage(Builder builder) {
        super(builder);

        this.featureImageField = builder.featureImageField;
        this.richDescriptionField = builder.rDescriptionField;

    }

    // getters

    public ImageField getFeatureImageField() {
        return this.featureImageField;
    }

    public RDescriptionField getRichDescriptionField() {
        return this.richDescriptionField;
    }


    // builder

    public static class Builder extends KioskPage.Builder<Builder, AboutPage> {
        private Image featureImage;
        private String altText;
        private ImageField featureImageField;
        private String rDescription;
        private RDescriptionField rDescriptionField;


        public Builder featureImage(Image image) {
            this.featureImageField = null;
            this.featureImage = image;

            return self();
        }

        public Builder featureImageAltText(String text) {
            this.featureImageField = null;
            this.altText = text;

            return self();
        }

        public Builder featureImageField(ImageField featureImageField) {
            this.featureImage = null;
            this.altText = null;
            this.featureImageField = featureImageField;

            return self();
        }

        public Builder richDescription(String description) {
            this.rDescriptionField = null;
            this.rDescription = description;

            return self();
        }

        public Builder richDesriptionField(RDescriptionField rDescriptionField) {
            this.rDescription = null;            
            this.rDescriptionField = rDescriptionField;

            return self();
        }

        protected void validateAndPrepareChild() {
            
            if (this.featureImageField == null) {
                if (this.featureImage == null || this.altText == null) {
                    throw new RuntimeException("Invalid page build!");

                }

                // build image field with supplied image and alt text
                this.featureImageField = new ImageField(FEATURE_IMG_FIELD_NAME, this.featureImage, this.altText);
                
            }

            if (this.rDescriptionField == null) {
                if (this.rDescription == null) {
                    throw new RuntimeException("Invalid page build!");
                }

                this.rDescriptionField = new RDescriptionField(RICH_DESCR_FIELD_NAME, this.rDescription);
                
            }

        }

        protected AboutPage buildChild() {
            return new AboutPage(this);
        }


        @Override
        protected Builder self() {
            return this;
        }

        
    }
    
}
