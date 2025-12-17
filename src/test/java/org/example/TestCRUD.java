package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class TestCRUD {

    private static String fileDir;

    @Before
    public void loadProperties() {
        ResourceBundle bundle = ResourceBundle.getBundle("file");
        fileDir = bundle.getString("lucene.dir");
    }

    @Test
    public void testInsert() throws IOException {
        // 创建文档对象
        Document document = new Document();
        document.add(new StringField("id", "18327858931", Field.Store.YES));

        document.add(new TextField("name", "米子手机", Field.Store.YES));

        document.add(new IntPoint("price", 4000));
        document.add(new StoredField("price", 4000));

        document.add(new IntPoint("num", 10));
        document.add(new StoredField("num", 10));

        document.add(new StoredField("image", "111"));

        document.add(new StringField("categoryName", "手机", Field.Store.YES));
        document.add(new StringField("brandName", "米子", Field.Store.YES));

        document.add(new TextField("spec", "16g+512GB", Field.Store.YES));

        document.add(new IntPoint("saleNum", 999));
        document.add(new StoredField("saleNum", 999));
        document.add(new NumericDocValuesField("saleNumSort", 999));

        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();

        // 创建索引目录对象
        Directory directory = FSDirectory.open(Paths.get(fileDir));

        // 创建索引写入对象
        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);

        // 索引里添加文档
        indexWriter.addDocument(document);

        indexWriter.close();
    }

    @Test
    public void testUpdate() throws IOException {
        // 创建文档对象
        Document document = new Document();
        document.add(new StringField("id", "18327858931", Field.Store.YES));

        document.add(new TextField("name", "果子手机", Field.Store.YES));

        document.add(new IntPoint("price", 800));
        document.add(new StoredField("price", 800));

        document.add(new IntPoint("num", 10));
        document.add(new StoredField("num", 10));

        document.add(new StoredField("image", "111"));

        document.add(new StringField("categoryName", "手机", Field.Store.YES));
        document.add(new StringField("brandName", "果子", Field.Store.YES));

        document.add(new TextField("spec", "16g+512GB", Field.Store.YES));

        document.add(new IntPoint("saleNum", 999));
        document.add(new StoredField("saleNum", 999));
        document.add(new NumericDocValuesField("saleNumSort", 999));

        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();

        // 创建索引目录对象
        Directory directory = FSDirectory.open(Paths.get(fileDir));

        // 创建索引写入对象
        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);

        // 在索引里修改文档，如果没有找到对因的文档就是新增文档
        indexWriter.updateDocument(new Term("id", "18327858931"), document);

        indexWriter.close();
    }

    @Test
    public void testDelete() throws IOException {
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();

        // 创建索引目录对象
        Directory directory = FSDirectory.open(Paths.get(fileDir));

        // 创建索引写入对象
        IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);

        // 删除指定id的值
        //indexWriter.deleteDocuments(new Term("id", "18327868931"));
        // 删除所有值
        indexWriter.deleteAll();

        indexWriter.close();
    }
}
