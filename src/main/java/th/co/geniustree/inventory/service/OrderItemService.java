/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import th.co.geniustree.inventory.model.OrderItem;

/**
 *
 * @author toy
 */
public interface OrderItemService {
     public OrderItem save(OrderItem orderItem);

    public void deleteByName(OrderItem orderItem);

    public List<OrderItem> findAll();
    
    public List<OrderItem> findByIdLike(Integer orderItem);

    public Page findAll(PageRequest pageRequest);
    public Page findAllPage(PageRequest pageRequest);
    
    public Page<OrderItem> findAll(Pageable page);
    
}
