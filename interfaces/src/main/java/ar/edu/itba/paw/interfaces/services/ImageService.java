package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public interface ImageService {

    Image createImage(byte[] data);

    Optional<Image> findById(long id);


    byte[] getByteaCheck(long imageId) throws IOException, ImageNotFoundException;

}

