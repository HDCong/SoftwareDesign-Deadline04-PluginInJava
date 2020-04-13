import com.hdcong.internal.Add2Number;
import com.hdcong.myplugin.MyPlugin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class UsingPlugin {
    private static List<MyPlugin> plugins;
    private static final String PLUGINS_FOLDER_DIR = "plugins";

    public static void main(String[] argv) {
        initPlugin();
        System.out.println("List class loaded: ");
        for (int i = 0; i < plugins.size(); i++) {
            System.out.println(plugins.get(i).getName());
        }

        System.out.println((plugins.get(0).execute("8.5", "15.5")).toString());

        System.out.println((String) (plugins.get(1).execute("notepad", "")));

    }

    private static void initPlugin() {
        plugins = new ArrayList<>();
        // load internal plugin extends, define in package: com.hdcong.internal
        loadInternalClass();

        // Firstly, build the external plugin.
        // Then copy the package (in out of project PluginExternal: external folder) to plugins folder
        // load the .class in plugins/external
        loadExternalClass();

    }

    private static void loadInternalClass() {
        // Only one plugin extends the MyPlugin abstract class
        plugins.add(new Add2Number());
    }

    private static void loadExternalClass() {

        List<Class> classes = loadClassInPluginsFolder(new File(PLUGINS_FOLDER_DIR));

        for (Class c : classes) {
            try {
                plugins.add((MyPlugin) c.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Class> loadClassInPluginsFolder(File file)  {
        // Reference: https://stackoverflow.com/questions/32222151/how-to-load-all-compiled-class-from-a-folder

        List<Class> classesLoaded = new ArrayList<>();

        ClassLoader classLoader = null;
        try {
            classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return classesLoaded;
        }

        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }
        });


        for (File fileName : files) {
            System.out.println(fileName);
            // in my case: should loaded class: external.ExternalPluginImplement
            String className = fileName.getName().substring(0, fileName.getName().length() - 6);
            try {
                Class<?> clazz = classLoader.loadClass(className);
                classesLoaded.add(clazz);
                System.out.println("Loaded class " + className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classesLoaded;
    }

}
