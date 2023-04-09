package org.jzz.study.net.rpc;

import java.util.Map;

/** 测试RPC服务 */
/** 注意：
 * 1、先启动RPCService；
 * 2、运行RPCTest，测试远程调用
 */
public class RPCTest {
	public static void main(String[] args) {
		ProxyFactory<ProducerService> proxy = new ProxyFactory<>(ProducerService.class);	//代理的服务端接口，这样就能模拟直接调用服务端方法
		ProducerService producer = proxy.getProxyObject();
//		Map<String, String> result = producer.handupTask("hello");	
//		System.out.println(result);
//		System.out.println(producer.add(1, 2));
		
	}
}
