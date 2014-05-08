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
import th.co.geniustree.inventory.model.Customer;
import th.co.geniustree.inventory.service.CustomerService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author toy
 */
@ManagedBean
@SessionScoped
public class CustomerController implements Serializable {

    private Customer customer;
    private List<Customer> customers;
    private String keyword;
    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());
    private final CustomerService customerService = JSFSpringUtils.getBean(CustomerService.class);
    private String customerId;

    @PostConstruct
    public void CustomerController() {
        reset();
    }

    public void reset() {
        customers = customerService.findAll();
    }

    public void onCreate() {
        customer = new Customer();
    }

    public void onSave() {
        try {
            customer = customerService.save(customer);
            showMessage(FacesMessage.SEVERITY_INFO, "save user", "success");
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "save user", "fail");
        }
    }

    public void onDelete() {
        customerService.deleteByName(customer);

        showMessage(FacesMessage.SEVERITY_INFO, "delete user", "success");
    }

    public void onSelect() {
        String c = requestParam("customerId");
        int indexOf = this.getCustomers().indexOf(new Customer(c));
        customer = this.getCustomers().get(indexOf);
    }

//    public void onSelect() {
//        Customer c = new Customer();
//        c.setId(customerId);
//        customer=getCustomers().get(getCustomers().indexOf(c));
//    }
    public List<Customer> findAllCustomer() {
        return customerService.findAll();
    }

    public List<Customer> onSearch() {
        if(keyword==null){
            System.out.println("Noooooooooooooooooooo");
            
        }
        customers = customerService.findByFirstNameLike(keyword);
        return customers;
    }

    //----------------------------------------------------------------------------

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {

        if (customers == null) {
            customers = new ArrayList<>();
        }

        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    private String requestParam(String customerId) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(customerId);
    }

    private void showMessage(FacesMessage.Severity severity, String title, String body) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, body));
    }

}
