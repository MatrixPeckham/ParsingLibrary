package com.matrixpeckham.parse.examples.query;

//import com.sun.java.swing.*;
import com.matrixpeckham.parse.engine.Axiom;
import com.matrixpeckham.parse.engine.DynamicRule;
import com.matrixpeckham.parse.engine.Query;
import com.matrixpeckham.parse.engine.Structure;
import com.matrixpeckham.parse.engine.Term;
import com.matrixpeckham.parse.parse.Assembly;
import com.matrixpeckham.parse.parse.Parser;
import com.matrixpeckham.parse.parse.tokens.Token;
import com.matrixpeckham.parse.parse.tokens.TokenAssembly;
import com.matrixpeckham.parse.utensil.TypeOrType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class JaqlMediator implements ActionListener {

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
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> parser;

    /**
     *
     */
    protected ChipSource chipSource;

    /**
     *
     */
    protected Speller speller;

    /**
     * Parse the input as a query, prove it repeatedly, and show its results.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == goButton) {
            try {
                actionPerformedUnsafe(e);
            } catch (Exception ex) {
                String text = ex.toString();
                if (ex.getMessage() != null) {
                    text = ex.getMessage();
                }
                resultArea.append(text + "\n");
                return;
            }
        }
        if (source == clearButton) {
            resultArea.setText("");
        }
    }
    /*
     * This method provides all the "go" actions and relies on
     * actionPerformed to handle exceptions.
     */

    /**
     *
     * @param e
     */
    protected void actionPerformedUnsafe(ActionEvent e) {
        Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> result
                = parseInput();
        if (result == null) {
            resultArea.append("\nCannot parse input text.");
            return;
        }
        QueryBuilder b = result.getTarget();
        Query q = b.build(chipSource());
        showResults(q);
    }
    /*
     * Return a ChipSource, an axiom source for facts about
     * chips, customers, and orders.
     */

    /**
     *
     * @return
     */
    protected ChipSource chipSource() {
        if (chipSource == null) {
            chipSource = new ChipSource();
        }
        return chipSource;
    }

    /**
     * Initialize this object, using the components of
     *
     * @param goButton a <code>JaqlUe</code>.
     * @param clearButton
     * @param queryArea
     * @param resultArea
     * @param metadataArea
     */
    public void initialize(
            JButton goButton,
            JButton clearButton,
            JTextArea queryArea,
            JTextArea resultArea,
            JTextArea metadataArea) {

        this.goButton = goButton;
        this.clearButton = clearButton;
        this.metadataArea = metadataArea;
        this.queryArea = queryArea;
        this.resultArea = resultArea;
    }
    /*
     * Apply the Jaql parser to the input.
     */

    /**
     *
     * @return
     */
    protected Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> parseInput() {
        String s = queryArea.getText();
        Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> a
                = new TokenAssembly<>(s);
        a.setTarget(new QueryBuilder(speller()));
        Assembly<Token, TypeOrType<Axiom, Term>, QueryBuilder> result
                = parser().completeMatch(a);
        return result;
    }
    /*
     * The parser to use for queries.
     */

    /**
     *
     * @return
     */
    protected Parser<Token, TypeOrType<Axiom, Term>, QueryBuilder> parser() {
        if (parser == null) {
            parser = new JaqlParser(speller()).start();
        }
        return parser;
    }
    /*
     * Show the results of a successfully built query.
     */

    /**
     *
     * @param q
     */
    protected void showResults(Query q) {
        Structure head = q.head();
        DynamicRule tail = q.resolvent();
        while (true) {
            if (!tail.canFindNextProof()) {
                break;
            }
            for (int i = 0; i < head.arity(); i++) {
                if (i > 0) {
                    resultArea.append(", ");
                }
                Object value = head.terms()[i].eval();
                resultArea.append(value.toString());
            }
            resultArea.append("\n");
        }
    }
    /*
     * Return a speller to use for checking class and variable
     * names;
     */

    /**
     *
     * @return
     */
    protected Speller speller() {
        if (speller == null) {
            speller = new ChipSpeller();
        }
        return speller;
    }

    private static final Logger LOG
            = Logger.getLogger(JaqlMediator.class.getName());

}
