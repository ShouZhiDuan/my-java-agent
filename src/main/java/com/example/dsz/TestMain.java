package com.example.dsz;

import javassist.NotFoundException;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/12/11 18:12
 * @Description:
 */
public class TestMain {
    public static void main(String[] args) throws NotFoundException {
        //CtClass ctClass = ClassPool.getDefault().get("com.example.dsz.java_agent.TimeTest");
        System.out.println(TestMain.class.getSimpleName());
    }
}
