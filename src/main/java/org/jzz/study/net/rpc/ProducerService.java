package org.jzz.study.net.rpc;

import java.util.Map;

/** 服务提供者，接口 */
public interface ProducerService {
	Map<String, String> handupTask(String str);
	
	Integer add(Integer a, Integer b);	//不能用基本类型！！否则代理报错
}