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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
public class BarcodeController implements Serializable {

    private final ProductService productService = JSFSpringUtils.getBean(ProductService.class);
    private final PackageService packageService = JSFSpringUtils.getBean(PackageService.class);
    private final CategoryService categoryService = JSFSpringUtils.getBean(CategoryService.class);

    private ProductPackage pack;
    private Product product;
    private List<Product> products;
    private Category category;
    private List<Category> categories;

    private String selectedBarcode;
    private String selectedLabel;
    private List<String> selectedLabels;
    private Boolean redirect;
    private Boolean requestNewBarcode;

    @PostConstruct
    public void postConstruct() {
    }

    //business logic------------------------------------------------------------
    public void onCreate() {
        product = new Product();
        pack = new ProductPackage();
    }

    public void onSave() {
        product = productService.findOne(product.getId());

        pack.setProduct(product);
        packageService.savePackage(pack);

        product.getPackages().add(pack);
        productService.save(product);

        redirect=true;
    }

    public String onredirect() {
        if (isRedirect()){
            redirect=false;
            return "add-product-item.xhtml?selectedBarcode=" + pack.getBarcode() + "faces-redirect=true";
        }
        return "";
    }

    public void findAllProduct() {
        products = productService.findAll();
    }

    public void reset() {
        if (isRequestNewBarcode()){
            onCreate();
            pack.setBarcode(selectedBarcode);
            requestNewBarcode=false;
        }
        getSelectedLabels();
        categories = categoryService.searchByNameList(selectedLabels);
        products = productService.findAll();
    }

    public void filterCategories() {
        List<String> collectedLabels;
        if (selectedLabel.equals("All")) {
            products = productService.findAll();
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
            products = productService.findAll();
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

    public ProductPackage getPack() {
        if (pack == null) {
            pack = new ProductPackage();
        }
        return pack;
    }

    public void setPack(ProductPackage pack) {
        this.pack = pack;
    }

    public String getSelectedBarcode() {
        return selectedBarcode;
    }

    public void setSelectedBarcode(String selectedBarcode) {
        this.selectedBarcode = selectedBarcode;
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

    public Boolean isRedirect() {
        if (redirect==null){
            redirect=false;
        }
        return redirect;
    }

    public void setRedirect(Boolean redirect) {
        this.redirect = redirect;
    }

    public Boolean isRequestNewBarcode() {
        if (requestNewBarcode==null){
            requestNewBarcode=false;
        }
        return requestNewBarcode;
    }

    public void setRequestNewBarcode(Boolean requestNewBarcode) {
        this.requestNewBarcode = requestNewBarcode;
    }



}
