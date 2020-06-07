package db.entities;

public abstract class OsmEntity {
    //at this stage it's not really necessary too, i guess
    protected long userId;
    protected String userName;
    protected boolean visible;
    protected int version;
    protected long changelist;
}
