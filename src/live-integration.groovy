import optim.Solver

def solver = new Solver()

def bindings = new Binding([
	solver: solver,
	maximize: solver.&maximize,
	dvar: solver.&variable
])

def shell = new GroovyShell(bindings)
shell.evaluate(new File('live.groovy'))
