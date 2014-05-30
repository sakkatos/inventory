/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import th.co.geniustree.inventory.lazyload.OrderItemLazyLoad;
import th.co.geniustree.inventory.model.OrderItem;
import th.co.geniustree.inventory.model.PurchaseOrder;
import th.co.geniustree.inventory.service.OrderItemService;
import th.co.geniustree.inventory.service.PurchaseOrderService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
@ManagedBean
@SessionScoped
public class OrderItemController implements Serializable {

    private OrderItem orderItem;
    private List<OrderItem> orderItems;
    private OrderItemLazyLoad orderItemLazyLoad;
    private Integer keyword;
    private String orderItemId;
    private PurchaseOrder purchaseOrder;
    private static final Logger LOG = LoggerFactory.getLogger(OrderItemController.class);
    private final OrderItemService orderItemService = JSFSpringUtils.getBean(OrderItemService.class);

    @PostConstruct
    public void OrderItemController() {
        reset();
    }

    public void reset() {
        orderItems = orderItemService.findAll();
    }

    public void loadLazy() {
        orderItemLazyLoad = new OrderItemLazyLoad();
    }

    public void onCreate() {
        orderItem = new OrderItem();  
        purchaseOrder = new PurchaseOrder();
    }

    public void onSave() {
        try {
            orderItem.setPurchaseOrder(purchaseOrder);
            orderItem = orderItemService.save(orderItem);         
            LOG.info("################################################"+orderItem.getId());            
            LOG.info("################################################"+purchaseOrder.getId());
            showMessage(FacesMessage.SEVERITY_INFO, "save user", "success");
        } catch (Exception ex) {
            LOG.info("########################### ERROR -->"+ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "save user", "fail");
        }
    }

    public void onDelete() {
        orderItemService.deleteByName(orderItem);
        showMessage(FacesMessage.SEVERITY_INFO, "delete user", "success");
    }

    public void onSelect() {
        orderItem = this.getOrderItemLazyLoad().getRowData(orderItemId);
        LOG.info("----------------------------------------------------->"+orderItem.getId());
    }
//    public void onSelect() {
//        String o = requestParam("orderItemId");
//        int indexOf = this.getOrderItems().indexOf(new OrderItem(o));
//        orderItem = this.getOrderItems().get(indexOf);
//    }
//    public void onSelect() {
//        String o = orderItemId;
//        Integer id = Integer.valueOf(o);
//        int indexOf = this.getOrderItems().indexOf(new OrderItem(id));
//        orderItem = this.getOrderItems().get(indexOf);
//    }

    public List<OrderItem> findAllOrderItems() {
        return orderItemService.findAll();
    }

    public List<OrderItem> onSearch() {
        orderItems = orderItemService.findByIdLike(keyword);
        return orderItems;
    }

    //------------------------------------------------------------------------------------------
    
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public OrderItemLazyLoad getOrderItemLazyLoad() {
        return orderItemLazyLoad;
    }

    public void setOrderItemLazyLoad(OrderItemLazyLoad orderItemLazyLoad) {
        this.orderItemLazyLoad = orderItemLazyLoad;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List<OrderItem> getOrderItems() {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getKeyword() {
        return keyword;
    }

    public void setKeyword(Integer keyword) {
        this.keyword = keyword;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    private String requestParam(String paramName) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(paramName);
    }

    private void showMessage(FacesMessage.Severity severity, String title, String body) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, body));
    }

}
