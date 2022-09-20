package tacocalc;

import javafx.scene.Node;

import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AppController implements Initializable{

    //Connects the main Shoppingslist class to the FXML file
    @FXML
    private GridPane ingredientsList;

    private ShoppingList shoppingList;

    private List<Integer> indexOfCurrent;

    private ArrayList<ArrayList<Node>> elements = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        updateElements();
        ingredientsList.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e){
                System.out.println("Press");
                if(e.getCode().equals(KeyCode.ENTER)){
                    System.out.println("Enter");
                    CheckBox c = new CheckBox();
                    c.setStyle("-fx-label-padding: 0;");
                    TextField t = new TextField();
                    ingredientsList.addRow(indexOfCurrent.get(1), c, t);
                    updateElements();
                }
            }
        });
        ingredientsList.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent m){
                Node n = m.getPickResult().getIntersectedNode().getParent();
                indexOfCurrent.add(GridPane.getColumnIndex(n));
                indexOfCurrent.add(GridPane.getRowIndex(n));
            }
        });
    }

    @FXML
    public void onGridClick(){
        ingredientsList.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent m){
                Node n = (Node)m.getSource();
            }
        });
    }

    private void updateElements(){
        int i = 0;
        ArrayList<Node> temp = new ArrayList<>();
        for(Node n : ingredientsList.getChildren()){
            if(i%2==0 && i != 0){
                temp.add(n);
                elements.add(temp);
                temp = new ArrayList<>();
            }
            temp.add(n);
        }
    }

    private void toShoppingList(){
    }

    @FXML
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            System.out.println(node.toString());
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}