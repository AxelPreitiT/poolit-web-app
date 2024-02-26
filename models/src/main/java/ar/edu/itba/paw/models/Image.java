package ar.edu.itba.paw.models;

import javax.persistence.*;

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

    @Column(name = "small")
    @Basic(fetch = FetchType.LAZY)
    private byte[] small;

    @Column(name = "medium")
    @Basic(fetch = FetchType.LAZY)
    private byte[] medium;

    @Column(name = "large")
    @Basic(fetch = FetchType.LAZY)
    private byte[] large;

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

    public void setData(final byte[] data,final Size size){
        switch (size){
            case LARGE:{
                this.large = data;
                return;
            }
            case MEDIUM:{
                this.medium = data;
                return;
            }
            case SMALL:{
                this.small = data;
                return;
            }
            default:{
                this.data = data;
            }
        }
    }

    public byte[] getData(final Size size){
        switch (size){
            case LARGE: return large;
            case MEDIUM: return medium;
            case SMALL: return small;
            default:return data;
        }
    }

    public enum Size{
        SMALL(300,200),
        MEDIUM(750,500),
        LARGE(1920,1080),
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

