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
    private String selectedLabel;
    private List<String> selectedLabels;

    //business logic------------------------------------------------------------
    public void onCreate() {
        Category root = categoryService.findRoot();
        product = new Product();
        product.setCategory(root);

    }

    public void onSave() {
        category = categoryService.findOne(product.getCategory().getId());

        product.setCategory(category);
        productService.save(product);

        category.getProducts().add(product);
        categoryService.save(category);

        products = findAllProducts();
    }

    public void onEdit() {
        Category newCategory = categoryService.findOne(product.getCategory().getId());
        Category oldCategory = categoryService.findByName(product.getCategory().getName());

        product.setCategory(category);
        productService.save(product);

        newCategory.getProducts().add(product);
        categoryService.save(newCategory);

        oldCategory.getProducts().remove(product);
        categoryService.save(oldCategory);

        products = findAllProducts();

    }

    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    public void onRemove() {
        productService.remove(product);
        products = findAllProducts();
    }

    public Integer finditemProductAmount(Product product) {
        return itemService.sumAmountByProduct(product);
    }

    public void onSelect() {
        Product p = new Product();
        p.setId(selectedProductId);
        product = getProducts().get(getProducts().indexOf(p));
    }

    public void reset() {
        categories=categoryService.findAllOrderByName();
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

    public void filterProductCategories() {
        System.out.println(selectedLabel);
        List<String> collectedLabels = new ArrayList<>();
        if (selectedLabel.equals("All")) {
            products = findAllProducts();
        }
        if (!selectedLabel.equals("All")) {
            collectedLabels = collectCategoryLabelsDepthFirstSearch(
                    categoryService.findByName(selectedLabel));
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

}
