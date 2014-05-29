/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductPackage;
import th.co.geniustree.inventory.service.CategoryService;
import th.co.geniustree.inventory.service.PackageService;
import th.co.geniustree.inventory.service.ProductService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author Nook
 */
@ManagedBean
@SessionScoped
public class BarcodeManageController implements Serializable {

    private final ProductService productService = JSFSpringUtils.getBean(ProductService.class);
    private final PackageService packageService = JSFSpringUtils.getBean(PackageService.class);
    private final CategoryService categoryService = JSFSpringUtils.getBean(CategoryService.class);

    private ProductPackage pack;
    private List<ProductPackage> packs;
    private Product product;
    private List<Product> products;
    private Category category;
    private List<Category> categories;

    private String selectedProductId;
    private String selectedBarcode;
    private static final Logger LOG = LoggerFactory.getLogger(BarcodeManageController.class);
    //business logic------------------------------------------------------------
    public void onCreate() {
        pack = new ProductPackage();
    }

    public void onSave() {
        pack.setProduct(product);
        packageService.savePackage(pack);

        product.getPackages().add(pack);
        productService.save(product);
        reset();
    }

    public void onEdit() {
        packageService.savePackage(pack);
        reset();
    }

    public void onRemove() {
        product.getPackages().remove(pack);
        reset();
    }

    public void reset() {
        selectedBarcode = "";
        String selectedProductId1 = getSelectedProductId();
//        if(selectedProductId1.isEmpty()){
//            selectedProductId1 = "1234";
//        }
        product = productService.findOne(selectedProductId1);
        LOG.info("------------------------------------------" + product);
//        System.out.println("#####################################################################" + product.getId() + product.getName());
        packs = product.getPackages();

    }

    public void onSelect() {
        pack = packageService.findOne(selectedBarcode);
    }

    //getter and setter---------------------------------------------------------
    public ProductPackage getPack() {
        return pack;
    }

    public void setPack(ProductPackage pack) {
        this.pack = pack;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getSelectedProductId() {
        if (selectedProductId == null) {
            selectedProductId = "";
        }
        return selectedProductId;
    }

    public void setSelectedProductId(String selectedProductId) {
        LOG.info("====================================================================================================================================SET" + selectedProductId);
        this.selectedProductId = selectedProductId;
    }

    public List<ProductPackage> getPacks() {
        if (packs == null) {
            packs = new ArrayList<>();
        }
        return packs;
    }

    public void setPacks(List<ProductPackage> packs) {
        this.packs = packs;
    }

    public String getSelectedBarcode() {
        if (selectedBarcode == null) {
            selectedBarcode = "0";
        }
        return selectedBarcode;
    }

    public void setSelectedBarcode(String selectedBarcode) {
        this.selectedBarcode = selectedBarcode;
    }
}
