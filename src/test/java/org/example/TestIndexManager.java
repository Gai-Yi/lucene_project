package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.example.dao.SkuDao;
import org.example.dao.impl.SkuDaoImpl;
import org.example.entity.Sku;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestIndexManager {

    private static String fileDir;

    @Before
    public void loadProperties() {
        ResourceBundle bundle = ResourceBundle.getBundle("file");
        fileDir = bundle.getString("lucene.dir");
    }


    @Test
    public void createIndexTest() throws IOException {
        SkuDao skuDao = new SkuDaoImpl();
        // 1.采集数据
        List<Sku> skus = skuDao.selectAll();
        // 创建文档集合
        List<Document> documents = new ArrayList<>();
        for (Sku sku : skus) {
            // 2.创建文档对象
            Document document = new Document();

            /*
             * 商品id
             * 是否分词：不分词 不会根据拆散的id去查询数据
             * 是否索引：索引 是否需要使用id来查询数据，是则需要，一般需要
             * 是否存储：存储 id一般在业务上需要确定唯一数据
             */
            document.add(new StringField("id", sku.getId(), Field.Store.YES));

            /*
             * 商品名字
             * 是否分词：分词 需要将名字分词来查询
             * 是否索引：索引 需要通过name来查询数据
             * 是否存储：存储 需要在前台展示name数据
             */
            document.add(new TextField("name", sku.getName(), Field.Store.YES));

            /*
             * 商品价格
             * 是否分词：分词 需要分价格区间查询数据，就必须要分词
             * 是否索引：索引 需要分价格区间查询
             * 是否存储：存储 需要在前台展示price数据
             */
            document.add(new IntPoint("price", sku.getPrice()));
            document.add(new StoredField("price", sku.getPrice()));

            /*
             * 商品数量
             * 是否分词：分词 如果需要根据数量区间查询则进行分词
             * 是否索引：索引 如果需要分数量区间查询
             * 是否存储：存储 需要在前台展示num数据
             */
            document.add(new IntPoint("num", sku.getNum()));
            document.add(new StoredField("price", sku.getPrice()));

            /*
             * 图片
             * 是否分词：不分词 不会根据图片路径去查询
             * 是否索引：不索引 不查询，所以不索引
             * 是否存储：存储 需要通过路径获取图片展示前台
             */
            document.add(new StoredField("image", sku.getImage()));

            /*
             * 分类名称
             * 是否分词：不分词 类型是一个专有名词，不用再划分
             * 是否索引：索引 需要通过类型来查询
             * 是否存储：存储 需要在前台展示分类
             */
            document.add(new StringField("categoryName", sku.getCategoryName(), Field.Store.YES));

            /*
             * 品牌名称
             * 是否分词：不分词 品牌是一个专有名词，不用再划分
             * 是否索引：索引 需要分通过品牌名称查询
             * 是否存储：存储 需要在前台展示品牌名称
             */
            document.add(new StringField("brandName", sku.getBrandName(), Field.Store.YES));

            /*
             * 商品规格
             * 是否分词：不分词 重要搜索字段
             * 是否索引：索引 需要分通过规格查询
             * 是否存储：存储 需要在前台展示规格信息
             */
            document.add(new TextField("spec", sku.getSpec(), Field.Store.YES));

            // 销量字段 - 三种方式结合
            // 1. 用于范围查询
            document.add(new IntPoint("saleNum", sku.getSaleNum()));
            // 2. 用于排序
            document.add(new NumericDocValuesField("saleNumSort", sku.getSaleNum()));
            // 3. 存储原始值用于展示
            document.add(new StoredField("saleNum", sku.getSaleNum()));
            documents.add(document);
        }

        // 3.创建分词器，使用中文分词器IKAnalyzer来处理
        Analyzer analyzer = new IKAnalyzer();
        // 4.创建索引目录对象，索引存放的位置
        Directory directory = FSDirectory.open(Paths.get(fileDir));
        // 5.创建索引写入配置对象，指定使用的分词器
        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
        // 6.创建索引写入对象，指定索引目录和写入配置对象
        IndexWriter writer = new IndexWriter(directory, writerConfig);
        // 7.写入文档到索引库
        for (Document document : documents) {
            writer.addDocument(document);
        }
        // 8.关闭索引写入对象
        writer.close();
    }
}
