package com.xuxianda.lesson02;

/**
 * @Author: xuxianda
 * @Date: 2019/5/27 11:09
 * @Version 1.0
 * 一、volatile关键字：当多个线程操作共享数据时，可以保证内存中的数据可见。多个线程操作数据时，操作的是主内存中的数据，而不是缓存中的数据
 *
 * 注意：
 * 1、volatile不具备互斥性
 * 2、volatile不能保证变量的原子性
 */
/**
 * 本质问题是修改了cpu高速缓存中的数据，却没有修改主内存中的数据
 */

/**
 * 大家都知道，计算机在执行程序时，每条指令都是在CPU中执行的，而执行指令过程中，势必涉及到数据的读取和写入。由于程序运行过程中的临时数据是存放在主存（物理内存）当中的，这时就存在一个问题，由于CPU执行速度很快，而从内存读取数据和向内存写入数据的过程跟CPU执行指令的速度比起来要慢的多，因此如果任何时候对数据的操作都要通过和内存的交互来进行，会大大降低指令执行的速度。因此在CPU里面就有了高速缓存。
 　　也就是，当程序在运行过程中，会将运算需要的数据从主存复制一份到CPU的高速缓存当中，那么CPU进行计算时就可以直接从它的高速缓存读取数据和向其中写入数据，当运算结束之后，再将高速缓存中的数据刷新到主存当中。举个简单的例子，比如下面的这段代码：
    i = i + 1;
 　　当线程执行这个语句时，会先从主存当中读取i的值，然后复制一份到高速缓存当中，然后CPU执行指令对i进行加1操作，然后将数据写入高速缓存，最后将高速缓存中i最新的值刷新到主存当中。
 　　这个代码在单线程中运行是没有任何问题的，但是在多线程中运行就会有问题了。在多核CPU中，每条线程可能运行于不同的CPU中，因此每个线程运行时有自己的高速缓存（对单核CPU来说，其实也会出现这种问题，只不过是以线程调度的形式来分别执行的）。本文我们以多核CPU为例。
    详细见https://www.cnblogs.com/dolphin0520/p/3920373.html
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while(true){
            /*synchronized (td){*/   //如果使用锁的话，线程会锁住这块代码，效率会比较低，推荐使用volatile
                if(td.isFlag()){
                    System.out.println("------------------");
                    break;
                }
            /*}*/
        }
    }

}

class ThreadDemo implements Runnable {
    private volatile boolean flag = false;

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        flag = true;
        System.out.println("flag=="+flag);
    }

    public boolean isFlag() {
        return flag;
    }

}
