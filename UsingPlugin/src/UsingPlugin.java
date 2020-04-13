import com.hdcong.internal.Add2Number;
import com.hdcong.myplugin.MyPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class UsingPlugin {
    private static List<MyPlugin> plugins;
    private static final String EXTERNAL_PACKAGE = "external";


    public static void main(String[] argv) {
        initPlugin();
        for(int i = 0; i < plugins.size();i++){
            System.out.println(plugins.get(i).getName());
        }

        System.out.println((plugins.get(0).execute("8.5","15.5")).toString());

        System.out.println((String)(plugins.get(1).execute("notepad","")));

    }

    private static void initPlugin() {
        plugins = new ArrayList<>();
        // load internal plugin extends, define in package: com.hdcong.internal
        loadInternalFunction();

        // Firstly, build the external plugin.
        // Then copy the package (in out of project PluginExternal: external folder) to plugins folder
        // load the .class in plugins/external
        loadExternalFunction();

    }

    private static void loadInternalFunction() {
        // Only one plugin extends the MyPlugin abstract class
        plugins.add(new Add2Number());
    }
    private static void loadExternalFunction() {
        // Make sure package folder contain only .class type file
        List<Class> classes = loadClassInExternalFolder();

        for (Class c : classes) {
            try {
                plugins.add((MyPlugin) c.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Class> loadClassInExternalFolder() {

        List<Class> classes = new ArrayList<>();

        File externalFolder = new File("plugins/");

        ClassLoader classLoader = null;

        try {
            classLoader = new URLClassLoader(new URL[]{externalFolder.toURL()});

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return classes;
        }

        String[] fileNames = externalFolder.list();

        if (fileNames==null) return classes;

        for (String fileName : fileNames) {
            if (fileName.endsWith(".class")) {
                // in my case: should loaded class: external.ExternalPluginImplement
                String className =   EXTERNAL_PACKAGE+ "." + fileName.substring(0, fileName.length() - 6);
                System.out.println(className);
                try {
                    Class<?> newClass = classLoader.loadClass(className);
                    classes.add(newClass);
                } catch (ClassNotFoundException e) {
                    System.out.println('s');
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}
