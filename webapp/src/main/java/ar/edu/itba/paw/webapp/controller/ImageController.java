package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getImage(@PathVariable("imageId") final int imageId) throws ImageNotFoundException {
        LOGGER.debug("GET Request to /image/{}", imageId);
        final Image image = imageService.findById(imageId).orElseThrow(ImageNotFoundException::new);
        return image.getData();
    }

}
