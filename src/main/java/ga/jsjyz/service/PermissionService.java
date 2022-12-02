package ga.jsjyz.service;

import ga.jsjyz.util.Response;

public interface PermissionService {
    Response login(String username, String password);

    Response register(String username, String password);
}
