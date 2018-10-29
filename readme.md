json 轻量级数据交换格式。

 class  Person{
   int age;
   String name;
   boolean  b;
   double d;
 }
 
 ---->json
 1，  Person  --json-》{} 
       {
         "age":20,
         "name":"zhangsan",
         "b":false,
         "d":3.0
        }
 2,List<Person> ------->[{},{},{}]
 
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
      
  需求
  前台
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
       ...
   后台  
    管理员登录
    商品管理： 添加商品、修改、商品上下架
    品类管理:查看、添加
    订单管理：查看、发货
    
    数据表结构设计
    1，用户表（用户名唯一、MD5加密）
    2，类别表（无限层级表结构）
     id    name     parent_id       
     1    电子产品      0
     2    手机          1
     3     华为手机     2 
     4     小米手机     2
     5     meta系列     3
     ...
      查询电子产品下所有的子类别？
        递归查询
        
      
    
  
      

