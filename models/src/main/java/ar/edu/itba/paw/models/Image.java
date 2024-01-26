package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="images_image_id_seq" )
    @SequenceGenerator(sequenceName = "images_image_id_seq" , name = "images_image_id_seq", allocationSize = 1)
    @Column(name = "image_id")
    private Long imageId;

    public void setData(byte[] data) {
        this.data = data;
    }

    @Column(name = "bytea")
    private byte[] data;

    protected Image() {

    }

    public Image(Long id, byte[] data) {
        this.imageId = id;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("Image { imageId: %d }",
                imageId);
    }

    public Long getImageId() {
        return imageId;
    }

    public byte[] getData() {
        return data;
    }

    public Image(byte[] data) {
        this.data = data;
    }

    public enum Size{
        SMALL(300,300),
        MEDIUM(700,700),
        LARGE(1400,1400),
        FULL(0,0);

        private final int  width;
        private final int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}

