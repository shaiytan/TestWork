package shaiytan.ssatrloader;

import java.io.Serializable;

/**
 * Created by Shaiytan on 17.05.2017.
 */

public class RepoRecord implements Serializable {
    private long id;
    private String name;
    private String ownerName;
    private String description;
    private String avatar;

    public RepoRecord(long id, String name, String ownerName, String description, String avatar) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getDescription() {
        return description;
    }

}
