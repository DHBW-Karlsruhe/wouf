

package org.bh.tests.apps.keyCrawler;

import java.util.Properties;
import Prog1Tools.IOTools;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco Hammel
 */
public class PropertyFileUpdater {

    Properties propFile = new Properties();
    String propPath;
    String vzPath;
    String classPath;
    List<Class<?>> enumClasses = new ArrayList<Class<?>>();
    private static int version = 1;

    public PropertyFileUpdater(){
        PropertyFileUpdater.version = PropertyFileUpdater.version + 1;
    }
    @SuppressWarnings({"unchecked", "unchecked", "unchecked"})
    public static void main(String[] args) {
    
        PropertyFileUpdater pfu = new PropertyFileUpdater();
        pfu.propPath = IOTools.readLine("Pfad zum PropertyFile: ");

        pfu.vzPath = IOTools.readLine("root packet der Anwendung oder Componente: ");
        if(IOTools.readBoolean("gesamtes VZ (true)/oder best. Klasse (false): ")){
            pfu.classPath = null;
        }else{
            pfu.classPath = IOTools.readLine("Pfad der Klasse:");
        }
        System.out.println("Gefundene Enum(s):");
        try {
            pfu.propFile.load(new FileReader(pfu.propPath));
            for(Class<?> clazz : ClassFinder.getClasses(pfu.vzPath, pfu.classPath)){
                if(clazz.isEnum()){
                    pfu.enumClasses.add(clazz);
                    System.out.println(clazz);
                }
            }
            System.out.println("Update Property File with keys: ");
            for(Class<?> clazz : pfu.enumClasses){
                for(Object o : clazz.getEnumConstants()){
                    System.out.println(o);
                    if(pfu.propFile.getProperty(o.toString()) == null){
                        pfu.propFile.setProperty(o.toString(), "TO_FILL");
                        pfu.propFile.setProperty(o.toString() + "_LONG", "TO_FILL");
                    }
                }
            }
            pfu.propFile.store(new FileWriter(pfu.propPath), "verion: " + PropertyFileUpdater.version);
        } catch (ClassNotFoundException ex) {
            System.out.println("Klasse "+ pfu.classPath + " oder Verzeichnis " + pfu.vzPath +
                    "wurde nicht gefunden bzw. enthält keine Klassen");
        }catch (IOException ex){
            System.out.println("Property File konnte nicht geladen, oder geschrieben werden");
        }

        
        
    }
        

}
