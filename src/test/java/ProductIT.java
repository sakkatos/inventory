/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductPackage;
import th.co.geniustree.inventory.repo.ProductRepo;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Transactional(propagation = Propagation.REQUIRED)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-orm.xml"})
public class ProductIT {

    @Autowired
    private ProductRepo productRepo;

    public ProductIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Rollback(true)
    public void saveProduct() {

        Category category = new Category();
        category.setId("001");
        category.setName("Accesory");

        ProductPackage productPackage = new ProductPackage();
        productPackage.setBarcode("10230");
        productPackage.setName("ลัง");
        productPackage.setAmountPerPack(12);
        
        List<ProductPackage> packages = new ArrayList<>();
        packages.add(productPackage);
        
        Product product = new Product();
        product.setId("1234567890");
        product.setName("watch");
        product.setCategory(category);
        product.setPackages(packages);
        productRepo.save(product);
        List<Product> products = productRepo.findAll();
        assertNotNull(products);
    }
}
