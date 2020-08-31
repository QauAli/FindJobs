package Models;

import java.io.File;

public class UserImage {
    String id;
    File image;

    public UserImage(String id, File image) {
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public File getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
