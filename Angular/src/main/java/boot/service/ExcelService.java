package boot.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import boot.dao.ExcelDao;

@Service("excelService")
public class ExcelService 
{

	@Autowired
	ExcelDao excelDao;

	@Transactional
	public Object uploadAptitude(MultipartFile file) throws IOException 
	{
		return excelDao.uploadAptitude(file);
	}

}
