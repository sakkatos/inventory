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
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.model.ProductItem;
import th.co.geniustree.inventory.service.CategoryService;
import th.co.geniustree.inventory.service.PackageService;
import th.co.geniustree.inventory.service.ProductItemService;
import th.co.geniustree.inventory.service.ProductService;
import th.co.geniustree.inventory.util.ItemLazyLoad;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author Nook
 */
@ManagedBean
@SessionScoped
public class ItemManageController implements Serializable {

    private final ProductService productService = JSFSpringUtils.getBean(ProductService.class);
    private final CategoryService categoryService = JSFSpringUtils.getBean(CategoryService.class);
    private final PackageService packageService = JSFSpringUtils.getBean(PackageService.class);
    private final ProductItemService itemService = JSFSpringUtils.getBean(ProductItemService.class);

    private ProductItem item;
    private List<ProductItem> items;
    private Product product;
    private ItemLazyLoad itemLazy;

    private String selectedProductId;
    private Integer selectedItemId;
    private Locale locale = Locale.getDefault();
    private TimeZone timeZone = Calendar.getInstance().getTimeZone();

    //business logic------------------------------------------------------------
    public void onSave() {
    }

    public void onRemove() {
        product.getProductItems().remove(item);
        productService.save(product);
        itemService.removeItem(item);
    }

    public void onSelect() {
        item = itemService.findOne(selectedItemId);
    }

    public void onEdit() {
    }

    public void reset() {
        System.out.println("=================================================");
        System.out.println("product " + selectedProductId);
        System.out.println("=================================================");
        if (selectedProductId == null) {
            selectedProductId = "";
        }
        if (selectedProductId.isEmpty()) {
            product = productService.findOne("T001");
        }else {
            product = productService.findOne(selectedProductId);
        }
        getItemLazy().setProduct(product);
    }

    public void filtertItemsByProduct() {
        product = productService.findOne(selectedProductId);
        items = itemService.itemOrderByDateDescend(product);
    }

    //getter and setter---------------------------------------------------------
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

    public Product getProduct() {
        if (product == null) {
            product = new Product();
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ItemLazyLoad getItemLazy() {
        if (itemLazy == null) {
            itemLazy = new ItemLazyLoad();
        }
        itemLazy.setProduct(getProduct());
        return itemLazy;
    }

    public void setItemLazy(ItemLazyLoad itemLazy) {
        this.itemLazy = itemLazy;
    }

    public String getSelectedProductId() {
        if (selectedProductId == null) {
            selectedProductId = "";
        }
        return selectedProductId;
    }

    public void setSelectedProductId(String selectedProductId) {
        System.out.println("=================================================");
        System.out.println(selectedProductId);
        System.out.println("=================================================");
        this.selectedProductId = selectedProductId;
    }

    public Integer getSelectedItemId() {
        if (selectedItemId == null) {
            selectedItemId = 0;
        }
        return selectedItemId;
    }

    public void setSelectedItemId(Integer selectedItemId) {
        this.selectedItemId = selectedItemId;
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
        if (timeZone == null) {
            timeZone = Calendar.getInstance().getTimeZone();
        }
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

}
