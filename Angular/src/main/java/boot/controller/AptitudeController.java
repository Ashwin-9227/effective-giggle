package boot.controller;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.model.UserDetails;
import boot.service.AptitudeService;
import boot.service.ExcelService;
import boot.service.UserService;

@RestController
@SuppressWarnings("unchecked")
public class AptitudeController 
{

	@Autowired
	UserService userService;

	@Autowired
	AptitudeService aptitudeService;	

	@Autowired
	ExcelService excelService;

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object registerUser(@RequestBody UserDetails userDetails) {
		return userService.registerUser(userDetails);
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object loginUser(@RequestBody UserDetails userDetails) {
		return userService.loginUser(userDetails);
	}

	@RequestMapping(value = "/getAptitude", method = RequestMethod.GET, headers = "Accept=application/json")
	public Object getinitialization() throws Exception {
		return aptitudeService.getinitialization();
	}

	@RequestMapping(value = "/validateAnswer", method = RequestMethod.POST, headers = "Accept=application/json")
	public Object validateanswers(@RequestBody Object object) throws Exception {
		LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) object;
		return aptitudeService.validateanswers(linkedHashMap.get("validateanswerarray"),Integer.parseInt(linkedHashMap.get("userid").toString()));
	}

	@RequestMapping(value = "/uploadAptitude", method = RequestMethod.POST, headers = "Content-Type= multipart/form-data")
	public Object uploadAptitude(@RequestParam("file") MultipartFile file) throws IOException {
		return excelService.uploadAptitude(file);
	}

}
