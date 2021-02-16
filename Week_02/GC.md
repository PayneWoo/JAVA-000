# JVM GC 总结

## 一、GC 日志解读与分析

### 使用默认的并行 GC,  观察 Young GC 与 Full GC

```
java -XX:+PrintGCDetails GCLogAnalysis
 
1) 模拟 OOM，java -Xmx128m -XX:+PrintGCDetails GCLogAnalysis
2) 分别使用 512m, 1024m, 2048m, 4086m ,观察 GC 信息的不同
```

执行以下命令：

```java
// 编译 GCLogAnalysis.java
javac -encoding UTF-8 GCLogAnalysis.java

// 执行 GCLogAnalysis 并打印 GC 日志
java -XX:+PrintGCDetails GCLogAnalysis     
```

分析产生的 GC 日志，可以看出默认使用的是 ParallelGC（并行GC）,默认最大堆内存是 4294967296 bit(即 4 G，默认为物理内存的 1/4)，YoungGen 区会有 young GC, 当 old 区快满时会触发 Full GC。从 Full GC 也可以看出`年轻代大小：年老代大小 = 1 ：2`

```java
CommandLine flags: -XX:InitialHeapSize=268435456 -XX:MaxHeapSize=4294967296 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
2021-02-13T20:57:34.727-0800: 0.104: [GC (Allocation Failure) [PSYoungGen: 65536K->10745K(76288K)] 65536K->25363K(251392K), 0.0100091 secs] [Times: user=0.02 sys=0.07, real=0.01 secs] 

2021-02-13T20:57:34.749-0800: 0.126: [GC (Allocation Failure) [PSYoungGen: 76281K->10749K(141824K)] 90899K->42479K(316928K), 0.0103166 secs] [Times: user=0.02 sys=0.08, real=0.01 secs] 

2021-02-13T20:57:34.797-0800: 0.173: [GC (Allocation Failure) [PSYoungGen: 141821K->10751K(141824K)] 173551K->84936K(316928K), 0.0182915 secs] [Times: user=0.03 sys=0.15, real=0.02 secs] 

2021-02-13T20:57:34.833-0800: 0.210: [GC (Allocation Failure) [PSYoungGen: 141823K->10750K(272896K)] 216008K->127724K(448000K), 0.0192674 secs] [Times: user=0.03 sys=0.16, real=0.02 secs] 

2021-02-13T20:57:34.914-0800: 0.291: [GC (Allocation Failure) [PSYoungGen: 272894K->10737K(272896K)] 389868K->209612K(472064K), 0.0301917 secs] [Times: user=0.06 sys=0.24, real=0.03 secs] 

2021-02-13T20:57:34.944-0800: 0.321: [Full GC (Ergonomics) [PSYoungGen: 10737K->0K(272896K)] [ParOldGen: 198875K->171146K(332288K)] 209612K->171146K(605184K), [Metaspace: 2734K->2734K(1056768K)], 0.0216299 secs] [Times: user=0.23 sys=0.01, real=0.02 secs] 
```



接下来，指定 Xmx 的大小为 128m，来看看 GC 的情况

```
java -Xmx128m -XX:+PrintGCDetails GCLogAnalysis
```

会发现频繁的 Full GC，最后产生了堆内存溢出：

