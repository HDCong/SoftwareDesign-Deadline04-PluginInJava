package external;

import com.hdcong.myplugin.MyPlugin;

import java.io.IOException;

public class ExternalPluginImplement extends MyPlugin {
    @Override
    public String getName() {
        return "External Plugin";
    }

    @Override
    public Object execute(Object o, Object o1) {
        String s = (String) o;
        try{
            Runtime.getRuntime().exec(s);
            return "Success";
        } catch (IOException e) {
            return "Failed";
        }
    }
}
