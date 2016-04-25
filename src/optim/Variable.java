package optim;

public class Variable extends Expression {

	private Solver solver;
	private String name;
	
	Variable(Solver solver, String name) {
		this.solver = solver;
		this.name = name;
	}
	
	public Solver getSolver() {
		return solver;
	}

	public String getName() {
		return name;
	}
	
	public double eval(Assignment as) {
		return as.get(name);
	}
	
	public String toString() {
		return name;
	}
}
