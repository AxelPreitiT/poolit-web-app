package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageService {

    Image createImage(byte[] data);

    Optional<Image> findById(long id);

}

