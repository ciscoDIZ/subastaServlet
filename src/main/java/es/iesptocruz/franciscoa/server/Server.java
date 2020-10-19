package es.iesptocruz.franciscoa.server;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Server {
	private final String WEBBAPP_DIR_LOCATION;
	private final Tomcat TOMCAT;
	private String webPort;
	private static Server server;
	
	private Server() {
			WEBBAPP_DIR_LOCATION = "src/main/webapp/";
			TOMCAT = new Tomcat();
			webPort = System.getenv("PORT");
	}
	
	public void setResources() {
		StandardContext context = (StandardContext) TOMCAT.addWebapp("",
				new File(WEBBAPP_DIR_LOCATION).getAbsolutePath());
		System.out.println("configurando aplicacion con directorio base: "+
				new File("./"+ WEBBAPP_DIR_LOCATION).getAbsolutePath());
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
		TOMCAT.setPort(Integer.parseInt(webPort));
	}
	
	public void setPort() {
		if(webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}
		TOMCAT.setPort(Integer.parseInt(webPort));
	}
	private static Server initServer() {
		Server retorno=null;
		if(server == null) {
			retorno = new Server();
		}
		return retorno;
	}
	public void startTomcat() {
		TOMCAT.enableNaming();
		TOMCAT.getConnector();
		try {
			TOMCAT.start();
		}catch (LifecycleException e) {
			e.printStackTrace();
		}
		TOMCAT.getServer().await();
	}
	
	public static void ini() {
		server = initServer();
		server.setPort();
		server.setResources();
		server.startTomcat();
	}
}
