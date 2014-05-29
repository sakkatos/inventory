/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import th.co.geniustree.inventory.model.OrderItem;
import th.co.geniustree.inventory.model.PurchaseOrder;

/**
 *
 * @author toy
 */
public interface PurchaseOrderService {
    public PurchaseOrder save(PurchaseOrder purchaseOrder);

    public void deleteByName(PurchaseOrder purchaseOrder);

    public List<PurchaseOrder> findAll();
    
//    public List<PurchaseOrder> findByFirstNameLike(PurchaseOrder purchaseOrder);

//    public Page findAll(PageRequest pageRequest);
//    public Page findAllPage(PageRequest pageRequest);
    
    public Page<PurchaseOrder> findAll(Pageable page);
    
}
