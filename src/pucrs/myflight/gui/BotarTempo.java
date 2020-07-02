package pucrs.myflight.gui;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class BotarTempo {

    static double resposta;

    public static double pegaHorario() {
        Stage window = new Stage();
        window.setTitle("Mapas com JavaFX");

        TextField timeInput = new TextField();
        timeInput.setFocusTraversable(false);
        timeInput.setPromptText("Digite um horario");

        Button btnSelecionar = new Button("Selecionar");
        btnSelecionar.setOnAction(e -> {
            if (timeInput != null) {
                if (ehDouble(timeInput.getText()) == true) {
                    resposta = Double.parseDouble(timeInput.getText());
                    window.close();
                } else {
                    timeInput.setPromptText("Digite um numero!");
                    timeInput.clear();
                    timeInput.setFocusTraversable(false);
                }
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(timeInput, btnSelecionar);
        Scene scene = new Scene(layout, 200, 90);
        window.setScene(scene);
        window.showAndWait();

        return resposta;

    }
    
    private static boolean ehDouble(String valorX) {
        try {
            double valor = Double.parseDouble(valorX);
            System.out.println("Horario selecionado " + valor);
            return true;
        } catch (Exception e) {
            System.out.println("erro " + valorX + " nao é um numero");
            return false;
        }
    }
}




