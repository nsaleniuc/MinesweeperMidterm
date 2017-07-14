import javafx.scene.control.ToggleGroup;

import javax.swing.*;

/**
 * Created by user on 7/14/2017.
 */
public class MineFieldGui {
    private JPanel base;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton button1;
    private JLabel heightLabel;
    private JButton startGameButton;
    private JTextArea bombText;
    private JTextArea heightTextArea;
    private JTextArea widthEditText;
    private JRadioButton customChoice;
    private JRadioButton expertChoice;
    private JRadioButton mediumChoice;
    private JRadioButton easyChoice;
    private static JFrame frame = new JFrame("MineSweeper");

    public static void main(String[] args) {
        frame.setContentPane(new MineFieldGui().base);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents(){
        startGameButton = new JButton();
        bombText = new JTextArea();
        heightTextArea = new JTextArea();
        widthEditText = new JTextArea();
        customChoice = new JRadioButton();
        easyChoice = new JRadioButton();
        mediumChoice = new JRadioButton();
        expertChoice = new JRadioButton();
        easyChoice.setSelected(true);
        groupButtons();
    }

    private void groupButtons(){
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(easyChoice);
        buttonGroup.add(mediumChoice);
        buttonGroup.add(expertChoice);
        buttonGroup.add(customChoice);

    }

}
