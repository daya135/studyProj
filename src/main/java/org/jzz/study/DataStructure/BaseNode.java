package org.jzz.study.DataStructure;

/* 用于树的打印 */
public interface BaseNode {
	BaseNode getLeft();
	BaseNode getRight();
	BaseNode getParent();
	String getValue();
}
