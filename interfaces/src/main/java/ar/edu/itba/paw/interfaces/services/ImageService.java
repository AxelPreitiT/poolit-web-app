package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.Image;

import java.io.InputStream;
import java.util.Optional;

public interface ImageService {

    Image createImage(byte[] data);

    Optional<Image> findById(long id);

    void deleteImage(final long imageId) throws ImageNotFoundException;

    Image getImageOrDefault(long imageId, final Image.Size size, InputStream defaultImageInputStream) throws ImageNotFoundException;

}

