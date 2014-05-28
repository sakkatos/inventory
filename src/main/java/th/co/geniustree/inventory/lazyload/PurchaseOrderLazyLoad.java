/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.lazyload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import th.co.geniustree.inventory.model.PurchaseOrder;
import th.co.geniustree.inventory.service.PurchaseOrderService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
public class PurchaseOrderLazyLoad extends LazyLoad<PurchaseOrder>{
private final PurchaseOrderService purchaseOrderService = JSFSpringUtils.getBean(PurchaseOrderService.class);
    @Override
    public Page<PurchaseOrder> load(Pageable page) {
        return purchaseOrderService.findAll(page);
    }
    
}
