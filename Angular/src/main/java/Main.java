import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Main {

	public static void main(String[] args) throws IOException 
	{
		//AptitudeValidateanswers aptitudeValidateanswers = new AptitudeValidateanswers();

		FileInputStream fis=new FileInputStream(new File("Aptitude.xlsx"));  

		HSSFWorkbook wb=new HSSFWorkbook(fis);   

		HSSFSheet sheet=wb.getSheetAt(0);  

		System.out.println(sheet);
	}

}
