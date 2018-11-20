#https://datascience.stackexchange.com/questions/30026/find-the-minimum-value-of-x2y2-with-genetic-algorithm
import Genetic
f = lambda x,y: 5-24*x+17*x**2-(11./3.)*x**3 + (1./4.)*x**4
gen = Genetic.Genetic(f)
minim = gen.run()
print('Minimum found      X =', minim[0], ', Y =', f(minim[0],0))
