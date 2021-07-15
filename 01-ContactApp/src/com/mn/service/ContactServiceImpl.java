package com.mn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mn.entity.Contact;
import com.mn.repository.ContactRepository;

@Service // constructor injection
public class ContactServiceImpl implements ContactService {
	private ContactRepository contactrepository;

	@Autowired
	public ContactServiceImpl(ContactRepository contactrepository) {
		this.contactrepository = contactrepository;
	}

	@Override // save the contact
	public boolean saveContact(Contact contact) {
		contact.setActiveSw("Y");
		Contact savedEntity = contactrepository.save(contact);
		if (savedEntity != null && savedEntity.getContactId() != null) {
			return true;
		}
		return false;
	}

	@Override // gives the list of all contacts
	public List<Contact> getAllContacts() {
		Contact contactFilter = new Contact();
		contactFilter.setActiveSw("Y");
		Example<Contact> example = Example.of(contactFilter);
		List<Contact> contacts = contactrepository.findAll(example);
		return contacts;
	}

	public Page<Contact> getAllContactNew(Integer pageNo, Integer pageSize) {

		Contact contactFilter = new Contact();
		contactFilter.setActiveSw("Y");
		Example<Contact> example = Example.of(contactFilter);
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		Page<Contact> findAll = contactrepository.findAll(example, pageRequest);

		return findAll;

	}

	@Override // edit method
	public Contact getContactById(Integer contactId) {
		Optional<Contact> findById = contactrepository.findById(contactId);
		if (findById.isPresent()) {

			return findById.get();
		}
		return null;
	}

	@Override // deletes contact
	public boolean deleteContact(Integer contactId) {
		/*
		 * //Hard Delete Approach boolean existsById =
		 * contactrepository.existsById(contactId); if (existsById) {
		 * contactrepository.deleteById(contactId); } return false;
		 */

		// Soft Delete Approach
		Optional<Contact> findById = contactrepository.findById(contactId);
		if (findById.isPresent()) {
			Contact contact = findById.get();
			contact.setActiveSw("N");
			contactrepository.save(contact);
			return true;
		}
		return false;
	}

}
