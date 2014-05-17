/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nook
 */
public class BackBean {
    
    private String name;
    private List<BackBean> children;

    public String getName() {
        if (name==null){
            name="";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BackBean> getChildren() {
        if (children==null){
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<BackBean> children) {
        this.children = children;
    }
    
    
    
}