![image-20210213211811827](https://i.loli.net/2021/02/17/VjeWim9Zq3Ln8aH.png)

当最大堆内存设置的过小时，会发生频繁的 Full GC，还可能导致 OOM 异常。**所以，合理设置 Xmx，可以有效降低 Full GC 的频率，从而减少 JVM 停顿时间，从而提升程序效率。**

2 中分类(一般不区分 Full GC 和 Major GC )：

- Young GC（只针对年轻代进行GC）、Full GC（针对年轻代和年老代进行GC）

- Minor GC（也就是年轻代的GC）、Major GC（也就是年老代的GC）

  

  <img src="https://i.loli.net/2021/02/17/FHz7xfgeclLABjX.png" alt="image-20210214004312887" style="zoom:50%;" />

### CMS GC, 观察 Young GC 与 Full GC

执行命令：

```
java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
```

输出日志：

![image-20210214005158355](https://i.loli.net/2021/02/14/7RfsFd8bScNwW2Y.png)

从输出的GC日志，可以看出 CMS GC 的 6 个阶段：

<img src="https://i.loli.net/2021/02/11/s2anOx6FR1uMqWK.png" alt="image-20210211193115312" style="zoom:50%;" />



### 使用串行GC, 观察 Young GC 与 Full GC

执行命令：

```java
java -XX:+UseSerialGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
```

从输出的GC日志，可以看出串行GC的GC时间比并行GC要长。这表明并行GC的垃圾回收效率更高。




### 使用 G1 GC, 观察 Young GC 与 Full GC

执行命令：

```java
java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis 
```

从输出的GC日志：

![image-20210214011843164](https://i.loli.net/2021/02/14/ENtLnceSUO3ukax.png)

可以看出 G1 GC 的六个阶段：

<img src="https://i.loli.net/2021/02/14/TW2QOKugEzfmXpG.png" alt="image-20210214011957053" style="zoom:50%;" />

将堆内存设置为 4g:

```javascript
java -XX:+UseG1GC -Xms4g -Xmx4g -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis 
```

从日志可以看出，由于堆内存设置的很大，只有 young GC 产生：

```java
2021-02-14T01:22:12.658-0800: [GC pause (G1 Evacuation Pause) (young) 204M->67M(4096M), 0.0251290 secs]
2021-02-14T01:22:12.711-0800: [GC pause (G1 Evacuation Pause) (young) 245M->124M(4096M), 0.0205297 secs]
2021-02-14T01:22:12.760-0800: [GC pause (G1 Evacuation Pause) (young) 302M->175M(4096M), 0.0196190 secs]
2021-02-14T01:22:12.810-0800: [GC pause (G1 Evacuation Pause) (young) 353M->234M(4096M), 0.0214447 secs]
2021-02-14T01:22:12.857-0800: [GC pause (G1 Evacuation Pause) (young) 412M->298M(4096M), 0.0214097 secs]
2021-02-14T01:22:12.904-0800: [GC pause (G1 Evacuation Pause) (young) 476M->358M(4096M), 0.0216545 secs]
2021-02-14T01:22:12.952-0800: [GC pause (G1 Evacuation Pause) (young) 536M->409M(4096M), 0.0179813 secs]
2021-02-14T01:22:13.000-0800: [GC pause (G1 Evacuation Pause) (young) 611M->473M(4096M), 0.0250297 secs]
2021-02-14T01:22:13.083-0800: [GC pause (G1 Evacuation Pause) (young) 755M->561M(4096M), 0.0340883 secs]
2021-02-14T01:22:13.155-0800: [GC pause (G1 Evacuation Pause) (young) 825M->643M(4096M), 0.0294690 secs]
2021-02-14T01:22:13.245-0800: [GC pause (G1 Evacuation Pause) (young) 993M->747M(4096M), 0.0449902 secs]
2021-02-14T01:22:13.421-0800: [GC pause (G1 Evacuation Pause) (young) 1271M->876M(4096M), 0.0641201 secs]
2021-02-14T01:22:13.545-0800: [GC pause (G1 Evacuation Pause) (young) 1300M->972M(4096M), 0.0586541 secs]
执行结束!共生成对象次数:12099
```

## 二、GC 总结
### 7 类 GC 算法：
```
1. 串行 GC（Serial GC）: 单线程执行，应用需要暂停；
2. 并行 GC（ParNew、Parallel Scavenge、Parallel Old）: 多线程并行地执行垃圾回收，关注与高吞吐；
3. CMS（Concurrent Mark-Sweep）: 多线程并发标记和清除，关注与降低延迟；
4. G1（G First）: 通过划分多个内存区域做增量整理和回收，进一步降低延迟；
5. ZGC（Z Garbage Collector）: 通过着色指针和读屏障，实现几乎全部的并发执行，几毫秒级别的延迟，线性可扩展；
6. Epsilon: 实验性的 GC，供性能分析使用；
7. Shenandoah: G1 的改进版本，跟 ZGC 类似。
```
### GC 算法的演进路线：
```
1. 串行 -> 并行: 重复利用多核 CPU 的优势，大幅降低 GC 暂停时间，提升吞吐量。
2. 并行 -> 并发： 不只开多个 GC 线程并行回收，还将GC操作拆分为多个步骤，让很多繁重的任务和应用线程一起并 发执行，减少了单次 GC 暂停持续的时间，这能有效降低业务系统的延迟。
3. CMS -> G1： G1 可以说是在 CMS 基础上进行迭代和优化开发出来的，划分为多个小堆块进行增量回收，这样就更 进一步地降低了单次 GC 暂停的时间
4. G1 -> ZGC:：ZGC 号称无停顿垃圾收集器，这又是一次极大的改进。ZGC 和 G1 有一些相似的地方，但是底层的算法 和思想又有了全新的突破。
```

### GC 的选择：
1. 如果系统考虑吞吐优先，CPU 资源都用来最大程度处理业务，用 Parallel GC；

2. 如果系统考虑低延迟有限，每次 GC 时间尽量短，用 CMS GC；

3. 如果系统内存堆较大，同时希望整体来看平均 GC 时间可控，使用 G1 GC。

4. 对于内存大小的考量：

   - 一般 4G 以上，算是比较大，用 G1 的性价比较高。

   - 一般超过 8G，比如 16G-64G 内存，非常推荐使用 G1 GC。
   
目前绝大部分 Java 应用系统，堆内存并不大比如 2G-4G 以内，而且对 10ms 这种低延迟的 GC 暂停不敏感，也就是说处理一个业务步骤，
大概几百毫秒都是可以接受的，GC 暂停 100ms 还是 10ms 没多大区别。
另一方面，系统的吞吐量反 而往往是我们追求的重点，这时候就需要考虑采用并行 GC。
如果堆内存再大一些，可以考虑 G1 GC。如果内存非常大（比如超过 16G，甚至是 64G、128G），或者是对延迟非常 敏感（比如高频量化交易系统），就需要考虑使用本节提到的新 GC（ZGC/Shenandoah）。

### 常用的 GC 组合
![image-20210211200810928](https://i.loli.net/2021/02/11/r8NakEJQhnbpD7A.png)

### 垃圾收集器的对比
![image-20210211200353944](https://i.loli.net/2021/02/11/I5DXSnf1xAvBpHg.png)

