/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import th.co.geniustree.inventory.lazyload.CustomerLazyLoad;

/**
 *
 * @author toy
 */
@ManagedBean
@SessionScoped
public class CustomerWithPaging  implements Serializable{

    private static final Logger LOG = LoggerFactory.getLogger(CustomerWithPaging.class);

    private CustomerLazyLoad customerLoadLazy;
    
    @PostConstruct
    public void postConstruct() {
        reset();
    }

    public CustomerLazyLoad getCustomerLoadLazy() {
        return customerLoadLazy;
    }

    public void setCustomerLoadLazy(CustomerLazyLoad customerLoadLazy) {
        this.customerLoadLazy = customerLoadLazy;
    }

    private void reset() {
        customerLoadLazy = new CustomerLazyLoad();

    }

}