package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.enums.Unit;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(String id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " does not exist"));
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProductById(String id){

        // First we search if the product exists
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " does not exist"));

        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProductById(String id,
                                  String name,
                                  String category,
                                  String brand,
                                  double quantity,
                                  Unit unit,
                                  double price,
                                  String currency){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " does not exist"));

        if(name!=null && name.length()>0 &&
                category!=null && category.length()>0 &&
                brand!=null && brand.length()>0 &&
                quantity>0 &&
                unit!=null &&
                price>0 &&
                currency!=null && currency.length()>0){

            product.setName(name);
            product.setCategory(category);
            product.setBrand(brand);
            product.setQuantity(quantity);
            product.setUnit(unit);
            product.setPrice(price);
            product.setCurrency(currency);

        }

        productRepository.save(product);
    }

    @Transactional
    public void updateProductPrice(String id,
                                   double price){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " does not exist"));

        if(price>0){
            product.setPrice(price);
        }

        productRepository.save(product);
    }

    @Transactional
    public void updateProductQuantity(String id,
                                      double quantity){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " does not exist"));

        if(quantity>0){
            product.setQuantity(quantity);
        }

        productRepository.save(product);
    }

}

