package com.matrixpeckham.parse.examples.sling;

//import com.sun.java.swing.*;
//import com.sun.java.swing.text.*;
//import com.sun.java.swing.event.*;
//import com.sun.java.swing.border.*;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideFont;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideTextArea;
import static com.matrixpeckham.parse.utensil.SwingUtensil.ideTitledBorder;
import static com.matrixpeckham.parse.utensil.SwingUtensil.launch;
import java.awt.BorderLayout;
import static java.awt.Color.black;
import java.awt.Dimension;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.KeyEvent.VK_G;
import static java.lang.Short.MAX_VALUE;
import java.util.logging.Logger;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.Box;
import static javax.swing.Box.createHorizontalBox;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createVerticalBox;
import static javax.swing.Box.createVerticalGlue;
import javax.swing.JButton;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import static javax.swing.KeyStroke.getKeyStroke;
import static javax.swing.SwingConstants.HORIZONTAL;


public class SlingIde {

    /**
     *
     */
    protected SlingMediator mediator;

    /**
     *
     */
    protected JButton clearButton;

    /**
     *
     */
    protected JButton goButton;

    /**
     *
     */
    protected JButton haltButton;

    /**
     *
     */
    protected JTextArea messageArea;

    /**
     *
     */
    protected JTextArea programArea;

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
    protected SlingPanel plotPanel;

    /**
     *
     */
    protected int preferredWidth = 500;

    /**
     *
     */
    protected Dimension min = new Dimension(preferredWidth, 30);
    /*
     * Creates and returns the box that contains the IDE's
     * buttons.
     */

    /**
     *
     * @return
     */
    protected Box buttonBox() {
        Box b = createHorizontalBox();
        b.add(goButton());
        b.add(haltButton());
        b.add(createHorizontalGlue());
        b.add(clearButton());
        return b;
    }
    /*
     * Creates and returns the box that contains the IDE's
     * buttons.
     */

    /**
     *
     * @return
     */
    protected JPanel buttonPanel() {
        JPanel upperButtons = new JPanel();
        upperButtons.setLayout(new BorderLayout());
        upperButtons.add(goButton(), "Center");
        upperButtons.add(haltButton(), "South");

        JPanel p = new JPanel();
        p.setBorder(createEmptyBorder(10, 5, 5, 5));
        p.setLayout(new BorderLayout());
        p.add(upperButtons, "North");
        p.add(clearButton(), "South");
        return p;
    }
    /*
     * The button that clears the message area.
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
     * The button that starts the proof thread.
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
    /*
     * The panel with the program/message split pane and the
     * button panel.
     */

