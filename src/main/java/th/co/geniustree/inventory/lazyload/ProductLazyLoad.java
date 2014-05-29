/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.lazyload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import th.co.geniustree.inventory.model.Product;
import th.co.geniustree.inventory.service.ProductService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author Nook
 */
public class ProductLazyLoad extends LazyDataModel<Product> {

    private final ProductService productService = JSFSpringUtils.getBean(ProductService.class);
    private Product product;
    private List<Product> products;
    private List<String> categoryLabels;
    private long totalElements;

    public Page<Product> load(Pageable page) {
        if (!getCategoryLabels().isEmpty()) {
            for (String s : getCategoryLabels()) {
                System.out.println(s);
            }
        }

        if (getCategoryLabels().contains("root")) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Find All!!");
            }
            return productService.findAll(page);
        }

        return productService.searchProductByCategoryName(getCategoryLabels(), page);
    }

    @Override
    public List<Product> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        Sort.Direction direction;
        if (sortOrder == SortOrder.ASCENDING) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        if (pageSize == 0) {
            pageSize = 10;
        }
        if (sortField == null) {
            sortField = "id";
        }
        if (sortField.isEmpty()) {
            sortField = "id";
        }

        Page<Product> page = load(new PageRequest(first / pageSize, pageSize, direction, sortField));

        if (page != null) {
            products = page.getContent();
            totalElements = page.getTotalElements();
            this.setRowCount((int) totalElements);
        } else {
            products = new ArrayList<>();
            totalElements = 0L;
            setRowCount(0);
        }
        return products;
    }

    @Override
    public Product getRowData(String rowKey) {
        Product itemRow = new Product();
        itemRow.setId(rowKey);
        return getProducts().get(getProducts().indexOf(itemRow));
    }

    @Override
    public String getRowKey(Product item) {
        return product.getId();
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

    public List<String> getCategoryLabels() {
        if (categoryLabels == null) {
            categoryLabels = new ArrayList<>();
        }
        return categoryLabels;
    }

    public void setCategoryLabels(List<String> categoryLabels) {
        this.categoryLabels = categoryLabels;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

}
