/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.ejb.Local;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import th.co.geniustree.inventory.model.Category;
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
    private List<Product> products;
    private ItemLazyLoad itemLazy;

    private String selectedProductId;
    private Integer selectedItemId;
    private String categoryLabel;
    private List<String> categoryLabels;
    private Locale locale = Locale.getDefault();
    private TimeZone timeZone = Calendar.getInstance().getTimeZone();

    //business logic------------------------------------------------------------
    public void onSave() {
    }

    public void onRemove() {
    }

    public void onSelect() {
        item = itemService.findOne(selectedItemId);
    }

    public void onEdit() {
    }

    public void reset() {
        if (product == null) {
            product = new Product();
        }
        items = itemService.itemOrderByDateDescend(product);
        products = productService.findAll();
        categoryLabels = collectCategoryLabelsDepthFirstSearch(categoryService.findRoot());
        if (categoryLabels.contains("root")) {
            categoryLabels.remove("root");
            List<String> tmp = new ArrayList<>();
            tmp.add("All");
            for (String s : categoryLabels) {
                tmp.add(s);
            }
            categoryLabels = tmp;
        }
    }

    public void filtertItemsByProduct() {
        product = productService.findOne(selectedProductId);
        items = itemService.itemOrderByDateDescend(product);
    }

    public void filterProducstByCategory() {
        List<String> collectedLabels;
        if (categoryLabel.equals("All")) {
            products = productService.findAll();
        }
        if (!categoryLabel.equals("All")) {
            collectedLabels = collectCategoryLabelsDepthFirstSearch(
                    categoryService.findByName(categoryLabel));
            products = productService.searchProductByCategoryName(collectedLabels);
        }
    }

    public List<String> collectCategoryLabelsDepthFirstSearch(Category c) {
        List<String> labelList = new ArrayList<>();
        labelList.add(c.getName());
        List<String> childLabelList = recursiveGetLabelDepthFirstSearch(c.getChildren());
        Iterator<String> iterator = childLabelList.iterator();
        while (iterator.hasNext()) {
            labelList.add(iterator.next());
        }
        return labelList;
    }

    public List<String> recursiveGetLabelDepthFirstSearch(List<Category> children) {
        List<String> labelList = new ArrayList<>();
        if (children != null) {
            Iterator<Category> iterator = children.iterator();
            while (iterator.hasNext()) {
                Category c = iterator.next();
                labelList.add(c.getName());
                List<String> childlabelList = recursiveGetLabelDepthFirstSearch(c.getChildren());
                Iterator<String> subIterator = childlabelList.iterator();
                while (subIterator.hasNext()) {
                    labelList.add(subIterator.next());
                }
            }
        }
        return labelList;
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

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public String getCategoryLabel() {
        if (categoryLabel == null) {
            categoryLabel = "";
        }
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public List<String> getCategoryLabels() {
        if (categoryLabels == null) {
            categoryLabels = collectCategoryLabelsDepthFirstSearch(categoryService.findRoot());
            if (categoryLabels.contains("root")) {
                categoryLabels.remove("root");
                List<String> tmp = new ArrayList<>();
                tmp.add("All");
                for (String s : categoryLabels) {
                    tmp.add(s);
                }
                categoryLabels = tmp;
            }
        }
        return categoryLabels;
    }

    public void setCategoryLabels(List<String> categoryLabels) {
        this.categoryLabels = categoryLabels;
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
