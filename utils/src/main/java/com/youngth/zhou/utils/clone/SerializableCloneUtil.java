package com.youngth.zhou.utils.clone;

import java.io.*;

/**
 * @author YoungTH
 * @date 2019/5/22
 */
public class SerializableCloneUtil {

    public static<T> T serializableClone(T t) {
        ByteArrayOutputStream outPut = null;
        ByteArrayInputStream input = null;
        try {
            outPut = new ByteArrayOutputStream();
            ObjectOutputStream objOutPut = new ObjectOutputStream(outPut);
            objOutPut.writeObject(t);
            input = new ByteArrayInputStream(outPut.toByteArray());
            ObjectInputStream objInput = new ObjectInputStream(input);
            return (T) objInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outPut != null) {
                    outPut.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
