package optim;

import java.io.PrintStream;
import java.util.ArrayList;

public class Solver {
	
	public final static int DEBUG = 1000;
	
	final ArrayList<optim.Variable> variables = new ArrayList<>();
	final ArrayList<optim.Constraint> constraints = new ArrayList<>();
	optim.Expression maximize = null;
	optim.Expression minimize = null;
	
	public optim.Variable variable(String name) {
		Variable v = new Variable(this,name);
		variables.add(v);
		if (DEBUG>=1000) System.out.println("variable: "+v);
		return v;
	}

	public ExprList variables(int n, String prefix) {
		ExprList list = new ExprList(n);
		for (int i=0; i<n; i++)
			list.add(variable(prefix+i));
		return list;
	}
	
	public ExprList variables(String[] names) {
		ExprList list = new ExprList(names.length);
		for (int i=0; i<names.length; i++)
			list.add(variable(names[i]));
		return list;
	}
	
	public Expression constant(double value) {
		return new Expression() {
			public double eval(Assignment as) {
				return value;
			}
			public String toString() {
				return ""+value;
			}
		};
	}

	public ExprList constants(int n, double value) {
		ExprList list = new ExprList(n);
		for (int i=0; i<n; i++)
			list.add(constant(value));
		return list;
	}
	
	public ExprList constants(double[] values) {
		ExprList list = new ExprList(values.length);
		for (int i=0; i<values.length; i++)
			list.add(constant(values[i]));
		return list;
	}
	
	public void add(Constraint c) {
		constraints.add(c);
		if (DEBUG>=1000) System.out.println("constraint: "+c);
	}
	
	public void maximize(Expression x) {
		maximize = x;
		if (DEBUG>=1000) System.out.println("maximize: "+x);
	}
	
	public void minimize(Expression x) {
		minimize = x;
		if (DEBUG>=1000) System.out.println("minimize: "+x);
	}
	
	public void prettyPrint(PrintStream out, Assignment as) {
		out.println("VARIABLES");
		for (Variable v : variables)
			out.println(v.eval(as)+" = "+v);
		out.println("CONSTRAINTS");
		for (Constraint c : constraints)
			out.println(c.eval(as)+" = "+c);
		if (minimize != null) {
			out.println("MINIMIZE");
			out.println(minimize.eval(as)+" = "+minimize);
		}
		if (maximize != null) {
			out.println("MAXIMIZE");
			out.println(maximize.eval(as)+" = "+maximize);
		}
	}
	
	/** not really a solver -- just displays the model for as */
	public void solve(Assignment as) {
		prettyPrint(System.out,as);
	}

	public void solve(Assignment as, PrintStream printStream) {
		prettyPrint(printStream, as);
	}
	
	/** create a trivial assignment that most probably is not a solution */
	public void solve() {
		Assignment as = new Assignment();
		for (Variable v : variables)
			as.put(v.getName(),0.0);
		solve(as);
	}
}
