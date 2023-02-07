package com.cubex.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column()
    private String name;

    @Column()
    private int price;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'USD'")
    private String currency;

    @Column()
    private String image;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isBought;

    @ManyToMany()
    private List<Cart> carts;

    @ManyToMany()
    private List<Purchase> purchases;

    public Product(String name, String image, String currency, int price) {
        this.name = name;
        this.currency = currency;
        this.image = image;
        this.price = price;
    }

}
