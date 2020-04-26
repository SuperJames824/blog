package com.blog.lucene;

import com.blog.entity.Blog;
import com.blog.util.DateUtil;
import com.blog.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class BlogIndex {

    private Directory directory;
    private IndexWriter indexWriter;
    private IndexReader indexReader;




    /**
     * 添加索引
     * @param blog
     * @throws IOException
     */
    public void addIndex(Blog blog) throws IOException {
        indexWriter=getWriter();
        Document document=new Document();
        addField(document,blog);
        //将文档对象写入索引库
        indexWriter.addDocument(document);
        indexWriter.close();
    }

    /**
     * 删除索引
     * @param blogId
     * @throws IOException
     */
    public void deleteIndex(String blogId) throws IOException {
        indexWriter=getWriter();
        indexWriter.deleteDocuments(new Term("id",blogId));
        indexWriter.forceMergeDeletes();
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 更新索引
     * 原理就是先删除后添加
     * @param blog
     * @throws IOException
     */
    public void  update(Blog blog) throws IOException {
        indexWriter=getWriter();
        Document document=new Document();
        addField(document,blog);
        indexWriter.updateDocument(new Term("id",String.valueOf(blog.getId())),document);
        indexWriter.close();
    }

    /**
     * 搜索
     * @param q
     * @return
     * @throws Exception
     */
    public List<Blog> searchBlog(String q)
            throws Exception
    {
        directory =  FSDirectory.open(new File("D:\\temp\\blog\\lucene").toPath());
        indexReader = DirectoryReader.open(directory);
        IndexSearcher is = new IndexSearcher(indexReader);
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();


        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);
        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);

        //should表示某一个query可以加,也可以不加
        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

        //执行查询
        TopDocs hits = is.search(booleanQuery.build(), 100);

        //渲染结果
        //Highlighte包含三个主要部分:  1)段划分器: fragmenter   2)计分器:Scorer  3)格式化器：Formatter
        //1.1算分,算出得分高的片段
        QueryScorer scorer = new QueryScorer(query);
        //1.2显示得分高的片段   减少片断的默认大小
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        //2.指定高亮格式 变红变粗
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        //3.执行高亮
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);//第一个参数是对查到的结果进行实例化；第二个是片段得分（显示得分高的片段，即摘要）
        highlighter.setTextFragmenter(fragmenter);//设置片段
        List<Blog> blogList = new LinkedList();
        //取文档列表
        for (ScoreDoc scoreDoc : hits.scoreDocs)
        {
            //根据id取文档
            Document doc = is.doc(scoreDoc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(doc.get("id")));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            //标题和内容需要高亮展示
            String title = doc.get("title");
            String content = StringEscapeUtils.escapeHtml(doc.get("content"));//转义,忽略掉内容文本中的html格式
            if (title != null)
            {
                //把全部得分高的摘要给显示出来
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));//第一个参数是对哪个参数进行设置；第二个是以流的方式读入
                String hTitle = highlighter.getBestFragment(tokenStream, title);//把得分高的加高亮显示出来
                if (StringUtil.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if (content != null)
            {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if (StringUtil.isEmpty(hContent))
                {
                    if (content.length() <= 200) {
                        blog.setContent(content);
                    } else {
                        blog.setContent(content.substring(0, 200));
                    }
                }
                else {
                    blog.setContent(hContent);
                }
            }
            blogList.add(blog);
        }
        return blogList;
    }


    /**
     * 获取索引库写入流indexWriter
     * @return
     * @throws IOException
     */
    private IndexWriter getWriter() throws IOException {
        //建立索引库
        directory= FSDirectory.open(new File("D:\\temp\\blog\\lucene").toPath());
        //初始化写入流配置类(用中文分析器)
        IndexWriterConfig config=new IndexWriterConfig(new SmartChineseAnalyzer());
        //获取索引库写入流
        indexWriter=new IndexWriter(directory,config);
        return  indexWriter;
    }


    /**
     * 给文档对象添加不同域
     * @param document
     * @param blog
     */
    private void addField(Document document,Blog blog){
        document.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(),Field.Store.YES));
        document.add(new StringField("releaseDate", DateUtil.dateFormat(new Date()),Field.Store.YES));
                                                            //注意这里是纯文本!!,不然前端展示会出现格式问题
        document.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));

    }






}
