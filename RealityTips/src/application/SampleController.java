package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SampleController {

    @FXML
    private TextField billField;

    @FXML
    private TextField tipField;

    @FXML
    private TextField nbPeopleField;

    @FXML
    private Button btnCalculate;

    @FXML
    private Label labelTipPerPerson;

    @FXML
    private Label labelTotalPerPerson;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField dateLabel;

    @FXML
    private Button btnLoadData;

    private ArrayList<String> historic = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    void onBtnCalculate(ActionEvent event) throws ParseException, IOException, ClassNotFoundException {
        double bill = 0;
        double tip = 0;
        int nbPeople = 0;

        String date = String.valueOf(dateLabel.getText());
        errorLabel.setText("");

        try {
            date = TipExceptions.date(date);
            bill = TipExceptions.bill(billField.getText());
            tip = TipExceptions.tip(tipField.getText());
            nbPeople = TipExceptions.nbPeople(nbPeopleField.getText());

            Calculate calcul = new Calculate(bill, tip, nbPeople);

            double tipPerson = calcul.getTipPerPerson(bill, tip, nbPeople);
            labelTipPerPerson.setText(String.valueOf(df.format(tipPerson)));

            double totalPerson = calcul.getTotalPerPerson(bill, tip, nbPeople);
            labelTotalPerPerson.setText(String.valueOf(df.format(totalPerson)));

            String historyDate = date + ";" + bill + " ;" + tip + " ;" + nbPeople;

            saveHistory(historyDate, date, calcul);

        } catch (IndexOutOfBoundsException e) {
            errorLabel.setText(e.getMessage());
        } catch (NumberFormatException e) {
            errorLabel.setText("Les caractères ne sont pas autorisés");
        }
    }

    @FXML
    void onLoadData(ActionEvent event) throws IOException {
        String inputDate = dateLabel.getText();

        if (inputDate.isEmpty()) {
            errorLabel.setText("Veuillez entrer une date.");
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(inputDate);

            loadDataForDate(inputDate);

        } catch (ParseException e) {
            errorLabel.setText("Format de date invalide. Utilisez le format dd/MM/yyyy.");
        }
    }

    private void loadDataForDate(String inputDate) throws IOException {
        File file = new File("src/history.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == 4 && parts[0].trim().equals(inputDate.trim())) {
                        billField.setText(parts[1].trim());
                        tipField.setText(parts[2].trim());
                        nbPeopleField.setText(parts[3].trim());

                        errorLabel.setText("Données chargées pour la date " + inputDate);
                        return;
                    }
                }
            }
        }

        errorLabel.setText("Aucune donnée trouvée pour la date " + inputDate);
        billField.setText("");
        tipField.setText("");
        nbPeopleField.setText("");
    }

    void saveHistory(String history, String dateHistory, Calculate calculate) throws IOException {
        File file = new File("src/history.txt");
        ArrayList<String> newHistoric = new ArrayList<>();
        boolean dateExists = false;

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(dateHistory)) {
                        newHistoric.add(history);
                        dateExists = true;
                    } else {
                        newHistoric.add(line);
                    }
                }
            }
        }

        if (!dateExists) {
            newHistoric.add(history);
        }

        historic = newHistoric;

        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String line : historic) {
                printWriter.println(line);
            }
        }
    }
}
