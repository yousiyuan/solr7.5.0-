package com.solr.demo.test;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import com.solr.demo.entity.Products;
import com.solr.demo.entity.SolrHelper;

/**
 * 高亮查询
 */
public class SolrDemo5 {

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
	 * 高亮查询 匹配搜索条件（包括分词之后）的部分会高亮显示
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
		solrQuery.setStart(0);// 显示第一页
		// solrQuery.setStart(10);// 显示第二页
		solrQuery.setRows(10);// 每页显示10条记录

		// 开启高亮
		solrQuery.setHighlight(true);
		// 高亮设置
		solrQuery.addHighlightField("title");
		solrQuery.addHighlightField("desc");
		solrQuery.addHighlightField("catalog_name_txt_ik");
		solrQuery.setHighlightSimplePre("<red>");// 前缀
		solrQuery.setHighlightSimplePost("</red>");// 后缀

		// 执行查询并返回响应对象
		QueryResponse response = solrClient.query(solrQuery);
		SolrDocumentList docList = response.getResults();

		// 获得结果集中的索引文档数据量
		long num = docList.getNumFound();
		logger.info("一共：" + num + "条记录");

		// 将SolrDocumentList转换为List<T>
		List<Products> products = SolrHelper.formatBeanList(docList, Products.class);
		logger.info(products);

		// 获得高亮结构体
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		if (highlighting != null && highlighting.size() > 0)
			for (SolrDocument solrDocument : docList) {
				// 根据id获得每一个域的高亮内容
				Object id = solrDocument.getFieldValue("id");
				Map<String, List<String>> map = highlighting.get(id);
				if (map == null || map.size() <= 0)
					continue;

				System.out.println("\r\n\r\n");

				// 根据具体的域来获得高亮内容
				List<String> list = map.get("title");
				if (list == null || list.size() <= 0)
					continue;

				// 打印高亮内容
				for (String item : list) {
					logger.info(item);
				}

				System.out.println("\r\n\r\n");

				// 根据具体的域来获得高亮内容
				list = map.get("catalog_name_txt_ik");
				if (list == null || list.size() <= 0)
					continue;

				// 打印高亮内容
				for (String item : list) {
					logger.info(item);
				}

				System.out.println("\r\n\r\n");

				// 根据具体的域来获得高亮内容
				list = map.get("desc");
				if (list == null || list.size() <= 0)
					continue;

				// 打印高亮内容
				for (String item : list) {
					logger.info(item);
				}
			}

		// 关闭solr客户端对象
		solrClient.close();
	}

}
