package team5.cmsc495.recipemanager;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


import javax.swing.JLabel;
import java.awt.GridLayout;

public class Display{

    private JFrame frame;
    private JTextField txtIngredient;
    private Recipe recipe = new Recipe();
    private RecipeBook book = new RecipeBook();
    /**
     * Launch the application.  test
     */
    public static void main(String[] args){
        //TODO:Hardcoding ingredients, These will be replaced by the input from the screen



        Display display = new Display();
        display.frame.setVisible(true);

    }

    /**
     * Create the application.
     */
    public Display() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0, 0));
        frame.setTitle("Welcome to your pantry!");

        final JPanel panelInitialScreen = new JPanel();

        panelInitialScreen.setLayout(null);
        panelInitialScreen.setVisible(true);

        final JPanel panelRecipeChoicesScreen = new JPanel();

        panelRecipeChoicesScreen.setLayout(null);
        panelRecipeChoicesScreen.setVisible(false);

        final JPanel panelRecipeInstructionsScreen = new JPanel();
        frame.getContentPane().add(panelRecipeInstructionsScreen, "name_293133281550638");		panelRecipeInstructionsScreen.setLayout(null);
        panelRecipeInstructionsScreen.setVisible(false);


        final JPanel panelGroceryListScreen = new JPanel();

        panelGroceryListScreen.setLayout(null);



        JButton btnGetRecipes = new JButton("Get Recipes");
        btnGetRecipes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(myPantry.getIngredients().isEmpty()){


                }else{
                    panelRecipeChoicesScreen.setVisible(true);
                    panelRecipeChoicesScreen.setLayout(new GridLayout(0,1));
                    //Get the recipes that contain myPantry's ingredients
                    Set<String> recipsToDisplay=new HashSet<String>();
                    for(String ingredient:myPantry.getIngredients()){

                        for(String recipe:myCookbook.getRecipsFromIng(ingredient)){
                            recipsToDisplay.add(recipe);
                        }
                    }
                    //Add the buttons to display
                    JButton[] buttons=new JButton[recipsToDisplay.size()];
                    int index=0;
                    for(final String recipe:recipsToDisplay){
                        //Make an array of buttons so you can add action listeners to each one to
                        //display the recipe in the recipe screen JPanel, or the grocery list if
                        //the pantry is missing some ingredients
                        buttons[index]=new JButton(recipe);
                        buttons[index].addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                // check if you have everything the recipe asks for and show
                                //the recipe if so, otherwise show the grocery list
                                String[] ingsInRecipe=myCookbook.getIngsInRecipe(recipe);
                                boolean gotAllIngs=true;
                                ArrayList<String> groceryList= new ArrayList<String>();
                                for(String ing:ingsInRecipe){
                                    if(!myPantry.getIngredients().contains(ing)){
                                        gotAllIngs=false;
                                        groceryList.add(ing);
                                    }
                                }
                                for(String instruction:myCookbook.getInstr(recipe)){
                                    panelRecipeInstructionsScreen.add(new JLabel(instruction));
                                }
                                panelRecipeInstructionsScreen.setLayout(new GridLayout(0,1));
                                JButton doneCookingButton=new JButton("Done Cooking");
                                panelRecipeInstructionsScreen.add(doneCookingButton);
                                doneCookingButton.addActionListener(new ActionListener(){
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        frame.dispose();
                                    }
                                });
                                if(gotAllIngs){
                                    //Display recipe
                                    panelRecipeInstructionsScreen.setVisible(true);
                                    frame.setTitle("Cook Away!");
                                    panelRecipeChoicesScreen.setVisible(false);

                                }
                                else{
                                    //Display grocery list
                                    panelGroceryListScreen.setLayout(new GridLayout(0,1));
                                    JLabel lblWeDisplayThe = new JLabel("You're missing these ingredients!");
                                    panelGroceryListScreen.add(lblWeDisplayThe);
                                    for(String ing:groceryList){
                                        javax.swing.JCheckBox box=new javax.swing.JCheckBox();
                                        box.setText(ing);
                                        panelGroceryListScreen.add(box);
                                    }
                                    JButton nextButton=new JButton("next");
                                    panelGroceryListScreen.add(nextButton);
                                    nextButton.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            //Display the recipe!
                                            //Add the right recipe to the screen first
                                            panelRecipeInstructionsScreen.setVisible(true);
                                            panelGroceryListScreen.setVisible(false);
                                            frame.setTitle("Cook Away!");
                                        }
                                    });
                                    panelGroceryListScreen.setVisible(true);
                                    panelRecipeChoicesScreen.setVisible(false);
                                    frame.setTitle("Grocery List");
                                }
                            }
                        });
                        panelRecipeChoicesScreen.add(buttons[index]);
                        index++;
                    }
                    panelInitialScreen.setVisible(false);
                    frame.setTitle("Recipe Suggestions");
                }


            }
        });

        btnGetRecipes.setBounds(138, 182, 117, 29);
        panelInitialScreen.add(btnGetRecipes);


        txtIngredient = new JTextField();
        txtIngredient.setBounds(112, 4, 130, 28);
        txtIngredient.setText("Ingredient");
        panelInitialScreen.add(txtIngredient);
        txtIngredient.setColumns(10);

        JButton btnAddIngButton = new JButton("Enter");
        btnAddIngButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO
                System.out.println(txtIngredient.getText());
                myPantry.addIngredient(txtIngredient.getText());



            }
        });
        btnAddIngButton.setBounds(254, 5, 76, 29);
        panelInitialScreen.add(btnAddIngButton);


    }
}