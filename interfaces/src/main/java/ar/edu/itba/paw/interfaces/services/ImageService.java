package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public interface ImageService {

    Image createImage(byte[] data);

    Optional<Image> findById(long id);

    byte[] getImageBytea(long imageId) throws ImageNotFoundException;

    Image updateImage(final byte[] content, final long imageId) throws ImageNotFoundException;

//    TODO: delete
    byte[] getByteaCheck(long imageId) throws IOException, ImageNotFoundException;



}

