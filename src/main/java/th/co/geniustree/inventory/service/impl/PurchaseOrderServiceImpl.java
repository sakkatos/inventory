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
import th.co.geniustree.inventory.model.PurchaseOrder;
import th.co.geniustree.inventory.repo.PurchaseOrderRepo;
import th.co.geniustree.inventory.service.PurchaseOrderService;

/**
 *
 * @author toy
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PurchaseOrderServiceImpl implements PurchaseOrderService{
    
    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepo.save(purchaseOrder);
    }

    @Override
    public void deleteByName(PurchaseOrder purchaseOrder) {
        purchaseOrderRepo.delete(purchaseOrder);
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepo.findAll();
    }

    @Override
    public Page findAll(PageRequest pageRequest) {
        return purchaseOrderRepo.findAll(pageRequest);
    }

    @Override
    public Page findAllPage(PageRequest pageRequest) {
        return null;
    }

//    @Override
//    public List<PurchaseOrder> findByFirstNameLike(PurchaseOrder purchaseOrder) {
//        return purchaseOrderRepo.findByFirstNameLike(purchaseOrder);
//    }
    
}
