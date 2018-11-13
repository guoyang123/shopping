package com.neuedu.controller.portal;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
public class ProductController {


    @Autowired
    IProductService productService;
    /**
     * 商品详情
     *
     * */
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(Integer productId){

        return  productService.detail_portal(productId);
    }

    /**
     * restful
     * http://localhost:8080/shopping/product/detail/productId/1
     * */
    @RequestMapping(value = "/detail/productId/{productId}")
    public ServerResponse detailRestful(@PathVariable("productId") Integer productId){

        return  productService.detail_portal(productId);
    }
    /**
     * 前台-搜索商品并排序
     *
     * */
    @RequestMapping(value = "/list.do")
   public ServerResponse list(@RequestParam(required = false) Integer categoryId,
                              @RequestParam(required = false)     String keyword,
                              @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                              @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                              @RequestParam(required = false,defaultValue = "")String orderBy){


       return  productService.list_portal(categoryId,keyword,pageNum,pageSize,orderBy);
   }

   /**
    *
    * http://localhost:8080/shopping/product/list/{categoryId}/{keyword}/{pageNum}/{pageSize}/{orderBy}
    * http://localhost:8080/shopping/product/list/100032/1/10/price_desc
    * */
    @RequestMapping(value = "/list/categoryId/{categoryId}/{pageNum}/{pageSize}/{orderBy}")
    public ServerResponse listRestfulByCategoryId(@PathVariable("categoryId") Integer categoryId,

                                      @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize,
                              @PathVariable("orderBy") String orderBy){


        return  productService.list_portal(categoryId,null,pageNum,pageSize,orderBy);
    }
    @RequestMapping(value = "/list/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}")
    public ServerResponse listRestfulByKeyword(
                                      @PathVariable("keyword")     String keyword,
                                      @PathVariable("pageNum") Integer pageNum,
                                      @PathVariable("pageSize") Integer pageSize,
                                      @PathVariable("orderBy") String orderBy){


        return  productService.list_portal(null,keyword,pageNum,pageSize,orderBy);
    }



}
