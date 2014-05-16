/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.controll;

import java.io.Serializable;
import java.util.ArrayList;
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
    private List<Category> noRootCategories;

    private Integer selectedCategoryId;
    private List<Integer> selectedFilterCategories;
    private String keyword;
    private Integer filterKeyword;
    private List<Integer> filterKeywords;

    @PostConstruct
    public void CategoryController() {
        findRoot();
        categories = findAll();
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
        noRootCategories = findAllExceptRoot();
    }

    public List<Category> findAll() {
        return categoryService.findAllOrderByName();
    }

    public void searchByName() {
        if (keyword.equals("")) {
            noRootCategories = findAllExceptRoot();
        }
        if (!keyword.equals("")) {
            noRootCategories = categoryService.searchByName(keyword);
        }
    }

    public List<Category> findAllExceptRoot() {
        return categoryService.findAllExceptRoot();
    }

    public void onSelect() {
        Category c = new Category();
        c.setId(selectedCategoryId);
        category = getCategories().get(getCategories().indexOf(c));
    }

    public void onremove() {
        changeParentToRoot(category);
        categoryService.remove(category);
        noRootCategories = findAllExceptRoot();
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

    public Category findRoot() {
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
        if (category == null) {
            category = new Category();
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        if (categories == null) {
            categories = findAll();
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

    public List<Category> getNoRootCategories() {
        if (noRootCategories == null) {
            noRootCategories = findAllExceptRoot();
        }
        return noRootCategories;
    }

    public void setNoRootCategories(List<Category> noRootCategories) {
        this.noRootCategories = noRootCategories;
    }

    public String getKeyword() {
        if (keyword == null) {
            keyword = "";
        }
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getSelectedFilterCategories() {
        if (selectedFilterCategories == null) {
            selectedFilterCategories = new ArrayList<>();
        }
        return selectedFilterCategories;
    }

    public void setSelectedFilterCategories(List<Integer> selectedFilterCategories) {
        this.selectedFilterCategories = selectedFilterCategories;
    }

    public Integer getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(Integer filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    public List<Integer> getFilterKeywords() {
        if (filterKeywords==null){
            
            
            
        }
        return filterKeywords;
    }

    public void setFilterKeywords(List<Integer> filterKeywords) {
        this.filterKeywords = filterKeywords;
    }

    
}
