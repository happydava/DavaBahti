package UI;

import db.UserDatabase;
import facade.GameFacade;
import model.questions.Question;
import model.questions.VocabularyQuestion;
import strategy.StrategySelector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private GameFacade gameFacade;
    private String selectedTestType;
    private boolean darkMode = false;
    private UserDatabase userDatabase = new UserDatabase();
    private StackPane globalRoot;

    @Override
    public void start(Stage primaryStage) {

        gameFacade = new GameFacade();



        showLoginScreen(primaryStage);
        primaryStage.setTitle("Language Learning App");
        primaryStage.show();
    }

    private void showLoginScreen(Stage primaryStage) {

        VBox root = new VBox(20);
        root.setStyle("-fx-alignment: center; -fx-padding: 40;");
        root.getChildren().add(0, createLogo());

        Label title = new Label("Welcome!");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(250);
        usernameField.setStyle("-fx-padding: 10 10 10 10; -fx-font-size: 18;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(250);
        passwordField.setStyle("-fx-padding: 10 10 10 10; -fx-font-size: 18;");

        Label resultLabel = new Label("");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        loginBtn.setOnAction(e -> {
            boolean success = userDatabase.loginUser(
                    usernameField.getText(),
                    passwordField.getText()
            );

            if (success) {
                resultLabel.setText("Login successful!");
                showTestSelection(primaryStage);
            } else {
                resultLabel.setText("Invalid login.");
            }
        });

        registerBtn.setOnAction(e -> {
            boolean success = userDatabase.registerUser(
                    usernameField.getText(),
                    passwordField.getText()
            );

            if (success) {
                resultLabel.setText("Registration successful! You can login now.");
            } else {
                resultLabel.setText("Username already exists!");
            }
        });

        root.getChildren().addAll(
                title,
                usernameField,
                passwordField,
                loginBtn,
                registerBtn,
                resultLabel
        );

        globalRoot = new StackPane(root);
        Scene scene = new Scene(globalRoot, 1200, 900);

        scene.getStylesheets().add(darkMode ? "dark.css" : "style.css");
        primaryStage.setScene(scene);
    }

    private void showTestSelection(Stage primaryStage) {

        VBox root = new VBox(20);
        root.getChildren().add(0, createLogo());

        root.getStyleClass().add("main-menu-root");

        Label title = new Label("Choose a test:");
        title.getStyleClass().add("main-menu-title");

        Button vocabularyBtn = new Button("Vocabulary Test");
        Button grammarBtn = new Button("Grammar Test");
        Button exitBtn = new Button("Exit");

        Button themeToggle = new Button(darkMode ? "☀ Light Mode" : "🌙 Dark Mode");

        vocabularyBtn.setOnAction(e -> {
            selectedTestType = "Vocabulary";
            showStrategySelection(primaryStage);
        });

        grammarBtn.setOnAction(e -> {
            selectedTestType = "Grammar";
            showStrategySelection(primaryStage);
        });

        exitBtn.setOnAction(e -> primaryStage.close());

        themeToggle.setOnAction(e -> toggleTheme(primaryStage, themeToggle));

        root.getChildren().addAll(title, vocabularyBtn, grammarBtn, themeToggle, exitBtn);

        globalRoot = new StackPane(root);
        Scene scene = new Scene(globalRoot, 1200, 900);

        scene.getStylesheets().add(darkMode ? "dark.css" : "style.css");
        primaryStage.setScene(scene);
    }

    private void showStrategySelection(Stage primaryStage) {

        VBox root = new VBox(20);
        root.getChildren().add(0, createLogo());

        root.getStyleClass().add("test-root");

        Label label = new Label("Choose repetition mode:");

        Button easyBtn = new Button("Easy");
        Button normalBtn = new Button("Normal");
        Button hardBtn = new Button("Hard");
        Button backBtn = new Button("Back");


        easyBtn.setOnAction(e -> {
            gameFacade.setRepetitionStrategy(StrategySelector.getStrategy("easy"));
            startTest(primaryStage);
        });

        normalBtn.setOnAction(e -> {
            gameFacade.setRepetitionStrategy(StrategySelector.getStrategy("normal"));
            startTest(primaryStage);
        });

        hardBtn.setOnAction(e -> {
            gameFacade.setRepetitionStrategy(StrategySelector.getStrategy("hard"));
            startTest(primaryStage);
        });

        backBtn.setOnAction(e -> showTestSelection(primaryStage));


        root.getChildren().addAll(label, easyBtn, normalBtn, hardBtn, backBtn);

        globalRoot = new StackPane(root);
        Scene scene = new Scene(globalRoot, 1200, 900);

        scene.getStylesheets().add(darkMode ? "dark.css" : "style.css");
        primaryStage.setScene(scene);
    }

    private void startTest(Stage primaryStage) {

        if (gameFacade.getCurrentQuestion() == null && !gameFacade.isTestComplete()) {
            gameFacade.startGame(selectedTestType);
        }

        if (gameFacade.isTestComplete()) {
            showResults(primaryStage);
            return;
        }

        Question question = gameFacade.getCurrentQuestion();
        if (question == null) {
            showResults(primaryStage);
            return;
        }

        VBox root = new VBox(20);
        root.getStyleClass().add("test-root");


        Label scoreLabel = new Label("Score: " + gameFacade.getScore());
        scoreLabel.getStyleClass().add("score-label");

        Label questionLabel = new Label(selectedTestType.equals("Vocabulary")
                ? "What is this?"
                : question.getQuestionText());

        ImageView imageView = null;

        if (selectedTestType.equals("Vocabulary") && question instanceof VocabularyQuestion) {
            imageView = new ImageView(new Image("file:" + ((VocabularyQuestion) question).getImagePath()));
            imageView.setFitWidth(330);
            imageView.setPreserveRatio(true);
        }

        List<String> options = question.getOptions();

        Button btn1 = new Button(options.get(0));
        Button btn2 = new Button(options.get(1));
        Button btn3 = new Button(options.get(2));
        Button btn4 = new Button(options.get(3));

        btn1.setOnAction(e -> handleAnswer(btn1.getText(), primaryStage));
        btn2.setOnAction(e -> handleAnswer(btn2.getText(), primaryStage));
        btn3.setOnAction(e -> handleAnswer(btn3.getText(), primaryStage));
        btn4.setOnAction(e -> handleAnswer(btn4.getText(), primaryStage));


        root.getChildren().add(scoreLabel);
        root.getChildren().add(questionLabel);

        if (imageView != null)
            root.getChildren().add(imageView);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setStyle("-fx-alignment: center;");

        int w = 350;
        int h = 100;

        btn1.setMinSize(w, h);
        btn1.setPrefSize(w, h);
        btn1.setMaxSize(w, h);

        btn2.setMinSize(w, h);
        btn2.setPrefSize(w, h);
        btn2.setMaxSize(w, h);

        btn3.setMinSize(w, h);
        btn3.setPrefSize(w, h);
        btn3.setMaxSize(w, h);

        btn4.setMinSize(w, h);
        btn4.setPrefSize(w, h);
        btn4.setMaxSize(w, h);


        grid.add(btn1, 0, 0);
        grid.add(btn2, 1, 0);
        grid.add(btn3, 0, 1);
        grid.add(btn4, 1, 1);

        root.getChildren().addAll(grid);

        globalRoot = new StackPane(root);
        Scene scene = new Scene(globalRoot, 1200, 900);

        scene.getStylesheets().add(darkMode ? "dark.css" : "style.css");
        primaryStage.setScene(scene);
    }

    private void showResults(Stage primaryStage) {

        VBox root = new VBox(20);
        root.getStyleClass().add("results-root");
        root.getChildren().add(0, createLogo());


        Label title = new Label("Test Complete!");
        Label details = new Label(gameFacade.getTestResult());
        Label scoreLabel = new Label("Total Score: " + gameFacade.getScore());

        Button backBtn = new Button("Back to main menu");
        backBtn.setOnAction(e -> {
            gameFacade.reset();
            showTestSelection(primaryStage);
        });

        root.getChildren().addAll(title, details, scoreLabel, backBtn);

        globalRoot = new StackPane(root);
        Scene scene = new Scene(globalRoot, 1200, 900);

        scene.getStylesheets().add(darkMode ? "dark.css" : "style.css");
        primaryStage.setScene(scene);
    }

    private void toggleTheme(Stage stage, Button themeButton) {
        darkMode = !darkMode;

        stage.getScene().getStylesheets().clear();

        if (darkMode) {
            stage.getScene().getStylesheets().add("dark.css");
            themeButton.setText("☀ Light Mode");
        } else {
            stage.getScene().getStylesheets().add("style.css");
            themeButton.setText("🌙 Dark Mode");
        }
    }

    private void handleAnswer(String selectedAnswer, Stage primaryStage) {
        gameFacade.checkAnswer(selectedAnswer);
        gameFacade.nextQuestion(selectedTestType);
        startTest(primaryStage);
    }

    private ImageView createLogo() {
        ImageView logo = new ImageView(new Image("file:C:\\Users\\ryshy\\IdeaProjects\\DavaBahti\\src\\main\\images\\logo.png"));

        logo.setFitWidth(180);      // ширина логотипа
        logo.setPreserveRatio(true);
        logo.setSmooth(true);

        logo.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0.3, 0, 5);");

        return logo;
    }

}

