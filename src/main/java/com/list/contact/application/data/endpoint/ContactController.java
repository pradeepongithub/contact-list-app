package com.list.contact.application.data.endpoint;

import com.list.contact.application.data.model.PageResponse;
import com.list.contact.application.data.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public PageResponse getAllContacts(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size) {
        return contactService.getPageableContacts(page, size);
    }

    @GetMapping("/search")
    public PageResponse getAllContactsByNamePrefix(@RequestParam(required = false) String namePrefix) {
        return contactService.getAllContactsByNamePrefix(namePrefix);
    }

}
