/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.lazyload;

import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import th.co.geniustree.inventory.model.Customer;
import th.co.geniustree.inventory.service.CustomerService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
public class CustomerLazyLoad extends LazyDataModel<Customer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerLazyLoad.class);
    private List<Customer> customers;
    private long totalElements;

    private final CustomerService customerService = JSFSpringUtils.getBean(CustomerService.class);

    public CustomerLazyLoad(){ 
    }
    
   @Override
    public Customer getRowData(String id) {
        for (Customer customer : customers) {
            if (customer.getId().toString().equals(id)) {
                return customer;
            }
        }

        return null;
    }

    @Override
    public void setRowIndex(final int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    @Override
    public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        Sort.Direction direction;
        if (sortOrder == SortOrder.ASCENDING) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        Page page = customerService.findAll(new PageRequest(first / pageSize, pageSize, direction, sortField));
        customers = page.getContent();
        totalElements = page.getTotalElements();
        this.setRowCount((int) page.getTotalElements());
//        for(Customer c: customers){
//            System.out.println(c.getFirstName());
//        }
        return customers;
    }

    public List<Customer> getContents() {
        return customers;
    }

    public long getTotalElements() {
        return totalElements;
    }

}
