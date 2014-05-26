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
import th.co.geniustree.inventory.model.ProductPackage;
import th.co.geniustree.inventory.repo.PackageRepo;

/**
 *
 * @author Nook
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PackageService {

    @Autowired
    private PackageRepo packageRepo;

    public List<ProductPackage> findAll() {
        return packageRepo.findAll();
    }

    public ProductPackage findOne(String barcode) {
        return packageRepo.findOne(barcode);
    }

    public ProductPackage findBarcode(String barcode) {
        return packageRepo.findBarcode(barcode);
    }

    public void savePackage(ProductPackage package1) {
        packageRepo.save(package1);
    }

    public Product findProductBarcodeBelongTo(String barcode) {
        return packageRepo.findProductBarcodeBelongTo(barcode);
    }
}
