/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import th.co.geniustree.inventory.model.Customer;
import th.co.geniustree.inventory.repo.CustomerRepo;

/**
 *
 * @author toy
 */
public class CustomerService {
    @Autowired
    public CustomerRepo customerRepo; 
    
    public void save(Customer customer) {
        customerRepo.save(customer);
    }
    
}
