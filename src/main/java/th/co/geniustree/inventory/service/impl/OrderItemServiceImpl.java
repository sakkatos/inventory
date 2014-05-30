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
import th.co.geniustree.inventory.model.OrderItem;
import th.co.geniustree.inventory.model.PurchaseOrder;
import th.co.geniustree.inventory.repo.OrderItemRepo;
import th.co.geniustree.inventory.service.OrderItemService;

/**
 *
 * @author toy
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepo.save(orderItem);
    }

    @Override
    public void deleteByName(OrderItem orderItem) {
        orderItemRepo.delete(orderItem);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepo.findAll();
    }

    @Override
    public List<OrderItem> findByIdLike(Integer orderItem) {
        return orderItemRepo.findByIdLike(orderItem);
    }

    @Override
    public Page findAll(PageRequest pageRequest) {
        return orderItemRepo.findAll(pageRequest);
    }

    @Override
    public Page findAllPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<OrderItem> findAll(Pageable page) {
        return orderItemRepo.findAll(page);
    }

    @Override
    public List<OrderItem> findByPurchaseOrderLike(PurchaseOrder purchaseOrder) {
        return orderItemRepo.findByPurchaseOrderLike(purchaseOrder);
    }

}
