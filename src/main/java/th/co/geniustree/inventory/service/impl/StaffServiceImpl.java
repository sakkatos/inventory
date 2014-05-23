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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.geniustree.inventory.model.Staff;
import th.co.geniustree.inventory.repo.StaffRepo;
import th.co.geniustree.inventory.service.StaffService;

/**
 *
 * @author toy
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StaffServiceImpl implements StaffService{

    @Autowired
    private StaffRepo staffRepo;
    
    @Override
    public Staff save(Staff staff) {
        return staffRepo.save(staff);
    }

    @Override
    public void deleteByName(Staff staff) {
        staffRepo.delete(staff);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepo.findAll();
    }

    @Override
    public List<Staff> findByFirstNameLike(String staff) {
       return staffRepo.findByFirstNameLike(staff);
    }

//    @Override
//    public Page findAll(PageRequest pageRequest) {
//        return staffRepo.findAll(pageRequest);
//        
//    }
//
//    @Override
//    public Page findAllPage(PageRequest pageRequest) {
//        return null;
//        
//    }

    @Override
    public Page<Staff> findAll(Pageable page) {
        return staffRepo.findAll(page);
    }
    
}
