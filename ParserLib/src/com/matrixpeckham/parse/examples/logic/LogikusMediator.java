package com.matrixpeckham.parse.examples.logic;

//import com.sun.java.swing.*;
//import com.sun.java.swing.border.*;
import com.matrixpeckham.parse.engine.Program;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Unification;
import static com.matrixpeckham.parse.examples.logic.LogikusFacade.program;
import static com.matrixpeckham.parse.examples.logic.LogikusFacade.query;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextArea;
import static javax.swing.SwingUtilities.invokeAndWait;

public class LogikusMediator
        implements ActionListener, Runnable {

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
    protected boolean proveRemaining;

    /**
     *
     */
    protected Thread computeThread;

    /**
     *
     */
    protected String lastProgramText = null;

    /**
     *
     */
    protected String lastQueryText = null;

    /**
     *
     */
    protected Program program;

    /**
     *
     */
    protected Query query;

    /**
     * This method reacts, when the user presses one of the IDE's buttons.
     *
     * @param event the event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object object = event.getSource();
        if (object == clearResultsButton) {
            resultsArea.selectAll();
            resultsArea.copy();
            resultsArea.setText("");
        }
        if (object == clearProgramButton) {
            programArea.selectAll();
            programArea.copy();
            programArea.setText("");
            queryArea.setText("");
        }

        if (object == proveNextButton || object == proveRestButton) {

            proveRemaining = (object == proveRestButton);
            conductProof();
        }
        if (object == haltButton) {
            if (computeThread != null) {
                computeThread.interrupt();
            }
            computeThread = null;
            setComputing(false);
        }
    }
    /*
     * Parse the program and query (if they have changed)
     * and proved the query in a separate thread.
     */

    /**
     *
     */
    protected void conductProof() {
        setComputing(true);
        try {
            parseProgramAndQuery();
        } catch (Exception e) {
            String text = e.toString();
            if (e.getMessage() != null) {
                text = e.getMessage();
            }
            resultsArea.append(text + "\n");
            setComputing(false);
            return;
        }
        computeThread = new Thread(this);
        computeThread.start();
        // this thread will setComputing(false) in due time.
    }

    /**
     * Appends the given line to the results text area, scheduling this event
     * with the event-dispatching thread.
     *
     * @param s the string to append to the results area
     */
    protected void display(final String s) {
        // Using invokeAndWait() keeps appends from outrunning
        // the event dispatch thread.

        Runnable r = () -> {
            resultsArea.append(s);
        };
        try {
            invokeAndWait(r);
        } catch (InterruptedException | InvocationTargetException e) {
            resultsArea.append(e.getMessage());
        }
    }

    /**
     * Make the IDE's GUI components available.
     *
     * @param proveNextButton
     * @param proveRestButton
     * @param haltButton
     * @param clearProgramButton
     * @param clearResultsButton
     * @param programArea
     * @param resultsArea
     * @param queryArea
     */
    public void initialize(
            JButton proveNextButton,
            JButton proveRestButton,
            JButton haltButton,
            JButton clearProgramButton,
            JButton clearResultsButton,
            JTextArea programArea,
            JTextArea resultsArea,
            JTextArea queryArea) {

        this.proveNextButton = proveNextButton;
        this.proveRestButton = proveRestButton;
        this.haltButton = haltButton;
        this.clearProgramButton = clearProgramButton;
        this.clearResultsButton = clearResultsButton;
        this.programArea = programArea;
        this.resultsArea = resultsArea;
        this.queryArea = queryArea;
    }
    /*
     * Parses the program and query texts.
     */

    /**
     *
     */
    protected void parseProgramAndQuery() {

        boolean programChanged;
        String programText = programArea.getText();
        programChanged = (lastProgramText == null) || (!lastProgramText.equals(
                programText));
        if (programChanged) {
            program = program(programText);
        }
        lastProgramText = programText;

        String queryText = queryArea.getText();

// create a fresh query if the program changes or the
// query text changes
        if (programChanged || (lastQueryText == null) || (!lastQueryText.equals(
                queryText))) {
            query = query(queryText, program);
        }
        lastQueryText = queryText;
    }
    /*
     * Proves the query against the program.
     */

    /**
     *
     */
    protected void proveNext() {
        if (query.canFindNextProof()) {
            Unification vars = query.variables();
            if (vars.size() == 0) {
                display("yes\n");
            } else {
                display(vars + "\n");
            }
        } else {
            display("no\n");
        }
    }
    /*
     * Proves the query against the program until no proofs
     * remain.
     */

    /**
     *
     */
    protected void proveRemaining() {
        Unification vars = query.variables();
        while (query.canFindNextProof()) {
            if (vars.size() == 0) {
                display("yes\n");
                return;
            } else {
                display(vars + "\n");
            }
        }
        display("no\n");
    }

    /**
     * Proves the query against the program.
     */
    @Override
    public void run() {
        try {
            if (proveRemaining) {
                proveRemaining();
            } else {
                proveNext();
            }
        } catch (Exception e) {
            resultsArea.append(e.getMessage());
        } finally {
            setComputing(false);
        }
    }
    /*
     * Sets the state of the IDE to computing or not. Most of the
     * IDE's controls are grayed out during computation of a
     * program.
     *
     * @param   boolean  if true, indicates that a proof thread
     *                   is finding one or more proofs
     */

    /**
     *
     * @param computing
     */
    protected void setComputing(boolean computing) {

        // computing means everything is disabled, except "Halt"
        proveNextButton.setEnabled(!computing);
        proveRestButton.setEnabled(!computing);
        clearProgramButton.setEnabled(!computing);
        clearResultsButton.setEnabled(!computing);
        programArea.setEnabled(!computing);
        resultsArea.setEnabled(!computing);
        queryArea.setEnabled(!computing);

        haltButton.setEnabled(computing);
    }

    private static final Logger LOG
            = Logger.getLogger(LogikusMediator.class.getName());

}
