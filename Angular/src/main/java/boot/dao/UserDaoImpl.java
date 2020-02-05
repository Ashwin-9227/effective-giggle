package boot.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import boot.model.UserDetails;

@Repository
public class UserDaoImpl implements UserDao
{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Object registerUser(UserDetails userDetails) 
	{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(UserDetails.class);
		cr.add(Restrictions.eq("loginName", userDetails.getLoginName()));
		if(cr.list().isEmpty())
		{
			cr = session.createCriteria(UserDetails.class);
			cr.add(Restrictions.eq("email", userDetails.getEmail()));
			if(cr.list().isEmpty())
			{
				session.save(userDetails);
				return new Gson().toJson(userDetails);
			}
			else
				return new Gson().toJson("Email ID already exist");
		}
		else
			return new Gson().toJson("Login ID already exist");
	}

	public Map<String, Object> loginUser(UserDetails userDetails) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		boolean loginexists;
		boolean passwordcorrect;
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(UserDetails.class);
		cr.add(Restrictions.eq("loginName", userDetails.getLoginName()));

		loginexists = !cr.list().isEmpty();

		cr = session.createCriteria(UserDetails.class);
		cr.add(Restrictions.eq("loginName", userDetails.getLoginName()));
		cr.add(Restrictions.eq("password", userDetails.getPassword()));

		passwordcorrect = !cr.list().isEmpty();

		if(!loginexists)
		{
			map.put("status", "Login ID either does not exist or incorrect");
		}
		else if (!passwordcorrect)
		{
			map.put("status", "Entered password is incorrect");
		}
		else if (userDetails.getLoginName().equalsIgnoreCase("admin"))
		{
			cr = session.createCriteria(UserDetails.class);
			cr.add(Restrictions.ne("id", -1));
			map.put("userdetails", cr.list());
		}
		else
		{
			map.put("userdetails", cr.list().get(0));
			map.put("status", "Login Successfull!!!");
		}

		return map;

	}

	@Override
	public Map<String, Object> generatePdf() throws DocumentException, URISyntaxException, IOException 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("AptitudeReport.pdf"));

			document.open();
			Font f=new Font(FontFamily.TIMES_ROMAN,25.0f,Font.BOLD,BaseColor.BLUE);
			Paragraph p=new Paragraph("Aptitude survey Report",f);
			p.setAlignment(Paragraph.ALIGN_CENTER);
			document.add(p);
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			addTableHeader(table);
			addRows(table);
			//addCustomRows(table);

			document.add(table);
			document.close();

			map.put("path", Paths.get(Paths.get("AptitudeReport.pdf").toAbsolutePath().normalize().toString()));
			map.put("status", "Success");
		}catch (Exception e) {
			map.put("status", "Failure");
		}
		return map;
	}

	private static void addTableHeader(PdfPTable table) {
		UserDetails object = new UserDetails();
		Class clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String fieldname = "";
		for (Field field : fields) {
			if(!field.getName().equalsIgnoreCase("loginName") && !field.getName().equalsIgnoreCase("password") && !field.getName().equalsIgnoreCase("dob"))
			{
				if(field.getName().equalsIgnoreCase("id"))
				{
					fieldname = "S.NO";
				}
				else if (field.getName().equalsIgnoreCase("fullName"))
				{
					fieldname = "Full Name";
				}
				else if (field.getName().equalsIgnoreCase("email"))
				{
					fieldname = "Email ID";
				}
				else if (field.getName().equalsIgnoreCase("testresult"))
				{
					fieldname = "Test Result";
				}
				else if (field.getName().equalsIgnoreCase("lastattemptoftest"))
				{
					fieldname = "Last Attempt";
				}
				else if (field.getName().equalsIgnoreCase("totalattempts"))
				{
					fieldname = "Total Attempts";
				}
				PdfPCell header = new PdfPCell();
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setBorderWidth(2);
				header.setPhrase(new Phrase(fieldname));
				table.addCell(header);
			}
		}
	}

	private void addRows(PdfPTable table) {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(UserDetails.class);
		cr.add(Restrictions.ne("id", -1));
		List results = cr.list();
		int index = 1;

		for (Iterator iterator = results.iterator(); iterator.hasNext();){
			UserDetails userDetails = (UserDetails) iterator.next(); 
			table.addCell(String.valueOf(index++));
			table.addCell(userDetails.getFullName());
			table.addCell(userDetails.getEmail());
			table.addCell(userDetails.getTestresult());
			table.addCell(userDetails.getLastattemptoftest());
			table.addCell(String.valueOf(userDetails.getTotalattempts()));
		}
	}

	private static void addCustomRows(PdfPTable table) 
			throws URISyntaxException, BadElementException, IOException {
		Path path = Paths.get(Paths.get("logo.jpg").toAbsolutePath().normalize().toString());
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		img.scalePercent(2);

		PdfPCell imageCell = new PdfPCell(img);
		//table.addCell(imageCell);

		PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
		horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell);

		PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
		verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell);
	}



}
