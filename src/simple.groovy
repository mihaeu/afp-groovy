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
	constant(2).eq(constant(2)),
	constant(2).eq(constant(3))
])

assign ([
	x: 0.0,
	y: 10.0
])