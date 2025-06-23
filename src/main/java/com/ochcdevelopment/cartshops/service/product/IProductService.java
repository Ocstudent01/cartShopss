package com.ochcdevelopment.cartshops.service.product;

import com.ochcdevelopment.cartshops.dto.ProductDto;
import com.ochcdevelopment.cartshops.model.Product;
import com.ochcdevelopment.cartshops.request.ProductUpdateRequest;
import com.ochcdevelopment.cartshops.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    //agregar productos
    Product addProduct(AddProductRequest product);

    //buscar producto por id
    Product getProductById(Long id);

    //eliminar producto por id
    void deleteProductById(Long id);

    //actualizar producto por id
    Product updateProduct(ProductUpdateRequest product, Long productId);

    //listar todos los productos
    List<Product> getAllProducts();

    //listar productos por categoria
    List<Product>getProductsByCategory(String category);

    //listar los productos por marca
    List<Product>getProductsByBrand(String brand);

    //listar productos por categoria y marca
    List<Product>getProductsByCategoryAndBrand(String category,String brand);

    //listar productos por su nombre
    List<Product>getProductsByName(String name);

    //listar productos por Marca y nombre de categoria
    List<Product>getProductsByBrandAndName(String brand,String name);

    //contador de productos por marca y nombre de categoria
    Long countProductsByBrandAndName(String brand , String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
