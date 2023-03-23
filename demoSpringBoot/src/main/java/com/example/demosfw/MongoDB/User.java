package com.example.demosfw.MongoDB;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@Document("UserInfo")
public class User {
    @Id
    private Integer id;

    private String name;

    private String age;

    private Integer bookId;

    private List<Book> bookList;
}

