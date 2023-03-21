package com.example.multitenenttest.async;

import com.example.multitenenttest.util.TenantContext;
import lombok.NonNull;
import org.springframework.core.task.TaskDecorator;

public class TenantAwareTaskDecorator implements TaskDecorator {
    @Override
    @NonNull
    public Runnable decorate(@NonNull Runnable runnable) {
        String tenantId = TenantContext.getTenantId();
        return ()->{
            try {
                TenantContext.setTenantId(tenantId);
                runnable.run();
            }finally {
                TenantContext.setTenantId(null);
            }
        };
    }
}
