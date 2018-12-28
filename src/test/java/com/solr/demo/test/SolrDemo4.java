package com.solr.demo.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import com.solr.demo.entity.Products;
import com.solr.demo.entity.SolrHelper;

/**
 * solr条件过滤排序分页查询
 */
public class SolrDemo4 {

	/**
	 * 定义 solr url
	 */
	private final static String SOLR_URL = "http://192.168.1.104:8080/solr/def_core";

	private final static Logger logger = LogManager.getLogger();

	/**
	 * 获取solr客户端对象
	 */
	public HttpSolrClient getSolrClient() {
		HttpSolrClient.Builder builder = new HttpSolrClient.Builder(SOLR_URL);
		HttpSolrClient solrClient = builder.withConnectionTimeout(10000).withSocketTimeout(60000).build();
		return solrClient;
	}

	/**
	 * solr条件查询
	 */
	@Test
	public void queryDocTest1() throws Exception {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 创建查询对象
		SolrQuery solrQuery = new SolrQuery();

		// 设置查询条件
		solrQuery.set("q", "title:浪漫樱花");

		// 设置过滤条件
		solrQuery.set("fq", "price_f:[50 TO 100]");

		// 添加排序
		solrQuery.addSort("price_f", ORDER.desc);
		solrQuery.addSort("release_time_dt", ORDER.desc);

		// 分页查询
		// solrQuery.setStart(0);//显示第一页
		solrQuery.setStart(10);//显示第二页
		solrQuery.setRows(10);//每页显示10条记录

		// 执行查询并返回响应对象
		QueryResponse response = solrClient.query(solrQuery);
		SolrDocumentList docList = response.getResults();

		// 获得结果集中的索引文档数据量
		long num = docList.getNumFound();
		logger.info("一共：" + num + "条记录");

		// 将SolrDocumentList转换为List<T>
		List<Products> products = SolrHelper.formatBeanList(docList, Products.class);
		logger.info(products);

		// 关闭solr客户端对象
		solrClient.close();
	}

	/**
	 * 可以设置多个查询条件，也可以添加多个过滤条件
	 */
	@Test
	public void queryDocTest2() throws Exception {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 创建查询对象，默认查询全部
		SolrQuery solrQuery = new SolrQuery("*:*");

		// 设置查询条件
		solrQuery.set("q", "title:浪漫樱花");

		// 设置过滤条件
		solrQuery.set("fq", "price_f:[50 TO 100]");

		// 添加排序
		solrQuery.addSort("price_f", ORDER.desc);
		solrQuery.addSort("release_time_dt", ORDER.desc);

		// 分页查询
		// solrQuery.setStart(0);//显示第一页
		solrQuery.setStart(10);//显示第二页
		solrQuery.setRows(10);//每页显示10条记录

		// 执行查询并返回响应对象
		QueryResponse response = solrClient.query(solrQuery);
		SolrDocumentList docList = response.getResults();

		// 获得结果集中的索引文档数据量
		long num = docList.getNumFound();
		logger.info("一共：" + num + "条记录");

		// 将SolrDocumentList转换为List<T>
		List<Products> products = SolrHelper.formatBeanList(docList, Products.class);
		logger.info(products);

		// 关闭solr客户端对象
		solrClient.close();
	}

}
