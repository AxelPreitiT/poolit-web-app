package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.GET)
//    public ModelAndView showUploadForm() {
//        ModelAndView mav = new ModelAndView("/image-tester/uploadForm");
//        return mav;
//    }
//
//    //TODO: cambiar a image/upload si se usa
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
//        byte[] data = file.getBytes();
//        Image image=imageService.createImage(data);
//        ModelAndView modelAndView = new ModelAndView("/image-tester/imageDetails");
//        modelAndView.addObject("image", image);
//        return modelAndView;
//    }

    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getImage(@PathVariable("imageId") final int imageId) {
        return imageService.findById(imageId).orElseThrow(ImageNotFoundException::new).getData();
    }

}
