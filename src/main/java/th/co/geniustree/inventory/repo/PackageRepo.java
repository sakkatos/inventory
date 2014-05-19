/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductPackage;

/**
 *
 * @author Nook
 */
public interface PackageRepo extends JpaRepository<ProductPackage, String> {

    @Query("SELECT pkg FROM ProductPackage pkg WHERE pkg.barcode = ?1")
    public ProductPackage findBarcode(String barcode);

    @Query("SELECT pkg.product FROM ProductPackage pkg WHERE pkg.barcode = ?1 AND pkg.product != NULL")
    public Product findProductBarcodeBelongTo(String barcode);
    
}
