/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import th.co.geniustree.inventory.model.Customer;

/**
 *
 * @author toy
 */
public interface CustomerRepo extends JpaRepository<Customer, String>{
    public List<Customer> findByFirstNameLike(String customer);
    public Page<Customer> findAll(Pageable page);
}
