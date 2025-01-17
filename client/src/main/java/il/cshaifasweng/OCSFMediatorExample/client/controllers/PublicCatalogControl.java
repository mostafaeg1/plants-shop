package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.MsgClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static il.cshaifasweng.OCSFMediatorExample.client.App.getAllItems;
import static il.cshaifasweng.OCSFMediatorExample.client.App.getAllitemsUnderSale;
import static il.cshaifasweng.OCSFMediatorExample.client.controllers.SignUp.shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PublicCatalogControl {
    private boolean allitemsfilter=true;

    private ArrayList<Item> allitems;
    private ArrayList<Item> saleitems;

    @FXML
    private ChoiceBox<String> filterSelect;

    @FXML
    private TableView table=new TableView<Item>();

    @FXML
    private AnchorPane itemscontainer;

    @FXML
    void Back(ActionEvent event) throws IOException {
        MsgClass msg=new MsgClass("#get customers",null);
        SimpleClient.getClient().sendToServer(msg);
        App.setRoot("controllers/LogIN");
    }

    @FXML
    void filter(ActionEvent event) throws IOException {
        if (filterSelect.getValue().toString().equals("All Items")) {
            allitemsfilter=true;
        } else {
            allitemsfilter=false;
        }
        loadcatalog();
    }



    @FXML
    void createAcount(ActionEvent event) throws IOException {
        shop=true;
        MsgClass msg=new MsgClass("#get customers",null);
        SimpleClient.getClient().sendToServer(msg);
        App.setRoot("controllers/SignUp");
    }

    @FXML
    public void initialize() throws IOException, InterruptedException {
        filterSelect.getItems().addAll("All Items","Under Sale");
        filterSelect.getSelectionModel().select(0);
       loadcatalog();
    }

    public void loadcatalog() throws IOException {
        saleitems=getAllitemsUnderSale();
        allitems=getAllItems();
        itemscontainer.getChildren().removeAll(itemscontainer.getChildren());
        ArrayList<Item> items=null;
        if(allitemsfilter)
        {
            items = allitems;
        }
        else
        {
            items = saleitems;
        }
        boolean moveRight = false;
        int j = 0;
        if (items != null) {
            if (items.size() != 0) {
                itemscontainer.setMinHeight(items.size() * 120);      ///the height of the container is related to the amount of the items
                for (int i = 0; i < items.size(); i++) {
                    AnchorPane p = new AnchorPane();            //container of each item
                    p.setStyle("-fx-background-color: #243447");
                    p.setMinSize(350, 175);
                    moveRight = i % 2 == 1;
                    p.setLayoutX(5);
                    p.setLayoutY(5);
                    ////////////// img /////////////
                    ImageView imageview = new ImageView();
                    imageview.setFitWidth(165);   //width of img
                    imageview.setFitHeight(165); //height of img
                    System.out.println(i);
                    try{
                        File imageFile = new File(items.get(i).getImgURL());
                        String fileLocation = imageFile.toURI().toString();
                        Image fxImage = new Image(fileLocation);
                        imageview.setImage(fxImage);
                    } catch (Exception e) {
//                        File imageFile = new File("Images/no_image.jpg");
//                        String fileLocation = imageFile.toURI().toString();
                        Image fxImage = new Image("https://www.dreamstime.com/no-image-available-icon-flat-vector-no-image-available-icon-flat-vector-illustration-image132482953");
                        imageview.setImage(fxImage);
                    }
                    imageview.setLayoutX(5);           //x & y coordinate related in the pane
                    imageview.setLayoutY(5);

                    File imageFile = new File("Images/sale_image.jpg");
                    String fileLocation = imageFile.toURI().toString();
                    Image fxImage = new Image(fileLocation);
                    ImageView saleImg = new ImageView();
                    saleImg.setImage(fxImage);

                    if(items.get(i).isUnderSale()){
                        saleImg.setFitWidth(30);   //width of img
                        saleImg.setFitHeight(30); //height of img
                        saleImg.setLayoutX(315);           //x & y coordinate related in the pane
                        saleImg.setLayoutY(5);
                    }

                    //////////////// details of the item //////////////
                    ///////// price textfield ///////////
                    TextField name = new TextField("Name: " + items.get(i).getName());
                    name.setStyle("-fx-background-color:#F2F4F7");
                    name.setLayoutX(175);
                    name.setLayoutY(40);
                    name.setMinWidth(170);
                    name.setEditable(false);

                    ///////// type catalog number ///////////
                    TextField catologNum = new TextField("Catalog Number: " + items.get(i).getCatalogNumber());
                    catologNum.setStyle("-fx-background-color:#F2F4F7");
                    catologNum.setLayoutX(175);
                    catologNum.setMinWidth(170);
                    catologNum.setLayoutY(60);
                    catologNum.setEditable(false);

                    ///////// type textfield ///////////
                    TextField type = new TextField("Type: " + items.get(i).getType());
                    type.setStyle("-fx-background-color:#F2F4F7");
                    type.setLayoutX(175);           //x & y coordinate related in the pane
                    type.setLayoutY(80);
                    type.setMinWidth(170);
                    type.setEditable(false);

                    TextField color = new TextField("Color: " + items.get(i).getColor());
                    color.setStyle("-fx-background-color:#F2F4F7");
                    color.setLayoutX(175);           //x & y coordinate related in the pane
                    color.setLayoutY(100);
                    color.setMinWidth(170);
                    color.setEditable(false);


                    ///////// type textfield ///////////
                    TextField price = new TextField();
                    TextField priceAfterSale = new TextField();
                    if(items.get(i).isUnderSale()) {
                        price.setText("Original Price: " + items.get(i).getOriginal_price()+"₪");
                        priceAfterSale.setText("Price After "+items.get(i).getSalePercent()*100 +"% sale is: "+items.get(i).getPriceAfterSale()+"₪");
                    }
                    else{
                        price.setText("Price: "+items.get(i).getOriginal_price()+"₪");
                    }
                    price.setStyle("-fx-background-color:#F2F4F7");
                    price.setLayoutX(175);           //x & y coordinate related in the pane
                    price.setLayoutY(120);
                    price.setMinWidth(170);
                    priceAfterSale.setStyle("-fx-background-color:#F2F4F7");
                    priceAfterSale.setLayoutX(175);
                    priceAfterSale.setLayoutY(140);
                    priceAfterSale.setMinWidth(170);
                    price.setEditable(false);
                    priceAfterSale.setEditable(false);


                    /////////////// adding components to the pane /////////////
                    p.getChildren().add(imageview);
                    p.getChildren().add(price);
                    p.getChildren().add(type);
                    p.getChildren().add(name);
                    p.getChildren().add(color);
                    p.getChildren().add(catologNum);
                    if(items.get(i).isUnderSale()){
                        p.getChildren().add(saleImg);
                        p.getChildren().add(priceAfterSale);
                    }
                    if (moveRight) {
                        p.setLayoutY(220 * j);
                        p.setLayoutX(400);
                        j++;
                    } else {
                        p.setLayoutY(220 * j);
                    }
                    itemscontainer.getChildren().add(p);
                }
            }
        }
    }



}
