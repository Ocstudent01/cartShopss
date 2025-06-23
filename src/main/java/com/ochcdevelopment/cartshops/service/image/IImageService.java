package com.ochcdevelopment.cartshops.service.image;

import com.ochcdevelopment.cartshops.dto.ImageDto;
import com.ochcdevelopment.cartshops.model.Image;
import com.ochcdevelopment.cartshops.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    //esto va buscar una imagen por su id
    Image getImageById(long id);
    //eliminar una imagen por id
    void deleteImageById(long id);
    //esto se utiliza para guardar los archivos de imagenes
    List<ImageDto> saveImages(List<MultipartFile> files , Long productId);
    //esto es para actualizar los archivos de una imagen atravez de su id
    void updateImage(MultipartFile file, Long imageId);
}
