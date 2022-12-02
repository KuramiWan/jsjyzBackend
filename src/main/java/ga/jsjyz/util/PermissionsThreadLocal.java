package ga.jsjyz.util;

import ga.jsjyz.Bo.PermissionsBo;
import ga.jsjyz.pojo.User;

public class PermissionsThreadLocal {
    private PermissionsThreadLocal(){
    }
    private static final ThreadLocal<PermissionsBo> threadLocal = new ThreadLocal<>();
    public static void put(PermissionsBo permissionsBo){
        threadLocal.set(permissionsBo);
    }
    public static PermissionsBo get(){
        return threadLocal.get();
    }
    public static void  remove(){
        threadLocal.remove();
    }
}
