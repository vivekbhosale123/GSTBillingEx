package com.vdb.service;

import com.vdb.entity.Email;
import com.vdb.entity.Invoice;
import com.vdb.exception.RecordNotFoundException;
import com.vdb.repository.InvoiceRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendMail(String custEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mineMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mineMessageHelper.setFrom(fromEmail);

//            mineMessageHelper.setTo(custEmail.getToEmail());
//            mineMessageHelper.setCc(custEmail.getCcEmail());
//            mineMessageHelper.setSubject(custEmail.getEmailSubject());
//            mineMessageHelper.setText(custEmail.getEmailBody());

            Invoice getEmail=invoiceRepository.findByEmail(custEmail);

            if(custEmail.equals(getEmail.getEmail()))
            {
                mineMessageHelper.setTo(getEmail.getEmail());
            }else{
                new RecordNotFoundException("email not found");
            }

            mineMessageHelper.setText(String.valueOf(getEmail));

//            FileSystemResource fileSystemResource = new FileSystemResource(new File(custEmail.getEmailAttachment()));
//
//            mineMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            javaMailSender.send(mimeMessage);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
