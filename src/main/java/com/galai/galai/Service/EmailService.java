package com.galai.galai.Service;

import com.galai.galai.Entity.Commande;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws ResendException {
        String apiKey = "re_WtQ7jeXe_MuCxYM7A59fGnNkTbooJXs17";
        Resend client = new Resend(apiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Galai - best honey in ur city <onboarding@resend.dev>")
                .to(to)
//                .cc("carbon@example.com", "copy@example.com")
//                .bcc("blind@example.com", "carbon.copy@example.com")
                .replyTo("mbarkihoussem99@gmail.com")
                .subject(subject)
                .html(htmlBody)
                .build();
        client.emails().send(params);
    }

    public void sendEmailToAdmin(Commande commande) throws ResendException {
        String htmlBody = generateEmailBody(commande);
        sendHtmlMessage("mbarkihoussem99@gmail.com", "New Command", htmlBody);
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
