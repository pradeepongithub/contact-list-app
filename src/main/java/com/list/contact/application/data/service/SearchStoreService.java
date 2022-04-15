package com.list.contact.application.data.service;

import com.list.contact.application.data.model.Contact;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class SearchStoreService {

    public final Trie<String, String> searchStore = new PatriciaTrie<>();;

    public void initSearchStore(final List<Contact> contacts) {
        searchStore.clear();
        ofNullable(contacts)
                .ifPresent(contactList -> contactList.forEach(contact -> searchStore.put(contact.getName(), contact.getPhotoUrl())));
    }
}
