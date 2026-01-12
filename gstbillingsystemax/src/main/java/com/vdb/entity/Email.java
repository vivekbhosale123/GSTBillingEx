package com.vdb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Email {

    private String toEmail;

    private String ccEmail;

    private String emailSubject;

    private String emailBody;

    private String emailAttachment;

}
