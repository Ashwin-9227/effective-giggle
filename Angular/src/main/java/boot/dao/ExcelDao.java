package boot.dao;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelDao 
{
 
	public Object uploadAptitude(MultipartFile file) throws IOException ;
 
}
