package com.matrixpeckham.parse.engine;

import static com.matrixpeckham.parse.engine.Unification.empty;
import static java.lang.System.arraycopy;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * A DynamicRule represents a provable statement that a
 * structure is true if a following series of other
 * structures are true.
 * <p>
 * For example,
 * <blockquote><pre>
 *     bachelor(X) :- male(X), unmarried(X);
 * </pre></blockquote>
 * <p>
 * is a logical rule.
 * <p>
 * The head of this rule is the structure <code>bachelor(X)
 * </code>. A structure <code>bachelor(B)</code> can prove
 * itself by unifying with the head, and then proving the
 * remaining structures or "tail".
 * <p>
 * The tail in this example contains <code>male(X)</code>
 * and <code>unmarried(X)</code>.
 * <p>
 */
public class DynamicRule extends Rule
        implements DynamicAxiom {

    /**
     *
     */
    protected AxiomSource as;

    /**
     *
     */
    protected Scope scope;

    /**
     *
     */
    protected boolean headInvolved = false;

    /**
     *
     */
    protected DynamicRule tail;

    /*
     * Construct a provable rule for the given axiom source,
     * scope, and structures -- these structures must all be
     * capable of proving themselves. That is, they must be
     * consulting structures or gateways.
     */

    /**
     *
     * @param as
     * @param scope
     * @param structures
     */
    protected DynamicRule(
            AxiomSource as, Scope scope, Structure[] structures) {

        super(structures);
        this.as = as;
        this.scope = scope;
    }

    /**
     * Construct a provable rule for the given axiom source, scope, and rule.
     *
     * @param as    the source to consult for proving the structures in this
     *              dynamic rule
     *
     * @param scope a home for the variables in this dynamic rule
     * @param rule  the non-dynamic source of this rule.
     */
    protected DynamicRule(
            AxiomSource as, Scope scope, Rule rule) {

        this(as, scope,
                provableStructures(as, scope, rule.structures));
    }

    /**
     * "Can establish" means that either a rule can prove itself, or that the
     * rule is empty.
     * <p>
     * When a structure unifies with the head of a rule, the structure asks the
     * rule's tail if it can "establish" itself. This amounts to proving the
     * tail, unless this rule is empty. If this rule is empty, it can
     * "establish" itself, but it cannot "find next proof".
     *
     * @return <code>true</code> if this rule is empty, or if it is nonempty and
     *         can find another proof
     */
    public boolean canEstablish() {
        if (isEmpty()) {
            return true;
        }
        return canFindNextProof();
    }

    /**
     * Tests if this rule can find another proof, and, if so, sets this rule's
     * variables to the values that make the proof true.
     * <p>
     *
     * @return <code>true</code> if this rule can find another proof.
     */
    public boolean canFindNextProof() {
        if (isEmpty()) {
            return false;
        }
        /*
         * If we have already found a proof, the next proof may
         * come by finding another proof of the tail.
         */
        if (headInvolved) {
            if (tail().canFindNextProof()) {
                return true;
            }
        }
        /*
         * Prove our structures or give up. If the head is provable,
         * it means the head has unified with another rule in the
         * program. Our task then is to establish that either the
         * tail is empty, or that it is provable. "Can establish"
         * means is empty or provable.
         */
        while (true && !Thread.interrupted()) {
            headInvolved = head().canFindNextProof();
            if (!headInvolved) {
                return false;
            }

            if (tail().canEstablish()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the home of this dynamic rule's variables.
     *
     * @return the home of this dynamic rule's variables
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Return <code>true</code> if this rule contains no structures.
     *
     * @return <code>true</code> if this rule contains no structures.
     */
    public boolean isEmpty() {
        return structures.length == 0;
    }

    /**
     * Return a variable of the given name.
     *
     * @return a variable of the given name
     *
     * @param name the name to look up
     */
    public Variable lookup(String name) {
        return scope.lookup(name);
    }

    /**
     * Create provable versions of an input array of structures.
     *
     * @param as
     * @param scope
     * @param structures
     *
     * @return
     */
    protected static Structure[] provableStructures(
            AxiomSource as, Scope scope, Structure[] structures) {

        Structure[] provables = new Structure[structures.length];
        for (int i = 0; i < structures.length; i++) {
            Structure s = structures[i];
            // a "fact" is a rule asks if the fact is a known
            // fact, and must consult the ps to find out
            if (s instanceof Fact) {
                provables[i] = new ConsultingStructure(
                        as, s.functor, s.terms);
            } else {
                provables[i] = (Structure) structures[i].copyForProof(as, scope);
            }
        }
        return provables;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.as);
        hash = 71 * hash + Objects.hashCode(this.scope);
        hash = 71 * hash + (this.headInvolved ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.tail);
        return 71 * hash + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DynamicRule other = (DynamicRule) obj;
        if (!Objects.equals(this.as, other.as)) {
            return false;
        }
        if (!Objects.equals(this.scope, other.scope)) {
            return false;
        }
        if (this.headInvolved != other.headInvolved) {
            return false;
        }
        if (!Objects.equals(this.tail, other.tail)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns the series of structures which, if proven, prove the truth of the
     * head.
     *
     * @return the tail of this rule
     */
    @Override
    public DynamicRule resolvent() {
        return tail();
    }

    /**
     * Returns the series of structures after the head.
     *
     * @return the tail of this rule
     */
    public DynamicRule tail() {
        if (tail == null) {
            int len = structures.length;
            Structure[] rest = new Structure[len - 1];
            arraycopy(structures, 1, rest, 0, len - 1);
            tail = new DynamicRule(as, scope, rest);
        }
        return tail;
    }

    /**
     * Returns this executable rule's variables.
     *
     * @return unification a collection of variables from this rule
     */
    public Unification variables() {
        if (structures.length == 0) {
            return empty;
        }
        /*
         * The following approach keeps the variables in the order they
         * appear in the rule.
         */

        return head().variables().append(tail().variables());
    }

    private static final Logger LOG
            = Logger.getLogger(DynamicRule.class.getName());

}
