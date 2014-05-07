/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.util;

import javax.servlet.ServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author anonymous
 */
public class JSFSpringUtils {

    public static <T> T getBean(Class<T> clazz) {
        return JSFSpringUtils.getBean(JSFUtils.getServletContext(), clazz);
    }

    public static Object getBean(String bean) {
        return JSFSpringUtils.getBean(JSFUtils.getServletContext(), bean);
    }

    public static <T> T getBean(ServletContext servletContext, Class<T> clazz) {
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(clazz);
    }

    public static Object getBean(ServletContext servletContext, String bean) {
        return WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(bean);
    }
}
