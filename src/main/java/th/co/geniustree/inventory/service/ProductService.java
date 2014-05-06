/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    public ProductRepo productRepo;
    
    public void remove(Product product){
        productRepo.delete(product);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public void save(Product product) {
        productRepo.save(product);
    }

}
