package com.tgd.maintenance_soft_server.lib.blo_service.interfaces;

public interface UserIdentifiable<U> {
    void setUser(U user);
    U getUser();
}
