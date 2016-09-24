package com.matrixpeckham.parse.examples.query;

//import com.sun.java.swing.*;
//import com.sun.java.swing.border.*;
//import com.sun.java.swing.text.*;
import static com.matrixpeckham.parse.examples.query.ChipSource.queryChip;
import static com.matrixpeckham.parse.examples.query.ChipSource.queryCustomer;
import static com.matrixpeckham.parse.examples.query.ChipSource.queryOrder;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideFont;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideTextArea;
import static com.matrixpeckham.parse.utensil.SwingUtensil.launch;
import static com.matrixpeckham.parse.utensil.SwingUtensil.textPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.KeyEvent.VK_G;
import java.util.logging.Logger;
import javax.swing.JButton;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import static javax.swing.KeyStroke.getKeyStroke;
import javax.swing.border.EmptyBorder;

public class JaqlUe {

    /**
     *
     */
    protected JaqlMediator mediator;

    /**
     *
     */
    protected JButton goButton;

    /**
     *
     */
    protected JButton clearButton;

    /**
     *
     */
    protected JTextArea metadataArea;

    /**
     *
     */
    protected JTextArea queryArea;

    /**
     *
     */
    protected JTextArea resultArea;

    /**
     *
     */
    protected static final int PREFERREDWIDTH = 600;
    /*
     * The panel for the "Go!" and "Clear" buttons.
     */

    /**
     *
     * @return
     */
    protected JPanel buttonPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(goButton(), "North");
        p.add(clearButton(), "South");
        p.setBorder(new EmptyBorder(10, 6, 5, 6));
        return p;
    }
    /*
     * The "Clear" button.
     */

    /**
     *
     * @return
     */
    protected JButton clearButton() {
        if (clearButton == null) {
            clearButton = new JButton("Clear");
            clearButton.addActionListener(mediator());
            clearButton.setFont(ideFont());
        }
        return clearButton;
    }
    /*
     * The "Go!" button. This method also establishes "Ctrl-G"
     * as a shortcut for pressing the button.
     */

    /**
     *
     * @return
     */
    protected JButton goButton() {
        if (goButton == null) {
            goButton = new JButton("Go!");
            goButton.addActionListener(mediator());
            goButton.setFont(ideFont());

            // ctrl-g keystroke:
            KeyStroke ctrlg = getKeyStroke(VK_G, CTRL_MASK);

            goButton.registerKeyboardAction(mediator(),
                    ctrlg, WHEN_IN_FOCUSED_WINDOW);
        }
        return goButton;
    }
    /*
     * The split pane that contains the query area and the
     * result area.
     */

    /**
     *
     * @return
     */
    protected JSplitPane ioPane() {
        Dimension min = new Dimension(PREFERREDWIDTH, 80);
        Dimension pref = new Dimension(PREFERREDWIDTH, 180);

        JPanel q = textPanel(
                "Query", queryArea(), pref, min);
        JPanel r = textPanel(
                "Results", resultArea(), pref, min);

        JSplitPane jsp = new JSplitPane(
                VERTICAL_SPLIT, false, q, r);
        jsp.setDividerSize(3);
        return jsp;
    }

    /**
     * Launch the interactive development environment.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(
                new JaqlUe().mainPanel(), " Jaql and Chips");
    }
    /*
     * The main panel, which contains all components.
     */

    /**
     *
     * @return
     */
    protected JPanel mainPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(upperPanel(), "Center");
        p.add(metadataPanel(), "South");
        return p;
    }
    /*
     * The object that controls the component interactions.
     */

    /**
     *
     * @return
     */
    protected JaqlMediator mediator() {
        if (mediator == null) {
            mediator = new JaqlMediator();
            mediator.initialize(
                    goButton(),
                    clearButton(),
                    queryArea(),
                    resultArea(),
                    metadataArea());
        }
        return mediator;
    }
    /*
     * The metadata text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea metadataArea() {
        if (metadataArea == null) {
            metadataArea = ideTextArea();
            //ChipSource cs = new ChipSource();
            metadataArea.append(queryChip() + "\n");
            metadataArea.append(queryCustomer() + "\n");
            metadataArea.append(queryOrder() + "\n");
        }
        return metadataArea;
    }
    /*
     * The panel that contains the metadata text area.
     */

    /**
     *
     * @return
     */
    protected JPanel metadataPanel() {
        return textPanel(
                "Metadata",
                metadataArea(),
                new Dimension(PREFERREDWIDTH, 120),
                new Dimension(PREFERREDWIDTH, 80));
    }
    /*
     * The input text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea queryArea() {
        if (queryArea == null) {
            queryArea = ideTextArea();
            queryArea.setText(
                    "select ChipName, PricePerBag from Chip \n"
                    + "where Oil != \"Sunflower\"");
        }
        return queryArea;
    }
    /*
     * The output text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea resultArea() {
        if (resultArea == null) {
            resultArea = ideTextArea();
        }
        return resultArea;
    }
    /*
     * The panel that contains the query area, the result area
     * and the buttons.
     */

    /**
     *
     * @return
     */
    protected JPanel upperPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(ioPane(), "Center");
        p.add(buttonPanel(), "East");
        return p;
    }

    private static final Logger LOG = Logger.getLogger(JaqlUe.class.getName());

}
