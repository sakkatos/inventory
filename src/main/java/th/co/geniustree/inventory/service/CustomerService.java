/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import th.co.geniustree.inventory.model.Customer;

/**
 *
 * @author toy
 */

public interface CustomerService {
    public Customer save(Customer customer);

    public void deleteByName(Customer customer);

    public List<Customer> findAll();
    
    public List<Customer> findByNameLike(String customer);

    public Page findAll(PageRequest pageRequest);
    public Page findAllPage(PageRequest pageRequest);
    
}
