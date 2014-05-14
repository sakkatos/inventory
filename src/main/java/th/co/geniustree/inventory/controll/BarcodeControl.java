/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
public class BarcodeControl implements Serializable {

    private final ProductService productService = JSFSpringUtils.getBean(ProductService.class);
    private final PackageService packageService = JSFSpringUtils.getBean(PackageService.class);
    private final CategoryService categoryService = JSFSpringUtils.getBean(CategoryService.class);

    private ProductPackage pack;
    private Product product;
    private List<Product> products;
    private Category category;
    private List<Category> categories;

    private String selectedProductId;
    private String selectedBarcode;
    private String selectedCategory;

    @PostConstruct
    public void postConstruct() {
    }

    //business logic------------------------------------------------------------
    public void onCreate() {
        product = new Product();
        pack = new ProductPackage();
    }

    public void onSave() {
        product=productService.findOne(product.getId());
        pack.setProduct(product);
        packageService.savePackage(pack);
    }

    public void findAllProduct() {
        products = productService.findAll();
    }

    public void findProductByCategory() {
        products = productService.findProductByCategory(category);
        System.out.println(category.getName());
        for(Product p : products){
            System.out.print("Hello :::::::::::::::    ");
            System.out.println(p.getName());
        }
    }

    //getter and setter---------------------------------------------------------
    public Product getProduct() {
        if(product==null){
            product = new Product();
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = productService.findAll();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(String selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public Category getCategory() {
        if(category==null){
            category=new Category();
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        if (categories == null) {
            categories = categoryService.findAllOrderByName();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public ProductPackage getPack() {
        if (pack == null) {
            pack = new ProductPackage();
        }
        return pack;
    }

    public void setPack(ProductPackage pack) {
        this.pack = pack;
    }

    public String getSelectedBarcode() {
        return selectedBarcode;
    }

    public void setSelectedBarcode(String selectedBarcode) {
        this.selectedBarcode = selectedBarcode;
    }

    public String getSelectedCategory() {
        if (selectedCategory==null){
            selectedCategory="";
        }
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
    
    

}
