import optim.Assignment
import optim.Solver

def solver = new Solver();
def constraints = { args -> args.each { solver.add it } }

def assignment = new Assignment()
def assign = { args -> args.each { String k, Double v -> assignment.put(k, v)} }

def binding = new Binding([
	solver: solver,
	dvar: solver.&variable,
	maximize: solver.&maximize,
	constant: solver.&constant,
	constraints: constraints,
	assign: assign
])

//def conf = new CompilerConfiguration()
//conf.scriptBaseClass = SolverBaseScriptClass.class.name
//def shell = new GroovyShell(this.class.classLoader, binding, conf)

def shell = new GroovyShell(binding)
shell.evaluate(new File('src/simple.groovy'))

solver.solve(assignment)