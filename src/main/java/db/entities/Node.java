package db.entities;

import java.util.List;

public class Node extends OsmEntity{
    private long id;
    private float lat;
    private float lon;
    private List<Tag> tags;
}
