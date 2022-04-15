package com.list.contact.application.data.service;

import com.list.contact.application.data.model.Contact;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
public class CsvDataService {

    @Value("${app.data.file}")
    private String csvFile;

    public List<Contact> getAllCsvContacts() {

        InputStreamReader csvFileReader = new InputStreamReader(
                Objects.requireNonNull(CsvDataService.class.getResourceAsStream(csvFile)), StandardCharsets.UTF_8);

        return new CsvToBeanBuilder<Contact>(csvFileReader)
                .withType(Contact.class)
                .build()
                .parse();
    }

}
