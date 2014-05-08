/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.geniustree.inventory.model.Customer;
import th.co.geniustree.inventory.repo.CustomerRepo;
import th.co.geniustree.inventory.service.CustomerService;

/**
 *
 * @author toy
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public void deleteByName(Customer customer) {
        customerRepo.delete(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    public Page findAll(PageRequest pageRequest) {
        return customerRepo.findAll(pageRequest);
    }

    @Override
    public Page findAllPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<Customer> findByFirstNameLike(String customer) {
        return customerRepo.findByFirstNameLike(customer);
    }
}
