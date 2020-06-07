DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS nodes;

CREATE TABLE osm_entities(
    id BIGINT NOT NULL PRIMARY KEY,
    username VARCHAR(256) NOT NULL,
    uid BIGINT NOT NULL,
    visible BOOLEAN NOT NULL,
    version INT NOT NULL,
    changelist BIGINT NOT NULL
)

CREATE TABLE nodes(
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL
) INHERITS (osm_entities);   //вставит и удалит и в osm_entities

CREATE TABLE tags(
    osm_entity_id BIGINT NOT NULL REFERENCES osm_entities(id),
    key VARCHAR(256) NOT NULL,
    value VARCHAR(256) NOT NULL,
    PRIMARY KEY (osm_entity_id, key)
);