package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class TestIndexSearch {
    @Test
    public void testIndexSearch() throws ParseException, IOException {
        // 1.创建Query搜索对象
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建搜索解析器，参数分别是默认解析的域和分词器
        QueryParser queryParser = new QueryParser("name", analyzer);
        // 创建搜索对象，值为解析的搜索条件
        Query query = queryParser.parse("华为手机");
//        Query query = queryParser.parse("name:手机 AND 华为");

        // 2.创建Directory流对象，声明索引库位置，相当于是一个文件夹的操作对象
        // FSDirectory是一个抽象类，open方法可以自动根据当前系统创建合适的实现类，然后调用对应的方法来读取和写入索引库
        Directory directoryReader = FSDirectory.open(Paths.get("D:\\java\\project\\lucene_projet\\dir"));

        // 3.创建索引读取对象IndexReader，根据刚刚创建Directory对象读取
        IndexReader indexReader = DirectoryReader.open(directoryReader);

        // 4.创建索引搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 5.使用索引搜索对象，参数为搜索的对象和返回的条数
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 获取查询结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        // 6.解析结果集
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            // 根据文档id从搜索对象里获取对应的文档
            Document doc = indexSearcher.doc(docId);
            System.out.println("docId: " + docId);
            System.out.println("id: " + doc.get("id"));
            System.out.println("name: " + doc.get("name"));
            System.out.println("price: " + doc.get("price"));
            System.out.println("num: " + doc.get("num"));
            System.out.println("image: " + doc.get("image"));
            System.out.println("categoryName: " + doc.get("categoryName"));
            System.out.println("brandName: " + doc.get("brandName"));
            System.out.println("spec: " + doc.get("spec"));
            System.out.println("saleNum: " + doc.get("saleNum"));
        }
        indexReader.close();
    }
}
