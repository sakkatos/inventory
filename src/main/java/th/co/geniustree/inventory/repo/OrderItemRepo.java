/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import th.co.geniustree.inventory.model.OrderItem;
import th.co.geniustree.inventory.model.PurchaseOrder;

/**
 *
 * @author toy
 */
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{
    public List<OrderItem> findByIdLike(Integer orderItem);
    
     @Query("SELECT oi FROM OrderItem oi WHERE oi.purchaseOrder = ?1")
    public List<OrderItem> findOrderItemByPurchaseOrder(PurchaseOrder purchaseOrder);
    
}
