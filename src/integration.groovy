import optim.Assignment
import optim.Solver

// globals (not really, only accessible from this script)
def solver = new Solver();
def assignment = new Assignment()

def constraints = { args -> args.each { solver.add it } }
def assign = { args -> args.each { String k, Double v -> assignment.put(k, v)} }
def constant = { Double d -> solver.constant(d) }
def constants = { Map m -> solver.constants m.values() as double[] }
def variables = { String[] args -> solver.variables(args) }

Number.metaClass.getConst = { ->
	solver.constant(delegate as double)
}

def binding = new Binding([
	solver: solver,
	dvar: solver.&variable,
	run: solver.&variables,
	maximize: solver.&maximize,
	minimize: solver.&minimize,
	constant: constant,
	constants: constants,
	variables: variables,
	eq: solver.&equals,
	constraints: constraints,
	assign: assign
])

//def conf = new CompilerConfiguration()
//conf.scriptBaseClass = SolverBaseScriptClass.class.name
//def shell = new GroovyShell(this.class.classLoader, binding, conf)

def shell = new GroovyShell(binding)
shell.evaluate(new File('src/simple.groovy'))
solver.solve(assignment)

// reset globals
solver = new Solver()
assignment = new Assignment()

shell.evaluate(new File('src/oil.groovy'))
solver.solve(assignment)
