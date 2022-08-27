package dto;

public class NewPostDTO {
    private String picture;

    private String pictureName;
    private String text;

    public NewPostDTO(String picture, String text, String pictureName) {
        this.picture = picture;
        this.pictureName = pictureName;
        this.text = text;
    }
    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
