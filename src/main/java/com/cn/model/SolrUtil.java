package com.cn.model;

import org.apache.commons.logging.Log;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SolrUtil {
    //指定solr服务器的地址
    private final static String SOLR_URL = "http://localhost:8983/solr/";

    /**
     * 创建SolrServer对象
     *
     * 该对象有两个可以使用，都是线程安全的
     * 1、CommonsHttpSolrServer：启动web服务器使用的，通过http请求的
     * 2、 EmbeddedSolrServer：内嵌式的，导入solr的jar包就可以使用了
     * 3、solr 4.0之后好像添加了不少东西，其中CommonsHttpSolrServer这个类改名为HttpSolrClient
     *
     * @return
     */
    public HttpSolrClient createSolrServer(){
        HttpSolrClient solr = null;
        solr = new HttpSolrClient(SOLR_URL);
        return solr;
    }


    /**
     * 往索引库添加文档
     * @throws IOException
     * @throws SolrServerException
     */
    public void addDoc() throws SolrServerException, IOException {
        //构造一篇文档
        SolrInputDocument document = new SolrInputDocument();
        //往doc中添加字段,在客户端这边添加的字段必须在服务端中有过定义
        document.addField("id", "8");
        document.addField("name", "周新星");
        document.addField("description", "一个灰常牛逼的军事家");
        //获得一个solr服务端的请求，去提交 ,选择具体的某一个solr core
        HttpSolrClient solr = new HttpSolrClient(SOLR_URL + "my_core");
        solr.add(document);
        solr.commit();
        solr.close();
    }


    /**
     * 根据id从索引库删除文档
     */
    public void deleteDocumentById() throws Exception {
        //选择具体的某一个solr core
        HttpSolrClient server = new HttpSolrClient(SOLR_URL+"delta");
        //删除文档
        server.deleteById("8");
        //删除所有的索引
        //solr.deleteByQuery("*:*");
        //提交修改
        server.commit();
        server.close();
    }

    /**
     * 查询
     * @throws Exception
     */
    public List querySolr() throws Exception{
        HttpSolrClient solrServer = new HttpSolrClient(SOLR_URL+"delta/");
        SolrQuery query = new SolrQuery();
        //下面设置solr查询参数
        query.set("q", "*:*");// 参数q 查询所有
        //query.set("q","周星驰");//相关查询，比如某条数据某个字段含有周、星、驰三个字 将会查询出来 ，这个作用适用于联想查询

        //参数fq, 给query增加过滤查询条件
        //query.addFilterQuery("id:[0 TO 9]");//id为0-4

        //给query增加布尔过滤条件
        //query.addFilterQuery("description:演员"); //description字段中含有“演员”两字的数据

        //参数df,给query设置默认搜索域
        query.set("df", "name");

        //参数sort,设置返回结果的排序规则
        query.setSort("name",SolrQuery.ORDER.desc);
        //获取查询结果
        QueryResponse response = solrServer.query(query);
        //两种结果获取：得到文档集合或者实体对象

        //查询得到文档的集合
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("通过文档集合获取查询的结果");
        System.out.println("查询结果的id最大值：" + solrDocumentList.getNumFound());
        System.out.println(solrDocumentList);
        System.out.println(response.getResults().getNumFound());
        //得到实体对象
        List tmpLists = response.getBeans(User.class);
        System.out.println(tmpLists.size());
        return tmpLists;
    }
}
