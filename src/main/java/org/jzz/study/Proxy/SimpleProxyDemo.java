package org.jzz.study.Proxy;
import static org.jzz.study.util.Print.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.jzz.study.util.Print;

/** 被代理的接口 */
interface Interface {
  void doSomething();
  void somethingElse(String arg);
}
/** 被代理的接口实例 */
class RealObject implements Interface {
	public void doSomething() { 
		print("RealObject doSomething"); 
	}
	public void somethingElse(String arg) { 
		print("RealObject somethingElse " + arg);
	}
}	
/** 静态代理类 */
class SimpleProxy implements Interface {
  private Interface proxied;
  public SimpleProxy(Interface proxied) {
    this.proxied = proxied;
  }
  public void doSomething() {
    print("SimpleProxy doSomething");
    proxied.doSomething();
  }
  public void somethingElse(String arg) {
    print("SimpleProxy somethingElse " + arg);
    proxied.somethingElse(arg);
  }
}	
/** 动态代理类 */
class DynamicProxyHandler implements InvocationHandler {
	private Object proxied;
	public DynamicProxyHandler (Object obj) {
		this.proxied = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Print.print(proxy.getClass());
		Print.print(method);
		Print.printArr(args, " ");
		return method.invoke(proxied, args);
	}
	
}
/** 测试类 */
public class SimpleProxyDemo {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("parmvalue");
  }
  public static void main(String[] args) {
	RealObject realObj = new RealObject();
	Print.print("=============无代理==============================================");
    consumer(realObj);
    Print.print("=============静态代理==============================================");
    consumer(new SimpleProxy(realObj));
    Print.print("=============动态代理==============================================");
    //Java动态代理只能代理接口，要代理类需要使用第三方的CLIGB等类库。
	Interface proxy = (Interface)Proxy.newProxyInstance(
				Interface.class.getClassLoader(), 
//				new Class[]{Interface.class},	//跟.getClass().getInterfaces()一样，都是类数组
				realObj.getClass().getInterfaces(),	
				new DynamicProxyHandler(realObj));
	consumer(proxy);	//调用(Interface)proxy的将方法全部转发给DynamicProxyHandler执行
  }
} 

