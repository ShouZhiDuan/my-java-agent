package com.java.agent.test;

import lombok.Data;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/12/11 14:51
 * @Description:
 */
@Data
public class MyAgent {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(agentOps);
    }

    public static void premain(String agentOps) {
        System.out.println("=========premain方法执行2========");
        System.out.println(agentOps);
    }

}
