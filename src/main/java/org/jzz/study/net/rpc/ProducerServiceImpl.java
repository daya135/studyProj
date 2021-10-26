package org.jzz.study.net.rpc;

import java.util.HashMap;
import java.util.Map;

/** 服务提供者 */
public class ProducerServiceImpl implements ProducerService{

	@Override
	public Map<String, String> handupTask(String str) {
		Map<String, String> map = new HashMap<>();
		map.put("接受的参数", str);
		map.put("调用结果", "OK");
		return map;
	}
	
	@Override
	public Integer add(Integer a, Integer b) {
		return a + b;
	}
}
