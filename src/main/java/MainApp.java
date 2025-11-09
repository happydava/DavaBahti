import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainMenuScene;
    private Scene wordLessonScene;
    private Scene grammarTestScene;

    private String currentUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Language Learning App");

        createLoginScene();
        createMainMenuScene();
        createWordLessonScene();
        createGrammarTestScene();

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void createLoginScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Language Learning App");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setPrefWidth(200);
        signUpButton.setOnAction(e -> showSignUpDialog());

        Button loginButton = new Button("Log In");
        loginButton.setPrefWidth(200);
        loginButton.setOnAction(e -> showLoginDialog());

        root.getChildren().addAll(titleLabel, signUpButton, loginButton);

        loginScene = new Scene(root, 300, 250);
    }

    private void showSignUpDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Sign Up");

        ButtonType signUpButtonType = new ButtonType("Sign Up", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(signUpButtonType, ButtonType.CANCEL);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        dialog.getDialogPane().setContent(new VBox(10, usernameField, passwordField));

        dialog.setResultConverter(btn -> {
            if (btn == signUpButtonType) {
                return usernameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(username -> {
            // Здесь можно добавить логику сохранения пользователя
            currentUser = username;
            primaryStage.setScene(mainMenuScene);
        });
    }

    private void showLoginDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Log In");

        ButtonType loginButtonType = new ButtonType("Log In", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password-");

        dialog.getDialogPane().setContent(new VBox(10, usernameField, passwordField));

        dialog.setResultConverter(btn -> {
            if (btn == loginButtonType) {
                return usernameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(username -> {
            // Здесь можно добавить логику проверки пользователя
            currentUser = username;
            primaryStage.setScene(mainMenuScene);
        });
    }


    private void createMainMenuScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + currentUser);

        Button studyWordsButton = new Button("Study New Words");
        studyWordsButton.setPrefWidth(200);
        studyWordsButton.setOnAction(e -> primaryStage.setScene(wordLessonScene));

        Button grammarTestsButton = new Button("Grammar Tests");
        grammarTestsButton.setPrefWidth(200);
        grammarTestsButton.setOnAction(e -> primaryStage.setScene(grammarTestScene));

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(e -> primaryStage.close());

        Button backButton = new Button("Back to Login");
        backButton.setPrefWidth(200);
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));

        root.getChildren().addAll(welcomeLabel, studyWordsButton, grammarTestsButton, exitButton, backButton);

        mainMenuScene = new Scene(root, 300, 300);
    }

    private void createWordLessonScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label questionLabel = new Label("¿Hola?");
        Label answerLabel = new Label("Hello");

        ToggleGroup answerGroup = new ToggleGroup();

        RadioButton option1 = new RadioButton("Hello");
        RadioButton option2 = new RadioButton("Goodbye");
        RadioButton option3 = new RadioButton("Thank you");
        RadioButton option4 = new RadioButton("Yes");

        option1.setToggleGroup(answerGroup);
        option2.setToggleGroup(answerGroup);
        option3.setToggleGroup(answerGroup);
        option4.setToggleGroup(answerGroup);

        Button submitButton = new Button("Submit");
        Label resultLabel = new Label();

        submitButton.setOnAction(e -> {
            if (option1.isSelected()) {
                resultLabel.setText("Excellent!");
            } else {
                resultLabel.setText("Try again!");
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(questionLabel, option1, option2, option3, option4, submitButton, resultLabel, backButton);

        wordLessonScene = new Scene(root, 400, 300);
    }

    private void createGrammarTestScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label questionLabel = new Label("The cat ___ on the mat.");
        Label answerLabel = new Label("is");

        ToggleGroup answerGroup = new ToggleGroup();

        RadioButton option1 = new RadioButton("is");
        RadioButton option2 = new RadioButton("are");
        RadioButton option3 = new RadioButton("am");
        RadioButton option4 = new RadioButton("be");

        option1.setToggleGroup(answerGroup);
        option2.setToggleGroup(answerGroup);
        option3.setToggleGroup(answerGroup);
        option4.setToggleGroup(answerGroup);

        Button submitButton = new Button("Submit");
        Label resultLabel = new Label();

        submitButton.setOnAction(e -> {
            if (option1.isSelected()) {
                resultLabel.setText("Excellent!");
            } else {
                resultLabel.setText("Try again!");
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(questionLabel, option1, option2, option3, option4, submitButton, resultLabel, backButton);

        grammarTestScene = new Scene(root, 400, 300);
    }
}