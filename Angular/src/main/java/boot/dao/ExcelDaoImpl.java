package boot.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import boot.model.AptitudeAnswers;
import boot.model.AptitudeQuestions;
import boot.model.AptitudeValidateanswers;

@Repository
public class ExcelDaoImpl implements ExcelDao
{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("deprecation")
	public Object uploadAptitude(MultipartFile file) throws IOException 
	{
		String response = "";
		StringBuilder fileName = new StringBuilder();
		if (file.isEmpty()) {
			return "please select a file!";
		}

		try {
			fileName.append(saveUploadedFiles(Arrays.asList(file)));
		} catch (IOException e) {
			return "Bad request"+e;
		}

		String str = "";
		Query query;
		AptitudeQuestions aptitudeQuestions = new AptitudeQuestions();
		List<AptitudeQuestions> listAptitudeQuestions = new ArrayList<AptitudeQuestions>();

		AptitudeAnswers aptitudeAnswers = new AptitudeAnswers();
		List<AptitudeAnswers> listAptitudeAnswers = new ArrayList<AptitudeAnswers>();

		AptitudeValidateanswers aptitudeValidateanswers = new AptitudeValidateanswers();
		List<AptitudeValidateanswers> listAptitudeValidateanswers = new ArrayList<AptitudeValidateanswers>();

		FileInputStream fis=new FileInputStream(new File(fileName.toString()));  

		XSSFWorkbook xb=new XSSFWorkbook(fis); 
		FormulaEvaluator formulaEvaluator=xb.getCreationHelper().createFormulaEvaluator();

		for (int i = 0; i < xb.getNumberOfSheets(); i++)
		{
			XSSFSheet sheet = xb.getSheetAt(i);
			XSSFRow row = sheet.getRow(0);
			int col_num = -1;

			for(int j=0; j < row.getLastCellNum(); j++)
			{
				for(int k=0; k < row.getLastCellNum(); k++)
				{
					if(row.getCell(k).getStringCellValue().trim().equals(row.getCell(j).getStringCellValue().trim()))
						col_num = k;
				}

				for(Row row1: sheet)  
				{
					if(row1.getRowNum() != 0)
					{
						String value ="";
						XSSFRow row2 = sheet.getRow(row1.getRowNum());
						XSSFCell cell = row2.getCell(col_num);

						if(sheet.getSheetName().equalsIgnoreCase("Questions"))
						{
							aptitudeQuestions = new AptitudeQuestions();
							if(!listAptitudeQuestions.isEmpty() && listAptitudeQuestions.size() >= row1.getRowNum()
									&& listAptitudeQuestions.get(row1.getRowNum()-1) != null)
							{
								aptitudeQuestions = listAptitudeQuestions.get(row1.getRowNum()-1);
							}
							switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
							{
							case Cell.CELL_TYPE_NUMERIC: 
								value = String.valueOf((int)cell.getNumericCellValue()); 
								System.out.println("Value of the Excel Cell is - "+ value);
								aptitudeQuestions.setQuesid(Integer.parseInt(value));
								break; 

							case Cell.CELL_TYPE_STRING: 
								value = cell.getStringCellValue();
								System.out.println("Value of the Excel Cell is - "+ value);
								aptitudeQuestions.setQuestion(value);
								break;
							}
							if(!listAptitudeQuestions.isEmpty() && listAptitudeQuestions.size() >= row1.getRowNum() 
									&& listAptitudeQuestions.get(row1.getRowNum()-1) != null)
							{
								listAptitudeQuestions.set(row1.getRowNum()-1, aptitudeQuestions);
							}
							else 
							{
								listAptitudeQuestions.add(row1.getRowNum()-1, aptitudeQuestions);
							}
						}
						else if(sheet.getSheetName().equalsIgnoreCase("Answers"))
						{
							aptitudeAnswers = new AptitudeAnswers();
							if(!listAptitudeAnswers.isEmpty() && listAptitudeAnswers.size() >= row1.getRowNum()
									&& listAptitudeAnswers.get(row1.getRowNum()-1) != null)
							{
								aptitudeAnswers = listAptitudeAnswers.get(row1.getRowNum()-1);
							}
							switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
							{
							case Cell.CELL_TYPE_NUMERIC: 
								value = String.valueOf((int)cell.getNumericCellValue()); 
								System.out.println("Value of the Excel Cell is - "+ value);
								int checkAnswid = aptitudeAnswers.getAnswid();
								int checkQuesid = aptitudeAnswers.getQuesid();
								if(checkAnswid == 0)
								{
									aptitudeAnswers.setAnswid(Integer.parseInt(value));
								}
								else if(checkAnswid != 0 && checkQuesid == 0)
								{
									aptitudeAnswers.setQuesid(Integer.parseInt(value));
								}
								break;

							case Cell.CELL_TYPE_STRING: 
								value = cell.getStringCellValue();
								System.out.println("Value of the Excel Cell is - "+ value);
								aptitudeAnswers.setAnswer(value);
								break;
							}
							if(!listAptitudeAnswers.isEmpty() && listAptitudeAnswers.size() >= row1.getRowNum() 
									&& listAptitudeAnswers.get(row1.getRowNum()-1) != null)
							{
								listAptitudeAnswers.set(row1.getRowNum()-1, aptitudeAnswers);
							}
							else 
							{
								listAptitudeAnswers.add(row1.getRowNum()-1, aptitudeAnswers);
							}

						}
						else if(sheet.getSheetName().equalsIgnoreCase("Validation"))
						{

							aptitudeValidateanswers = new AptitudeValidateanswers();
							if(!listAptitudeValidateanswers.isEmpty() && listAptitudeValidateanswers.size() >= row1.getRowNum()
									&& listAptitudeValidateanswers.get(row1.getRowNum()-1) != null)
							{
								aptitudeValidateanswers = listAptitudeValidateanswers.get(row1.getRowNum()-1);
							}
							switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
							{
							case Cell.CELL_TYPE_NUMERIC: 
								value = String.valueOf((int)cell.getNumericCellValue()); 
								System.out.println("Value of the Excel Cell is - "+ value);
								int checkValid = aptitudeValidateanswers.getValid();
								int checkQuesid = aptitudeValidateanswers.getQuesid();
								int checkAnswid = aptitudeValidateanswers.getAnswid();
								if(checkValid == 0)
								{
									aptitudeValidateanswers.setValid(Integer.parseInt(value));
								}
								else if (checkValid != 0 && checkQuesid == 0)
								{
									aptitudeValidateanswers.setQuesid(Integer.parseInt(value));
								}
								else if (checkValid != 0 && checkQuesid != 0 && checkAnswid == 0)
								{
									aptitudeValidateanswers.setAnswid(Integer.parseInt(value));
								}
								break;

							case Cell.CELL_TYPE_STRING: 
								value = cell.getStringCellValue();
								System.out.println("Value of the Excel Cell is - "+ value);
								break;
							}
							if(!listAptitudeValidateanswers.isEmpty() && listAptitudeValidateanswers.size() >= row1.getRowNum() 
									&& listAptitudeValidateanswers.get(row1.getRowNum()-1) != null)
							{
								listAptitudeValidateanswers.set(row1.getRowNum()-1, aptitudeValidateanswers);
							}
							else 
							{
								listAptitudeValidateanswers.add(row1.getRowNum()-1, aptitudeValidateanswers);
							}


						}
					}
				}
			}
		}
		xb.close();

		if(!listAptitudeQuestions.isEmpty() && !listAptitudeAnswers.isEmpty() && !listAptitudeValidateanswers.isEmpty())
		{	
			try{

				Session session = this.sessionFactory.getCurrentSession();

				str = "truncate table AptitudeQuestions";
				query = session.createSQLQuery(str);
				query.executeUpdate();

				for(AptitudeQuestions questions :  listAptitudeQuestions)
				{
					str = "insert into AptitudeQuestions values ("+questions.getQuesid()+",'"+questions.getQuestion()+"',1,"+questions.getSortorder()+")";
					query = session.createSQLQuery(str);
					query.executeUpdate();

				}


				str = "truncate table AptitudeAnswers";
				query = session.createSQLQuery(str);
				query.executeUpdate();

				for(AptitudeAnswers answers :  listAptitudeAnswers)
				{
					str = "insert into AptitudeAnswers values ("+answers.getAnswid()+","+answers.getQuesid()+",'"+answers.getAnswer()+"',"+answers.getSortorder()+")";
					query = session.createSQLQuery(str);
					query.executeUpdate();
				}
				str = "truncate table AptitudeValidateanswers";
				query = session.createSQLQuery(str);
				query.executeUpdate();

				for(AptitudeValidateanswers validateanswers :  listAptitudeValidateanswers)
				{
					str = "insert into AptitudeValidateanswers values ("+validateanswers.getValid()+","+validateanswers.getQuesid()+","+validateanswers.getAnswid()+")";
					query = session.createSQLQuery(str);
					query.executeUpdate();
				}

				response =  new Gson().toJson("Import Successfull!!!");

			} catch (Exception e){
				response = new Gson().toJson("Error in import!!!");
			}

		}
		return response;

	}

	//save file
	private String saveUploadedFiles(List<MultipartFile> files) throws IOException {

		String filename = "";
		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; //next pls
			}

			filename = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			Path path = Paths.get(Paths.get(".").toAbsolutePath().normalize().toString()+ "\\" +file.getOriginalFilename());
			Files.write(path, bytes);
			System.out.println();

		}
		return filename;

	}



}
