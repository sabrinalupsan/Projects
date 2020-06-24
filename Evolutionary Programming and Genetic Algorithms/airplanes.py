import numpy as np


# calculate the fitness function and check the feasability of x
def fitness(x, n, c, v, m, max):
    val = 0
    cost = 0
    for i in range(n):
        val = val + x[i] * v[i]
        cost = cost + x[i] * c[i]
    return cost <= max, val


# generate initial population
# I: costFile, valueFile, visibilityFile - the name of the files of cost, value, visibility
# max -maximum capacity
# dim - number of individuals in the population
# E: pop - initial population
# n - dimension of the problem
def generate(costFile, valueFile, visibilityFile, max, dim):
    c = np.genfromtxt(costFile)
    v = np.genfromtxt(valueFile)
    m = np.genfromtxt(visibilityFile)
    n = len(c)
    pop = []
    for i in range(dim):
        gata = False
        while gata == False:
            x = np.random.randint(0, 100, n)
            gata, val = fitness(x, n, c, v, m, max)
        x = list(x)
        x = x + [val]
        pop = pop + [x]
    return pop, dim, n, c, v, m, max


# untiform crossover between x and y, arrays with n individuals
# I :x,y,n same as above
# E: c1, c2 - the children
def crossover_uniform(x, y, n):
    c1 = x.copy()
    c2 = y.copy()
    for i in range(n):
        r = np.random.randint(0, 2)
        if r == 1:
            c1[i] = y[i]
            c2[i] = x[i]
    return c1, c2


# I: pop,dim,n - same as above
#     c, v, max - the problem data
#     pc- crossover probability
# E: po -children population
def crossover_population(pop, dim, n, c, v, m, max, pc):
    po = []
    for i in range(0, dim - 1, 2):
        x = pop[i]
        y = pop[i + 1]
        r = np.random.uniform(0, 1)
        if r <= pc:
            c1, c2 = crossover_uniform(x[:n], y[:n], n)
            fez, val = fitness(c1, n, c, v, m, max)
            if fez:
                c1 = c1 + [val]
            else:
                c1 = x.copy()
            fez, val = fitness(c2, n, c, v, m, max)
            if fez:
                c2 = c2 + [val]
            else:
                c2 = y.copy()
        else:
            c1 = x.copy()
            c2 = y.copy()
        po = po + [c1]
        po = po + [c2]
    return po


# I:a,b -the interval in which it resets
# E: y - the new value
def mutate_uniform(a, b):
    y = np.random.uniform(a, b)
    return y


# I:pop,dim,n - population of dimension dimx(n+1)
#   pm - mutation probability
# E: - mpop - mutated population
def mutate_population(pop, dim, n, c, v, m, max, pm):
    mpop = pop.copy()
    for i in range(dim):
        x = pop[i][:n].copy()
        for j in range(n):
            r = np.random.uniform(0, 2)
            if r <= pm:
                x[j] = mutate_uniform(0, max)
        fez, val = fitness(x, n, c, v, m, max)
        if fez:
            x = x + [val]
            mpop[i] = x.copy()
    return mpop


def selection(pop, dim, n, c, v, m, max):
    maximumAutonomy = 0
    for i in range(dim):
        x = pop[i][:n].copy()
        sumOfAutonomy = 0
        sumOfVisibility = 0
        for j in range(n):
            sumOfAutonomy = sumOfAutonomy + x[j] * v[j]
            sumOfVisibility = sumOfVisibility + x[j] * m[j]
        if sumOfAutonomy > maximumAutonomy and (sumOfVisibility / (x[0] + x[1] + x[2])) > 2000:
            maximumAutonomy = sumOfAutonomy
            individual = x
    return individual


def printResult(ind, c, v, m):
    print("The optimum solution is:", ind[0], "units of airplanes I,", ind[1], "units of airplanes II and", ind[2],
          "units of airplanes III")
    print("The average autonomy is: ", (ind[0] * v[0] + ind[1] * v[1] + ind[2] * v[2]) / (ind[0] + ind[1] + ind[2]))
    print("The average visibility is: ", (ind[0] * m[0] + ind[1] * m[1] + ind[2] * m[2]) / (ind[0] + ind[1] + ind[2]))
    print("The total cost is: ", ind[0] * c[0] + ind[1] * c[1] + ind[2] * c[2])

# Apel
# import airplanes as a
# p,dim,n,c,v,m,max=a.generate("costs.txt","values.txt","visibility.txt",5000,1000)
# o=a.crossover_population(p,dim,n,c,v,m,max,0.8)
# popF=a.mutate_population(o,dim,n,c,v,m,max,0.3)
# ind=a.selection(popF,dim,n,c,v,m,max)
# a.printResult(ind,c,v,m)
