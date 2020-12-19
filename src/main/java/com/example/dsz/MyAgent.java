package com.example.dsz;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/12/11 17:03
 * @Description:
 */
public class MyAgent {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(agentOps);
        //add transformer
        inst.addTransformer(new MyTransformer());

    }

/*    public static void premain(String agentOps) {
        System.out.println("=========premain方法执行2========");
        System.out.println(agentOps);
    }*/

}
