package com.ppublica.apps.kiosk.domain.model.cms.pages;

import java.util.ArrayList;
import java.util.List;

public class FieldContainer {
    private final Long id;
    private final String fieldContainerName;
    private final List<FieldContainer> childContainers;
    private final List<RichTextLongDescriptionField> richTextLongDescriptionFields;
    private final List<RegularTextLongDescriptionField> regularTextLongDescriptionFields;
    private final List<ImageField> imageFields;
    private final List<ButtonField> buttonFields;
    private final List<UrlField> urlFields;

    public FieldContainer(Long id, String fieldContainerName, List<FieldContainer> childContainers, List<RichTextLongDescriptionField> richTextLongDescriptionFields,
                            List<RegularTextLongDescriptionField> regularTextLongDescriptionFields, List<ImageField> imageFields,
                            List<ButtonField> buttonFields, List<UrlField> urlFields) {
        this.id = id;
        this.fieldContainerName = fieldContainerName;
        this.childContainers = childContainers;
        this.richTextLongDescriptionFields = richTextLongDescriptionFields;
        this.regularTextLongDescriptionFields = regularTextLongDescriptionFields;
        this.imageFields = imageFields;
        this.buttonFields = buttonFields;
        this.urlFields = urlFields;
    }

    // getters
    public Long getId() {
        return this.id;
    }

    public String getFieldContainerName() {
        return this.fieldContainerName;
    }

    public List<FieldContainer> getChildContainers() {
        return this.childContainers;
    }

    public List<RichTextLongDescriptionField> getRichTextLongDescriptionFields() {
        return this.richTextLongDescriptionFields;
    }

    public List<RegularTextLongDescriptionField> getRegularTextLongDescriptionFields() {
        return this.regularTextLongDescriptionFields;
    }

    public List<ImageField> getImageFields() {
        return this.imageFields;
    }

    public List<ButtonField> getButtonFields() {
        return this.buttonFields;
    }

    public List<UrlField> getUrlFields() {
        return this.urlFields;
    }

    public boolean hasNestedContainer() {
        return !this.childContainers.isEmpty();
    }

    public static class Builder {
        private Long id;
        private String fieldContainerName;
        private List<FieldContainer> childContainers = new ArrayList<>();
        private List<RichTextLongDescriptionField> richTextLongDescriptionFields = new ArrayList<>();
        private List<RegularTextLongDescriptionField> regularTextLongDescriptionFields = new ArrayList<>();
        private List<ImageField> imageFields = new ArrayList<>();
        private List<ButtonField> buttonFields = new ArrayList<>();
        private List<UrlField> urlFields = new ArrayList<>();

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder fieldContainerName(String fieldContainerName) {
            this.fieldContainerName = fieldContainerName;
            return this;
        }

        public Builder childContainers(List<FieldContainer> childContainers) {
            this.childContainers = childContainers != null ? childContainers : new ArrayList<>();
            return this;
        }

        public Builder addChildContainer(FieldContainer fieldContainer) {
            this.childContainers.add(fieldContainer);
            return this;
        }

        public Builder richTextLongDescriptionFields(List<RichTextLongDescriptionField> richTextLongDescriptionFields) {
            this.richTextLongDescriptionFields = richTextLongDescriptionFields != null ? richTextLongDescriptionFields : new ArrayList<>();
            return this;
        }

        public Builder regularTextLongDescriptionFields(List<RegularTextLongDescriptionField> regularTextLongDescriptionFields) {
            this.regularTextLongDescriptionFields = regularTextLongDescriptionFields != null ? regularTextLongDescriptionFields : new ArrayList<>();
            return this;
        }

        public Builder imageFields(List<ImageField> imageFields) {
            this.imageFields = imageFields = imageFields != null ? imageFields : new ArrayList<>();
            return this;
        }

        public Builder buttonFields(List<ButtonField> buttonFields) {
            this.buttonFields = buttonFields != null ? buttonFields : new ArrayList<>();
            return this;
        }

        public Builder urlFields(List<UrlField> urlFields) {
            this.urlFields = urlFields != null ? urlFields : new ArrayList<>();
            return this;
        }

        public FieldContainer build() {
            if(fieldContainerName == null || fieldContainerName.isEmpty()) {
                throw new RuntimeException("FieldContainerName is required");
            }

            return new FieldContainer(id, fieldContainerName, childContainers, richTextLongDescriptionFields,
                            regularTextLongDescriptionFields, imageFields, buttonFields, urlFields);

        }

    }

}
