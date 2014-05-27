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
import th.co.geniustree.inventory.model.ProductItem;
import th.co.geniustree.inventory.service.ProductItemService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author Nook
 */
public class ItemLazyLoad extends LazyDataModel<ProductItem> {

    private final ProductItemService itemService = JSFSpringUtils.getBean(ProductItemService.class);

    private Product product;
    private ProductItem item;
    private List<ProductItem> items;
    private long totalElements;

    public Page<ProductItem> load(Pageable page) {
        return itemService.findByProductLazyLoad(getProduct(), page);
    }

    @Override
    public List<ProductItem> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        Sort.Direction direction;
        if (sortOrder == SortOrder.ASCENDING) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        if (pageSize == 0) {
            pageSize = 10;
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "id";
        }

        Page<ProductItem> page = load(new PageRequest(first / pageSize, pageSize, direction, sortField));

        if (page != null) {
            items = page.getContent();
            totalElements = page.getTotalElements();
            this.setRowCount((int) totalElements);
        } else {
            items = new ArrayList<>();
            totalElements = 0L;
            setRowCount(0);
        }
        return items;
    }

    @Override
    public ProductItem getRowData(String rowKey) {
        Integer id = Integer.parseInt(rowKey);
        ProductItem itemRow = new ProductItem();
        return getItems().get(getItems().indexOf(itemRow));
    }

    @Override
    public String getRowKey(ProductItem item) {
        return item.getId().toString();
    }

    public List<ProductItem> getContents() {
        return items;
    }

    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public void setRowIndex(final int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    public Product getProduct() {
        if (product == null) {
            product = new Product();
            product.setId("0");
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductItem getItem() {
        return item;
    }

    public void setItem(ProductItem item) {
        this.item = item;
    }

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

}
