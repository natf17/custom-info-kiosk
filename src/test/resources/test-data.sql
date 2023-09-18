INSERT INTO kiosk_locale(id, abbrev, name) VALUES(1, 'SP', 'Spanish') ON CONFLICT (id) DO NOTHING;
INSERT INTO kiosk_locale(id, abbrev, name) VALUES(2, 'EN', 'English') ON CONFLICT (id) DO NOTHING;

--INSERT INTO rich_text_long_description_field(field_name, field_value, page_container) VALUES();