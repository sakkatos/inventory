/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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

    private final CustomerService custerService = getCustomerManagedBean();
    
    @PostConstruct
    public void CustomerControl() {

    }
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
}
