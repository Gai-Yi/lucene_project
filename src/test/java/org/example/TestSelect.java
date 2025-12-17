package org.example;

import org.example.dao.SkuDao;
import org.example.dao.impl.SkuDaoImpl;
import org.example.entity.Sku;
import org.junit.Test;

import java.util.List;


public class TestSelect {

    @Test
    public void testSelectAll() {
        SkuDao skuDao = new SkuDaoImpl();
        List<Sku> skus = skuDao.selectAll();
        for (Sku sku : skus) {
            System.out.println(sku);
        }
    }
}
