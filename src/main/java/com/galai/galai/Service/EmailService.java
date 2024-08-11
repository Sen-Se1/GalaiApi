package com.galai.galai.Service;

import com.galai.galai.Entity.Commande;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("Galai - best honey in ur city <houssemmbarki615@gmail.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        emailSender.send(message);
    }

    public void sendEmailToAdmin(Commande commande) throws MessagingException {
        String htmlBody = generateEmailBody(commande);
        sendHtmlMessage("boujnehrania3@gmail.com", "New Command", htmlBody);
    }

    private String generateEmailBody(Commande commande) {
        String ligneCommande = commande.getLignesCommande().stream()
                .map(lC -> "<div style='margin-bottom: 4px;'>" +
                        "<strong>" + lC.getProduit().getNom() + "</strong>" +
                        " / " + lC.getQtt() + " / " + lC.getGrammage() + " g" +
                        " / " + lC.getPrix() + " DT</div>")
                .collect(Collectors.joining());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String formattedDate = commande.getDateCreation().format(formatter);

        return
            "<html><head><style>" +
                "table {width: 100%; border-collapse: collapse; font-family: Arial, sans-serif;}" +
                "th, td {padding: 8px; border-bottom: 2px solid #ddd;}" +
                "th {background-color: #f2f2f2; text-align: left;}" +
                "tr {border-bottom: 1px solid #ddd;}" +
                "h1 {font-family: Arial, sans-serif;}" +
            "</style></head><body>" +
                "<h1>New Command Details</h1>" +
                "<table border='1' cellpadding='5' cellspacing='0'>" +
                    "<thead>" +
                        "<tr>" +
                            "<th>Date</th>" +
                            "<th>Nom</th>" +
                            "<th>Prénom</th>" +
                            "<th>Adresse</th>" +
                            "<th>Pays</th>" +
                            "<th>Ville</th>" +
                            "<th>C.Postale</th>" +
                            "<th>N°Téléphone</th>" +
                            "<th colspan='4'>NomP / Quantité / Grammage / Prix</th>" +
                            "<th>Prix Totale</th>" +
                        "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                        "<tr>" +
                            "<td>" + formattedDate + "</td>" +
                            "<td>" + commande.getNom() + "</td>" +
                            "<td>" + commande.getPrenom() + "</td>" +
                            "<td>" + commande.getAdresse() + "</td>" +
                            "<td>" + commande.getPays() + "</td>" +
                            "<td>" + commande.getVille() + "</td>" +
                            "<td>" + commande.getCodePostal() + "</td>" +
                            "<td>" + commande.getNumeroTelephone() + "</td>" +
                            "<td colspan='4'>" + ligneCommande + "</td>" +
                            "<td>" + commande.getPrixTotal() + "</td>" +
                        "</tr>" +
                    "</tbody>" +
                "</table>" +
            "</body></html>";
    }
}
