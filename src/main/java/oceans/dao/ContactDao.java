package oceans.dao;

import oceans.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDao extends JpaRepository<Contact, Integer> {
    Contact findFirstByOrderByDatetimeDesc();
}
