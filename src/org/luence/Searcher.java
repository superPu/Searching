package org.luence;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.Scanner;

public class Searcher {
    public static void main(String[] args) throws Exception {
        String indexDir = "/home/pujinwen/index";// 创建索引的目录
        System.out.println("请输入您要搜索的关键字：");
        Scanner scanner = new Scanner(System.in);
        String queryStr = scanner.next();
        Search(indexDir,queryStr);
    }
  //查询索引的方法，传入两个参数一个是创建索引的目录，第二个参数是检索的条件。
    public static  void Search(String indexDir,String queryStr) throws Exception {

        Directory directory=FSDirectory.open(new File(indexDir));

        IndexSearcher searcher=new IndexSearcher(directory);

        QueryParser parser=new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));

        Query query=parser.parse(queryStr);

        TopDocs hits = searcher.search(query, 10);

        long begin = System.currentTimeMillis();

         hits = searcher.search(query, 10);
        long end = System.currentTimeMillis();

        System.out.println("search the word: "+queryStr+".  all search use time is:"+(end-begin)+"。 and get resultnum is : "+hits.totalHits);
        for(ScoreDoc doc :hits.scoreDocs)
        {
            Document document = searcher.doc(doc.doc);
            System.out.println(document.get("filePath"));
        }
    }


}
   
