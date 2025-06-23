package com.ochcdevelopment.cartshops.controller;

import com.ochcdevelopment.cartshops.dto.ProductDto;
import com.ochcdevelopment.cartshops.exceptions.AlreadyExistsException;
import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.Product;
import com.ochcdevelopment.cartshops.request.ProductUpdateRequest;
import com.ochcdevelopment.cartshops.request.AddProductRequest;
import com.ochcdevelopment.cartshops.response.ApiResponse;
import com.ochcdevelopment.cartshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    //listar o mostrar todos los productos
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success..!",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    // mostrar o buscar productos por su id
    @GetMapping("/product/{productId}/product")
    // el funciona como un alias para poder referenciar mejor el nombre de la ruta del mapeo (@PathVariable("productId") Long id)
    public ResponseEntity<ApiResponse>getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return  ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    //Agregar  productos
    //addProductRequest proviene de mi metodo publico que esta hecho en el paquete services
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Add products Success!",productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    //actualizar productos por su productId - ojo utilizo @RequestBody para que el cuerpo de la solicitud se lea y deserialice en un objeto y patvariable para mapear dicha solicitud y vincularlo
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try {
            Product theProducts = productService.updateProduct(request,productId);
            ProductDto productDto = productService.convertToDto(theProducts);
            return ResponseEntity.ok(new ApiResponse("Update products Success!",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    //eliminar o delete productos por su prodcutId
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!",productId));
        } catch (ResourceNotFoundException e) {
            //uso ResourceNotFoundException cuando tengo en mi servicios una condicion de control de errores y exception es cuando no tienes ninguna condicion y quieres agregarle para asi controlar mejor los errores
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    //listar productos por su marca y nombre
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse>getProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            List<ProductDto>convertedProducts = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    // listar productos por categoria y su marca -- ojo cuando se usa mas de un parametro de la misma clase se usa RequestParam
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse>getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",null));
        }
    }

    //listar productos por nombre
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse>getProductsByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",null));
        }
    }

    //listar o encontar productos por marca
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product>products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
        }
    }

    //listar todos los prodcutos por categoria
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse>findProductByCategory(@PathVariable String category){
        try {
            List<Product>products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found ", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
        }
    }

    //contar los productos por su marca y nombre
    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse>countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {

            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!",productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
        }
    }

}
