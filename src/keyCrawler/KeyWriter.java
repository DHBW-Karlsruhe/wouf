/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package keyCrawler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Hammel
 */
public class KeyWriter {

    static List<Object> enumConst;
    static Set<String> keys;
    static String path;
    static Properties keyFile = new Properties();

    public static void catchEnumConstsFromClass(Class<?> clazz){
        if(clazz.isEnum()){
            for(Object e : clazz.getEnumConstants()){
                enumConst.add(e);
            }
        }
    }

    public static void catchKeysFromFile(Properties keyFile){
        for(Object key : keyFile.keySet()){
            KeyWriter.keys.add(key.toString());
        }
    }

    public static void updatePropertyFile(){
        KeyWriter.catchKeysFromFile(keyFile);
        String enumKey;
        Properties outputProp = new Properties();
        for(Object e : KeyWriter.enumConst){
            enumKey = e.getClass().toString() + e.toString();
            if(!KeyWriter.keys.contains(enumKey)){
                outputProp.put(enumKey, "");
            }
        }
        try {
            outputProp.store(new FileOutputStream(KeyWriter.path), null);
        } catch (IOException ex) {
            System.out.println("could not store propertyFile");
        }
    }
    



}
