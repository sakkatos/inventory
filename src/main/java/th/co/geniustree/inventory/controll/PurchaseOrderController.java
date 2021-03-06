/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import th.co.geniustree.inventory.lazyload.PurchaseOrderLazyLoad;
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
public class PurchaseOrderController implements Serializable {

    private PurchaseOrder purchaseOrder;
    private List<PurchaseOrder> purchaseOrders;
    private PurchaseOrderLazyLoad purchaseOrderLazyLoad;
    private List<OrderItem> orderItems;
    private String keyword;
    private String purchaseOrderId;
    private static final Logger LOG = Logger.getLogger(PurchaseOrderController.class.getName());
    private final PurchaseOrderService purchaseOrderService = JSFSpringUtils.getBean(PurchaseOrderService.class);
    private final OrderItemService orderItemService = JSFSpringUtils.getBean(OrderItemService.class);

    @PostConstruct
    public void PurchaseOderController() {
        reset();
    }

    public void reset() {
        purchaseOrders = purchaseOrderService.findAll();
    }

    public void lazyLoad() {
        purchaseOrderLazyLoad = new PurchaseOrderLazyLoad();
    }

    public void onCreate() {
        purchaseOrder = new PurchaseOrder();
    }

    public void onSave() {
        try {
            purchaseOrder.setSaleDate(Calendar.getInstance().getTime());
            purchaseOrder.setSaleTime(Calendar.getInstance().getTime());
            purchaseOrder = purchaseOrderService.save(purchaseOrder);
            showMessage(FacesMessage.SEVERITY_INFO, "เพิ่มข้อมูล", "สำเร็จ");
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "เพิ่มข้อมูล", "ไม่สำเร็จ");
        }

    }

//    public void onSave() {
//        try {
//            purchaseOrder = purchaseOrderService.save(purchaseOrder);
//            showMessage(FacesMessage.SEVERITY_INFO, "save user", "success");
//        } catch (Exception ex) {
//            LOG.log(Level.INFO, ex.getMessage(), ex);
//            showMessage(FacesMessage.SEVERITY_ERROR, "save user", "fail");
//        }
//
//    }
    public void onDelete() {
        purchaseOrderService.deleteByName(purchaseOrder);
        showMessage(FacesMessage.SEVERITY_INFO, "ลบข้อมูล", "สำเร็จ");
    }

    public void onSelect() {
        purchaseOrder = this.getPurchaseOrderLazyLoad().getRowData(purchaseOrderId);
    }

    public List<OrderItem> onSelectOrderItem(){
        purchaseOrder = this.getPurchaseOrderLazyLoad().getRowData(purchaseOrderId);
        orderItems = orderItemService.findByPurchaseOrderLike(purchaseOrder);
        return orderItems;
    }
//    public void onSelect() {
//        String p = requestParam("purchaseOrderId");
//        int indexOf = this.getPurchaseOrders().indexOf(new PurchaseOrder(p));
//        purchaseOrder = this.getPurchaseOrders().get(indexOf);
//    }
//    public void onSelect() {
//        String p = purchaseOrderId;
//        Integer id = Integer.valueOf(p);
//        int indexOf = this.getPurchaseOrders().indexOf(new PurchaseOrder(id));
//        purchaseOrder = this.getPurchaseOrders().get(indexOf);
//    }

    public List<PurchaseOrder> findAllPurchaseOrder() {
        return purchaseOrderService.findAll();
    }

    //------------------------------------------------------------------------------------------------------
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public PurchaseOrderLazyLoad getPurchaseOrderLazyLoad() {
        return purchaseOrderLazyLoad;
    }

    public void setPurchaseOrderLazyLoad(PurchaseOrderLazyLoad purchaseOrderLazyLoad) {
        this.purchaseOrderLazyLoad = purchaseOrderLazyLoad;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        if (purchaseOrders == null) {
            purchaseOrders = new ArrayList<>();
        }
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    private void showMessage(FacesMessage.Severity severity, String title, String body) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, body));
    }

    private String requestParam(String paramName) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(paramName);
    }
}
