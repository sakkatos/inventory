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
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private String selectedBarcode;
    private String massage = "";
    private Boolean redirect;
    private Locale locale = Locale.getDefault();
    private TimeZone timeZone = Calendar.getInstance().getTimeZone();
    private SimpleDateFormat smpDateFormat;

    @PostConstruct
    public void postConstruct() {

    }

//business logic----------------------------------------------------------------
    public void addItemByBarcode() {
        if (!isBarcodeExist()) {
            massage = "No barcode info";
            System.out.println(massage);
            redirect = true;
        }
        if (isBarcodeExist() && !isBarcodeBelongTo()) {
            massage = "The barcode not belong to any product";
        }
        if (isBarcodeExist() && isBarcodeBelongTo()) {
            pack = packageService.findBarcode(barcode);
            product = pack.getProduct();
            item = new ProductItem();
            insertItemByBarcode();
            updateItemLog();
        }

    }

    public String onRedirect() {
        if (isRedirect()) {
            redirect = false;
            return "add-barcode.xhtml?selectedBarcode=" + barcode + "faces-redirect=true";
        }
        return "";
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
        Calendar cal = Calendar.getInstance();
        item.setAmount(pack.getAmountPerPack() * 1);
        item.setDateIn(cal.getTime());
        item.setTimeIn(cal.getTime());
        item.setProduct(product);
        itemService.saveItem(item);
        
        product.getProductItems().add(item);
        productService.save(product);
        barcode = "";
    }

    public void insertItemByHand() {
        item.setAmount(pack.getAmountPerPack() * amountOfPack);

        item.setDateIn(Calendar.getInstance().getTime());
        item.setTimeIn(Calendar.getInstance().getTime());
        item.setProduct(product);
        itemService.saveItem(item);
    }

    public void updateItemLog() {
        items = itemService.itemOrderByDateDescend(product);
    }

    public void reset() {
        barcode="";
        pack = new ProductPackage();
        items = new ArrayList<>();
    }

    public Integer sumItemByProduct() {
        if (itemService.sumAmountByProduct(getPack().getProduct()) == null) {
            return 0;
        }
        return itemService.sumAmountByProduct(getPack().getProduct());
    }

    public void onRemoveItem() {
        itemService.removeItem(item);
        items = itemService.itemOrderByDateDescend(pack.getProduct());
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
        if (items == null) {
            items = new ArrayList<>();
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
        if (pack == null) {
            pack = new ProductPackage();
        }
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

    public String getSelectedBarcode() {
        return selectedBarcode;
    }

    public void setSelectedBarcode(String selectedBarcode) {
        this.selectedBarcode = selectedBarcode;
    }

    public Boolean isRedirect() {
        if (redirect == null) {
            redirect = false;
        }
        return redirect;
    }

    public void setRedirect(Boolean redirect) {
        this.redirect = redirect;
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public TimeZone getTimeZone() {
        if(timeZone==null){
            timeZone = Calendar.getInstance().getTimeZone();
        }
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public SimpleDateFormat getSmpDateFormat() {
        if (smpDateFormat==null){
            smpDateFormat = new SimpleDateFormat("dd:MMM:yyyy");
            smpDateFormat.setCalendar(Calendar.getInstance());
        }
        return smpDateFormat;
    }

    public void setSmpDateFormat(SimpleDateFormat smpDateFormat) {
        this.smpDateFormat = smpDateFormat;
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
