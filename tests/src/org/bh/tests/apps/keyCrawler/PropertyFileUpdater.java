

package org.bh.tests.apps.keyCrawler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Prog1Tools.IOTools;

/**
 *
 * @author Marco Hammel
 */
public class PropertyFileUpdater {

    SortedProperties propFile = new SortedProperties();
    String propPathRead;
    String propPathWrite;
    String vzPath;
    String classPath;
    List<Class<?>> enumClasses = new ArrayList<Class<?>>();
    private static int version = 1;
    boolean createCopy;

    public PropertyFileUpdater(){
        PropertyFileUpdater.version = PropertyFileUpdater.version + 1;
    }

    public static void main(String[] args) {
        System.out.println("<---------- Property File Updater für Business Horizon -------->");
    
        PropertyFileUpdater pfu = new PropertyFileUpdater();
        if(pfu.createCopy = IOTools.readBoolean("Schreiben in seperates PropertyFile? (true/false): ")){
            pfu.propPathRead = IOTools.readLine("Pfad zu lesendem PropertyFile: ");
            pfu.propPathWrite = IOTools.readLine("Pfad zu schreibendem PropertyFile: ");
        }else{
            pfu.propPathRead = IOTools.readLine("Pfad zum PropertyFile: ");
        }

        pfu.vzPath = IOTools.readLine("root packet der Anwendung oder Componente: ");
        if(IOTools.readBoolean("gesamtes VZ (true)/oder best. Klasse (false): ")){
            pfu.classPath = null;
        }else{
            pfu.classPath = IOTools.readLine("Pfad der Klasse:");
        }
        System.out.println("Gefundene Enum(s):");
        try {
            pfu.propFile.load(new FileReader(pfu.propPathRead));

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
                        pfu.propFile.setProperty(o.toString() + "_long", "TO_FILL");
                    }
                }
            }
            if(pfu.createCopy){
                if(!(new File(pfu.propPathWrite)).exists()){
                    new File(pfu.propPathWrite).createNewFile();
                }
                pfu.propFile.store(new FileWriter(pfu.propPathWrite), "verion: " + PropertyFileUpdater.version);
            }else{
                pfu.propFile.store(new FileWriter(pfu.propPathRead), "verion: " + PropertyFileUpdater.version);
            }
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Klasse "+ pfu.classPath + " oder Verzeichnis " + pfu.vzPath +
                    " wurde nicht gefunden bzw. enthält keine Klassen");
        }catch (IOException ex){
            System.out.println("Property File konnte nicht geladen, oder geschrieben werden");
        }  
    }
        

}
