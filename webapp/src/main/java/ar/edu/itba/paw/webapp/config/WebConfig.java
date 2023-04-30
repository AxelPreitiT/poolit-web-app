package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan({"ar.edu.itba.paw.webapp.controller","ar.edu.itba.paw.services","ar.edu.itba.paw.persistence"})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;
    @Value("classpath:schema.sql")
    private Resource schema;


    @Bean
    public ViewResolver viewResolver(){
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
/*
    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl(String.format("jdbc:postgresql://localhost/%s",environment.getProperty("DB_NAME")));
        ds.setUsername(environment.getProperty("DB_USER"));
        ds.setPassword(environment.getProperty("DB_PASSWORD"));
        return ds;
    }
*/

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl(String.format("jdbc:postgresql://localhost:%s/%s",environment.getProperty("DB_PORT"),environment.getProperty("DB_NAME")));
        ds.setUsername(environment.getProperty("DB_USER"));
        ds.setPassword(environment.getProperty("DB_PASSWORD"));
        return ds;
    }


    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schema);
        return populator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public MessageSource messageSource(){
        final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();

        ms.setCacheSeconds((int) TimeUnit.SECONDS.toSeconds(5));
        ms.setBasename("classpath:i18n/messages");
        ms.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return ms;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10485760); // Tamaño máximo de archivo en bytes, 1024*1024*10
        return multipartResolver;
    }


}
