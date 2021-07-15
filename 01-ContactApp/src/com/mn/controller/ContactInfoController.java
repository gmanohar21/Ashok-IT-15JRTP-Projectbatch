package com.mn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mn.entity.Contact;
import com.mn.service.ContactService;

@Controller("/app")
public class ContactInfoController {

	private ContactService contactService;

	public ContactInfoController(ContactService contactService) {
		this.contactService = contactService;
	}

	@GetMapping("/loadForm")
	public String loadForm(Model model) {
		Contact ct = new Contact();
		model.addAttribute("contact", ct);
		return "contact";
	}

	@PostMapping("/saveContact")
	public String SaveContactButton(Contact contact, Model model) {

		boolean isSaved = contactService.saveContact(contact);

		if (isSaved) {
			model.addAttribute("sccMsg", "Saved Successfully ");

		} else {
			model.addAttribute("errMsg", "Not Saved");
		}
		return "contact";
	}

	/*	@GetMapping("/viewContacts")
		public ModelAndView displayAllContacts() {
			ModelAndView mav = new ModelAndView();
			List<Contact> allContacts = contactService.getAllContacts();
			// Setting data to model in key value pairs
			mav.addObject("contacts", allContacts);
			// Setting logical view name
			mav.setViewName("viewContacts");
			return mav;
		}
	*/
	@GetMapping("/viewContacts")
	public ModelAndView displayAllContacts(HttpServletRequest req) {

		Integer pageNo = 1;
		Integer pageSize = 3;

		String reqParam = req.getParameter("pno");
		if (reqParam != null && !" ".equals(reqParam)) {
			pageNo = Integer.parseInt(reqParam);
		}
		ModelAndView mav = new ModelAndView();
		Page<Contact> page = contactService.getAllContactNew(pageNo - 1, pageSize);
		int totalPages = page.getTotalPages();
		List<Contact> allContacts = page.getContent();
		// Setting data to model in key value pairs
		mav.addObject("contacts", allContacts);
		mav.addObject("tp", totalPages);
		mav.addObject("currPno", pageNo);
		// Setting logical view name
		mav.setViewName("viewContacts");
		return mav;
	}

}
