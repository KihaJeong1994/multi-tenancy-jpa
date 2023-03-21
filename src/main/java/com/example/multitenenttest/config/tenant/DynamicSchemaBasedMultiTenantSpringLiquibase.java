package com.example.multitenenttest.config.tenant;

import com.example.multitenenttest.domain.entity.master.Tenant;
import com.example.multitenenttest.repository.master.TenantRepository;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Slf4j
public class DynamicSchemaBasedMultiTenantSpringLiquibase implements InitializingBean , ResourceLoaderAware {

    @Autowired
    private  TenantRepository masterTenantRepository;

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("tenantLiquibaseProperties")
    private  LiquibaseProperties liquibaseProperties;

    private ResourceLoader resourceLoader;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("DynamicDataSources based multitenancy enabled");
        this.runAllSchemas(dataSource, masterTenantRepository.findAll());
    }

    protected void runAllSchemas(DataSource dataSource, Collection<Tenant> tenants) throws LiquibaseException {
        for(Tenant tenant:tenants){
            log.info("Initializing Liquibase for tenant "+ tenant.getTenantId());
            SpringLiquibase liquibase = this.getSpringLiquibase(dataSource,tenant.getSchema());
            liquibase.afterPropertiesSet();
            log.info("Liquibase ran for tenant "+ tenant.getTenantId());
        }
    }

    protected SpringLiquibase getSpringLiquibase(DataSource dataSource,String schema) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(getResourceLoader());
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        if (liquibaseProperties.getParameters() != null) {
            liquibaseProperties.getParameters().put("schema", schema);
            liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        } else {
            liquibase.setChangeLogParameters(Collections.singletonMap("schema", schema));
        }
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        return liquibase;

    }
}
