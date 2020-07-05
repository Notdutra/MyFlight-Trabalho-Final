package pucrs.myflight.gui;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ComfirmBox {

    static boolean resposta;

    public static boolean display(String titulo, String mensagem) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titulo);
        window.setMinWidth(240);
        window.setMinHeight(80);
        
        Label label = new Label();
        label.setText(mensagem);

        Button botaoSim  = new Button("Sim");
        Button botaoNao = new Button("Nao");

        botaoSim.setOnAction(e -> {
            resposta = true;
            window.close();
        });

        botaoNao.setOnAction(e -> {
            resposta = false;
            window.close();
        });
        
        HBox layout = new HBox(15);
        layout.getChildren().addAll(label, botaoSim , botaoNao);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return resposta;
    }
}


