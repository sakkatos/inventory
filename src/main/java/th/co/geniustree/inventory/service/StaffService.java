/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import th.co.geniustree.inventory.model.Staff;

/**
 *
 * @author toy
 */
public interface StaffService {
    public Staff save(Staff staff);

    public void deleteByName(Staff staff);

    public List<Staff> findAll();
    
    public List<Staff> findByFirstNameLike(String staff);

    public Page findAll(PageRequest pageRequest);
    public Page findAllPage(PageRequest pageRequest);
    
}
