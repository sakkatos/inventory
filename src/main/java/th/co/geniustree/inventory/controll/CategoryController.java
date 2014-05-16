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
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.service.CategoryService;
import th.co.geniustree.inventory.util.JSFSpringUtils;

/**
 *
 * @author Nook
 */
@ManagedBean
@SessionScoped
public class CategoryController implements Serializable {

    private final CategoryService categoryService = JSFSpringUtils.getBean(CategoryService.class);

    private Category category;
    private List<Category> categories;

    private Integer selectedCategoryId;
    
    @PostConstruct 
    public void CategoryController(){
        findRoot();
        findAll();
    }

    //business logic------------------------------------------------------------
    public void onCreate() {
        category = new Category();
        category.setParent(findRoot());
    }

    public void onSave() {
        Category parent = categoryService.findOne(category.getParent().getId());
        category.setParent(parent);
        categoryService.save(category);
        findAll();
    }

    public void findAll() {
        categories = categoryService.findAllOrderByName();
    }

    public void onSelect() {
        Category c = new Category();
        c.setId(selectedCategoryId);
        category = getCategories().get(getCategories().indexOf(c));
    }

    public void onremove() {
        changeParentToRoot(category);
        categoryService.remove(category);
        findAll();
    }

    public void changeParentToRoot(Category parent) {
        Category root = findRoot();
        List<Category> cList = categoryService.findByParent(parent);
        if (!cList.isEmpty()) {
            for (Category c : cList) {
                c.setParent(root);
            }
            categoryService.saveCategories(cList);
        }
    }
    
    public Category findRoot(){
        Category root = categoryService.findRoot();
        if (root == null) {
            root = new Category();
            root.setName("root");
            categoryService.save(root);
        }
        return root;
    }

    //getter and setter---------------------------------------------------------
    public Category getCategory() {
        if (category==null){
            category = new Category();
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        if (categories == null) {
            categories = categoryService.findAllOrderByName();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getSelectedCategoryId() {
        if (selectedCategoryId == null) {
            selectedCategoryId = 0;
        }
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Integer selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

}
