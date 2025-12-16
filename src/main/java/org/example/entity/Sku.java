package org.example.entity;

import lombok.Data;

@Data
public class Sku {
    private String id;
    private String name;
    private Integer price;
    private Integer num;
    private String image;
    private String categoryName;
    private String brandName;
    private String spec;
    private Integer saleNum;
}
