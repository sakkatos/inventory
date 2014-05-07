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
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductItem;
import th.co.geniustree.inventory.model.ProductPackage;
import th.co.geniustree.inventory.service.CategoryService;
import th.co.geniustree.inventory.service.PackageService;
import th.co.geniustree.inventory.service.ProductItemService;
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
    private ProductPackage pack;
    private Category category;
    private Integer amountOfPack;
    private String barcode;
    private String selectedProductId;
    private ProductItem productItem;
    private List<ProductItem> productItems;

    private final ProductService productService = getProductManagedBean();
    private final CategoryService categoryService = getCategoryManagedBean();
    private final PackageService packageService = getPackageManagedBean();
    private final ProductItemService itemService = getItemManagedBean();

    @PostConstruct
    public void postConstruct() {
        products = productService.findAll();
//        LOG.debug("start logger on {}", new Date());
    }

//business logic----------------------------------------------------------------
    public Boolean isBarcodeExist() {
        ProductPackage pkg = packageService.findBarcode(getBarcode());
        return pkg != null;
    }

    public void onCreate() {
        product = new Product();
        pack = new ProductPackage();
        amountOfPack = 0;
    }

    public void onEditProduct() {
        productService.save(product);
    }

    public void onSaveProduct() {
        product.getPackages().add(pack);
        product.setCategory(getRootCategory());
//        product.setAmount(amountOfPack * pack.getAmountPerPack());
        pack.setProduct(product);
        productService.save(product);
        getProducts().add(product);
    }

    public void insertProductItem() {

    }

    public void onDeleteProduct() {
        productService.remove(product);
        products.remove(product);
    }

    public void onSelectProduct() {
        Product p = new Product();
        p.setId(selectedProductId);
        product = getProducts().get(getProducts().indexOf(p));
    }

    public List<Product> findAllProduct() {
        return productService.findAll();
    }

    public Category getRootCategory() {
        category = categoryService.findRoot();
        return category;
    }

//getter and setter-------------------------------------------------------------
    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    public String getBarcode() {
        if (barcode == null) {
            barcode = "";
        }
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
            products = new ArrayList();
        }
        return products;
    }

    public String getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(String selectedProductId) {
        this.selectedProductId = selectedProductId;
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

    public PackageService getPackageManagedBean() {
        ServletContext servletContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(PackageService.class);
    }

    public ProductItemService getItemManagedBean() {
        ServletContext servletContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(ProductItemService.class);
    }
}
