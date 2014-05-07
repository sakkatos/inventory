/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.inventory.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Nook
 */
@Entity
public class ProductPackage implements Serializable {

    @Id
    private String barcode;
    private String name;
    private Integer amountPerPack;

    @ManyToOne
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getAmountPerPack() {
        return amountPerPack;
    }

    public void setAmountPerPack(Integer amountPerPack) {
        this.amountPerPack = amountPerPack;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.barcode);
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
        final ProductPackage other = (ProductPackage) obj;
        if (!Objects.equals(this.barcode, other.barcode)) {
            return false;
        }
        return true;
    }
}
