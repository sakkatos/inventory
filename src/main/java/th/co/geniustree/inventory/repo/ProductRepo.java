/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public interface ProductRepo extends JpaRepository<Product, String> {

    @Query("SELECT p From Product p JOIN p.packages pk WHERE pk.barcode = ?1")
    public Product findByBarcode(String barcode);

    @Query("SELECT p FROM Product p ORDER BY p.name")
    public List<Product> findAllProduct();
    
    @Query("SELECT p FROM Product p WHERE p.category = ?1 ORDER BY p.category")
    public List<Product> findProducstByCategory(Category category);
    
    @Query("SELECT p FROM Product p WHERE p.category.name IN :nameCategories")
    public Page<Product> searchProductByCategoryName(@Param("nameCategories")List<String> nameCategories,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.name IN :nameCategories")
    public List<Product> searchProductByCategoryName(@Param("nameCategories") List<String> nameCategories);

}
