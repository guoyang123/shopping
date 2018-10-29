#### json 轻量级数据交换格式。
#####JAVA实体类
 ```
  class  Person{
   int age;
   String name;
   boolean  b;
   double d;
   }
 ```
 ##### json表示单个对象
 
       {
         "age":20,
         "name":"zhangsan",
         "b":false,
         "d":3.0
        }
 ##### json表示集合
 
      [
        { "age":20,
                  "name":"zhangsan",
                  "b":false,
                  "d":3.0},
        { "age":21,
                  "name":"zhangsan",
                  "b":false,
                  "d":3.0},
        { "age":22,
                  "name":"zhangsan",
                  "b":false,
                  "d":3.0}
      ]
      
  ### 项目需求
  ##### 前台
  
    购买
      商品->首页、商品列表、商品详情
      购物车->商品添加到购物车、更改购物车中商品数量、删除商品、全选、取消全选、单选、结算
      订单
          下单：订单确认(地址管理)，订单提交
          订单中心：订单列表、订单详情、取消订单
      地址 : crud
      支付: 支付宝
    用户体系
       登录
       注册
       修改密码
       
   ##### 后台  
    管理员登录
    商品管理： 添加商品、修改、商品上下架
    品类管理:查看、添加
    订单管理：查看、发货
    
    
   ##### 数据表结构设计
    1，用户表（用户名唯一、MD5加密）
    2，类别表（无限层级表结构）
     id    name     parent_id       
     1    电子产品      0
     2    手机          1
     3     华为手机     2 
     4     小米手机     2
     5     meta系列     3
     
    查询电子产品下所有的子类别？-->递归查询
   
    3,商品表
    4,购物车表
    5，订单表
    6，订单明细表
    7，支付信息表
    8，收货地址表     
  ## mybatis-generator插件用法
  
   1,pom.xml添加插件
   
        <plugin>
         <groupId>org.mybatis.generator</groupId>
         <artifactId>mybatis-generator-maven-plugin</artifactId>
         <version>1.3.6</version>
         <configuration>
           <verbose>true</verbose>
           <overwrite>true</overwrite>
         </configuration>
        </plugin>
       
   2,pom中添加依赖
 
          <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>5.1.47</version>
             </dependency>
             <dependency>
               <groupId>org.mybatis.generator</groupId>
               <artifactId>mybatis-generator-core</artifactId>
               <version>1.3.5</version>
             </dependency>
    
   3，创建插件生成的配置文件
    
   #### generatorConfig.xml
      
       <?xml version="1.0" encoding="UTF-8" ?>
       <!DOCTYPE generatorConfiguration PUBLIC
               "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
               "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
       <generatorConfiguration>
           <classPathEntry location=""/>
           <context id="context" targetRuntime="MyBatis3Simple">
               <commentGenerator>
                   <property name="suppressAllComments" value="false"/>
                   <property name="suppressDate" value="true"/>
               </commentGenerator>
               <jdbcConnection userId="" password="" driverClass="" connectionURL=""/>
               <javaTypeResolver>
                   <property name="forceBigDecimals" value="false"/>
               </javaTypeResolver>
               <javaModelGenerator targetPackage="" targetProject=".">
                   <property name="enableSubPackages" value="false"/>
                   <property name="trimStrings" value="true"/>
               </javaModelGenerator>
               <sqlMapGenerator targetPackage="" targetProject=".">
                   <property name="enableSubPackages" value="false"/>
               </sqlMapGenerator>
               <javaClientGenerator targetPackage="" type="XMLMAPPER" targetProject=".">
                   <property name="enableSubPackages" value="false"/>
               </javaClientGenerator>
               <table schema="" tableName="" enableCountByExample="false" enableDeleteByExample="false"
                      enableSelectByExample="false" enableUpdateByExample="false"/>
           </context>
       </generatorConfiguration>
  
  
      

