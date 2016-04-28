import optim.Expression

abstract class SolverBaseScriptClass extends Script {
	void dvar(String name) {
		def solver = this.binding.solver
		solver.variable name
	}

	void maximize(Expression expression) {
		def solver = this.binding.solver
		solver.maximize expression
	}
}
