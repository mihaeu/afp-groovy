/*
dvar float+ x;
dvar float+ y;

maximize x + y;

constraints {
	2*x + y + 10 <= 20;
	x + 3*y >= 10;
}
*/

def x = dvar 'x'
def y = dvar 'y'

maximize x + y

constraints ([
	(2.const * x + y + 10.const).leq(20.const),
	(x + 3.const * y).geq(10.const)
])

assign ([
	x: 0.0,
	y: 10.0
])
