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
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.service.CategoryService;
import th.co.geniustree.inventory.service.ProductItemService;
import th.co.geniustree.inventory.service.ProductService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author Nook
 */
@ManagedBean
@SessionScoped
public class ProductController implements Serializable {

    private final ProductService productService = JSFSpringUtils.getBean(ProductService.class);
    private final CategoryService categoryService = JSFSpringUtils.getBean(CategoryService.class);
    private final ProductItemService itemService = JSFSpringUtils.getBean(ProductItemService.class);

    private Product product;
    private List<Product> products;
    private Category category;
    private List<Category> categories;

    private Integer itemProductAmount;
    private String selectedProductId;
    private List<String> filterOptionByCategories;

    //business logic------------------------------------------------------------
    
    public void onCreate(){
        Category root = categoryService.findRoot();
        product = new Product();
        product.setCategory(root);
        
    }
    
    public void onSave() {
        category = categoryService.findOne(product.getCategory().getId());
        product.setCategory(category);
        productService.save(product);
        products = findAllProducts();
    }

    public void onEdit(){
        category = categoryService.findOne(product.getCategory().getId());
        product.setCategory(category);
        productService.save(product);
        products = findAllProducts();
    }
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    public void onRemove() {
        productService.remove(product);
        products = findAllProducts();
    }

    public void finditemProductAmount() {
        itemProductAmount = itemService.sumAmountByProduct(product);
    }

    public void onSelect() {
        Product p = new Product();
        p.setId(selectedProductId);
        product = getProducts().get(getProducts().indexOf(p));
    }

    //getter and setter---------------------------------------------------------
    public Product getProduct() {
        if (product == null) {
            product = new Product();
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = findAllProducts();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Category getCategory() {
        if (category == null) {
            category = new Category();
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

    public Integer getItemProductAmount() {
        if (itemProductAmount == null) {
            itemProductAmount = 0;
        }
        return itemProductAmount;
    }

    public void setItemProductAmount(Integer itemProductAmount) {
        this.itemProductAmount = itemProductAmount;
    }

    public String getSelectedProductId() {
        if (selectedProductId == null) {
            selectedProductId = "";
        }
        return selectedProductId;
    }

    public void setSelectedProductId(String selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public List<String> getFilterOptionByCategories() {
        if (filterOptionByCategories==null){
            filterOptionByCategories = new ArrayList<>();
            List<Category> cList = categoryService.findAllOrderByName();
            for (Category c:cList){
                filterOptionByCategories.add(c.getName());
            }
        }
        return filterOptionByCategories;
    }

    public void setFilterOptionByCategories(List<String> filterOptionByCategories) {
        this.filterOptionByCategories = filterOptionByCategories;
    }

    
}
