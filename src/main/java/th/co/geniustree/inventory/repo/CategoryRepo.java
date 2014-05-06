/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import th.co.geniustree.inventory.model.Category;

/**
 *
 * @author Nook
 */
public interface CategoryRepo extends JpaRepository<Category, String>{
    
    
}
