package com.example.myApp.service;

import com.example.myApp.model.Product;
import com.example.myApp.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    ProductRepo repo;

    public ProductService(ProductRepo repo){
        this.repo=repo;
    }


    public List<Product> getProducts(){
        return repo.findAll();
    }

    public Product getProductById(int prodId){
       return repo.findById(prodId).orElse(new Product());
    }

    public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImageData(imageFile.getBytes());

        return repo.save(prod);
    }


    public Product updateProduct(int id, Product prod, MultipartFile imageFile) throws IOException {
        prod.setImageData(imageFile.getBytes());
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());

        return repo.save(prod);
    }



    public void deleteProduct(int id) {
        repo.deleteById(id);
    }


    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
