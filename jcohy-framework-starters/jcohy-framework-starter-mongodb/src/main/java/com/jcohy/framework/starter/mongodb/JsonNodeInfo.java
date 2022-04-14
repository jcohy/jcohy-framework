package com.jcohy.framework.starter.mongodb;

import java.util.LinkedList;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.util.Assert;

/**
 * 描述: json tree 节点信息.
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 4/6/22:11:04
 * @since 2022.0.1
 */
public class JsonNodeInfo {

	/**
	 * mongo keys: class1.class2.item.
	 */
	private volatile String nodeKeys;

	/**
	 * jsonPath语法：/class1/class2/item.
	 */
	private volatile String nodePath;

	/**
	 * 节点关系.
	 */
	private final LinkedList<String> elements;

	/**
	 * tree 的 叶子节点，此处为引用.
	 */
	private final JsonNode leafNode;

	public JsonNodeInfo(LinkedList<String> elements, JsonNode leafNode) {
		Assert.notNull(elements, "elements can not be null.");
		this.nodeKeys = null;
		this.nodePath = null;
		this.elements = elements;
		this.leafNode = leafNode;
	}

	/**
	 * 获取 mongo db的 key 语法.
	 * @return mongo db的 key 语法
	 */
	public String getNodeKeys() {
		if (this.nodeKeys == null) {
			synchronized (this) {
				if (this.nodeKeys == null) {
					StringJoiner nodeKeysJoiner = new StringJoiner(".");
					this.elements.forEach(nodeKeysJoiner::add);
					this.nodeKeys = nodeKeysJoiner.toString();
				}
			}
		}
		return this.nodeKeys;
	}

	/**
	 * 获取 json path 语法路径.
	 * @return jsonPath 路径
	 */
	public String getNodePath() {
		if (this.nodePath == null) {
			synchronized (this) {
				if (this.nodePath == null) {
					StringJoiner nodePathJoiner = new StringJoiner("/", "/", "");
					this.elements.forEach(nodePathJoiner::add);
					this.nodePath = nodePathJoiner.toString();
				}
			}
		}
		return this.nodePath;
	}

	/**
	 * 获取第一个元素.
	 * @return element
	 */
	public String getFirst() {
		return this.elements.getFirst();
	}

	public LinkedList<String> getElements() {
		return this.elements;
	}

	public JsonNode getLeafNode() {
		return this.leafNode;
	}

}
