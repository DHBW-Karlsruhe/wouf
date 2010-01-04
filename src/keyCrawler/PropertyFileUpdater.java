

package keyCrawler;

/**
 *
 * @author Marco Hammel
 */
public class PropertyFileUpdater {

    public static void main(String[] args) {
        if(args.length >= 3){
            args[0] = args[0] + args[1];
            args[1] = args[2] + args[3];
        }
        System.out.println(args[0]);
        System.out.println(args[1]);
        
        KeyWriter.path = args[0];

        try {
            for (Class<?> clazz : ClassCrawler.getClassesSimple(args[1], null)) {
                KeyWriter.catchEnumConstsFromClass(clazz);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("NCF");
        }
    }

}
