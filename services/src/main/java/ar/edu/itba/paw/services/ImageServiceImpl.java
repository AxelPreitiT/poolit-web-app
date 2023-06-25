package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageDao imageDao;

    @Value("classpath:images/profile.jpeg")
    private Resource defaultImg;

    @Autowired
    public ImageServiceImpl(final ImageDao imageDao){
        this.imageDao = imageDao;
    }

    @Transactional
    @Override
    public Image createImage(byte[] data) {
        if(data.length<=0){
            return imageDao.create(null);
        }
        return imageDao.create(data);
    }

    @Transactional
    @Override
    public Optional<Image> findById(long imageId){
        return imageDao.findById(imageId);
    }

    @Transactional
    @Override
    public void replaceImage(long id, byte[] data) { imageDao.replaceImage(id,data); }

    @Transactional
    @Override
    public byte[] getByteaCheck(long imageId) throws IOException, ImageNotFoundException {
        Image img = findById(imageId).orElseThrow(ImageNotFoundException::new);
        if(img.getData() == null){
            InputStream inputStream = defaultImg.getInputStream();
            byte[] input = new byte[defaultImg.getInputStream().available()];
            for (int i = 0; i < input.length; i++) {
                int byteRead = inputStream.read();
                if (byteRead == -1) break;
                input[i] = (byte)byteRead;
            }
            return input;
        }
        return img.getData();
    }

}
