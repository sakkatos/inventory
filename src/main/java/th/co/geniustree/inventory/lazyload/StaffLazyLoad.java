/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.lazyload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import th.co.geniustree.inventory.model.Staff;
import th.co.geniustree.inventory.service.StaffService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
public class StaffLazyLoad extends LazyLoad<Staff>{
    private final StaffService staffService = JSFSpringUtils.getBean(StaffService.class);

    @Override
    public Page<Staff> load(Pageable page) {
        return staffService.findAll(page);
    }
    
}
