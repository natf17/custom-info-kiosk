CREATE TABLE IF NOT EXISTS kiosk_locale (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, 
    abbrev VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL
);

--Schema for PAGES
CREATE TABLE IF NOT EXISTS page (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    page_type VARCHAR(50) NOT NULL,
    page_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS page_internals (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    locale BIGINT REFERENCES kiosk_locale(id) NOT NULL,
    created_on DATE NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    page_status VARCHAR(50) NOT NULL,
    page BIGINT NOT NULL REFERENCES page ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS page_title_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value VARCHAR(100),
    page BIGINT REFERENCES page ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS field_container (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_container_name VARCHAR(100) NOT NULL,
    has_nested_container BOOLEAN NOT NULL,
    has_container_parent BOOLEAN NOT NULL,
    parent BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS button_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value BOOLEAN,
    field_container BIGINT NOT NULL REFERENCES field_container ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS image_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_container BIGINT NOT NULL REFERENCES field_container ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS image (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    location VARCHAR(500),
    width INTEGER,
    height INTEGER,
    image_field BIGINT NOT NULL REFERENCES image_field ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS regular_text_long_description_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value VARCHAR(800),
    field_container BIGINT NOT NULL REFERENCES field_container ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rich_text_long_description_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value VARCHAR(800),
    field_container BIGINT NOT NULL REFERENCES field_container ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS url_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value VARCHAR(500),
    field_container BIGINT NOT NULL REFERENCES field_container ON DELETE CASCADE
);


--Schema for COLLECTION
CREATE TABLE IF NOT EXISTS simple_collection_type_impl (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS collection_internals (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    locale BIGINT REFERENCES kiosk_locale(id) NOT NULL,
    created_on DATE NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    page_status VARCHAR(50) NOT NULL,
    simple_collection_type BIGINT NOT NULL REFERENCES simple_collection_type_impl ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS collection_name_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value VARCHAR(100),
    simple_collection_type BIGINT REFERENCES simple_collection_type_impl ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS text_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value VARCHAR(500),
    simple_collection_type BIGINT NOT NULL REFERENCES simple_collection_type_impl ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS numeric_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value BIGINT,
    simple_collection_type BIGINT NOT NULL REFERENCES simple_collection_type_impl ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS boolean_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value BOOLEAN,
    simple_collection_type BIGINT NOT NULL REFERENCES simple_collection_type_impl ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS image_field_coll (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    simple_collection_type BIGINT NOT NULL REFERENCES simple_collection_type_impl ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS image_coll (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    location VARCHAR(500),
    width INTEGER,
    height INTEGER,
    image_field BIGINT NOT NULL REFERENCES image_field_coll ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS linked_coll_field (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    field_value BIGINT REFERENCES simple_collection_type_impl ON DELETE SET NULL,
    simple_collection_type BIGINT NOT NULL REFERENCES simple_collection_type_impl ON DELETE CASCADE

);