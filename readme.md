 ### 前置知识
 #### json 轻量级数据交换格式。
 
 ##### JAVA实体类
 
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
  #### 前台
  
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
       
   #### 后台  
    管理员登录
    商品管理： 添加商品、修改、商品上下架
    品类管理:查看、添加
    订单管理：查看、发货
    
    
   ### 数据表结构设计
   1，用户表（用户名唯一、MD5加密）
   
    create table neuedu_user(
     `id`       int(11)      not null  auto_increment comment '用户id',
     `username`       varchar(50)      not null   comment '用户名',
     `password`       varchar(50)      not null   comment '用户密码,MD5加密',
     `email`       varchar(50)      not null   comment '用户email',
     `phone`       varchar(20)    not null   comment '用户phone',
     `question`      varchar(100)      not null   comment '找回密码问题',
     `answer`      varchar(100)      not null   comment '找回密码答案',
     `role`       int(4)      not null   comment '角色0-管理员,1-普通用户',
     `create_time`       datetime      not null   comment '创建时间',
    `update_time`       datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`),
     UNIQUE KEY `user_name_unique` (`username`) USING BTREE
    )ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
    
   2，类别表（无限层级表结构）
   
    create table neuedu_category(
     `id`       int(11)      not null  auto_increment comment '类别id',
     `parent_id`       int(11)      default null   comment '父类Id,当pareng_id=0,说明是根节点，一级类别',
     `name`       varchar(50)      DEFAULT null   comment '类别名称',
     `status`       tinyint(1)      DEFAULT '1'  comment '类别状态1-正常，2-已废弃',
     `sort_order`       int(4)    DEFAULT null   comment '排序编号，同类展示顺序，数值相等则自然排序',
     `create_time`       datetime      not null   comment '创建时间',
    `update_time`       datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`)
    )ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET=utf8;
    
     id    name     parent_id       
     1     电子产品      0
     2     手机          1
     3     华为手机      2 
     4     小米手机      2
     5     meta系列      3
     
    查询电子产品下所有的子类别？-->递归查询
   
   3,商品表
   
    create table neuedu_product(
    `id`       int(11)      not null  auto_increment comment '商品id',
    `category_id`       int(11)      not  null   comment '类别id，对应neuedu_category表的主键',
    `name`       varchar(100)      not null   comment '商品名称',
    `subtitle`       varchar(200)      DEFAULT null   comment '商品副标题',
    `main_image`       varchar(500)      DEFAULT null   comment '产品主图，url相对地址',
    `sub_images`       text       comment '图片地址，json格式',
    `detail`       text        comment '商品详情',
    `price`       DECIMAL (20,2)      not NULL   comment '价格，单位-元保留两位小数',
    `stock`       int(11)   not NULL   comment '库存数量',
    `status`       int(6)    DEFAULT '1'   comment '商品状态，1-在售 2-下架 3-删除',
    `create_time`       datetime      not null   comment '创建时间',
    `update_time`        datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`)
     )ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
     
   4,购物车表
   
    create table neuedu_cart(
    `id`       int(11)      not null  auto_increment,
    `user_id`       int(11)      not  null  ,
    `product_id`       int(11)      not  null  ,
    `quantity`       int(11)      not null   comment '商品数量',
    `checked`       int(11)      DEFAULT null   comment '是否选择，1=已勾选，0=未勾选',
    `create_time`       datetime      not null   comment '创建时间',
    `update_time`       datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`),
     key `user_id_index`(`user_id`) USING BTREE
    )ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
   5，订单表
   
    create table neuedu_order(
     `id`       int(11)      not null  auto_increment comment '订单id',
     `user_id`       int(11)      DEFAULT  null  ,
     `order_no`       bigint(20)      DEFAULT  null comment '订单号'  ,
     `shipping_id`       int(11)      DEFAULT  null  ,
     `payment`       decimal(20,2)      DEFAULT  null  comment '实际付款金额，单位元，保留两位小数' ,
     `payment_type`       int(4)      DEFAULT  null comment '支付类型，1-在线支付' ,
     `postage`       int(10)      DEFAULT null   comment '运费，单位是元',
     `status`       int (10)      DEFAULT null   comment '订单状态：0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭',
     `payment_time`      datetime     DEFAULT null   comment '支付时间',
     `send_time`       datetime      DEFAULT null   comment '发货时间',
     `end_time`       datetime      DEFAULT null   comment '交易完成时间',
     `close_time`       datetime      DEFAULT null   comment '交易关闭时间',
     `create_time`       datetime      not null   comment '创建时间',
     `update_time`       datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`),
     UNIQUE  KEY `order_no_index` (`order_no`) USING  BTREE
     )ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
   6，订单明细表
   
    create table neuedu_order_item(
     `id`       int(11)      not null  auto_increment comment '订单id',
     `user_id`       int(11)      DEFAULT  null  ,
     `order_no`       bigint(20)      DEFAULT  null comment '订单号'  ,
     `product_id`       int(11)      DEFAULT  null  comment '商品id',
     `product_name`       varchar(100)      DEFAULT  null  comment '商品名称' ,
     `product_image`       varchar(500)      DEFAULT  null comment '商品图片地址' ,
     `current_unit_price`       decimal(20,2)      DEFAULT null   comment '生成订单时的商品单价，单位元，保留两位小数',
     `quantity`       int (10)      DEFAULT null   comment '商品数量',
     `total_price`      decimal(20,2)     DEFAULT null   comment '商品总价，单位元，保留两位小数',
     `create_time`       datetime      not null   comment '创建时间',
     `update_time`       datetime      not null   comment '最后一次更新时间',
      PRIMARY KEY(`id`),
      KEY `order_no_index` (`order_no`) USING  BTREE,
      KEY `order_no_user_id_index` (`user_id`,`order_no`) USING  BTREE
      )ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;
   7，支付信息表
   
    create table neuedu_payinfo(
    `id`       int(11)      not null  auto_increment,
    `user_id`       int(11)      DEFAULT  null  ,
    `order_no`       bigint(20)      DEFAULT  null comment '订单号'  ,
    `pay_platform`       int(10)      DEFAULT null   comment '支付平台 1-支付宝 2-微信',
     `platform_number`       VARCHAR (200)      DEFAULT null   comment '支付宝支付流水号',
     `platform_status`       VARCHAR (20)      DEFAULT null   comment '支付宝支付状态',
    `create_time`       datetime      not null   comment '创建时间',
    `update_time`       datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`)
     )ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
   8，收货地址表 
 
    create table neuedu_shopping(
    `id`       int(11)      not null  auto_increment,
    `user_id`       int(11)      not  null  ,
    `receiver_name`       varchar(20)      default   null  COMMENT '收货姓名' ,
    `receiver_phone`       varchar(20)      default   null  COMMENT '收货固定电话' ,
    `receiver_mobile`       varchar(20)      default   null  COMMENT '收货移动电话' ,
    `receiver_province`       varchar(20)      default   null  COMMENT '省份' ,
    `receiver_city`       varchar(20)      default   null  COMMENT '城市' ,
    `receiver_district`       varchar(20)      default   null  COMMENT '区/县' ,
    `receiver_address`       varchar(200)      default   null  COMMENT '详细地址' ,
     `receiver_zip`       varchar(6)      default   null  COMMENT '邮编' ,
    `create_time`       datetime      not null   comment '创建时间',
    `update_time`       datetime      not null   comment '最后一次更新时间',
     PRIMARY KEY(`id`)
    )ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
   ### 插件    
  #### 插件一、mybatis-generator插件
  
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
  
   ##### generatorConfig.xml
      
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
  
  #### 插件二、mybatis分页插件
  
   1，pom中添加依赖
   
      <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper</artifactId>
        <version>4.1.0</version>
      </dependency>
      <dependency>
        <groupId>com.github.miemiedev</groupId>
        <artifactId>mybatis-paginator</artifactId>
        <version>1.2.17</version>
      </dependency>
      <dependency>
        <groupId>com.github.jsqlparser</groupId>
        <artifactId>jsqlparser</artifactId>
        <version>0.9.4</version>
      </dependency>
  
   2，spring配置文件，sqlSessionFactoryBean中添加分页插件属性
    
      <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
          <property name="configLocation" value="classpath:mybatis-config.xml"></property>
          <property name="mapperLocations" value="classpath:com/neuedu/mapper/*Mapper.xml" ></property>
         <property name="dataSource" ref="dataSource"></property>
          <!-- 分页插件 -->
          <property name="plugins">
              <array>
                  <bean class="com.github.pagehelper.PageHelper">
                      <property name="properties">
                          <value>
                              dialect=mysql
                          </value>
                      </property>
                  </bean>
              </array>
          </property>
      </bean>
  #### 插件三-接口测试插件-restlet
  
  
  
  ### 搭建ssm框架
  
    1,pom.xml
    2,添加配置文件 
     spring配置文件
     springmvc配置文件
     mybatis配置文件
     web.xml
    3,使用框架 
       

