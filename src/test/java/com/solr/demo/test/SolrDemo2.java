package com.solr.demo.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;

/**
 * 从solr索引库删除索引文档
 */
public class SolrDemo2 {

	/**
	 * 定义 solr url
	 */
	private final static String SOLR_URL = "http://192.168.1.104:8080/solr/def_core";

	/**
	 * 获取solr客户端对象
	 */
	public HttpSolrClient getSolrClient() {
		HttpSolrClient.Builder builder = new HttpSolrClient.Builder(SOLR_URL);
		HttpSolrClient solrClient = builder.withConnectionTimeout(10000).withSocketTimeout(60000).build();
		return solrClient;
	}

	/**
	 * 从solr索引库删除索引文档
	 */
	@Test
	public void deleteIndexDocumentTest1() throws IOException, SolrServerException {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 把solr文档对象添加到索引库
		solrClient.deleteById("1601");
		// 等价于：
		// solrClient.deleteByQuery("id:c0001");

		// 提交到索引库
		solrClient.commit();

		// 关闭solr客户端对象
		solrClient.close();
	}

	/**
	 * 从solr索引库删除所有的索引文档
	 */
	@Test
	public void deleteIndexDocumentTest2() throws IOException, SolrServerException {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 把solr文档对象添加到索引库
		solrClient.deleteByQuery("*:*");

		// 提交到索引库
		solrClient.commit();

		// 关闭solr客户端对象
		solrClient.close();
	}

}
