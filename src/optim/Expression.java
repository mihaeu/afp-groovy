
package optim;

public abstract class Expression {
	
	public abstract double eval(Assignment as);

	public Expression negative() {
		final Expression self = this;
		return new Expression() {
			public double eval(Assignment as) {
				return - self.eval(as);
			}
			public String toString() {
				return "-"+self;
			}
		};
	}

	public Expression sum(Expression that) {
		final Expression self = this;
		return new Expression() {
			public double eval(Assignment as) {
				return self.eval(as) + that.eval(as);
			}
			public String toString() {
				return "("+self+"+"+that+")";
			}
		};
	}
	
	public Expression diff(Expression that) {
		final Expression self = this;
		return new Expression() {
			public double eval(Assignment as) {
				return self.eval(as) - that.eval(as);
			}
			public String toString() {
				return "("+self+"-"+that+")";
			}
		};
	}
	
	public Expression prod(Expression that) {
		final Expression self = this;
		return new Expression() {
			public double eval(Assignment as) {
				return self.eval(as) * that.eval(as);
			}
			public String toString() {
				return "("+self+"*"+that+")";
			}
		};
	}
	
	public Expression div(Expression that) {
		final Expression self = this;
		return new Expression() {
			public double eval(Assignment as) {
				return self.eval(as) / that.eval(as);
			}
			public String toString() {
				return "("+self+"/"+that+")";
			}
		};
	}
	
	private static final double EQ_EPS = 1E-6;

	public Constraint eq(Expression that) {
		final Expression self = this;
		return new Constraint() {
			public boolean eval(Assignment as) {
				return Math.abs(self.eval(as)-that.eval(as)) <= EQ_EPS;
			}
			public String toString() {
				return self+" == "+that;
			}
		};
	}

	public Constraint geq(Expression that) {
		final Expression self = this;
		return new Constraint() {
			public boolean eval(Assignment as) {
				return self.eval(as) >= that.eval(as)-EQ_EPS;
			}
			public String toString() {
				return self+" >= "+that;
			}
		};
	}

	public Constraint leq(Expression that) {
		final Expression self = this;
		return new Constraint() {
			public boolean eval(Assignment as) {
				return self.eval(as) <= that.eval(as)+EQ_EPS;
			}
			public String toString() {
				return self+" <= "+that;
			}
		};
	}

	public Expression plus(Expression that) {
		return sum(that);
	}

	public Expression minus(Expression that) {
		return diff(that);
	}

	public Expression multiply(Expression that) {
		return prod(that);
	}

	public Constraint equals(Expression that) {
		return eq(that);
	}

	public Constraint compareTo(Expression that) {
		return eq(that);
	}
}

