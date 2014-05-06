/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package th.co.geniustree.inventory.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Nook
 */
@Entity
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    private Integer amount;
    @Column(name = "expect_price")
    private BigDecimal expectPrice;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    private BigDecimal cost;
    private String image;
    
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
    @ManyToOne(targetEntity = Category.class)
    private Category category;
    @OneToMany
    private List<Package> packages;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        if (amount==null){
            amount =0;
        }
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getExpectPrice() {
        return expectPrice;
    }

    public void setExpectPrice(BigDecimal expectPrice) {
        this.expectPrice = expectPrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<OrderItem> getOrderItems() {
        if (orderItems == null){
            orderItems = new ArrayList<>();
        }
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Package> getPackages() {
        if (packages==null){
            packages = new ArrayList<>();
        }
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
    
    
    
}
