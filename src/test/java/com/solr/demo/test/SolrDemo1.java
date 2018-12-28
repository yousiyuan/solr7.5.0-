package com.solr.demo.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.solr.demo.entity.Products;

/**
 * 向solr索引库添加索引文档
 */
public class SolrDemo1 {

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
	 * 向solr索引库添加索引文档
	 */
	@Test
	public void test1() throws IOException, SolrServerException, ParseException {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 封装实体对象
		Products products = new Products();
		products.setPid("1601");
		products.setName("家天下韩式复古气息米黄玫瑰夜灯静音闹钟 3寸");
		products.setCatalog(23);
		products.setCatalogName("与钟不同");
		products.setPrice(19f);
		products.setNumber(10000);
		products.setDescription(
				"比4寸略小，它是3寸，款式颜色图案一样。都是关于韩式复古、罗马情怀、玫瑰风情的。浪漫而温馨，送她，告诉她，天天响你，爱就在身边。透亮玻璃不起雾，双铃响亮也清脆，发光指针更加亮。带闹钟、有夜灯、有开关，一节五号电池即可。新改良，将开关和电池隐藏更防止误操作。");
		products.setPicture("2013110914060203_S.jpg");
		products.setReleaseTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-01-14 18:59:34"));

		// 添加到solr
		solrClient.addBean(products);

		// 提交到索引库
		solrClient.commit();

		// 关闭solr客户端对象
		solrClient.close();
	}

	/**
	 * 向solr索引库添加索引文档
	 */
	@Test
	public void test2() throws IOException, SolrServerException {
		// 创建solr客户端对象
		SolrClient solrClient = getSolrClient();

		// 创建一个solr文档对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "c0001");
		solrInputDocument.addField("title", "花儿朵朵彩色金属门后挂 8钩免钉门背挂钩2066");// 使用IK_Analyzer分词器
		solrInputDocument.addField("catalog_i", "17");
		solrInputDocument.addField("catalog_name_txt_ik", "幽默杂货");
		solrInputDocument.addField("price_f", "18.9f");
		solrInputDocument.addField("number_i", "10000");
		solrInputDocument.addField("desc",
				"这是以金属为主材的一款切割型产品，因为创意，因为实用，所以让金属不于冰冷，而如此这般成为了焦点。这是一组关于大小花朵的粉红色挂钩，专用于门后悬挂收纳衣裤、围巾、钥匙等零碎杂乱的随手物品，更易查找，更好收纳。不占空间，无须钻孔，不伤家具。");
		solrInputDocument.addField("picture_txt_ik", "2014032613103438.png");
		solrInputDocument.addField("release_time_dt", "2015-01-14 18:59:33");

		// 把solr文档对象添加到索引库
		solrClient.add(solrInputDocument);
		// 提交到索引库
		solrClient.commit();
		// 关闭solr客户端对象
		solrClient.close();
	}

}
