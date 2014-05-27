/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.lazyload;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import th.co.geniustree.inventory.model.OrderItem;
import th.co.geniustree.inventory.service.OrderItemService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
public class OrderItemLazyLoad extends LazyLoad<OrderItem> {
    private final OrderItemService orderItemService = JSFSpringUtils.getBean(OrderItemService.class);

    @Override
    public Page<OrderItem> load(Pageable page) {
        return orderItemService.findAll(page);
    }

    public int indexOf(OrderItem orderItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
