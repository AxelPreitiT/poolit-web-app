package ar.edu.itba.paw.persistence.config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

//
//@ComponentScan({"ar.edu.itba.paw.persistence"})
//@Configuration
//public class TestConfig {
//    @Value("classpath:hsqldb.sql")
//    private Resource hsqldb;
//    @Value("classpath:cities.sql")
//    private Resource cities;
//
////    @Value("classpath:schema.sql")
////    private Resource schema;
//    @Bean
//    public DataSource dataSource() {
//        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(org.postgresql.Driver.class);
//        dataSource.setUrl("jdbc:postgresql://localhost:5434/paw_2023a_07");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("test");
//        return dataSource;
//    }
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource ds){
//        final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//        dataSourceInitializer.setDataSource(ds);
//        dataSourceInitializer.setDatabasePopulator(databasePopulator());
//        return dataSourceInitializer;
//    }
//    private DatabasePopulator databasePopulator(){
//        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//        databasePopulator.addScript(hsqldb);
////        databasePopulator.addScript(cities);
////        databasePopulator.addScript(schema);
//        return databasePopulator;
//    }
//}
