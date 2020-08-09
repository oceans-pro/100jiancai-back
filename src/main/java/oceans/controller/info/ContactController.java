package oceans.controller.info;

import oceans.model.Contact;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/info")
public class ContactController {
    @Resource
    private ContactService contactService;

    @GetMapping("/contact")
    public ResponseEntity<StatusMsgData<Contact>> getContact() {
        Contact contact = contactService.selectLatestContact();
        return new ResponseEntity<>(
                new StatusMsgData<>(contact),
                HttpStatus.OK
        );
    }

    @PostMapping("/contact")
    public ResponseEntity<StatusMsgData<String>> postContact(Contact contact) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contact.setDatetime(simpleDateFormat.format(new Date()));
        contact.setId(null);
        contactService.insertOne(contact);
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_TIP, "修改成功"),
                HttpStatus.OK
        );
    }
}
