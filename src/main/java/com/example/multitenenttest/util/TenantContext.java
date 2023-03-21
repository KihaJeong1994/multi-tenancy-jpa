package com.example.multitenenttest.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TenantContext {
    private TenantContext(){}

    private static InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId){
        log.debug("Setting tenantId to "+tenantId);
        currentTenant.set(tenantId); // ThreadLocal sets the variable that is available to current Thread
    }

    public static String getTenantId(){
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.remove();
    }
}
