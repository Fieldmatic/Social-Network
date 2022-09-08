package dto;

public class ProfilePictureDTO {
    private String picture;

    private String pictureName;

    public ProfilePictureDTO(String picture, String pictureName) {
        this.picture = picture;
        this.pictureName = pictureName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
