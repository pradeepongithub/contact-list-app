package com.list.contact.application.data.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "url")
    private String photoUrl;
}
