package com.solr.demo.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrHelper {

	/**
	 * 通过反射将SolrDocument转换成Bean
	 */
	public static <T> T formatBean(SolrDocument solrDocument, Class<? extends T> clazz) throws Exception {
		Constructor<?> constructor = clazz.getDeclaredConstructor(new Class<?>[] {});
		Object instance = constructor.newInstance(new Object[] {});
		Field[] fieldArray = clazz.getDeclaredFields();
		Class<?> fieldType = null;
		for (Field field : fieldArray) {
			fieldType = field.getType();
			field.setAccessible(true);
			// 获取字段上的注解
			org.apache.solr.client.solrj.beans.Field annotation = field
					.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
			if (annotation != null) {
				String key = annotation.value();
				Object value = solrDocument.getFieldValue(key);
				if (value == null)
					continue;

				if (fieldType.equals(Short.class))
					field.set(instance, Short.valueOf(value.toString()));
				else if (fieldType.equals(Integer.class))
					field.set(instance, Integer.valueOf(value.toString()));
				else if (fieldType.equals(Long.class))
					field.set(instance, Long.valueOf(value.toString()));
				else if (fieldType.equals(Float.class))
					field.set(instance, Float.valueOf(value.toString()));
				else if (fieldType.equals(Double.class))
					field.set(instance, Double.valueOf(value.toString()));
				else if (fieldType.equals(Boolean.class))
					field.set(instance, Boolean.valueOf(value.toString()));
				else if (fieldType.equals(String.class))
					field.set(instance, value.toString());
				else if (fieldType.equals(Date.class))
					field.set(instance,
							new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(value.toString()));
			}
		}
		return clazz.cast(instance);
	}

	/**
	 * 将SolrDocumentList转换成BeanList
	 */
	public static <T> List<T> formatBeanList(SolrDocumentList solrDocumentList, Class<? extends T> clazz)
			throws Exception {
		List<T> list = new ArrayList<T>();
		for (SolrDocument solrDocument : solrDocumentList) {
			list.add(formatBean(solrDocument, clazz));
		}
		return list;
	}

}
