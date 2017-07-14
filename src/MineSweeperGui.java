import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class MineSweeperGui extends JFrame {

    private final int FRAME_WIDTH = 350;
    private final int FRAME_HEIGHT = 400;

    private final JLabel statusbar;

    public MineSweeperGui() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        Board board = new Board(statusbar);

        add(board);

        setResizable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ex = new MineSweeperGui();
            ex.setVisible(true);
        });
    }
}