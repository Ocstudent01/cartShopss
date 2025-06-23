package com.ochcdevelopment.cartshops.service.image;

import com.ochcdevelopment.cartshops.dto.ImageDto;
import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.Image;
import com.ochcdevelopment.cartshops.model.Product;
import com.ochcdevelopment.cartshops.repository.ImageRepository;
import com.ochcdevelopment.cartshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements  IImageService{
    //uso a la interfaz ImageRepository del paquete repository
    private final ImageRepository imageRepository;
    //interfaz del producto ubicado en el paquete product
    private final IProductService productService;


    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No image found id: " + id));
    }

    @Override
    public void deleteImageById(long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("No image found with id: " + id);
        });

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                //String downloadUrl = "/api/v1/image/download/" + image.getId();
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);

                //saveImage.setDownloadUrl("/api/v1/image/download/"+ saveImage.getId());
                saveImage.setDownloadUrl(buildDownloadUrl+saveImage.getId());
                imageRepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(saveImage.getId());
                imageDto.setImageName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                savedImageDto.add(imageDto);


            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getName());
            //para getBytes necesitas si o si generar un try catch para control  errores
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
            // ya no puede ser exception sino IO para que funcione SQL Exception
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
