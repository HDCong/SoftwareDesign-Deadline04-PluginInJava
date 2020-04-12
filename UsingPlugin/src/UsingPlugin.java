import com.hdcong.internal.Add2Number;
import com.hdcong.myplugin.MyPlugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class UsingPlugin {
    private static List<MyPlugin> plugins;
    private static final String RESOURCE_REL_PATH = "plugins/";
    private static final String PLUGINS_PACKAGE_NAME = "external";


    public static void main(String[] argv) {
        System.out.println("aiaiia");

        initPlugin();
        System.out.println((String)(plugins.get(2).execute("notepad","")));

    }

    private static void initPlugin() {
        plugins = new ArrayList<>();
        loadInternalFunction();
        loadExternalFunction();

    }

    private static void loadInternalFunction() {
        plugins.add(new Add2Number());
    }
    private static void loadExternalFunction() {
        List<Class> classes = loadClassViaPath();

        for (Class c : classes) {
            try {
                MyPlugin newPlugin = (MyPlugin) c.newInstance();
                plugins.add(newPlugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Class> loadClassViaPath() {
        List<Class> classes = new ArrayList<>();

        ClassLoader classLoader = createClassLoaderAtResourceDirectory();
        if (classLoader == null)
            return classes;

        //get directory of plugin package
        File pluginsDirectory = new File(RESOURCE_REL_PATH  + PLUGINS_PACKAGE_NAME+"/");

        //get all file names in this directory
        String[] fileNames = pluginsDirectory.list();

        for (String fileName : fileNames) {
            System.out.println(fileName);

            // we are only interested in .class files
            if (fileName.endsWith(".class")) {
                // removes the .class extension
                String className =   PLUGINS_PACKAGE_NAME+ "." + fileName.substring(0, fileName.length() - 6);
                System.out.println(className);
                try {
                    //create a new class with this class name
                    Class newClass = classLoader.loadClass(className);
                    //add new class to the list
                    classes.add(newClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return classes;
                }
            }
        }

        return classes;
    }

    private static ClassLoader createClassLoaderAtResourceDirectory() {
        //get directory of resource via relative path
        File resourceDirectory = new File(RESOURCE_REL_PATH );

        //create a classLoader in this directory
        ClassLoader classLoader;
        try {
            // Convert file to a URL
            URL url = resourceDirectory.toURI().toURL();
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            classLoader = new URLClassLoader(urls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return classLoader;
    }

}
