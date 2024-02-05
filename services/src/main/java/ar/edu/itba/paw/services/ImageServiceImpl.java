package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageDao imageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

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

    private byte[] resizeImage(final byte[] image, Image.Size imageSize) throws IOException {
        if(image == null || image.length ==0 || imageSize.equals(Image.Size.FULL)){
            return image;
        }
        int width = imageSize.getWidth();
        int height = imageSize.getHeight();
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(image));
        double aspectRatio = (double) original.getWidth() /original.getHeight();
        int newWidth = 0;
        int newHeight = 0;
        //gana la medida más grande
        if(aspectRatio>1){
            newWidth = width;
            //(width/originalWidth)*originalHeight
            newHeight = (int)(width/aspectRatio);
            //newWidth/newHeight = width/((width*originalHeight)/originalWidth) = originalWidth/originalHeight
        }else{
            //(height/originalHeight)*originalWidth
            newWidth = (int)(height*aspectRatio);
            newHeight = height;
            //newWidth/newHeight = ((height*originalWidth)/originalHeight)/height = originalWidth/originalHeight
        }
        BufferedImage res = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
        res.createGraphics().drawImage(original,0,0,newWidth,newHeight,null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(res,"jpg",outputStream);

        return outputStream.toByteArray();
    }

    @Transactional
    @Override
    public Image getImageOrDefault(long imageId, final Image.Size size, InputStream defaultImageInputStream) throws ImageNotFoundException {
        Image img = findById(imageId).orElseThrow(ImageNotFoundException::new);
        if(img.getData() != null && img.getData().length != 0){
            if(img.getData(size)==null){
                //we need to resize the image
                try {
                    img.setData(resizeImage(img.getData(),size),size);
                }catch (Exception e){
                    LOGGER.error("There was an error when trying to resize image with id {} for size {}",imageId,size);
                    final Image auxImage = new Image(img.getImageId(), img.getData());
                    auxImage.setData(img.getData(),size);
                    return auxImage;
//                    return img;
                }
            }
            return img;
        }
        //we return the default provided image
        try {
            byte[] bytes = StreamUtils.copyToByteArray(defaultImageInputStream);
            Image ans =  new Image(img.getImageId(),bytes);//return a dummy image with the default contents
            ans.setData(bytes,size);//set the data at the wanted size
            return ans;
        }catch (Exception e){
            throw new ImageNotFoundException();
        }
    }


    @Transactional
    @Override
    public void deleteImage(final long imageId) throws ImageNotFoundException {
        final Image image = findById(imageId).orElseThrow(ImageNotFoundException::new);
        imageDao.delete(image);
    }


}
