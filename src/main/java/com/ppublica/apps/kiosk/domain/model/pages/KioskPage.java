package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.validator.KioskPageFieldTypeValidator;

abstract class KioskPage {
    public static final String PAGE_TITLE_FIELD_TYPE = "PageTitle";
    public static final String PAGE_TITLE_FIELD_NAME_DEFAULT = "PageTitle";

    protected ToCmsPageFieldConverter cmsConverter = new ToCmsPageFieldConverter();

    private KioskPageMetadata metadata;
    private KioskPageField<String> pageTitleField;
    private KioskPageType kioskPageType;
    private String pageName;

    private Page pageRep;

    protected KioskPage(KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, String pageName) {
        this(null, pageTitleField, pageMetadata, kioskPageType, pageName);
    }

    protected KioskPage(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, String pageName) {
        this.pageRep = pageRep;
        this.pageTitleField = pageTitleField;
        this.metadata = pageMetadata;
        this.kioskPageType = kioskPageType;
        this.pageName = pageName;
    }

    // getters

    public KioskPageType getPageType() {
        return this.kioskPageType;
    }

    public KioskPageField<String> getPageTitleField() {
        return this.pageTitleField;
    }

    public KioskPageMetadata getKioskPageMetadata() {
        return this.metadata;
    }

    public Page getPageRep() {

        Page.Builder pageRepBuilder;

        if(this.pageRep == null) {
            pageRepBuilder = new Page.Builder().pageInternals(this.metadata.getPageInternals())
                                .titleField(cmsConverter.toPageTitleField(pageTitleField))
                                .pageType(kioskPageType.toString())
                                .pageName(pageName);
            
            this.pageRep = buildPageRep(pageRepBuilder);
        }

        return this.pageRep;
    }

    protected abstract Page buildPageRep(Page.Builder pageRepBuilder);

    /*
     * Processes PageInternals and the PageTitleField from pageRep, enforcing its naming scheme (field types)
     */
    static <B extends Builder<B,M>, M extends KioskPage> Builder<B, M> processFromPageRep(Page pageRep, Builder<B, M> builder) {
        ToKioskPageFieldConverter converter = new ToKioskPageFieldConverter();        
        PageTitleField pageTitleField = pageRep.getPageTitleField();
        KioskPageField<String> kioskPageTitleField = converter.toStringField(pageTitleField);

        KioskPageMetadata pageMetadata = KioskPageMetadata.fromPageInternals(pageRep.getPageInternals());

        builder.kioskPageMetadata(pageMetadata)
               .kioskPageTitleField(kioskPageTitleField);

        return builder;

    }



    // builder

    static abstract class Builder<B extends Builder<B, M>, M extends KioskPage> {
        protected Page pageRep;
        protected ToKioskPageFieldConverter toKioskPageFieldConverter = new ToKioskPageFieldConverter();

        protected KioskPageField<String> kioskPageTitleField;
        private EditedPageFieldPayload<String> editedPageTitleField;
        protected String pageTitle;

        protected KioskPageMetadata pageMetadata;
        private PageStatus pageStatus;
        private LocalDate createdOn;
        private LocalDateTime lastModified;
        private Long kioskLocaleId;

        protected KioskPageType kioskPageType;

        protected String pageName;

        protected Builder(KioskPageType kioskPageType, Page pageRep) {
            this.kioskPageType = kioskPageType;
            this.pageRep = pageRep;
        }

        protected Builder(KioskPageType kioskPageType) {
            this(kioskPageType, null);
        }

        public B pageName(String pageName) {
            this.pageName = pageName;
            return self();
        }

        public B kioskPageTitleField(KioskPageField<String> kioskPageTitleField) {
            this.kioskPageTitleField = kioskPageTitleField;
            return self();
        }


        public B editedPageTitleField(EditedPageFieldPayload<String> pageTitleField) {
            this.editedPageTitleField = pageTitleField;
            return self();
        }

        public B pageTitle(String title) {
            this.pageTitle = title;
            return self();
        }


        public B kioskPageMetadata(KioskPageMetadata pageMetadata) {
            this.pageMetadata = pageMetadata;
            return self();
        }

        public B pageStatus(PageStatus pageStatus) {
            this.pageStatus = pageStatus;
            return self();
        }

        public B createdOn(LocalDate createdOn) {
            this.createdOn = createdOn;
            return self();
        }

        public B lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return self();
        }

        public B withLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return self();
        }



        public M build() {
            validateAndPrepare();
            
            

            
            return buildChild();
        }

        /*
         * Page components can be either set directly, or in "pieces".
         * If the component is set directly, it overrides the "pieces".
         */
        private void validateAndPrepare() {

            if(this.kioskPageType == null) {
                throw new RuntimeException("The page type is required");
            }

            if(pageRep != null) {
                // process pageRep, overriding everything else

                if(!this.kioskPageType.toString().equals(pageRep.getPageType())) {
                    throw new RuntimeException("The Page provided is of the incorrect type");
                }

                PageTitleField pageTitleField = pageRep.getPageTitleField();
                KioskPageField<String> kioskPageTitleField = toKioskPageFieldConverter.toStringField(pageTitleField);

                KioskPageMetadata pageMetadata = KioskPageMetadata.fromPageInternals(pageRep.getPageInternals());

                this.kioskPageMetadata(pageMetadata)
                    .kioskPageTitleField(kioskPageTitleField)
                    .pageName(pageRep.getPageName());

            }

            if(kioskPageTitleField == null) {
                // use the EditedPageFieldPayload
                if(editedPageTitleField == null) {
                    editedPageTitleField = new EditedPageFieldPayload<String> (PAGE_TITLE_FIELD_NAME_DEFAULT, pageTitle);
                }
                kioskPageTitleField = editedPageTitleField.toKioskPageField(PAGE_TITLE_FIELD_TYPE, true);
            }


            // validate field created
            if (!KioskPageFieldTypeValidator.isValid(kioskPageTitleField, PAGE_TITLE_FIELD_TYPE)) {
                throw new RuntimeException("PageTitleField fieldType does not match");
            }



            if(pageMetadata == null) {
                if(this.pageStatus == null) {
                    throw new RuntimeException("PageStatus is required");
                }

                if(this.createdOn == null) {
                    throw new RuntimeException("CreatedOn is required");
                }

                if(this.lastModified == null) {
                    throw new RuntimeException("LastModified is required");
                }

                LocalDateTime createdOnTime = this.createdOn.atStartOfDay();
                if(createdOnTime.isAfter(this.lastModified)) {
                    throw new RuntimeException("CreatedOn cannot be after lastModified");
                }

                if(this.kioskLocaleId == null) {
                    throw new RuntimeException("Locale id is required");
                }

                pageMetadata = new KioskPageMetadata(kioskLocaleId, pageStatus, createdOn, lastModified);

            }


            if(this.pageName == null) {
                throw new RuntimeException("The page name is required");
            }

            
            validateAndPrepareChild();

        }

        protected abstract void validateAndPrepareChild();

        protected abstract M buildChild();

        protected abstract B self();        

    }

}
