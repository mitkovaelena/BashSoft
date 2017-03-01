package StaticData;

import java.util.HashSet;

public class SessionData {
    public static String currentPath = System.getProperty("user.dir"); //the application’s directory in the file system
    public static HashSet<Thread> threadPool = new HashSet<>();
}
