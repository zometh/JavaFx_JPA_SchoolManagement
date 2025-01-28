package sn.zomethdev.school_management_jpa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.zomethdev.school_management_jpa.dao.ClasseImpl;
import sn.zomethdev.school_management_jpa.dao.EtudiantImpl;
import sn.zomethdev.school_management_jpa.entities.Classe;
import sn.zomethdev.school_management_jpa.entities.Etudiant;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class HelloController implements Initializable {
    public TableView<Etudiant> table_students;
    public TextField prenomField;
    public TextField nomField;
    public TextField moyenneField;
    public ChoiceBox<Classe> choiceBoxClasse;
    ClasseImpl classeImpl;
    EtudiantImpl etudiantImpl;
    @FXML
    private TableColumn<?, ?> idE;

    @FXML
    private TableColumn<?, ?> matrciuleE;

    @FXML
    private TableColumn<?, ?> moyenneE;
    @FXML
    private TableColumn<?, ?> effectifClasse;
    @FXML
    private TableColumn<?, ?> prenomE;
    @FXML
    private TableColumn<?, ?> nomE;
    @FXML
    private TableColumn<?, ?> classeE;
    @FXML
    private TableColumn<?, ?> idClasse;

    @FXML
    private TableColumn<?, ?> nomClasse;

    @FXML
    private TextField nomClasseField;
    @FXML
    private TableView<Classe> tableClasse;

    @FXML
    void loadSelectedClasse(MouseEvent event) {
        Classe selectedClasse = tableClasse.getSelectionModel().getSelectedItem();
        nomClasseField.setText(selectedClasse.getNom());
    }

    @FXML
    void addClasse(ActionEvent event) {

        String nom = nomClasseField.getText();
        if (!nom.isEmpty()) {
            if (!classeImpl.isClassExist(nom.toUpperCase())) {
                Classe classe = new Classe();
                classe.setNom(nom.toUpperCase());
                classe.setEffectif(0);
                classeImpl.add(classe);
                nomClasseField.clear();
                LoadClasssesToTable();
                loadChoiceBox();
                showMessage("Classe ajoutée avec succès", false);
            } else {
                showMessage("La classe existe déja!", true);

            }
        } else {
            showMessage("Veuillez entrer un nom", true);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classeImpl = new ClasseImpl();
        etudiantImpl = new EtudiantImpl();
        loadChoiceBox();
        LoadClasssesToTable();
        LoadEtudiantsToTable();

    }

    void LoadClasssesToTable() {
        idClasse.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomClasse.setCellValueFactory(new PropertyValueFactory<>("nom"));
        effectifClasse.setCellValueFactory(new PropertyValueFactory<>("effectif"));
        tableClasse.setItems(classeImpl.list());
    }

    void LoadEtudiantsToTable() {
        idE.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomE.setCellValueFactory(new PropertyValueFactory<>("nom"));
        moyenneE.setCellValueFactory(new PropertyValueFactory<>("moyenne"));
        prenomE.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        matrciuleE.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        classeE.setCellValueFactory(new PropertyValueFactory<>("classe"));


        table_students.setItems(etudiantImpl.list());
    }

    @FXML
    void deleteClasse(ActionEvent event) {
        Classe selectedClasse = tableClasse.getSelectionModel().getSelectedItem();
        if (selectedClasse != null) {
            if (etudiantImpl.etudiantsByClasse(selectedClasse).isEmpty()) {
                classeImpl.delete(selectedClasse);
                showMessage("Classe supprimée avec succès", false);
                LoadClasssesToTable();
                nomClasseField.clear();
                loadChoiceBox();
            } else {
                showMessage("La classe ne peut pas etre supprimée car elle contient des étudiants", true);
            }
        } else {
            showMessage("Sélectionnez d'abord la classe à supprimer", true);
        }

    }

    @FXML
    void editClasse(ActionEvent event) {
        Classe selectedClasse = tableClasse.getSelectionModel().getSelectedItem();
        if (selectedClasse != null) {
            String nom = nomClasseField.getText().toUpperCase();
            if (!classeImpl.isClassExist(nom.toUpperCase())) {
                if (!nom.isEmpty()) {
                    selectedClasse.setNom(nom);

                    classeImpl.update(selectedClasse);
                    showMessage("Classe modifiée avec succès", false);
                    LoadClasssesToTable();
                    nomClasseField.clear();
                } else {
                    showMessage("Veuillez saisir un nom!", true);
                }
            } else {
                showMessage("La classe existe déja!", true);

            }

        } else {
            showMessage("Sélectionnez d'abord la classe à modifier", true);
        }

    }

    void showMessage(String message, boolean errror) {
        Alert alert = new Alert(errror ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(errror ? "Erreur" : "Information");

        alert.setContentText(message);
        alert.show();

    }

    public void loadSelectedStudent(MouseEvent mouseEvent) {
        Etudiant selected = table_students.getSelectionModel().getSelectedItem();
        if (selected != null) {
            prenomField.setText(selected.getPrenom());
            nomField.setText(selected.getNom());
            moyenneField.setText(String.valueOf(selected.getMoyenne()));
            choiceBoxClasse.setValue(selected.getClasse());
        }
    }

    public void deleteEtudiant(ActionEvent actionEvent) {
        Etudiant selected = table_students.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Classe classe = selected.getClasse();
            classe.setEffectif(classe.getEffectif() - 1);
            classeImpl.update(classe);
            etudiantImpl.delete(selected);
            clearEtudiantForm();
            LoadEtudiantsToTable();
            LoadClasssesToTable();
            showMessage("Etudiant supprimé avec succès", false);


        } else {
            showMessage("Sélectionnez d'abord l'étudiant à supprimer", true);
        }
    }

    public void editEtudiant(ActionEvent actionEvent) {
        Etudiant selected = table_students.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (areFieldsFilled()) {
                System.out.println(moyenneField.getText());
                if (isCorrectMoyenne()) {
                    Classe oldClasse = selected.getClasse();
                    oldClasse.setEffectif(oldClasse.getEffectif() - 1);
                    classeImpl.update(oldClasse);
                    selected.setClasse(choiceBoxClasse.getValue());
                    selected.setPrenom(prenomField.getText());
                    selected.setNom(nomField.getText());
                    selected.setMoyenne(Double.parseDouble(moyenneField.getText()));
                    String uuid = UUID.randomUUID().toString();
                    String matricule = nomField.getText() + nomField.getText() + uuid;
                    selected.setMatricule(matricule);
                    etudiantImpl.update(selected);
                    Classe classe = choiceBoxClasse.getValue();

                    classe.setEffectif(classe.getEffectif() + 1);
                    classeImpl.update(classe);

                    clearEtudiantForm();
                    LoadEtudiantsToTable();
                    LoadClasssesToTable();

                    showMessage("Etudiant modifié avec succès", false);
                } else {
                    showMessage("Moyenne incorrect!", true);
                }

            } else {
                showMessage("Veuillez remplir tous les champs!", true);
            }
        } else {
            showMessage("Sélectionnez d'abord l'étudiant à modifier", true);

        }
    }

    boolean isCorrectMoyenne() {
        String moyenne = moyenneField.getText();
        boolean isCorrectMoyenne = moyenne.matches("\\d+") &&
                Double.parseDouble(moyenne) >= 0 || Double.parseDouble(moyenne) <= 20;
        return isCorrectMoyenne;
    }

    public void addEtudiant(ActionEvent actionEvent) {
        if (areFieldsFilled()) {
            if (isCorrectMoyenne()) {
                Classe classe = choiceBoxClasse.getSelectionModel().getSelectedItem();
                classe.setEffectif(classe.getEffectif() + 1);
                classeImpl.update(classe);
                String uuid = UUID.randomUUID().toString();
                String matricule = nomField.getText() + nomField.getText() + uuid;
                Etudiant etudiant = new Etudiant();
                etudiant.setPrenom(prenomField.getText());
                etudiant.setNom(nomField.getText());
                etudiant.setMatricule(matricule);
                etudiant.setMoyenne(Double.parseDouble(moyenneField.getText()));
                etudiant.setClasse(classe);

                etudiantImpl.add(etudiant);
                LoadClasssesToTable();

                LoadEtudiantsToTable();
                clearEtudiantForm();
                showMessage("Etudiant ajouté", false);
            } else {
                showMessage("Moyenne incorrect!", true);

            }

        } else {
            showMessage("Veuillez remplir tous les champs", true);
        }

    }

    public void loadChoiceBox() {
        ObservableList<Classe> classes = classeImpl.list();

        choiceBoxClasse.setItems(classes);
    }

    boolean areFieldsFilled() {
        return !prenomField.getText().isEmpty()
                && !nomField.getText().isEmpty()
                && !moyenneField.getText().isEmpty()
                && choiceBoxClasse.getSelectionModel().getSelectedItem() != null;
    }

    void clearEtudiantForm() {
        prenomField.clear();
        nomField.clear();
        moyenneField.clear();
        choiceBoxClasse.getSelectionModel().clearSelection();
    }


}