package com.solr.demo.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import com.solr.demo.entity.Products;
import com.solr.demo.entity.SolrHelper;

/**
 * solr条件查询
 */
public class SolrDemo3 {

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
	 * solr条件查询，不初始化查询条件，需要先设置查询条件，再添加过滤条件
	 */
	@Test
	public void queryIndexDocumentTest1() throws Exception {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 创建查询对象，未初始化查询条件，如果不设置查询条件（或直接添加过滤条件），就会查询不出来任何结果
		SolrQuery solrQuery = new SolrQuery();

		// 设置要展示的域
		// solrQuery.setFields("id", "title", "catalog_name_txt_ik", "desc");

		// 设置《查询》条件
		solrQuery.set("q", "title:围巾 and desc:保暖");

		// 添加过滤条件（必须先设置查询条件）
		solrQuery.addFilterQuery("id:3395");

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
	 * solr条件查询，已初始化查询条件，可以直接添加过滤条件
	 */
	@Test
	public void queryIndexDocumentTest2() throws Exception {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 创建查询对象，初始化查询条件
		SolrQuery solrQuery = new SolrQuery("*:*");

		// 设置要展示的域
		// solrQuery.setFields("id", "title", "catalog_name_txt_ik", "desc");

		// 设置《过滤》条件
		solrQuery.addFilterQuery("title:围巾 and desc:保暖");
		solrQuery.addFilterQuery("id:3395");

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
