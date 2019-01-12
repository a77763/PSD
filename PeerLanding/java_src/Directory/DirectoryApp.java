package Directory;

import Directory.Resources.Historico;

// import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import io.dropwizard.setup.Bootstrap;

public class DirectoryApp extends Application<DirectoryConf>{
    public static void main(String[] args) throws Exception {
        new DirectoryApp().run(args);
    }

    public void initialize(Bootstrap<DirectoryConf> bootstrap) { }

    public void run (DirectoryConf configuration, Environment environment) throws Exception {
        Historico hist = new Historico();

        environment.jersey().register(hist);
        //environment.healthChecks().register("empresas",
        //        new HealthCheck(configuration.empresas));
    }
}
