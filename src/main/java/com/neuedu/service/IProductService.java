package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;

import javax.servlet.http.HttpSession;

public interface IProductService {

   ServerResponse saveOrUpdate(Product product);
   /**
    * 商品上下架
    * @param productId 商品id
    * @param  status 商品状态
    * */
   ServerResponse set_sale_status(Integer productId,Integer status);
   /**
    * 商品详情
    * */
   ServerResponse detail(Integer productId);
}
