/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import th.co.geniustree.inventory.model.Category;

/**
 *
 * @author Nook
 */
public interface CategoryRepo extends JpaRepository<Category, Integer>{
    
    @Query("SELECT c FROM Category c ORDER BY c.name")
    public List<Category> findAllOrderByName();
    
    @Query("SELECT c FROM Category c WHERE c.parent=?1")
    public List<Category> findByParent(Category category);
    
    @Query("SELECT c FROM Category c WHERE c.name = :name")
    public Category findByName(@Param("name")String name);
    
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name%")
    public List<Category> searchByName(@Param("name")String name);
    
    @Query("SELECT c FROM Category c WHERE c.name NOT IN ('root') ORDER BY c.name")
    public List<Category> findAllExceptRoot();
    
}
