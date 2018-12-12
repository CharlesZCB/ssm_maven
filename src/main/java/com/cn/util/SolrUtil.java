package com.cn.util;

import com.cn.dao.UserMapping;
import com.cn.model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SolrUtil {
    @Autowired
    UserMapping userMapping;
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
    public void addDoc(User user) throws SolrServerException, IOException {
//        //方法1：构造一篇文档
//        SolrInputDocument document = new SolrInputDocument();
//        //往doc中添加字段,在客户端这边添加的字段必须在服务端中有过定义
//        document.addField("id", "1");
//        document.addField("name", "周新星");
//        document.addField("age", 1);
//        document.addField("sex", 0);
//        //获得一个solr服务端的请求，去提交 ,选择具体的某一个solr core
//        HttpSolrClient solr = new HttpSolrClient(SOLR_URL + "delta");
//        solr.add(document);
//        solr.commit();
//        solr.close();
        //方法2
        HttpSolrClient server = new HttpSolrClient(SOLR_URL+"delta");
        server.addBean(user);
        server.commit();
        server.close();

    }


    /**
     * 根据id从索引库删除文档
     */
    public void deleteDocumentById(String id) throws Exception {
        //选择具体的某一个solr core
        HttpSolrClient server = new HttpSolrClient(SOLR_URL+"delta");
        //删除文档
        server.deleteById(id);
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
    public List<User> querySolr(int pn) throws Exception{
        HttpSolrClient solrServer = new HttpSolrClient(SOLR_URL+"delta/");
        SolrQuery query = new SolrQuery();
        //下面设置solr查询参数
        query.set("q", "*:*");// 参数q 查询所有
        //query.set("q","周星驰");//相关查询，比如某条数据某个字段含有周、星、驰三个字 将会查询出来 ，这个作用适用于联想查询

        //参数fq, 给query增加过滤查询条件
        //query.addFilterQuery("id:[0 TO 9]");//id为0-4

        //给query增加布尔过滤条件
        //query.addFilterQuery("description:演员"); //description字段中含有“演员”两字的数据
        /*设置从第0条开始取*/
        query.setStart((pn-1)*10);
        /*每次取出10条记录*/
        query.setRows(10);

        //参数sort,设置返回结果的排序规则
        query.setSort("create_at",SolrQuery.ORDER.desc);
        //获取查询结果
        QueryResponse response = solrServer.query(query);
        //两种结果获取：得到文档集合或者实体对象
        List<User> userList = response.getBeans(User.class);
        return  userList;
    }


//java8特性
//   public static  void  main(String[] args) throws IOException, SolrServerException {
//        String [] a={"a","b","c"};
//        for (String m:a){
//            System.out.println(m);
//        }
//        Map<String, Integer> items = new HashMap();
//        items.put("A", 10);
//        items.put("B", 20);
//        items.put("C", 30);
//        items.put("D", 40);
//        items.put("E", 50);
//        items.put("F", 60);
//
//        items.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
//
//        List list = new ArrayList();
//        list.add("q");
//        list.add("a");
//        list.add("b");
//        list.forEach(pp->{
//            System.out.println(pp);
//        });
//        for(int i=0;i<100000;i++){
//            User user = new User();
//            String uid=UUID.randomUUID().toString();
//            String name= "像话"+i;
//            int age =0;
//            int sex =1;
//            Date date = new Date();
//            user.setName(name);
//            user.setAge(age);
//            user.setSex(sex);
//            user.setId(uid);
//            user.setCreate_at(date);
//            SolrUtil solrUtil = new SolrUtil();
//            solrUtil.addDoc(user);
//            System.out.println("结束第"+i+"个");
//        }
//
//   }2a92ad96-052c-4cf3-90b6-e9847a543ee3
    //<update><query>id:2a92ad96-052c-4cf3-90b6-e9847a543ee3</query></update>
    public long querySolrAll() throws Exception{
        HttpSolrClient solrServer = new HttpSolrClient(SOLR_URL+"delta/");
        SolrQuery query = new SolrQuery();
        query.set("q","*:*");
        QueryResponse response = solrServer.query(query);
        long number= response.getResults().getNumFound();
        return  number;
    }

}
