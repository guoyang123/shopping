package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    ShippingMapper shippingMapper;
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
      //step1:参数校验
       if(shipping==null){
           return ServerResponse.serverResponseByError("参数错误");
       }
        //step2:添加
         shipping.setUserId(userId);
         shippingMapper.insert(shipping);
        //setp3:返回结果
        Map<String,Integer> map= Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.serverResponseBySuccess(map);
    }

    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {

        //step1:参数非空校验
        if(shippingId==null){
            return ServerResponse.serverResponseByError("参数错误");
        }
        //setp2:删除
         int result=shippingMapper.deleteByUserIdAndShippingId(userId,shippingId);
        //step3:返回结果
         if(result>0){
             return ServerResponse.serverResponseBySuccess();
         }

        return ServerResponse.serverResponseByError("删除失败");
    }

    @Override
    public ServerResponse update(Shipping shipping) {

        //step1：非空判断
        if(shipping==null){
            return ServerResponse.serverResponseByError("参数错误");
        }
        //step2:更新
        int result=  shippingMapper.updateBySelectiveKey(shipping);
        //step3:返回结果

        if(result>0){
            return ServerResponse.serverResponseBySuccess();
        }
        return ServerResponse.serverResponseByError("更新失败");
    }

    @Override
    public ServerResponse select(Integer shippingId) {
        //step1:参数非空校验
        if(shippingId==null){
            return ServerResponse.serverResponseByError("参数错误");
        }
       Shipping shipping= shippingMapper.selectByPrimaryKey(shippingId);


        return ServerResponse.serverResponseBySuccess(shipping);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList=shippingMapper.selectAll();
        PageInfo pageInfo=new PageInfo(shippingList);

        return ServerResponse.serverResponseBySuccess(pageInfo);
    }
}
