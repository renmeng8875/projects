package com.babytree.commons.protopack;

import com.alibaba.fastjson.JSONObject;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;
import com.babytree.commons.protopack.marshal.ObjectMarshal;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author renmeng
 * @Description: TODO
 * @date ${date}
 */
public class PackUnpackTest {
    @Test
    public void testPackUnpack(){
        TestBean inputBean = new TestBean();
        inputBean.setByteNum((byte)1);
        inputBean.setShortNum((short)2);
        inputBean.setIntNum(3);
        inputBean.setLongNum(4);
        inputBean.setVarBin("Whoes your daddy?");
        InnerBean innerBean = new InnerBean();
        innerBean.setAge(18);
        innerBean.setName("renmeng");
        innerBean.setNum(30000);
        List<InnerBean> innerBeanList = new ArrayList<>();
        innerBeanList.add(innerBean);
        inputBean.setBeanList(innerBeanList);
        ObjectMarshal marshal = new ObjectMarshal(inputBean);
        Pack pack = new Pack();
        marshal.marshal(pack);

        //模拟远程传输逻辑。。。。。。
        //*************网络上传输**********************
        byte[] rpcBytes = pack.getBuffer().array();
        System.err.println(rpcBytes.length);
        System.err.println(JSONObject.toJSONString(inputBean).getBytes().length);




        //*************下面是另一台机器的进程接收到二进字节流解包



        Unpack unpack = new Unpack(rpcBytes);
        TestBean outputBean = new TestBean();
        ObjectMarshal outputMarshal = new ObjectMarshal(outputBean);
        outputMarshal.unmarshal(unpack);
        Assert.assertEquals("renmeng",outputBean.getBeanList().get(0).getName());




    }


}


