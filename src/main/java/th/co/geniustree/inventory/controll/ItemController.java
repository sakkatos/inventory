/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ItemController implements Serializable {

    private final ProductService productService = getProductManagedBean();
    private final CategoryService categoryService = getCategoryManagedBean();
    private final PackageService packageService = getPackageManagedBean();
    private final ProductItemService itemService = getItemManagedBean();

    private Product product;
    private List<Product> products;
    private ProductPackage pack;
    private List<ProductPackage> packs;
    private Category category;
    private ProductItem item;
    private List<ProductItem> items;
    private Integer amountOfPack;
    private String barcode;
    private Integer selectedItemId;
    private String massage = "";

    @PostConstruct
    public void postConstruct() {
//        products = productService.findAll();
//        LOG.debug("start logger on {}", new Date());
    }

//business logic----------------------------------------------------------------
    public void addItemByBarcode() {
        String massage1 = "";
        String massage2 = "";
        if (!isBarcodeExist()) {
            massage1 = "No barcode info";
        }
        if (!isBarcodeBelongTo()) {
            massage2 = "The barcode not belong to any product";
        }
        if (isBarcodeExist() && isBarcodeBelongTo()) {
            pack = packageService.findBarcode(barcode);
            product = pack.getProduct();
            item = new ProductItem();
            insertItemByBarcode();
            updateItemLog();
        }
//        massage = massage1 + "  " + massage2;
        massage = concateMassage(massage1, massage2);

    }

    public Boolean isBarcodeExist() {
        ProductPackage pkg = packageService.findBarcode(getBarcode());
        return pkg != null;
    }

    public Boolean isBarcodeBelongTo() {
        Product prod = packageService.findProductBarcodeBelongTo(getBarcode());
        return prod != null;
    }

    public void insertItemByBarcode() {
        item.setAmount(pack.getAmountPerPack() * 1);
        item.setProduct(product);
        item.setDateIn(Calendar.getInstance().getTime());
        item.setTimeIn(Calendar.getInstance().getTime());
        itemService.saveItem(item);
    }

    public void insertItemByHand() {
        item.setAmount(pack.getAmountPerPack() * amountOfPack);
        item.setProduct(product);
        item.setDateIn(Calendar.getInstance().getTime());
        item.setTimeIn(Calendar.getInstance().getTime());
        itemService.saveItem(item);
    }

    public void updateItemLog() {
        product = productService.findByBarcode(barcode);
        items = itemService.itemOrderByDateDescend(product);
    }

    public Integer sumItemByProduct() {
        return itemService.sumAmountByProduct(product);
    }

    public void onRemoveItem() {
        itemService.removeItem(item);
        items = itemService.itemOrderByDateDescend(product);
    }

    public void onSelectItem() {
        ProductItem pi = new ProductItem();
        pi.setId(selectedItemId);
        item = getItems().get(getItems().indexOf(pi));
    }

    public void findAllProduct() {
        products = productService.findAll();
    }

//getter and setter-------------------------------------------------------------
    public List<ProductPackage> getPacks() {
        if (packs == null) {
            packs = new ArrayList<>();
        }
        return packs;
    }

    public void setPacks(List<ProductPackage> packs) {
        this.packs = packs;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public ProductItem getItem() {
        return item;
    }

    public void setItem(ProductItem item) {
        this.item = item;
    }

    public List<ProductItem> getItems() {
        if (items==null){
            items= new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
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

    public Integer getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(Integer selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private String concateMassage(String massage1, String massage2) {
        String totalMassage = "";
        Boolean isMassage1Null = (massage1 == null);
        Boolean isMassage2Null = (massage2 == null);
        System.out.println("Hello ! " + isMassage1Null + " and " + isMassage1Null);
        if (!isMassage1Null && !isMassage2Null) {
            totalMassage = massage1 + ", " + massage2;
        }
        if (isMassage1Null && !isMassage2Null) {
            totalMassage = massage2;
        }
        if (!isMassage1Null && isMassage2Null) {
            totalMassage = massage1;
        }
        return totalMassage;
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
