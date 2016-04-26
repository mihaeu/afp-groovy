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

def rawMaterial 			= 205
def products 				= ['light', 'medium', 'heavy']
def demand					= [light: 59, medium: 12, heavy: 13]
def processes				= ["1", "2"] as String[]
def production				= [light: [1: 12, 2: 16], medium: [1: 1, 2: 7], heavy: [1: 4, 2: 2]]
def consumption				= [1: 25, 2: 30]
def cost					= [1: 300, 2: 400]

//minimize processes sum { cost.it * processes.it }

def run = variables(processes)
constraints([
	constants(consumption).sumProd().leq(constant(rawMaterial)),
	products.each {}
])