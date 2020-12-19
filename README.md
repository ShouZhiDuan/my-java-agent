# Java Agent代理应用

#### a、JavaAgent工程地址
https://github.com/ShouZhiDuan/my-java-agent
#### b、ClassPath下手动创建
META-INF/MANIFEST.MF文件
>>
Manifest-Version: 1.0
Can-Redefine-Classes: true
Can-Retransform-Classes: true
Premain-Class: com.example.dsz.MyAgent
记住留空一行，必须
<<
#### c、将myagent.jar依赖到需要使用当前agent的工程中去(具体依赖具体情况定，必须依赖否则加载不到相关的jar)
<dependency>
    <groupId>com.agent</groupId>
    <artifactId>java-agent-test</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

#### d、使用JavaAgent启动参数(在使用方项目启动脚本置顶如下参数)
-javaagent:E:/dsz-git-work/java-agent-test/target/myagent.jar
