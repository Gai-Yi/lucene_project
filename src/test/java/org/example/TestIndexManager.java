package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.example.dao.SkuDao;
import org.example.dao.impl.SkuDaoImpl;
import org.example.entity.Sku;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestIndexManager {
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
            // 添加文档对象的值，Field.Store.YES表示是否将值存储在文档对象中
            document.add(new TextField("id", sku.getId(), Field.Store.YES));
            document.add(new TextField("name", sku.getName(), Field.Store.YES));
            document.add(new TextField("price", sku.getPrice().toString(), Field.Store.YES));
            document.add(new TextField("num", sku.getNum().toString(), Field.Store.YES));
            document.add(new TextField("image", sku.getImage(), Field.Store.YES));
            document.add(new TextField("categoryName", sku.getCategoryName(), Field.Store.YES));
            document.add(new TextField("brandName", sku.getBrandName(), Field.Store.YES));
            document.add(new TextField("spec", sku.getSpec(), Field.Store.YES));
            document.add(new TextField("saleNum", sku.getSaleNum().toString(), Field.Store.YES));
            documents.add(document);
        }

        // 3.创建分词器，StanderAnalyzer是Lucene提供的标准分词器，主要用来处理英文
        Analyzer analyzer = new StandardAnalyzer();
        // 4.创建索引目录对象，索引存放的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\java\\project\\lucene_projet\\dir"));
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
