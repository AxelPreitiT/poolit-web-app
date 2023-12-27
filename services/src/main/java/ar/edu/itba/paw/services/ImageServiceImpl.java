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

//    @Value("classpath:images/profile.jpeg")
//    private Resource defaultImg;

    @Autowired
    public ImageServiceImpl(final ImageDao imageDao){
        this.imageDao = imageDao;
    }

    @Transactional
    @Override
    public Image createImage(byte[] data) {
        if(data.length==0){
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
    public byte[] getImageByteaOrDefault(long imageId, InputStream defaultImageInputStream) throws ImageNotFoundException{
        Image img = findById(imageId).orElseThrow(ImageNotFoundException::new);
        try{
            if(img.getData() == null || img.getData().length == 0){
                //TODO: revisar esto, dice que no esta bien resrevar espacio con available
                byte[] input = new byte[defaultImageInputStream.available()];
                for (int i = 0; i < input.length; i++) {
                    int byteRead = defaultImageInputStream.read();
                    if (byteRead == -1) break;
                    input[i] = (byte)byteRead;
                }
                return input;
            }
        }catch (IOException ex){
            throw new ImageNotFoundException();
        }
        return img.getData();
    }

    //TODO: que mande el default el servicio que lo llama, asi tenemos la default del auto y de perfil
//    @Transactional
//    @Override
//    public byte[] getImageBytea(long imageId) throws ImageNotFoundException{
//        Image img = findById(imageId).orElseThrow(ImageNotFoundException::new);
//        try{
//            if(img.getData() == null){
//                InputStream inputStream = defaultImg.getInputStream();
//                byte[] input = new byte[defaultImg.getInputStream().available()];
//                for (int i = 0; i < input.length; i++) {
//                    int byteRead = inputStream.read();
//                    if (byteRead == -1) break;
//                    input[i] = (byte)byteRead;
//                }
//                return input;
//            }
//        }catch (IOException ex){
//            throw new ImageNotFoundException();
//        }
//        return img.getData();
//    }






    @Transactional
    @Override
    public Image updateImage(final byte[] content, final long imageId) throws ImageNotFoundException{
        final Image image = findById(imageId).orElseThrow(ImageNotFoundException::new);
        return imageDao.update(image,content);
    }


// TODO: delete
//    @Transactional
//    @Override
//    public byte[] getByteaCheck(long imageId) throws IOException, ImageNotFoundException {
//        Image img = findById(imageId).orElseThrow(ImageNotFoundException::new);
//        if(img.getData() == null){
//            InputStream inputStream = defaultImg.getInputStream();
//            byte[] input = new byte[defaultImg.getInputStream().available()];
//            for (int i = 0; i < input.length; i++) {
//                int byteRead = inputStream.read();
//                if (byteRead == -1) break;
//                input[i] = (byte)byteRead;
//            }
//            return input;
//        }
//        return img.getData();
//    }

}
