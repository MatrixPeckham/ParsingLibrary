package com.matrixpeckham.parse.examples.sling;

//import com.sun.java.swing.*;
//import com.sun.java.swing.text.*;
//import com.sun.java.swing.event.*;
import com.matrixpeckham.parse.imperative.Command;
import com.matrixpeckham.parse.imperative.CommandSequence;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.parse.tokens.Tokenizer;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SlingMediator
        implements ActionListener, ChangeListener, Runnable {

    /**
     *
     */
    protected JButton goSource;

    /**
     *
     */
    protected JButton haltSource;

    /**
     *
     */
    protected JButton clearSource;

    /**
     *
     */
    protected JSlider s1;

    /**
     *
     */
    protected JSlider s2;

    /**
     *
     */
    protected JTextArea programArea;

    /**
     *
     */
    protected JTextArea messageArea;

    /**
     *
     */
    protected SlingPanel plotPanel;

    /**
     *
     */
    protected Command command;

    /**
     *
     */
    protected Thread computeThread;

    /**
     *
     */
    protected String lastProgram;

    /**
     *
     */
    protected SlingTarget target;

    /**
     *
     */
    protected SlingParser parser = new SlingParser();

    /**
     * This method reacts, when the user presses one of the IDE's buttons.
     *
     * @param e the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object.equals(goSource)) {
            go();
        }
        if (object.equals(clearSource)) {
            messageArea.setText("");
            lastProgram = null;
        }
        if (object == haltSource) {
            if (computeThread != null) {
                computeThread.interrupt();
                //computeThread.stop();
            }
            computeThread = null;
            setComputing(false);
        }
    }
    /*
     * Throws a runtime exception if the input stream cannot be
     * completely recognized, and if the next element is a
     * reserved word.
     */

    /**
     *
     * @param in
     * @param out
     */
    protected void checkReserved(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> in,
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> out) {
        Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> which
                = out == null ? in : out;
        if (which.hasNext()) {
            String s = which.peek().toString();
            if (parser.wors.getReservedWords().contains(s)) {
                throw new RuntimeException(
                        "> " + s + " is a reserved word");
            }
        }
    }
    /*
     * Throws a runtime exception if the input stream cannot be
     * completely recognized.
     */

    /**
     *
     * @param program
     * @param in
     * @param out
     */
    protected void checkResult(
            String program,
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> in,
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> out) {

        if (out == null || out.hasNext()) {
            checkReserved(in, out);
            if (out == null) {
                throw new RuntimeException(
                        "> Cannot parse " + program);
            }
            if (out.hasNext()) {
                throw new RuntimeException(
                        "> Input appears complete after : \n> " + out.consumed(
                                " "));
            }
        }
    }
    /*
     * This method returns a composite command that it composes
     * by popping individual commands from an assembly.
     */

    /**
     *
     * @param out
     * @return
     */
    protected CommandSequence command(
            Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> out) {
        ArrayList<Command> statements = new ArrayList<>();
        while (!out.stackIsEmpty()) {
            statements.add(out.popVal().asV());
        }
        CommandSequence cs = new CommandSequence();
        int n = statements.size();
        for (int i = n - 1; i >= 0; i--) {
            Command o = statements.get(i);
            try {
                cs.addCommand(o);
            } catch (ClassCastException e) {
                throw new RuntimeException(
                        "Expected Command and found " + o.getClass());
            }
        }
        return cs;
    }
    /*
     * Parse the user's program, if it has changed, into a
     * command. Execute the command in a separate thread to allow
     * halting. Plot whatever functions the program specifies.
     */

    /**
     *
     */
    protected void go() {
        try {
            String thisProgram = programArea.getText();
            if (!thisProgram.equals(lastProgram)) {
                lastProgram = thisProgram;
                command = parse(thisProgram);
                setComputing(true);
                computeThread = new Thread(this);
                computeThread.start();
            } else {
                plotPanel.setPlot(target.getRenderables());
            }
        } catch (Exception e) {
            String text = e.toString();
            if (e.getMessage() != null) {
                text = e.getMessage();
            }
            messageArea.append(text + "\n");
        }
    }

    /**
     * Initialize this mediator with components from the a Sling IDE.
     *
     * @param goSource
     * @param haltSource
     * @param plotPanel
     * @param s1
     * @param s2
     * @param programArea
     * @param messageArea
     * @param clearSource
     */
    public void initialize(
            JButton goSource, JButton haltSource, JButton clearSource,
            JSlider s1, JSlider s2,
            JTextArea programArea, JTextArea messageArea,
            SlingPanel plotPanel) {

        this.goSource = goSource;
        this.haltSource = haltSource;
        this.clearSource = clearSource;
        this.s1 = s1;
        this.s2 = s2;
        this.programArea = programArea;
        this.messageArea = messageArea;
        this.plotPanel = plotPanel;
    }
    /*
     * Parse a program and return a command.
     */

    /**
     *
     * @param program
     * @return
     */
    protected Command parse(String program) {
        Tokenizer t = parser.tokenizer();
        t.setString(program);
        TokenAssembly<TypeOrType<SlingFunction, Command>, SlingTarget> ta
                = new TokenAssembly<>(t);
        target = new SlingTarget(s1, s2);
        ta.setTarget(target);
        Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> out
                = tryMatch(ta);
        checkResult(program, ta, out);
        return command(out);
    }
    /*
     * Execcute the command in a separate thread. This allows,
     * in particular, halting a large "for" loop.
     */

    @Override
    public void run() {
        try {
            command.execute();
            setComputing(false);
            plotPanel.setPlot(target.getRenderables());
        } catch (Exception e) {
            String text = e.toString();
            if (e.getMessage() != null) {
                text = e.getMessage();
            }
            messageArea.append(text + "\n");
            setComputing(false);
        }
    }
    /*
     * Sets the state of the IDE to computing or not. Most of the
     * IDE's controls are grayed out during computation of a
     * program.
     */

    /**
     *
     * @param computing
     */
    protected void setComputing(boolean computing) {

        // computing means everything is disabled, except "Halt"
        goSource.setEnabled(!computing);
        clearSource.setEnabled(!computing);
        programArea.setEnabled(!computing);
        messageArea.setEnabled(!computing);

        haltSource.setEnabled(computing);
    }
    /*
     * Any time a slider changes we re-plot.
     */

    @Override
    public void stateChanged(ChangeEvent e) {
        go();
    }
    /*
     * Try to recognize a user's program. Handle some track
     * exceptions but pass along any other exceptions.
     */

    /**
     *
     * @param ta
     * @return
     */
    protected Assembly<Token, TypeOrType<SlingFunction, Command>, SlingTarget> tryMatch(
            TokenAssembly<TypeOrType<SlingFunction, Command>, SlingTarget> ta) {
        try {
            return parser.start().bestMatch(ta);
        } catch (com.matrixpeckham.parse.examples.track.TrackException e) {
            ArrayList<String> rez = parser.wors.getReservedWords();
            if (rez.contains(e.getFound())) {
                throw new RuntimeException(
                        e.getMessage() + "\n> " + e.getFound()
                        + " is a reserved word");
            } else {
                throw e; // to a more generic handler
            }
        }
    }

    private static final Logger LOG
            = Logger.getLogger(SlingMediator.class.getName());

}
