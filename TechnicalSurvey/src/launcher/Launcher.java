package launcher;

import java.io.IOException;

import org.apache.tuscany.sca.host.embedded.SCADomain;

public class Launcher {

	public static void main(String[] args) throws IOException 
	{
		SCADomain scaDomain = SCADomain.newInstance("techsurvey.composite");
		System.in.read();
		System.out.println("<<--- Stopping --->>");
		scaDomain.close();

	}

}
