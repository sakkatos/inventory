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
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductItem;
import th.co.geniustree.inventory.repo.ProductItemRepo;

/**
 *
 * @author Nook
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductItemService {
    
    @Autowired
    private ProductItemRepo productItemRepo;
    
    public void saveItem(ProductItem productItem){
        productItemRepo.save(productItem);
    }
    
    public void removeItem(ProductItem productItem){
        productItemRepo.delete(productItem);
    }
    
    public List<ProductItem> itemOrderByDateDescend(Product product){
        return productItemRepo.findItemOrderByDateDescend(product);
    }
    
    public ProductItem findOne(Integer id){
        return productItemRepo.findOne(id);
    }
    
    public Page<ProductItem> findByProductLazyLoad(Product product,Pageable pageable){
        return productItemRepo.findByProduct(product, pageable);
    }
    
    public Integer sumAmountByProduct (Product product){
        return productItemRepo.sumAmountByProduct(product);
    }

}
