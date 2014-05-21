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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Nook
 */
@Entity
public class Product implements Serializable {

    @Id
    private String id;
    private String name;
    @Column(name = "expect_price")
    private BigDecimal expectPrice;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    private BigDecimal cost;

    @ManyToOne(targetEntity = Category.class)
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPackage> packages;
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductItem> productItems;
    @Transient
    private Integer sumAmount;

    public List<ProductItem> getProductItems() {
        if (productItems==null){
            productItems = new ArrayList<>();
        }
        return productItems;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    public Category getCategory() {
        if(category==null){
            category = new Category();
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<ProductPackage> getPackages() {
        if (packages == null) {
            packages = new ArrayList<>();
        }
        return packages;
    }

    public void setPackages(List<ProductPackage> packages) {
        this.packages = packages;
    }

    public Integer getSumAmount() {
        List<ProductItem> items = getProductItems();
        sumAmount=0;
        for (ProductItem item : items){
            sumAmount = sumAmount+item.getAmount();
        }
        return sumAmount;
    }
    
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

}
