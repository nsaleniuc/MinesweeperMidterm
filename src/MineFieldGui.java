import javax.swing.*;

/**
 * Created by user on 7/14/2017.
 */
public class MineFieldGui {
    private JPanel base;
    private JButton startGameButton;
    private JTextArea rowsEditText;
    private JRadioButton customChoice;
    private JRadioButton expertChoice;
    private JRadioButton mediumChoice;
    private JRadioButton easyChoice;
    private JTextArea bombsEditText;
    private JTextArea columnsEditText;
    private static JFrame frame = new JFrame("MineSweeper");

    public static void main(String[] args) {
        frame.setContentPane(new MineFieldGui().base);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents(){
        startGameButton = new JButton();
        bombsEditText = new JTextArea();
        rowsEditText = new JTextArea();
        columnsEditText = new JTextArea();
        customChoice = new JRadioButton();
        easyChoice = new JRadioButton();
        mediumChoice = new JRadioButton();
        expertChoice = new JRadioButton();
        easyChoice.setSelected(true);
        base = new JPanel();
        groupButtons();

        startGameButton.addActionListener(e -> {
            startGame();
        });
    }

    private void groupButtons(){
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(easyChoice);
        buttonGroup.add(mediumChoice);
        buttonGroup.add(expertChoice);
        buttonGroup.add(customChoice);
    }

    private void startGame(){
        if(easyChoice.isSelected()){
            MineSweeperGui.startGui(9 ,9,10);
            frame.setVisible(false);
        } else if(mediumChoice.isSelected()){
            MineSweeperGui.startGui(16 ,16,40);
            frame.setVisible(false);
        } else if(expertChoice.isSelected()){
            MineSweeperGui.startGui(30 ,16,40);
            frame.setVisible(false);
        } else if(customChoice.isSelected()){
            MineSweeperGui.startGui(Integer.parseInt(columnsEditText.getText()),Integer.parseInt(rowsEditText.getText()),Integer.parseInt(bombsEditText.getText()));
            frame.setVisible(false);
        }
    }

}
