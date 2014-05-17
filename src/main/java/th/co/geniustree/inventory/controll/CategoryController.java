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
import th.co.geniustree.inventory.model.BackBean;
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
    private String keyword;
    private String selectedLabel;
    private List<String> selectedLabels;
    private List<String> categoryLabels;
    private BackBean backBean;
    private List<BackBean> backBeans;

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

    public void getTreeCategories() {
        Category root = findRoot();
        BackBean bkBean = new BackBean();
        bkBean.setName(root.getName());
        bkBean.setChildren(recursiveGetChildren(root.getChildren()));
        backBeans = bkBean.getChildren();
    }

    public List<BackBean> recursiveGetChildren(List<Category> children) {
        List<BackBean> bList = new ArrayList<>();
        if (children != null) {
            BackBean b;
            for (Category eachChild : children) {
                b = new BackBean();
                b.setName(eachChild.getName());
                b.setChildren(recursiveGetChildren(eachChild.getChildren()));
                bList.add(b);
            }
        }
        return bList;
    }

    public void filterCategories() {
        int indexOf = getCategories().indexOf(category);
        Category c = getCategories().get(indexOf);
        categoryLabels = collectCategoryLabels(c);
        noRootCategories = categoryService.searchByNameList(categoryLabels);
    }

    public List<String> collectCategoryLabels(Category c) {
        List<String> labelList = new ArrayList<>();
        labelList.add(c.getName());
        List<String> childLabelList = recursiveGetLabel(c.getChildren());
        for (String label : childLabelList) {
            labelList.add(label);
        }
        return labelList;
    }

    public List<String> recursiveGetLabel(List<Category> children) {
        List<String> labelList = new ArrayList<>();
        if (children != null) {
            for (Category c : children) {
                labelList.add(c.getName());
                List<String> childlabelList = recursiveGetLabel(c.getChildren());
                for (String label : childlabelList) {
                    labelList.add(label);
                }
            }
        }
        return labelList;
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

    public BackBean getBackBean() {
        if (backBean == null) {
            backBean = new BackBean();
        }
        return backBean;
    }

    public void setBackBean(BackBean backBean) {
        this.backBean = backBean;
    }

    public List<BackBean> getBackBeans() {
        if (backBeans == null) {
            backBeans = new ArrayList<>();
        }
        return backBeans;
    }

    public void setBackBeans(List<BackBean> backBeans) {
        this.backBeans = backBeans;
    }

    public List<String> getCategoryLabels() {
        if (categoryLabels == null) {
            categoryLabels = new ArrayList<>();
        }
        return categoryLabels;
    }

    public void setCategoryLabels(List<String> categoryLabels) {
        this.categoryLabels = categoryLabels;
    }

    public String getSelectedLabel() {
        return selectedLabel;
    }

    public void setSelectedLabel(String selectedLabel) {
        this.selectedLabel = selectedLabel;
    }

    public List<String> getSelectedLabels() {
        selectedLabels = collectCategoryLabels(findRoot());
        return selectedLabels;
    }

    public void setSelectedLabels(List<String> selectedLabels) {
        this.selectedLabels = selectedLabels;
    }

    
}
