package com.nettm.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

public class Utils {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T string2Object(String objBody,
            Class<T> bean) {
        if (objBody == null || objBody.length() == 0)
            return null;

        T result = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream oin = null;
        try {
            bais = new ByteArrayInputStream(new Base64().decode(objBody));
            oin = new ObjectInputStream(bais);
            result = (T) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oin.close();
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static <T extends Serializable> String object2String(T value) {
        if (value == null)
            return null;

        String result = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream(4096);
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            oos.flush();

            result = new String(new Base64().encode(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
