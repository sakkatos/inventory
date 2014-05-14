/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public Category findRoot() {
        Category category = new Category();
        category.setId("1");
        category = categoryRepo.findOne(category.getId());
        if (category == null) {
            category = new Category();
            category.setId("1");
            category.setName("root");
            categoryRepo.save(category);
        }
        return category;
    }

    public List<Category> findAllOrderByName() {
        return categoryRepo.findAllOrderByName();
    }

    public void save(Category category) {
        categoryRepo.save(category);
    }

    public Category findOne(String id) {
        return categoryRepo.findOne(id);
    }
    
    public List<Category> findByParent (Category parent){
        return categoryRepo.findByParent(parent);
    }
    
    public void saveCategories(List<Category> categories){
        categoryRepo.save(categories);
    }
    
    public void remove(Category category){
        Category root = new Category();
        root.setId("1");root.setName("root");
        List<Category> cList = categoryRepo.findByParent(category);
        for (Category c :cList){
            c.setParent(root);
        }
        categoryRepo.save(cList);
        categoryRepo.delete(category);
    }
    
}
