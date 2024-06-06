import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class Main {
    static Main game;

    int gridHeight;
    int gridWidth;
    //holds current buttons that are in the grid
    JButton gridButtons[][];
    JFrame frame = new JFrame();
    JPanel centralPanel;
    //previouslly clicked button
    JButton buttonA;;
    int tagret = randomNumber(70, 40);
    int movesLeft = 10;
    int total = 0;
    JLabel totalLabel;
    JLabel movesLabel;
    JLabel targetLabel;

    //START OF THE PROGRAM!!!
    public static void main(String[] args) {
        game = new Main();
        game.frameSetup();
    }

    //setting up buttons/settings/labels on left side (basically setting whole screen)
    public void frameSetup(){
        //basic configuration of frame
        game.frame.setSize(1000, 1000);
        game.frame.setVisible(true);
        gridButtons = new JButton[gridHeight][gridWidth];
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        int startDimension = game.randomNumber(9,4);
        gridHeight = startDimension;
        gridWidth = startDimension;

        GridLayout gridLayout = new GridLayout(game.gridHeight, game.gridWidth, 5,5);
        game.centralPanel = new JPanel();
        game.centralPanel.setLayout(gridLayout);

        game.frame.add(centralPanel, BorderLayout.CENTER);

        //add labels on the left side
        game.generateLabelsOnLeftSide();

        //add buttons to center grid
        game.generateButtons();
    }



    //adding Total, Target, Moves labels on left side of screen
    public void generateLabelsOnLeftSide(){
        game.totalLabel = new JLabel("Total - "+game.total);
        game.movesLabel = new JLabel("Moves left - "+game.movesLeft);
        game.targetLabel = new JLabel("Target value - "+game.tagret);


        game.frame.add(targetLabel, BorderLayout.NORTH);
        game.frame.add(totalLabel, BorderLayout.SOUTH);
        game.frame.add(movesLabel, BorderLayout.WEST);
    }

    //adding buttons to grid in center and adding onClick function to each button
    public void generateButtons(){
        //clear previuis buttons
        game.centralPanel.removeAll();
        GridLayout gridLayout = new GridLayout(game.gridHeight, game.gridWidth, 5,5);
        game.centralPanel.setLayout(gridLayout);
        game.gridButtons = new JButton[game.gridHeight][game.gridWidth];

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                int randomValue = randomNumber(9, 0);
                //button in a grid
                JButton gridButton = new JButton(String.valueOf(randomValue));

                int finalI = i;  //used for disabling buttons from different rows and columns
                int finalJ = j;


                //onCLick function for button in a grid
                gridButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        //case when user has won the game
                        if(getLabelNumber(game.targetLabel) == getLabelNumber(game.totalLabel)){
                            game.centralPanel.removeAll();
                            JLabel won = new JLabel("You have won");
                            won.setFont(new Font("Calibri", Font.BOLD, 40));
                            game.buttonA = null;
                            game.centralPanel.updateUI();
                            game.centralPanel.add(won);
                        }

                        //case when user has lost the game
                        if(getLabelNumber(game.movesLabel) == 0){
                            game.centralPanel.removeAll();
                            JLabel lost = new JLabel("You have lost");
                            lost.setFont(new Font("Calibri", Font.BOLD, 40));
                            game.buttonA = null;
                            game.centralPanel.updateUI();
                            game.centralPanel.add(lost);
                        }

                        //disable buttons that are not on same column on row
                        game.disableButtons(finalI, finalJ);

                        //change text of previous button
                        int newBtnValue = game.changeButtonText(gridButton);

                        //add value of previously clicked button to total
                        game.totalLabel.setText("Total - "+String.valueOf(getLabelNumber(game.totalLabel)+newBtnValue));

                        //decrement number of moves by 1
                        game.movesLabel.setText("Moves left - "+String.valueOf(getLabelNumber(game.movesLabel)-1));
                    }
                });

                //adding button to global array of button so we can acces them from other places n the code
                game.gridButtons[i][j] = gridButton;

                //adding buttons to code
                centralPanel.add(gridButton);
            }
        }
        game.centralPanel.updateUI();
    }

    //getting number from labels on the left side(Target value - 100/Moves left - 10...)
    public static int getLabelNumber(JLabel label){
        return (parseInt(label.getText().split(" - ")[1]));
    }

    //change text of previous button ((prevBtn + currentBtn) % 10)
    public int changeButtonText(JButton clickedButton){
        int newValue;
        if(game.buttonA == null){
            game.buttonA = clickedButton;
            newValue = Integer.parseInt(clickedButton.getText());
        }else{
            newValue = (parseInt(game.buttonA.getText()) + parseInt(clickedButton.getText())) % 10;
            game.buttonA.setText(String.valueOf(newValue));
            game.buttonA = clickedButton;
        }
        return newValue;
    }

    //disable button that are not in the same column or row
    public void disableButtons(int height, int width){
        for (int i = 0; i < this.gridButtons.length; i++) {
            for (int j = 0; j < this.gridButtons[0].length; j++) {
                if(i == height || j == width){
                    //is in same column or row so enable button
                    game.gridButtons[i][j].setEnabled(true);
                }else{
                    //is in not in the same column or row so dissable button
                    game.gridButtons[i][j].setEnabled(false);
                }
            }
        }
    }

    //generate random number between min/max
    public int randomNumber(int max, int min){
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
