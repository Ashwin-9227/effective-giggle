import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregoriantoDateUtil {

	public static void main(String[] args) throws IOException, DatatypeConfigurationException 
	{

		GregorianCalendar gCal = new GregorianCalendar();

		XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);

		Date date = convertXMLGregoriantoDateUtil(xmlGregorianCalendar);
		System.out.println(xmlGregorianCalendar);
		System.out.println(date);


	}

	private static Date convertXMLGregoriantoDateUtil(XMLGregorianCalendar xmlGregorianCalendar)
	{
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a z");

		GregorianCalendar gCalendar = xmlGregorianCalendar.toGregorianCalendar();

		Date date = gCalendar.getTime();

		return date;

	}

}
