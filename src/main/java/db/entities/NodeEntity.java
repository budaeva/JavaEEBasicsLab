package db.entities;

import osm.jaxb.generated.Node;
import osm.jaxb.generated.Tag;

import java.util.ArrayList;
import java.util.List;

public class NodeEntity {
    private long id;
    private Double lon;
    private Double lat;
    private String user;
    private List<TagEntity> tags;

    public NodeEntity(Node node) {
        this.id = node.getId().longValue();
        this.lon = node.getLon();
        this.lat = node.getLat();
        this.user = node.getUser();
        this.tags = new ArrayList<>();
        for (Tag tag : node.getTag())
            tags.add(new TagEntity(tag, this.id));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }
}
