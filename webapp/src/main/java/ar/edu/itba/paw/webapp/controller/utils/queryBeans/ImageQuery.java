package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.models.Image;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class ImageQuery {
    @QueryParam("imageSize")
    @DefaultValue("FULL")
    private Image.Size imageSize;

    public Image.Size getImageSize() {
        return imageSize;
    }

    public void setImageSize(Image.Size imageSize) {
        this.imageSize = imageSize;
    }
}
