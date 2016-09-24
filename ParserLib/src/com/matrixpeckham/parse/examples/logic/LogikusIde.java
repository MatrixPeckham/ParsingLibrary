package com.matrixpeckham.parse.examples.logic;

//import com.sun.java.swing.*;
//import com.sun.java.swing.border.*;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideFont;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideTextArea;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideTitledBorder;
import static com.matrixpeckham.parse.utensil.SwingUtensil.launch;
import static com.matrixpeckham.parse.utensil.SwingUtensil.textPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Logger;
import javax.swing.Box;
import static javax.swing.Box.createHorizontalBox;
import static javax.swing.Box.createHorizontalGlue;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.JTextArea;


public class LogikusIde {

    /**
     *
     */
    protected LogikusMediator mediator;

    /**
     *
     */
    protected JTextArea programArea;

    /**
     *
     */
    protected JTextArea resultsArea;

    /**
     *
     */
    protected JTextArea queryArea;

    /**
     *
     */
    protected JButton proveNextButton;

    /**
     *
     */
    protected JButton proveRestButton;

    /**
     *
     */
    protected JButton haltButton;

    /**
     *
     */
    protected JButton clearProgramButton;

    /**
     *
     */
    protected JButton clearResultsButton;
    /*
     * Creates and returns the box that contains all of the IDE's
     * buttons.
     */

    /**
     *
     * @return
     */
    protected Box buttonBox() {
        Box b = createHorizontalBox();
        b.add(proveButtonPanel());
        b.add(createHorizontalGlue());
        b.add(clearButtonPanel());
        return b;
    }
    /*
     * Creates and returns the panel that contains the IDE's
     * clear buttons.
     */

    /**
     *
     * @return
     */
    protected JPanel clearButtonPanel() {
        JPanel p = new JPanel();
        p.setBorder(ideTitledBorder("Clear"));
        p.add(clearProgramButton());
        p.add(clearResultsButton());
        return p;
    }
    /*
     * The button that clears the results area.
     */

    /**
     *
     * @return
     */
    protected JButton clearProgramButton() {
        if (clearProgramButton == null) {
            clearProgramButton = new JButton("Program");
            clearProgramButton.addActionListener(mediator());
            clearProgramButton.setFont(ideFont());
        }
        return clearProgramButton;
    }
    /*
     * The button that clears the results area.
     */

    /**
     *
     * @return
     */
    protected JButton clearResultsButton() {
        if (clearResultsButton == null) {
            clearResultsButton = new JButton("Results");
            clearResultsButton.addActionListener(mediator());
            clearResultsButton.setFont(ideFont());
        }
        return clearResultsButton;
    }
    /*
     * The button that halts the proof thread.
     */

    /**
     *
     * @return
     */
    protected JButton haltButton() {
        if (haltButton == null) {
            haltButton = new JButton("Halt");
            haltButton.setEnabled(false);
            haltButton.addActionListener(mediator());
            haltButton.setFont(ideFont());
        }
        return haltButton;
    }

    /**
     * Launch a Logikus interactive development environment.
     * @param args
     */
    public static void main(String[] args) {
        launch(
                new LogikusIde().mainPanel(), "Logikus");
    }
    /*
     * Builds and returns the panel that contains all the
     * components of the IDE.
     */

    /**
     *
     * @return
     */
    protected JPanel mainPanel() {
        JPanel p = textPanel(
                "Program",
                programArea(),
                new Dimension(600, 300),
                new Dimension(600, 150));

        JPanel q = textPanel(
                "Query",
                queryArea(),
                new Dimension(600, 60),
                new Dimension(600, 60));

        JPanel r = textPanel(
                "Results",
                resultsArea(),
                new Dimension(600, 150),
                new Dimension(600, 75));

        JSplitPane inner = new JSplitPane(
                VERTICAL_SPLIT, false, q, r);
        JSplitPane outer = new JSplitPane(
                VERTICAL_SPLIT, false, p, inner);

        inner.setDividerSize(3);
        outer.setDividerSize(3);

        JPanel whole = new JPanel();
        whole.setLayout(new BorderLayout());
        whole.add(outer, "Center");
        whole.add(buttonBox(), "South");
        return whole;
    }
    /*
     * The object that controls or "mediates" the interaction
     * of this development environment's Swing components.
     */

    /**
     *
     * @return
     */
    protected LogikusMediator mediator() {
        if (mediator == null) {
            mediator = new LogikusMediator();
            mediator.initialize(
                    proveNextButton(),
                    proveRestButton(),
                    haltButton(),
                    clearProgramButton(),
                    clearResultsButton(),
                    programArea(),
                    resultsArea(),
                    queryArea());
        }
        return mediator;
    }
    /*
     * The program text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea programArea() {
        if (programArea == null) {
            programArea = ideTextArea();
        }
        return programArea;
    }
    /*
     * Creates and returns the panel that contains the IDE's
     * proof buttons.
     */

    /**
     *
     * @return
     */
    protected JPanel proveButtonPanel() {
        JPanel p = new JPanel();
        p.setBorder(ideTitledBorder("Prove"));
        p.add(proveNextButton());
        p.add(proveRestButton());
        p.add(haltButton());
        return p;
    }
    /*
     * The button that starts the proof thread.
     */

    /**
     *
     * @return
     */
    protected JButton proveNextButton() {
        if (proveNextButton == null) {
            proveNextButton = new JButton("Next");
            proveNextButton.addActionListener(mediator());
            proveNextButton.setFont(ideFont());
        }
        return proveNextButton;
    }
    /*
     * The button that starts the proof thread, asking it to
     * find all remaining proofs.
     */

    /**
     *
     * @return
     */
    protected JButton proveRestButton() {
        if (proveRestButton == null) {
            proveRestButton = new JButton("Rest");
            proveRestButton.addActionListener(mediator());
            proveRestButton.setFont(ideFont());
        }
        return proveRestButton;
    }
    /*
     * The query text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea queryArea() {
        if (queryArea == null) {
            queryArea = ideTextArea();
        }
        return queryArea;
    }
    /*
     * The results text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea resultsArea() {
        if (resultsArea == null) {
            resultsArea = ideTextArea();
        }
        return resultsArea;
    }

    private static final Logger LOG
            = Logger.getLogger(LogikusIde.class.getName());
}
