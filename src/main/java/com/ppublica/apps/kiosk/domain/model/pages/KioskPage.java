package com.ppublica.apps.kiosk.domain.model.pages;

abstract class KioskPage {
    public static final String DEFAULT_TITLE_FIELD_NAME = "PageTitle";
    
    private KioskPageInternals pageInternals;
    private PageTitleField pageTitle;


    public <B extends Builder<B,M>, M extends KioskPage> KioskPage(Builder<B, M> builder) {
        this.pageInternals = builder.pageInternals;
        this.pageTitle = builder.pageTitleField;

    }



    static abstract class Builder<B extends Builder<B, M>, M extends KioskPage> {
        private String pageTitle;
        private PageTitleField pageTitleField;
        private KioskPageInternals pageInternals;
        

        public B pageTitle(String title) {
            this.pageTitleField = null;
            this.pageTitle = title;

            return self();
        }

        public B pageTitleField(PageTitleField pageTitleField) {
            this.pageTitle = null;
            this.pageTitleField = pageTitleField;
            
            return self();
        }

        /*
         * MUST BE CHANGED: KioskPageInternals should not be supplied; it's an internal class
         */

        public B pageInternals(KioskPageInternals pageInternals) {
            this.pageInternals = pageInternals;
            
            return self();
        }

        public M build() {
            validateAndPrepare();
            return buildChild();
        }

        private void validateAndPrepare() {

            if (this.pageTitleField == null) {
                if (this.pageTitle == null) {
                    throw new RuntimeException("Invalid page build!");
                }
                
                this.pageTitleField = new PageTitleField(DEFAULT_TITLE_FIELD_NAME, this.pageTitle);
                

            }

            if (this.pageInternals == null) {
                this.pageInternals = new KioskPageInternals(PageMetadata.newPage());
            }

            validateAndPrepareChild();

        }

        protected abstract void validateAndPrepareChild();

        protected abstract M buildChild();

        protected abstract B self();        

    }

}
