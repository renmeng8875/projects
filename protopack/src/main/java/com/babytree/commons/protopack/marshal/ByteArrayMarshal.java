package com.babytree.commons.protopack.marshal;


import com.babytree.commons.protopack.base.Marshallable;
import com.babytree.commons.protopack.base.Pack;
import com.babytree.commons.protopack.base.Unpack;

public class ByteArrayMarshal implements Marshallable {

    public byte[] data;

    public ByteArrayMarshal() {
    }
    public ByteArrayMarshal(byte[] data) {
        this.data = data;
    }

    @Override
    public void marshal(Pack pack) {
        if(data != null){
            pack.putInt(data.length);
            for(byte b : data){
                pack.putByte(b);
            }
        }else{
            pack.putInt(0);
        }
    }

    @Override
    public void unmarshal(Unpack unpack) {
        int size = unpack.popInt();
        data = new byte[size];
        for(int i=0;i<size;i++){
            data[i] = unpack.popByte();
        }
    }
}