    /**
     *
     * @return
     */
    protected JPanel lowerPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(programMessagePane(), "Center");
        p.add(buttonPanel(), ("East"));
        return p;
    }

    /**
     * Launch a Sling interactive development environment.
     * @param args
     */
    public static void main(String args[]) {
        launch(
                new SlingIde().mainPane(), " Sling");
    }
    /*
     * A split pane that contains, ultimately, all the components
     * in the IDE.
     */

    /**
     *
     * @return
     */
    protected JSplitPane mainPane() {

        JSplitPane sp = new JSplitPane(
                VERTICAL_SPLIT,
                false,
                upperPanel(),
                lowerPanel());

        sp.setDividerSize(6);
        return sp;
    }
    /*
     * The object that controls or "mediates" the interaction
     * of this development environment's Swing components.
     */

    /**
     *
     * @return
     */
    protected SlingMediator mediator() {
        if (mediator == null) {
            mediator = new SlingMediator();
            mediator.initialize(
                    goButton(),
                    haltButton(),
                    clearButton(),
                    s1(),
                    s2(),
                    programArea(),
                    messageArea(),
                    plotPanel());
        }
        return mediator;
    }
    /*
     * The message text area.
     */

    /**
     *
     * @return
     */
    protected JTextArea messageArea() {
        if (messageArea == null) {
            messageArea = ideTextArea();
        }
        return messageArea;
    }
    /*
     * The <code>SlingPanel</code> where plotting occurs.
     */

    /**
     *
     * @return
     */
    protected SlingPanel plotPanel() {
        if (plotPanel == null) {
            plotPanel = new SlingPanel();
            plotPanel.setPreferredSize(new Dimension(
                            MAX_VALUE, MAX_VALUE));
        }
        return plotPanel;
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
     * The split pane that contains the program panel and the
     * message panel.
     */

    /**
     *
     * @return
     */
    protected JSplitPane programMessagePane() {

        JSplitPane inner = new JSplitPane(
                VERTICAL_SPLIT, false, titledProgramPanel(),
                titledMessagePanel());
        inner.setDividerSize(6);

        return inner;
    }
    /*
     * The first slider.
     */

    /**
     *
     * @return
     */
    protected JSlider s1() {
        if (s1 == null) {
            s1 = new JSlider(HORIZONTAL, 0, 100, 100);
            s1.addChangeListener(mediator());
        }
        return s1;
    }
    /*
     * The second slider.
     */

    /**
     *
     * @return
     */
    protected JSlider s2() {
        if (s2 == null) {
            s2 = new JSlider(HORIZONTAL, 0, 100, 100);
            s2.addChangeListener(mediator());
        }
        return s2;
    }
    /*
     * Creates a slider that the mediator will listen to.
     */

    /**
     *
     * @return
     */
    protected JSlider slider() {
        JSlider s = new JSlider(HORIZONTAL, 0, 100, 100);
        s.addChangeListener(mediator());
        return s;
    }
    /*
     * Creates and returns the box that contains a slider.
     */

    /**
     *
     * @param name
     * @param s
     * @return
     */
    protected Box sliderBox(String name, JSlider s) {
        Box b = createHorizontalBox();
        JLabel label = new JLabel(name);
        label.setFont(ideFont());
        label.setBorder(createEmptyBorder(0, 10, 0, 10));
        label.setForeground(black);
        b.add(label);
        b.add(s);
        return b;
    }
    /*
     * The panel that contains the slider boxes.
     */

    /**
     *
     * @return
     */
    protected JPanel sliderPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        Box b = createVerticalBox();
        b.add(sliderBox("s1", s1()));
        b.add(createVerticalGlue());
        b.add(sliderBox("s2", s2()));

        p.setBorder(createCompoundBorder(ideTitledBorder("Sliding Variables"),
                createEmptyBorder(5, 5, 5, 5)));
        p.add(b, "Center");

        return p;
    }
    /*
     * Forms and returns a standard text panel, which is
     * a scroll pane around a text area, with a title border.
     */

    /**
     *
     * @param title
     * @param ta
     * @param pref
     * @param min
     * @return
     */
    protected static JPanel textPanel(
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
    /*
     * The panel that wraps a title around the message area.
     */

    /**
     *
     * @return
     */
    protected JPanel titledMessagePanel() {

        return textPanel(
                "Messages",
                messageArea(),
                new Dimension(preferredWidth, 60),
                min);
    }
    /*
     * The panel that wraps a title around the plot panel.
     */

    /**
     *
     * @return
     */
    protected JPanel titledPlotPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(createCompoundBorder(ideTitledBorder("Plot"),
                createEmptyBorder(0, 5, 10, 5)));
        p.add(plotPanel(), "Center");
        p.setPreferredSize(new Dimension(preferredWidth, preferredWidth));
        p.setMinimumSize(new Dimension(50, 50));
        return p;
    }
    /*
     * The panel that wraps a title around the program area.
     */

    /**
     *
     * @return
     */
    protected JPanel titledProgramPanel() {

        return textPanel(
                "Program",
                programArea(),
                new Dimension(preferredWidth, 120),
                min);
    }
    /*
     * The panel that contains the (titled) plot panel and the
     * slider panel.
     */

    /**
     *
     * @return
     */
    protected JPanel upperPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(titledPlotPanel(), "Center");
        p.add(sliderPanel(), "South");
        return p;
    }

    private static final Logger LOG = Logger.getLogger(SlingIde.class.getName());
}
