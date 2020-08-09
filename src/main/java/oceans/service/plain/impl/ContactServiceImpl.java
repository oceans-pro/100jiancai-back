package oceans.service.plain.impl;

import oceans.dao.ContactDao;
import oceans.model.Contact;
import oceans.service.plain.ContactService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ContactServiceImpl implements ContactService {
    @Resource
    private ContactDao contactDao;

    @Override
    public Contact selectLatestContact() {
        return contactDao.findFirstByOrderByDatetimeDesc();
    }


    @Override
    public void insertOne(Contact contact) {
        Contact save = contactDao.save(contact);
    }
}
