package ar.edu.itba.paw.models;

public class Image {
    private final long imageId;
    private final byte[] data;

    public Image(long id, byte[] data) {
        this.imageId = id;
        this.data = data;
    }

    public Long getImageId() {
        return imageId;
    }

    public byte[] getData() {
        return data;
    }

}

