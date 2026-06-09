package com.example.myApp.service;

import com.example.myApp.model.Product;
import com.example.myApp.repository.ProductRepo;
import org.springframework.stereotype.Service;

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

    public void addProduct(Product prod){
        repo.save(prod);
    }

    public void updateProduct(Product prod) {
       repo.save(prod);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
