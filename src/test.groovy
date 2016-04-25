import optim.*

public class Test {

	private Solver solver = new Solver();
	private expected =
			"VARIABLES\n" +
			"0.0 = x\n" +
			"10.0 = y\n" +
			"CONSTRAINTS\n" +
			"true = ((2.0*x)+(y+10.0)) <= 20.0\n" +
			"true = (x+(3.0*y)) >= 10.0\n" +
			"MAXIMIZE\n" +
			"10.0 = (x+y)\n";

/*
dvar float+ x;
dvar float+ y;

maximize x + y;

constraints {
	2*x + y + 10 <= 20;
	x + 3*y >= 10;
}
 */
	def dvar(name) {
		return solver.variable(name);
	}

//		solver.add(solver.constant(2) == solver.constant(2))
//		solver.add((solver.constant(2) * x + y + solver.constant(10) == solver.constant(20)));

	def constant(number) {
		return solver.constant(number)
	}

	public void simple() {
		def x = dvar 'x'
		def y = dvar 'y'
		maximize x + y


		def leq = ((constant(2) * x) + (y + (constant(10)))) leq(solver.constant(20))
		constraints ([
				leq,
				x.sum(solver.constant(3).prod(y)).geq(solver.constant(10))
		])
		Assignment assignment = new Assignment([
				x: 0.0 as double,
				y : 10.0 as double
		]);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
		PrintStream printStream = new PrintStream(byteArrayOutputStream)
		solver.solve(assignment, printStream);
		assert expected.equals(byteArrayOutputStream.toString('UTF-8'))
	}

	def maximize(Expression that) {
		solver.maximize(that)
	}

	def constraints(List<Constraint> constraints) {
		constraints.each {Constraint constraint -> solver.add(constraint)}
	}

	public static void main(String[] args) {
		new Test().simple();
	}
}
