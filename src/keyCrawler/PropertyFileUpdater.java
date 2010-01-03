

package keyCrawler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Hammel
 */
public class PropertyFileUpdater {

    public static void main(String[] args) {
            KeyWriter kw = new KeyWriter(args[0]);
            for (Class<?> clazz : ClassCrawler.getClasses(args[1], null)) {
                KeyWriter.catchEnumConstsFromClass(clazz);
            }
    }

}
