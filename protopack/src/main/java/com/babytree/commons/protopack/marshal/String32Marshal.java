package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

/**
 * @author: renmeng
 * @date: 2018/5/31
 */
public class String32Marshal implements Marshallable {
    public String data;

    public String32Marshal() {
    }

    public String32Marshal(String data) {
        this.data = data;
    }

    @Override
    public void marshal(Pack pack) {
        pack.putVarstr32(data);
    }

    @Override
    public void unmarshal(Unpack unpack) {
        data = unpack.popVarstr32();
    }
}
