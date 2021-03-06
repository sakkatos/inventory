/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.geniustree.inventory.model.Category;
import th.co.geniustree.inventory.repo.CategoryRepo;

/**
 *
 * @author Nook
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public void save(Category category) {
        categoryRepo.save(category);
    }

    public void saveCategories(List<Category> categories) {
        categoryRepo.save(categories);
    }

    public void remove(Category category) {
        categoryRepo.delete(category);
    }

    public List<Category> searchByName(String name) {
        return categoryRepo.searchByName(name);
    }

    public List<Category> searchByNameList(List<String> nameList) {
        return categoryRepo.searchByNameList(nameList);
    }

    public Category findOne(Integer id) {
        return categoryRepo.findOne(id);
    }

    public Category findRoot() {
        Category root = categoryRepo.findByName("root");
        if (root == null) {
            root = new Category();
            root.setName("root");
            categoryRepo.save(root);
        }
        return root;
    }
    
    public Category findByName(String name){
        return categoryRepo.findByName(name);
    }

    public List<Category> findByParent(Category parent) {
        return categoryRepo.findByParent(parent);
    }

    public List<Category> findAllOrderByName() {
        return categoryRepo.findAllOrderByName();
    }

    public List<Category> findAllExceptRoot() {
        return categoryRepo.findAllExceptRoot();
    }

}
