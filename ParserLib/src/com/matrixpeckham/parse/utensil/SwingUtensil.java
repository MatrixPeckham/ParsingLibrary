package com.matrixpeckham.parse.utensil;

import java.awt.BorderLayout;
import static java.awt.Color.black;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.Frame;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import java.util.logging.Logger;
import static javax.swing.BorderFactory.createBevelBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.border.BevelBorder.RAISED;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.LEFT;
import static javax.swing.border.TitledBorder.TOP;

public class SwingUtensil {
    /*
     * Set the location of a frame to be the center of the
     * screen.
     */

    /**
     *
     * @param f
     */
    public static void center(Frame f) {
        Dimension sDim = getDefaultToolkit().getScreenSize();

        Dimension fDim = f.getSize();
        if (fDim.height > sDim.height) {
            fDim.height = sDim.height;
        }
        if (fDim.width > sDim.width) {
            fDim.width = sDim.width;
        }

        f.setLocation(
                (sDim.width - fDim.width) / 2,
                (sDim.height - fDim.height) / 2);
    }

    /**
     * A standard font for interactive development environment text areas.
     *
     * @return
     */
    public static Font ideFont() {
        int style = PLAIN;
        int size = 18;
        String name = "Verdana";
        Font f = new Font(name, style, size);
        if (!f.getFamily().equals(name)) {
            name = "Monospaced";
            f = new Font(name, style, size);
        }
        return f;
    }

    /**
     * Return a standard text area, with the standard font and border, and with
     * word wrapping enabled.
     *
     * @return
     */
    public static JTextArea ideTextArea() {
        JTextArea ta = new JTextArea();
        ta.setFont(ideFont());
        ta.setBorder(new EmptyBorder(5, 5, 5, 5));
        ta.setLineWrap(true);
        return ta;
    }

    /**
     * Return a standard titled border.
     *
     * @param title
     * @return
     */
    public static TitledBorder ideTitledBorder(String title) {
        TitledBorder tb = createTitledBorder(createBevelBorder(RAISED),
                title, LEFT, TOP);
        tb.setTitleColor(black);
        tb.setTitleFont(ideFont());
        return tb;
    }

    /**
     * Create a frame with the given title around the given component; center
     * and display the frame; listen for exit, closing the frame's window on
     * exit.
     *
     * @param c
     * @param title
     * @return
     */
    public static JFrame launch(Component c, String title) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(c);
        listen(frame);
        frame.pack();
        center(frame);
        frame.setVisible(true);
        return frame;
    }
    /*
     * Set up an exit listener for a frame.
     */

    /**
     *
     * @param f
     */
    public static void listen(Frame f) {
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit(0);
            }
        ;
    }

    );
}
/**
 * Returns a standard text panel, with a scroll pane around
 * a text area, and with a title border.
 *
 * @param title the panel title
 *
 * @param ta the text area to wrap
 *
 * @param pref the preferred size for this panel
     * @param min the minimum size for this panel
 *
 * @return a standard text panel, with a scroll pane
 *         around a text area, and with a title border.
 */
public static JPanel textPanel(
            String title, JTextArea ta,
            Dimension pref, Dimension min) {

        // scroll pane around text area
        JScrollPane s1 = new JScrollPane(ta);
        s1.setPreferredSize(pref);
        s1.setMinimumSize(min);

        // titled panel that contains scrolling text area
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(ideTitledBorder(title));
        p.add(s1, "Center");
        return p;
    }

    private static final Logger LOG
            = Logger.getLogger(SwingUtensil.class.getName());

}
