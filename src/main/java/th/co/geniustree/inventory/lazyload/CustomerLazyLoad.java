/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.lazyload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import th.co.geniustree.inventory.model.Customer;
import th.co.geniustree.inventory.service.CustomerService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
public class CustomerLazyLoad extends LazyLoad<Customer>{
    private final CustomerService customerService = JSFSpringUtils.getBean(CustomerService.class);

    @Override
    public Page<Customer> load(Pageable page) {
        return customerService.findAll(page);
    }
    
}
