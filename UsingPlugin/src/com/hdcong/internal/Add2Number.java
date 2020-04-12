package com.hdcong.internal;

import com.hdcong.myplugin.MyPlugin;

public class Add2Number extends MyPlugin {
    @Override
    public String getName() {
        return "Internal Plugin : Add 2 number";
    }

    @Override
    public Object execute(Object o, Object o1) {
        try{
            String s1 = (String)o;
            String s2= (String )o1;
            Double n1 = Double.parseDouble(s1);
            Double n2 = Double.parseDouble(s2);
            return n1+n2;
        }
        catch (Exception e){
            return "Failed";
        }
    }
}
