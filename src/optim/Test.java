package optim;

public class Test {

/*
dvar float+ x;
dvar float+ y;

maximize x + y;

constraints {
	2*x + y + 10 <= 20;
	x + 3*y >= 10;
}
 */
	public static void simple() {
		Solver solver = new Solver();
		Variable x = solver.variable("x");
		Variable y = solver.variable("y");
		solver.maximize(x.sum(y));
		solver.add(solver.constant(2).prod(x).sum(y.sum(solver.constant(10))).leq(solver.constant(20)));
		solver.add(x.sum(solver.constant(3).prod(y)).geq(solver.constant(10)));
		Assignment as = new Assignment();
		as.put("x",0.0);
		as.put("y",10.0);
		solver.solve(as);
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
	public static void oil() {
		double rawMaterial = 205;
		String[] products = {"light","medium","heavy"};
		double[] demand = {59,12,13};
		String[] processes = {"p1","p2"};
		double[][] production = {{12,16},{1,7},{4,2}};
		double[] consumption = {25,30};
		double[] cost = {300,400};
		//
		Solver solver = new Solver();
		ExprList run = solver.variables(processes);
		solver.minimize(run.sumProd(solver.constants(cost)));
		solver.add(run.sumProd(solver.constants(consumption)).leq(solver.constant(rawMaterial)));
		for (int q=0; q<products.length; q++) {
			Expression x = solver.constant(0);
			for (int p=0; p<processes.length; p++)
				x = x.sum(run.get(p).prod(solver.constant(production[q][p])));
			solver.add(x.geq(solver.constant(demand[q])));
		}
		//
		Assignment as = new Assignment();
		as.put("p1",2.25);
		as.put("p2",2.0);
		solver.solve(as);
	}

	public static void main(String[] args) {
		simple();
		oil();
	}
}
