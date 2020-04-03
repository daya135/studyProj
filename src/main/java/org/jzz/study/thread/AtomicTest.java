package org.jzz.study.thread;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.jzz.study.util.Print;

/** 测试线程安全问题,采用原子对象,同步方法来对比 */
public class AtomicTest {
	public static final int THREAD_COUNT = 50;

	static void testUnAtomic() throws InterruptedException{
		BankAccount sharedAccount = new BankAccount("account-csx", 0L);
		ArrayList<Thread> threads = new ArrayList<Thread>(THREAD_COUNT);
		for (int i = 0; i < THREAD_COUNT; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 10000; j++) {
//						sharedAccount.deposit(10.00);	//线程不安全
						sharedAccount.deposit_synchronized(10L);	//同步方法,结果正确,但取出的时候有问题
					}
				}
			});
			threads.add(thread);
			thread.start();
		}
		for (Thread thread: threads) {
			thread.join();	//等待所有线程执行完成
		}
//		Print.print("the balance is: " + sharedAccount.getBalance());	
		Print.print("the balance is: " + sharedAccount.getBalance_synchronized());	//同步步方法,需要调用join等待执行完,不然取出的结果也跟预期不一样
	}
	
	static void testAtomic() throws InterruptedException{
		BankAccount sharedAccount = new BankAccount(0L);
		ArrayList<Thread> threads = new ArrayList<Thread>(THREAD_COUNT);
		for (int i = 0; i < THREAD_COUNT; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 10000; j++) {
						sharedAccount.depositAtomic(10);	//原子类的方法
					}
				}
			});
			threads.add(thread);
			thread.start();
		}
		for (Thread thread: threads) {
			thread.join();	//等待所有线程执行完成
		}
		Print.print("the balance is: " + sharedAccount.getBalanceAtomic());
	}
	
	//真正需要使用AtomicReference的场景是你需要CAS类操作时，由于涉及到比较、设置等多于一个的操作(即一句话的操作多于一个)
	//如果仅需要通过赋值操作改变一个引用，确实不需要AtomicReference。
	static void testAtomicReference() {
		BankAccount b1 = new BankAccount(100L);
		BankAccount b2 = new BankAccount(500L);
		AtomicReference<BankAccount> atomicReference = new AtomicReference<BankAccount>(b1);
		b1.deposit(100L);
		atomicReference.compareAndSet(b1, b2);	//更改b1的成员变量不影响b1的地址,所以b2赋值成功
		BankAccount b3 = atomicReference.get();
		
		Print.print(b1);
		Print.print(b2);
		Print.print(b3);
		Print.print(b3 == b1);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Long startTime = System.currentTimeMillis();
//		testUnAtomic();
		testAtomic();	//较互斥的方案性能明显提高
		Long costTime = System.currentTimeMillis() - startTime;
		Print.print(" costTime: " + costTime);	
		
		testAtomicReference();
	}
}

/** 账户类
 *   测试多个线程操作账户存款时的线程安全问题
 *  */
class BankAccount {
    private String accountName;
    private Long balance;
    private AtomicLong intbalance = new AtomicLong();   //java没有提供AtomicDouble类型
    
    public BankAccount(String accountName, Long balance){
        this.accountName = accountName;
        this.balance =balance;
    }
    
    public BankAccount(Long balance){
        this.accountName = "account-csx";
        this.balance = balance;
        this.intbalance.set(balance);
    }

    /** 存款,原子对象版本 */
    public Long depositAtomic(int amount) {
        return intbalance.addAndGet(amount);
    }
    /** 存款,线程不安全版本 */
    public Long deposit(Long amount){
        balance = balance + amount;
        return balance;
    }
    /** 存款, 采用加锁的方式保证三大特征! */
    public synchronized Long deposit_synchronized(Long amount){
        balance = balance + amount;
        return balance;
    }
    public double getBalance() {
        return balance;
    }
    public Long getBalanceAtomic() {
    	return intbalance.get();
    }
    //此处加上synchronized关键字的目的是为了保证balance变量的可见性，进入synchronized代码块每次都会去从主内存中读取最新值
    public synchronized Long getBalance_synchronized() {
        return balance;
    }
    public double withdraw(Long amount){
        balance = balance - amount;
        return balance;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String toString() {
    	return accountName + " " + balance;
    }
}
