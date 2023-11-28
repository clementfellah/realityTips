package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TipExceptions {

    public static Double bill(String bill) throws IndexOutOfBoundsException {
        try {
            double parsedBill = Double.parseDouble(bill);
            if (parsedBill <= 0) {
                throw new IndexOutOfBoundsException("Bill ne peut pas être inférieur ou égal à 0");
            }
            return parsedBill;
        } catch (NumberFormatException e) {
            throw new IndexOutOfBoundsException("Veuillez saisir un nombre valide pour le champ Bill");
        }
    }

    public static Double tip(String tip) throws IndexOutOfBoundsException {
        try {
            double parsedTip = Double.parseDouble(tip);
            if (parsedTip <= 0) {
                throw new IndexOutOfBoundsException("Tip ne peut pas être inférieur ou égal à 0");
            }
            return parsedTip;
        } catch (NumberFormatException e) {
            throw new IndexOutOfBoundsException("Veuillez saisir un nombre valide pour le champ Tip");
        }
    }

    public static Integer nbPeople(String nbPeople) throws IndexOutOfBoundsException {
        try {
            int parsedNbPeople = Integer.parseInt(nbPeople);
            if (parsedNbPeople <= 0) {
                throw new IndexOutOfBoundsException("Le nombre de personnes ne peut pas être inférieur ou égal à 0");
            }
            return parsedNbPeople;
        } catch (NumberFormatException e) {
            throw new IndexOutOfBoundsException("Veuillez saisir un nombre entier valide pour le champ Nombre de personnes");
        }
    }

    public static String date(String date) throws IndexOutOfBoundsException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.parse(date);
            return date;
        } catch (ParseException e) {
            throw new IndexOutOfBoundsException("La date n'est pas au bon format (XX/XX/XXXX)");
        }
    }
}
