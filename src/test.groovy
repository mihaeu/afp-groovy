test ['fsdfs', 'sdfsdf']

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
	def dvar(String name) {
		return solver.variable(name);
	}

	def constant(number) {
		return solver.constant(number)
	}

	def solve(Map<String, Double> assignment) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
		PrintStream printStream = new PrintStream(byteArrayOutputStream)
		solver.solve(new Assignment(assignment), printStream);

		byteArrayOutputStream.toString('UTF-8')
	}

	def maximize(Expression that) {
		solver.maximize(that)
	}

	def constraints(List<Constraint> constraints) {
		constraints.each {Constraint constraint -> solver.add(constraint)}
	}

	public void simple() {
		Variable x = dvar 'x'
		Variable y = dvar 'y'
		maximize x + y

		constraints ([
				((constant(2) * x) + (y + (constant(10)))) == (solver.constant(20)),
				x.sum(solver.constant(3).prod(y)).geq(solver.constant(10))
		])

		def solution = solve([
				x: 0.0 as double,
				y: 10.0 as double
		])

		assert expected.equals(solution)
	}

	/*
float rawMaterial                     = 205;
{string} products                     = {"light","medium","heavy"};
float demand[products]                = [59,12,13];
{string} processes                    = {"1","2"};
float production[products][processes] = [[12,16],[1,7],[4,2]];
float consumption[processes]          = [25,30];
float cost[processes]                 = [300,400];

dvar float+ run[processes];

minimize sum (p in processes) cost[p] * run[p];

constraints {
  sum (p in processes) consumption[p] * run[p] <= rawMaterial;
  forall (q in products)
    sum (p in processes) production[q][p] * run[p] >= demand[q];
}
*/
	public void oil() {
		double rawMaterial = 205;
		String[] processes = ["p1","p2"];
		String[] products = ["light","medium","heavy"];
		double[] demand = [59,12,13];
		double[][] production = [[12,16],[1,7],[4,2]];
		double[] consumption = [25,30];
		double[] cost = [300,400];

		ExprList run = solver.variables(processes);
		solver.minimize(run.sumProd(solver.constants(cost)));
		solver.add(run.sumProd(solver.constants(consumption)).leq(solver.constant(rawMaterial)));
		for (int q=0; q<products.length; q++) {
			Expression x = solver.constant(0);
			for (int p=0; p<processes.length; p++)
				x = x.sum(run.get(p).prod(solver.constant(production[q][p])));
			solver.add(x.geq(solver.constant(demand[q])));
		}

		solve([
		        p1: 2.25,
				p2: 2.0
		]);
	}

	public static void main(String[] args) {
		new Test().simple();
		new Test().oil()
	}
}
