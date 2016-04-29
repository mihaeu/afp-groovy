import optim.Assignment

def x = dvar "x"
def y = dvar "y"

maximize x  + y

solver.add solver.constant(2).prod(x).sum(y.sum(solver.constant(10))).leq(solver.constant(20))
solver.add x.sum(solver.constant(3).prod(y)).geq(solver.constant(10))

solver.solve([
	x: 0.0 as double,
	y: 10.0 as double
] as Assignment)