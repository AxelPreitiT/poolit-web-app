package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Image;

import java.util.Optional;

public interface ImageDao {

    Image create(byte[] data);

    Optional<Image> findById(long imageId);

    void replaceImage(long id, byte[] data);

}

