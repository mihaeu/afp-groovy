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

def rawMaterial 	= 205
def products 		= ["light","medium","heavy"]
def demand 			= [59,12,13]
def processes 		= ["p1","p2"]
def production 		= [[12,16],[1,7],[4,2]]
def consumption 	= [25,30]
def cost 			= [300,400]

def run = variables processes
minimize run.sumProd(constants(cost))

constraint run.sumProd(constants(consumption)).leq(rawMaterial.const)

products.indices.each { q ->
	def x = 0.const
	processes.indices.each { p ->
		x = x + run.get(p) * production[q][p].const
	}
	constraint x.geq(demand[q].const)
}

assign ([
	p1: 2.25,
	p2: 2.0
])