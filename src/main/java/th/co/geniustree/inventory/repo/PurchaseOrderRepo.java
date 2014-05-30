/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import th.co.geniustree.inventory.model.OrderItem;
import th.co.geniustree.inventory.model.PurchaseOrder;

/**
 *
 * @author toy
 */
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {
    public List<PurchaseOrder> findByIdLike(Integer id);    
}
