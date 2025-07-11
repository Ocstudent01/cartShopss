package com.ochcdevelopment.cartshops.controller;

import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.Category;
import com.ochcdevelopment.cartshops.response.ApiResponse;
import com.ochcdevelopment.cartshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    //recibir todas las categorias
    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAllCategories(){
        //try catch es para manejar mejor los errores durante la ejecusion
        try {
            //listar de categorias
            List<Category> categories = categoryService.getAllCategories();

            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",INTERNAL_SERVER_ERROR));
        }
    }

    // agregar categorias
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategories(@RequestBody Category name) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return  ResponseEntity.ok(new ApiResponse("Success",theCategory));
        } catch (ResourceNotFoundException e) {

            //Conflic ustilizo cuando voy hacer el control de algo que ya existe
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    //conseguir o buscar categoria por id
    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse>getCategoryById(@PathVariable Long id){
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //conseguir o buscar category por su nombre
    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable String name){
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //eliminar category por id
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //actualizar categoria por id
    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse>updateCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category updateCategory = categoryService.updateCategory(category,id);
            return  ResponseEntity.ok(new ApiResponse("Update success!",updateCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
