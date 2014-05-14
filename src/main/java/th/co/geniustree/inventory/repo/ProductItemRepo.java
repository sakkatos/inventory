/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductItem;

/**
 *
 * @author Nook
 */
public interface ProductItemRepo extends JpaRepository<ProductItem, Integer>{
    
    @Query("SELECT pi FROM ProductItem pi WHERE pi.product = ?1 ORDER BY pi.dateIn,pi.timeIn DESC ")
    public List<ProductItem> findItemOrderByDateDescend(Product product); 
    
    public List<ProductItem> findByProduct(Product product); 
    
    @Query("SELECT SUM(pi.amount) FROM ProductItem pi WHERE pi.product = ?1")
    public Integer sumAmountByProduct(Product product);
    
}
