package ru.virtu.systems.config;

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import ru.virtu.systems.WicketApplication;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Alexey Pustohin
 */
public class FTInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Настраиваем спринг
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        servletContext.addListener(new ContextLoaderListener(context));
        context.register(AppConfig.class);
        context.register(SecurityConfig.class);

        // Фильтр Spring Security
        FilterRegistration.Dynamic securityFilter = servletContext
                .addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(null,false, "/*");

        // Фильтр Wicket
        FilterRegistration wicketFilter = servletContext
                .addFilter("virtu.systems.wicket", WicketFilter.class);
        wicketFilter.setInitParameter("applicationClassName", WicketApplication.class.getName());
        wicketFilter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
        wicketFilter.addMappingForUrlPatterns(null, true, "/*");
    }

}
