/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

    private ProductPackage pack = new ProductPackage();
    private List<ProductPackage> packs = new ArrayList<>();
    private Product product = new Product();
    private List<Product> products = new ArrayList<>();
    private Category category = new Category();
    private List<Category> categories = new ArrayList<>();

    private String selectedProductId;
    private String selectedBarcode;
    private static final Logger LOG = LoggerFactory.getLogger(BarcodeManageController.class);

    //business logic------------------------------------------------------------
    public void onCreate() {
        try {
            LOG.info("Create!!!!");
            pack = new ProductPackage();
            showMessage(FacesMessage.SEVERITY_INFO, "create Barcode", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "create Barcode", "fail");
        }

    }

    public void onSave() {
        try {
            pack.setProduct(product);
            packageService.savePackage(pack);
            product.getPackages().add(pack);
            productService.save(product);
            reset();
            showMessage(FacesMessage.SEVERITY_INFO, "save Barcode", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "save Barcode", "fail");
        }

    }

    public void onEdit() {
        try {
            packageService.savePackage(pack);
            reset();
            showMessage(FacesMessage.SEVERITY_INFO, "edit Barcode", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "edit Barcode", "fail");
        }
    }

    public void onRemove() {
        try {
            packageService.remove(pack);
            product.getPackages().remove(pack);
            productService.save(product);
            reset();
            showMessage(FacesMessage.SEVERITY_INFO, "remove Barcode", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "remove Barcode", "fail");
        }
    }

    public void reset() {
        selectedBarcode = "";
        product = productService.findOne(getSelectedProductId());
        packs = getProduct().getPackages();
    }

    public void onSelect() {
        pack = packageService.findOne(selectedBarcode);
    }

    //getter and setter---------------------------------------------------------
    public ProductPackage getPack() {
        if (pack == null) {
            pack = new ProductPackage();
        }
        return pack;
    }

    public void setPack(ProductPackage pack) {
        this.pack = pack;
    }

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

    private void showMessage(FacesMessage.Severity severity, String title, String body) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, body));
    }
}
