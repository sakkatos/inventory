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
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import th.co.geniustree.inventory.model.PurchaseOrder;
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
    private String keyword;
    private String purchaseOrderId;
    private static final Logger LOG = Logger.getLogger(PurchaseOrderController.class.getName());
    private final PurchaseOrderService purchaseOrderService = JSFSpringUtils.getBean(PurchaseOrderService.class);

    @PostConstruct
    public void PurchaseOderController() {
        reset();
    }

    public void reset() {
        purchaseOrders = purchaseOrderService.findAll();
    }

    public void onCreate() {
        purchaseOrder = new PurchaseOrder();
    }

    public void onSave() {
        try {
            purchaseOrder = purchaseOrderService.save(purchaseOrder);
            showMessage(FacesMessage.SEVERITY_INFO, "save user", "success");
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "save user", "fail");
        }

    }

    public void onDelete() {
        purchaseOrderService.deleteByName(purchaseOrder);

        showMessage(FacesMessage.SEVERITY_INFO, "delete user", "success");
    }

//    public void onSelect() {
//        String p = requestParam("purchaseOrderId");
//        int indexOf = this.getPurchaseOrders().indexOf(new PurchaseOrder(p));
//        purchaseOrder = this.getPurchaseOrders().get(indexOf);
//    }
    public void onSelect() {
        String p = requestParam("purchaseOrderId");

        Integer id = Integer.valueOf(p);
        int indexOf = this.getPurchaseOrders().indexOf(new PurchaseOrder(id));
        purchaseOrder = this.getPurchaseOrders().get(indexOf);
    }

    public List<PurchaseOrder> findAllPurchaseOrder() {
        return purchaseOrderService.findAll();
    }

    //------------------------------------------------------------------------------------------------------
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
