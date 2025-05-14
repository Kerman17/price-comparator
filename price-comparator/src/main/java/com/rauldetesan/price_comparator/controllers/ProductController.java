package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.enums.Unit;
import com.rauldetesan.price_comparator.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{productId}")
    public Product findProductById(@PathVariable String productId){
        return productService.findProductById(productId);
    }

    @GetMapping
    public List<Product> findAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping
    public void addProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @DeleteMapping("{productId}")
    public void deleteProductById(@PathVariable String productId){
        productService.deleteProductById(productId);
    }

    @PutMapping("{productId}")
    public void updateProductById(@PathVariable String productId,
                                  @RequestParam String name,
                                  @RequestParam String category,
                                  @RequestParam String brand,
                                  @RequestParam double quantity,
                                  @RequestParam Unit unit,
                                  @RequestParam double price,
                                  @RequestParam String currency){

        productService.updateProductById(
                productId,
                name,
                category,
                brand,
                quantity,
                unit,
                price,
                currency);
    }

    @PutMapping("{productId}/price")
    public void updateProductPrice(@PathVariable String productId,
                                   @RequestParam double price){

        productService.updateProductPrice(productId, price);
    }

    @PutMapping("{productId}/quantity")
    public void updateProductQuantity(@PathVariable String productId,
                                      @RequestParam double quantity){
        productService.updateProductQuantity(productId, quantity);
    }

}
