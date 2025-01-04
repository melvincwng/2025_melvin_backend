package iss.nus.edu.sg;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import iss.nus.edu.sg.api.CoinChangeResource;
import iss.nus.edu.sg.api.HealthCheckResource;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

public class Main extends Application<AppConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }


    @Override
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception {
        HealthCheckResource healthResource = new HealthCheckResource();
        CoinChangeResource coinResource = new CoinChangeResource();
        environment.jersey().register(healthResource);
        environment.jersey().register(coinResource);
        configureCors(environment);
    }

    // Configures Dropwizard to load the application's configuration file from the classpath using a ResourceConfigurationSourceProvider
    // and then calls the parent class's initialization logic
    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }

    // To allow CORS for React frontend's API call to go through
    // Reference: https://gist.github.com/aweiland/3406db0ae70e043d67a3bc09dc195e81
    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}