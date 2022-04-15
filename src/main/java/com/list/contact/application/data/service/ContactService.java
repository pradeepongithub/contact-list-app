package com.list.contact.application.data.service;

import com.list.contact.application.data.model.Contact;
import com.list.contact.application.data.model.PageResponse;
import com.list.contact.application.data.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final CsvDataService csvDataService;
    private final SearchStoreService searchStoreService;

    @Value("${app.data.source}")
    public String dataSource;

    public final PageResponse getPageableContacts(final int page, final int size) {
        return ofNullable(dataSource)
                .filter(d -> "db".equalsIgnoreCase(dataSource))
                .map(e -> getDBPageableContacts(page, size))
                .orElseGet(this::getCSVPageableContacts);
    }


    private PageResponse getDBPageableContacts(final int page, final int size) {
        Page<com.list.contact.application.data.entity.Contact> pageContacts = contactRepository.findAll(PageRequest.of(page, size));
        List<Contact> contacts = mapToContactModel(pageContacts.getContent());
        searchStoreService.initSearchStore(contacts);
        return PageResponse.builder().content(contacts).size(pageContacts.getTotalElements()).build();
    }

    private PageResponse getCSVPageableContacts() {
        List<Contact> contacts = csvDataService.getAllCsvContacts();
        searchStoreService.initSearchStore(contacts);
        return ofNullable(contacts)
                .map(e -> PageResponse.builder().size(e.size()).content(e).build())
                .orElse(new PageResponse());
    }

    private List<Contact> mapToContactModel(final List<com.list.contact.application.data.entity.Contact> contacts) {
        return of(contacts)
                .map(Collection::stream)
                .map(contact -> contact.map(e-> Contact.builder().name(e.getName()).photoUrl(e.getPhotoUrl()).build()))
                .map(e -> e.collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }


    public final PageResponse getAllContactsByNamePrefix(final String namePrefix) {
        return ofNullable(namePrefix)
                .map(searchStoreService.searchStore::prefixMap)
                .map(SortedMap::entrySet)
                .map(Collection::stream)
                .map(e -> e.map(c -> Contact.builder().name(c.getKey()).photoUrl(c.getValue()).build()))
                .map(e -> e.collect(Collectors.toList()))
                .map(contacts-> PageResponse.builder().content(contacts).build())
                .orElse(new PageResponse());
    }

}
