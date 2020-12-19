package com.example.dsz;

import javassist.*;

import javax.sound.midi.Soundbank;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.*;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/12/11 17:22
 * @Description:
 */
public class MyTransformer implements ClassFileTransformer {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";


    // 被处理的方法列表
    final static Map<String, List<String>> methodMap = new HashMap<>();

    public MyTransformer() {
        methodMap.put("com.example.dsz.java_agent.TimeTest", Arrays.asList("sayHello","sayHello2"));
    }

    @Override
    public byte[] transform(
            ClassLoader loader,//类加载器
            String className,//类名 com/example/dsz/java_agent/TimeTest
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer
    ) {
        if(className.contains("TimeTest")){
            System.out.println("className=" + className);
        }
        className = className.replace("/", ".");
        if (methodMap.containsKey(className)) {// 判断加载的class的包路径是不是需要监控的类
            System.out.println("当前拦截的className：" + className);
            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool.insertClassPath(classPath);
            CtClass  ctclass = null;
            try {
                System.out.println("进入try{}catch(){}");
                ctclass = pool.get(className);// 使用全称,用于取得字节码类<使用javassist>
                System.out.println("ctclass=" + ctclass);
                for (String methodName : methodMap.get(className)) {
                    System.out.println("当前methodName：" + methodName);
                    String outputStr = "\nSystem.out.println(\"this method " + methodName
                            + " cost:\" +(endTime - startTime) +\"ms.\");";
                    System.out.println("outputStr= " + outputStr);
                    CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);// 得到这方法实例
                    String newMethodName = methodName + "$old";// 新定义一个方法叫做比如sayHello$old
                    ctmethod.setName(newMethodName);// 将原来的方法名字修改

                    // 创建新的方法，复制原来的方法，名字为原来的名字
                    CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

                    // 构建新的方法体
                    StringBuilder bodyStr = new StringBuilder();
                    bodyStr.append("{");
                    bodyStr.append(prefix);
                    bodyStr.append(newMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
                    bodyStr.append(postfix);
                    bodyStr.append(outputStr);
                    bodyStr.append("}");

                    newMethod.setBody(bodyStr.toString());// 替换新方法
                    ctclass.addMethod(newMethod);// 增加新方法
                }
                ctclass.defrost();
                return ctclass.toBytecode();
            } catch (Exception e) {
                System.out.println("字节码操作异常" + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) throws NotFoundException {
        CtClass ctClass = null;
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(MyTransformer.class);
        pool.insertClassPath(classPath);
        ctClass = pool.get("com.example.dsz.java_agent.TimeTest");
        System.out.println(ctClass);
    }




}
