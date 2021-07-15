package com.mn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mn.entity.Contact;

public interface ContactService {
	public boolean saveContact(Contact contact);

	public List<Contact> getAllContacts();

	public Page<Contact> getAllContactNew(Integer pageNo, Integer pageSize);

	public Contact getContactById(Integer contactId);

	public boolean deleteContact(Integer contactid);
}
