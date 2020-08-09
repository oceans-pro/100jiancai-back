package oceans.service.plain;

import oceans.model.Contact;

public interface ContactService {
    Contact selectLatestContact();

    void insertOne(Contact contact);
}
