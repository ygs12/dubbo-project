package com.biao.mall.order.dubbo;

import com.biao.mall.common.dto.OrderDTO;
import com.biao.mall.common.dubbo.OrderDubboService;
import com.biao.mall.common.response.ObjectResponse;
import com.biao.mall.order.service.OrdersService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname OrderDubboServiceImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-06 12:39
 * @Version 1.0
 **/
@Service(version = "1.0.0",protocol = "${dubbo.protocol.id}",
        application = "${dubbo.application.id}",registry = "${dubbo.registry.id}")
public class OrderDubboServiceImpl implements  OrderDubboService{

    @Autowired
    private OrdersService ordersService;

    @Override
    public ObjectResponse<OrderDTO> createOrder(OrderDTO orderDTO) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        BusinessActionContext businessActionContext = new BusinessActionContext();
        /**这里使用了本地业务service中取得ObjectResponse的模式，*/
        if( ordersService.prepare(businessActionContext,orderDTO)){
            return (ObjectResponse<OrderDTO>) businessActionContext.getActionContext("responseObject");
        }
        return null;
    }
}
