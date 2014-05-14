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
import th.co.geniustree.inventory.model.Staff;
import th.co.geniustree.inventory.service.StaffService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
@ManagedBean
@SessionScoped
public class StaffController implements Serializable {

    private Staff staff;
    private List<Staff> staffs;
    private String keyword;
    private String staffId;
    private static final Logger LOG = Logger.getLogger(StaffController.class.getName());
    private final StaffService staffService = JSFSpringUtils.getBean(StaffService.class);

    @PostConstruct
    public void StaffController() {
        reset();
    }
    
      public void reset() {
        staffs = staffService.findAll();
    }

    public void onCreate() {
        staff = new Staff();
    }

    public void onSave() {
        try {
            staff = staffService.save(staff);
            showMessage(FacesMessage.SEVERITY_INFO, "save user", "success");
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "save user", "fail");
        }

    }

    public void onDelete() {
        staffService.deleteByName(staff);

        showMessage(FacesMessage.SEVERITY_INFO, "delete user", "success");
    }

    public void onSelect() {
        String s = requestParam("staffId");
        int indexOf = this.getStaffs().indexOf(new Staff(s));
        staff = this.getStaffs().get(indexOf);
    }

//    public void onSelectCustomer() {
//        Customer c = new Customer();
//        c.setId(customerId);
//        customer=getCustomers().get(getCustomers().indexOf(c));
//    }
    public List<Staff> findAllStaff() {
        return staffService.findAll();
    }

    //------------------------------------------------------------------------------------------

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Staff> getStaffs() {
        if (staffs == null) {
            staffs = new ArrayList<>();
        }
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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
