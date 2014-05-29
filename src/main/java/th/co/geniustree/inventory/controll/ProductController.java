/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import th.co.geniustree.inventory.lazyload.ItemLazyLoad;
import th.co.geniustree.inventory.lazyload.ProductLazyLoad;
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
    private String selectedLabel;
    private List<String> selectedLabels;
    private String costFilter;
    private String baseCostFilter;
    private String expectCostFilter;
    private String categoryFilter;
    private String productItemUrl;

    private ItemLazyLoad itemLazy;
    private ProductLazyLoad productLazy;

    //business logic------------------------------------------------------------
    public void onCreate() {
        Category root = categoryService.findRoot();
        product = new Product();
        product.setCategory(root);
    }

    public void onSave() {
        try {
            category = categoryService.findOne(product.getCategory().getId());
            product.setCategory(category);
            productService.save(product);
            category.getProducts().add(product);
            categoryService.save(category);
            showMessage(FacesMessage.SEVERITY_INFO, "save product", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "save product", "fail");
        }
    }

    public void onEdit() {
        try {
            Category newCategory = categoryService.findOne(product.getCategory().getId());
            Category oldCategory = categoryService.findByName(product.getCategory().getName());
            product.setCategory(category);
            productService.save(product);
            newCategory.getProducts().add(product);
            categoryService.save(newCategory);
            oldCategory.getProducts().remove(product);
            categoryService.save(oldCategory);
            showMessage(FacesMessage.SEVERITY_INFO, "edit product", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "edit product", "fail");
        }
    }

    public void onRemove() {
        try {
            productService.remove(product);
            showMessage(FacesMessage.SEVERITY_INFO, "remove user", "success");
        } catch (Exception ex) {
            System.out.println("LOG ==> " + ex.getMessage());
            showMessage(FacesMessage.SEVERITY_ERROR, "remove user", "fail");
        }
    }

    public Integer finditemProductAmount(Product product) {
        return itemService.sumAmountByProduct(product);
    }

    public void onSelect() {
        product = productLazy.getRowData();
    }

    public void onSelectLazyLoad() {
        onSelect();
        getItemLazy().setProduct(product);
        selectedProductId = product.getId();
    }

    public void reset() {
        categories = categoryService.findAllOrderByName();
        selectedLabel="All";
        filterProductCategories();
        selectedLabels = new ArrayList<>();
        for(String s :getProductLazy().getCategoryLabels()){
            selectedLabels.add(s);
        }
        if (selectedLabels.contains("root")) {
            selectedLabels.remove("root");
            List<String> tmp = new ArrayList<>();
            tmp.add("All");
            for (String s : selectedLabels) {
                tmp.add(s);
            }
            selectedLabels = tmp;
        }
    }

    public void filterProductCategories() {
        List<String> collectedLabels = new ArrayList<>();
        if (selectedLabel.equals("All")) {
            collectedLabels = collectCategoryLabelsDepthFirstSearch(
                    categoryService.findRoot());
        }
        if (!selectedLabel.equals("All")) {
            collectedLabels = collectCategoryLabelsDepthFirstSearch(
                    categoryService.findByName(selectedLabel));
        }
        getProductLazy().setCategoryLabels(collectedLabels);
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

    public String getSelectedLabel() {
        if (selectedLabel == null) {
            selectedLabel = "";
        }
        return selectedLabel;
    }

    public void setSelectedLabel(String selectedLabel) {
        this.selectedLabel = selectedLabel;
    }

    public List<String> getSelectedLabels() {
        if (selectedLabels == null) {
            selectedLabels = collectCategoryLabelsDepthFirstSearch(categoryService.findRoot());
            if (selectedLabels.contains("root")) {
                selectedLabels.remove("root");
                List<String> tmp = new ArrayList<>();
                tmp.add("All");
                for (String s : selectedLabels) {
                    tmp.add(s);
                }
                selectedLabels = tmp;
            }
        }
        return selectedLabels;
    }

    public void setSelectedLabels(List<String> selectedLabels) {
        this.selectedLabels = selectedLabels;
    }

    public String getCostFilter() {
        if (costFilter == null) {
            costFilter = "";
        }
        return costFilter;
    }

    public void setCostFilter(String costFilter) {
        this.costFilter = costFilter;
    }

    public String getBaseCostFilter() {
        if (baseCostFilter == null) {
            baseCostFilter = "";
        }
        return baseCostFilter;
    }

    public void setBaseCostFilter(String baseCostFilter) {
        this.baseCostFilter = baseCostFilter;
    }

    public String getExpectCostFilter() {
        if (expectCostFilter == null) {
            expectCostFilter = "";
        }
        return expectCostFilter;
    }

    public void setExpectCostFilter(String expectCostFilter) {
        this.expectCostFilter = expectCostFilter;
    }

    public String getCategoryFilter() {
        if (categoryFilter == null) {
            categoryFilter = "";
        }
        return categoryFilter;
    }

    public void setCategoryFilter(String categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public ItemLazyLoad getItemLazy() {
        if (itemLazy == null) {
            itemLazy = new ItemLazyLoad();
        }
        return itemLazy;
    }

    public void setItemLazy(ItemLazyLoad itemLazy) {
        this.itemLazy = itemLazy;
    }

    public String getProductItemUrl() {
        if (productItemUrl == null) {
            productItemUrl = "/product-item/product-item.xhtml";
        }
        return productItemUrl;
    }

    public void setProductItemUrl(String productItemUrl) {
        this.productItemUrl = productItemUrl;
    }

    public ProductLazyLoad getProductLazy() {
        if (productLazy == null) {
            productLazy = new ProductLazyLoad();
        }
        return productLazy;
    }

    public void setProductLazy(ProductLazyLoad productLazy) {
        this.productLazy = productLazy;
    }

    private void showMessage(FacesMessage.Severity severity, String title, String body) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, body));
    }
}
