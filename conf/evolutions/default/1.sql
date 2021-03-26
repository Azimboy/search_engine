# --- !Ups

CREATE EXTENSION pg_trgm;

CREATE TABLE activities (
 id SERIAL PRIMARY KEY,
 text VARCHAR NOT NULL
);

CREATE INDEX text_trgm ON activities USING GIST (text gist_trgm_ops);

# --- !Downs

DROP TABLE activities;

DROP EXTENSION pg_trgm;
