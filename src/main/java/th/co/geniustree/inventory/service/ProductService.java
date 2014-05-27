/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.repo.ProductRepo;

/**
 *
 * @author Nook
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public void save(Product product) {
        productRepo.save(product);
    }

    public void remove(Product product) {
        productRepo.delete(product);
    }

    public Product findOne(String id) {
        return productRepo.findOne(id);
    }

    public Product findByBarcode(String barcode) {
        return productRepo.findByBarcode(barcode);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public List<Product> findProductByCategory(Category category) {
        return productRepo.findProducstByCategory(category);
    }

    public Page<Product> searchProductByCategoryName(List<String> nameCategories,Pageable pageable) {
        return productRepo.searchProductByCategoryName(nameCategories, pageable);
    }
    
    public List<Product> searchProductByCategoryName(List<String> nameCategories) {
        return productRepo.searchProductByCategoryName(nameCategories);
    }

}
