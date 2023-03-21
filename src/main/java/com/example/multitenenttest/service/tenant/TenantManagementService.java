package com.example.multitenenttest.service.tenant;

public interface TenantManagementService {
    public void createTenant(String tenantId, String schema);
}
