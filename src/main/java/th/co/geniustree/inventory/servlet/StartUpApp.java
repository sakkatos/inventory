/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.service.CategoryService;

/**
 * Web application lifecycle listener.
 *
 * @author Nook
 */
public class StartUpApp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        CategoryService categoryService = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(CategoryService.class);
        if (categoryService.findRoot()==null){
            Category category = new Category();
            category.setId("1");
            category.setName("root");
            categoryService.save(category);
        };
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
