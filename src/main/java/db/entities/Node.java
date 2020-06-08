package db.entities;

import java.util.List;

public class Node extends OsmEntity{
    private long id;
    private float lon;
    private float lat;
    private String username;
    private long uid;
    private boolean visible;
    private int version;
    private long changelist;
    private List<Tag> tags;
}
