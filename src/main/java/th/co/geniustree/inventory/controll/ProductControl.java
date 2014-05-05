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
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.data.repository.Repository;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;
import sun.nio.cs.ext.PCK;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductPackage;
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

    private final ProductService productService = getProductManagedBean();

//business logic----------------------------------------------------------------
    public Product findByBarcode() {
        
        List<Product> productList = findAllProduct();
        for (Product p : productList){
            for (ProductPackage pk : p.getPackages()){
                if (pk.getBarcode().equals(pack.getBarcode())){
                    product=p;
                }
            }
        }
        return product;
    }

    public void saveProduct() {
        productService.saveProduct(product);
        getProducts().add(product);
    }

    public List<Product> findAllProduct() {
        return productService.findAllProduct();
    }

    private String requestParam(String paramName) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(paramName);
    }

//getter and setter-------------------------------------------------------------
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

    public ProductService getProductManagedBean() {
        ServletContext servletContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(ProductService.class);

    }
}
