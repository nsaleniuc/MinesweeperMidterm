import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class MineSweeperGui extends JFrame {

    private final int FRAME_WIDTH = 350;
    private final int FRAME_HEIGHT = 400;

    private final JLabel statusbar;

    public MineSweeperGui(int columns, int rows, int bombs) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        Board board = new Board(statusbar, columns, rows, bombs);
        setSize(columns*22, rows*24 + statusbar.getHeight());

        add(board);

        setResizable(true);
    }

    public static void startGui(int columns, int rows, int bombs) {
        SwingUtilities.invokeLater(() -> {
            JFrame ex = new MineSweeperGui(columns, rows, bombs);
            ex.setVisible(true);
        });
    }
}