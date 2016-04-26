package optim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class ExprList extends ArrayList<Expression> {
	
	private static final long serialVersionUID = 1L;

	public ExprList() {
		super();
	}
	public ExprList(int capa) {
		super(capa);
	}
	public ExprList(Collection<? extends Expression> coll) {
		super(coll);
	}
	
	public interface ZipFunction { // "zips" 2 expressions into 1
		public Expression apply(Expression x, Expression y);
	}
	
	public interface IndexFunction { // function with list index
		public Expression apply(int i, Expression x);
	}
	
	public Expression reduce(ZipFunction f) {
		Expression x = null;
		for (Expression e : this) {
			if (x == null) x = e;
			else x = f.apply(x,e);
		}
		return x;
	}
	
	public Expression sum(Function<Expression,Expression> f) {
		return map(f).reduce(Expression::sum);
	}
	
	public Expression sum() {
		return sum(x -> x);
	}
	
	public Expression prod(Function<Expression,Expression> f) {
		return map(f).reduce(Expression::prod);
	}
	
	public Expression prod() {
		return prod(x -> x);
	}
	
	public Expression sumProd(ExprList that) {
		return zip(that, Expression::prod).sum();
	}
	
	public ExprList zip(ExprList that, ZipFunction f) {
		int resize = Math.min(size(),that.size());
		ExprList result = new ExprList(resize);
		for (int i=0; i<resize; i++)
			result.add(f.apply(get(i),that.get(i)));
		return result;
	}

	public ExprList map(Function<Expression,Expression> f) {
		return map((i,x) -> f.apply(x));
	}

	public ExprList map(IndexFunction f) {
		ExprList result = new ExprList(size());
		for (int i=0; i<size(); i++)
			result.add(f.apply(i,get(i)));
		return result;
	}

	public Expression plus() {
		return sum();
	}

	public Expression multiply() {
		return prod();
	}
}
