/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.Package;
import th.co.geniustree.inventory.service.CategoryService;
import th.co.geniustree.inventory.service.ProductService;

/**
 *
 * @author Nook
 */
@ManagedBean
@SessionScoped
public class ProductControl implements Serializable {

    private Product product;
    private List<Product> products;
    private Package pack;
    private Category category;
    private Integer amountOfPack;
    private String barcode;

    private final ProductService productService = getProductManagedBean();
    private final CategoryService categoryService = getCategoryManagedBean();

    @PostConstruct
    public void postConstruct() {

//        LOG.debug("start logger on {}", new Date());
    }

//business logic----------------------------------------------------------------
  
    public void onIncreaseProduct(){
        Product hasBarcode = productService.findByBarcode(barcode);
    }

    public void onCreate() {
        product = new Product();
        pack = new Package();
    }

    public void onEditProduct() {
        productService.save(product);
    }

    public void onSaveProduct() {
        product.getPackages().add(pack);
        product.setCategory(getRootCategory());
        product.setAmount(amountOfPack * pack.getAmountPerPack());
        productService.save(product);
        getProducts().add(product);
    }

    public void onDeleteProduct() {
        productService.remove(product);
        products = findAllProduct();
    }

    public void onSelectProduct() {
        Product p = new Product();
        p.setId(requestParam("productId"));
        product = getProducts().get(getProducts().indexOf(p));
    }

    public List<Product> findAllProduct() {
        return productService.findAll();
    }

    public Category getRootCategory() {
        category = categoryService.findRoot();
        return category;
    }

    private String requestParam(String paramName) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(paramName);
    }

//getter and setter-------------------------------------------------------------
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getAmountOfPack() {
        return amountOfPack;
    }

    public void setAmountOfPack(Integer amountOfPack) {
        this.amountOfPack = amountOfPack;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
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
            products = findAllProduct();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ProductService getProductManagedBean() {
        ServletContext servletContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(ProductService.class);
    }

    public CategoryService getCategoryManagedBean() {
        ServletContext servletContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(CategoryService.class);
    }
}
