/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import th.co.geniustree.inventory.model.Staff;

/**
 *
 * @author toy
 */
public interface StaffRepo extends JpaRepository<Staff, String>{
    public List<Staff> findByFirstNameLike(String staff);
    
}
