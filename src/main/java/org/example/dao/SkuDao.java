package org.example.dao;

import org.example.entity.Sku;

import java.util.List;

public interface SkuDao {
    List<Sku> selectAll();
}
