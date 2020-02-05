package boot.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import com.itextpdf.text.DocumentException;

import boot.model.UserDetails;

public interface UserDao 
{
 
	public Object registerUser(UserDetails userDetails);

	public Map<String, Object> loginUser(UserDetails userDetails);
	
	public Map<String, Object> generatePdf() throws DocumentException, URISyntaxException, IOException;
 
}
