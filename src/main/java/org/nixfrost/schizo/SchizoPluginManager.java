package org.nixfrost.schizo;

import static org.nixfrost.schizo.util.Util.mcver;

public class SchizoPluginManager {
    public static Boolean canEnable(){
        return mcver.contains("1.20.4");
    }
}
