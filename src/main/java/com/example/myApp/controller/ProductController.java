package com.example.myApp.controller;

import com.example.myApp.model.Product;
import com.example.myApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {


    ProductService service;

    public ProductController(ProductService service){
        this.service=service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable int prodId){
        Product prod = service.getProductById(prodId);
        if(prod!=null) return new ResponseEntity<>(prod, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @PostMapping("/products/add")
    public ResponseEntity<?> addProduct(@ModelAttribute Product prod,
                                        @RequestPart MultipartFile imageFile){

        try{
            Product product= service.addProduct(prod,imageFile);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/products/{prodId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int prodId){
        Product product = service.getProductById(prodId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }


    @PutMapping("/products/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @ModelAttribute Product prod,
                              @RequestPart MultipartFile imageFile){

        Product product = null;
        try {
            product = service.updateProduct(id, prod, imageFile);
        } catch (IOException e) {
           return new ResponseEntity<>("Failed to Update", HttpStatus.BAD_REQUEST);
        }

        if(product!=null) return new ResponseEntity<>("Updated", HttpStatus.OK);
        else return new ResponseEntity<>("Failed to Update", HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product prod = service.getProductById(id);
        if(prod!=null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
