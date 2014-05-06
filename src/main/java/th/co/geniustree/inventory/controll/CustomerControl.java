/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;
import th.co.geniustree.inventory.model.Customer;
import th.co.geniustree.inventory.service.CustomerService;

/**
 *
 * @author toy
 */
@ManagedBean
@SessionScoped
public class CustomerControl implements Serializable {

    private Customer customer;
    private List<Customer> customers;
    private String keyword;
    private static final Logger LOG = Logger.getLogger(CustomerControl.class.getName());
    private final CustomerService customerService = getCustomerManagedBean();

    @PostConstruct
    public void CustomerControl() {
    }

    public void onCreateCustomer() {
        customer = new Customer();
    }

    public void onEditCustomer() {
        customerService.save(customer);
    }

    public void onSaveCustomer() {
        try {
            customer = customerService.save(customer);
            showMessage(FacesMessage.SEVERITY_INFO, "save user", "success");
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage(), ex);
            showMessage(FacesMessage.SEVERITY_ERROR, "save user", "fail");
        }
    }

    public void onDeleteCustomer() {
        customerService.remove(customer);
        customers = findAllCustomer();
    }

    public void onSelectCustomer() {
        Customer c = new Customer();
        c.setId(requestParam("customerId"));
        customer = getCustomers().get(getCustomers().indexOf(c));
    }

    public List<Customer> findAllCustomer() {
        return customerService.findAll();
    }

    private String requestParam(String paramName) {
        return FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get(paramName);
    }
    //----------------------------------------------------------------------------

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {
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

    public CustomerService getCustomerManagedBean() {
        ServletContext servletContext = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance()).getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(CustomerService.class);
    }

    private void showMessage(FacesMessage.Severity severity, String title, String body) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, title, body));
    }

}
