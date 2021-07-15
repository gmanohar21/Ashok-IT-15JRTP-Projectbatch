package com.mn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mn.entity.Contact;
import com.mn.service.ContactService;

@Controller
public class ViewContactsController {
	@Autowired
	private ContactService contactService;

	@GetMapping("/editContact")
	public ModelAndView editLink(@RequestParam("cid") Integer contactId) {// expecting contact id as request parameter

		ModelAndView mav = new ModelAndView();
		Contact cobj = contactService.getContactById(contactId);
		mav.addObject("contact", cobj);
		mav.setViewName("contact");
		return mav;

	}

	@GetMapping("/deleteContact")
	public String deleteLink(@RequestParam("cid") Integer contactId) {
		contactService.deleteContact(contactId);
		return "redirect:viewContacts";

	}
}
