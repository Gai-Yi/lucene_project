package org.example.dao.impl;

import org.example.dao.SkuDao;
import org.example.entity.Sku;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SkuDaoImpl implements SkuDao {

    @Override
    public List<Sku> selectAll() {

        ResourceBundle bundle = ResourceBundle.getBundle("db");
        String url = bundle.getString("jdbc.url");
        String driver = bundle.getString("jdbc.driver");
        String username = bundle.getString("jdbc.username");
        String password = bundle.getString("jdbc.password");

        // 获取连接
        Connection connection = null;
        // 获取预处理语句对象
        PreparedStatement statement = null;
        // 获取结果集对象
        ResultSet resultSet = null;
        // 创建返回值列表
        List<Sku> skus = new ArrayList<>();

        try {
            // 加载驱动
            Class.forName(driver);
            // 获取连接
            connection = DriverManager.getConnection(
                    url,
                    username, password);
            // 获取预处理语句对象
            statement = connection.prepareStatement("select * from tb_sku");
            // 执行查询
            resultSet = statement.executeQuery();
            // 从结果集中获取数据
            while (resultSet.next()) {
                Sku sku = new Sku();
                sku.setId(resultSet.getString("id"));
                sku.setName(resultSet.getString("name"));
                sku.setPrice(resultSet.getInt("price"));
                sku.setNum(resultSet.getInt("num"));
                sku.setImage(resultSet.getString("image"));
                sku.setCategoryName(resultSet.getString("category_name"));
                sku.setBrandName(resultSet.getString("brand_name"));
                sku.setSpec(resultSet.getString("spec"));
                sku.setSaleNum(resultSet.getInt("sale_num"));
                skus.add(sku);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return skus;
    }
}
