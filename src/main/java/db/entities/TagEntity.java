package db.entities;

import osm.jaxb.generated.Tag;

public class TagEntity {
    private long node_id;
    private String key;
    private String value;

    public TagEntity(Tag tag, long node_id) {
        this.node_id = node_id;
        this.key = tag.getK();
        this.value = tag.getV();
    }

    public TagEntity(long node_id, String key, String value) {
        this.node_id = node_id;
        this.key = key;
        this.value = value;
    }

    public long getNode_id() {
        return node_id;
    }

    public void setNode_id(long node_id) {
        this.node_id = node_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
