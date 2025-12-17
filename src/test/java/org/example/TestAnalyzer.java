package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;

public class TestAnalyzer {
    @Test
    public void testStanderAnalyzer() throws IOException {
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer();

        // 要分词的文本
        String text = "shuo is a big sao!";

        // 使用分词器对文本进行分词
        TokenStream tokenStream = analyzer.tokenStream("aaa", text);

        // 将指针归位，不归位会报错
        tokenStream.reset();

        // 获取分词后的结果
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        // 迭代打印分词结果
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }

        // 关闭资源
        tokenStream.close();
    }

    @Test
    public void testWhitespaceAnalyzer() throws IOException {
        // 创建分词器
        Analyzer analyzer = new WhitespaceAnalyzer();

        // 要分词的文本
        String text = "shuo is a big sao!";

        // 使用分词器对文本进行分词
        TokenStream tokenStream = analyzer.tokenStream("aaa", text);

        // 将指针归位，不归位会报错
        tokenStream.reset();

        // 获取分词后的结果
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        // 迭代打印分词结果
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }

        // 关闭资源
        tokenStream.close();
    }

    @Test
    public void testSimpleAnalyzer() throws IOException {
        // 创建分词器
        Analyzer analyzer = new SimpleAnalyzer();

        // 要分词的文本
        String text = "shuo is a big sao!";

        // 使用分词器对文本进行分词
        TokenStream tokenStream = analyzer.tokenStream("aaa", text);

        // 将指针归位，不归位会报错
        tokenStream.reset();

        // 获取分词后的结果
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        // 迭代打印分词结果
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }

        // 关闭资源
        tokenStream.close();
    }

    @Test
    public void testIKAnalyzer() throws IOException {
        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();

        // 要分词的文本
        String text = "铄是一个巨大的马叉虫！";
        String text1 = "果子手机";

        // 使用分词器对文本进行分词
        TokenStream tokenStream = analyzer.tokenStream("aaa", text1);

        // 将指针归位，不归位会报错
        tokenStream.reset();

        // 获取分词后的结果
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        // 迭代打印分词结果
        while (tokenStream.incrementToken()) {
            System.out.println(charTermAttribute.toString());
        }

        // 关闭资源
        tokenStream.close();
    }
}
