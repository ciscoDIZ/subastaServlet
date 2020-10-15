package es.iesptocruz.franciscoa.server;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Server {
	private String webappDirLocation;
	private Tomcat tomcat;
	private String webPort;
	private static Server server;
	
	private Server() {
			webappDirLocation = "src/main/webapp/";
			tomcat = new Tomcat();
			webPort = System.getenv("PORT");
	}
	
	public void setResources() {
		StandardContext context = (StandardContext)tomcat.addWebapp("",
				new File(webappDirLocation).getAbsolutePath());
		System.out.println("configurando aplicacion con directorio base: "+
				new File("./"+webappDirLocation).getAbsolutePath());
		File additionalWebInfClasses = new File("target/classes");
		WebResourceRoot resources = new StandardRoot(context);
		resources.addPreResources(new DirResourceSet(resources,
				"/WEB-INF/classes",additionalWebInfClasses.getAbsolutePath(),"/"));
		context.setResources(resources);
	}
	
	public void setPort(String port) {
		if(webPort == null || webPort.isEmpty()) {
			webPort = port;
		}
		tomcat.setPort(Integer.valueOf(webPort));
	}
	
	public void setPort() {
		if(webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}
		tomcat.setPort(Integer.valueOf(webPort));
	}
	private static Server initServer() {
		Server retorno=null;
		if(server == null) {
			retorno = new Server();
		}
		return retorno;
	}
	public void startTomcat() {
		tomcat.enableNaming();
		tomcat.getConnector();
		try {
			tomcat.start();
		}catch (LifecycleException e) {
			e.printStackTrace();
		}
		tomcat.getServer().await();
	}
	
	public static void ini() {
		server = initServer();
		server.setPort();
		server.setResources();
		server.startTomcat();
	}
}
