
def F (W,X, iter):
    retVal = 0
    for i in range(len(X)):
        retVal += W[i] * X[i]
    return retVal
def signum (val):
    if val < 0:
        return -1
    elif  val > 0:
        return 1
    else:
        return 0
def recalcWeight(Wold, Xvec, Y, C):
    newW = []
    for i in range(len(Wold)):
        newW.append(Wold[i]+C*(1-Y) * Xvec[i])
    return newW
def main():
    X = [[5.7, 6.3, 1],
        [9.3, 8.7, 1],
        [4.6, 5.2, 1],
        [10.1, 7.3, 1 ]]
    Y = []
    for i in range(len(X)):
        if i % 2 == 0:
            Y.append(+1)
        else:
            Y.append(-1)
        print ("Y[{0}]={1}".format(i,Y[i]))
    
    W = [0.1, 0.4, 0.3]
    C = 0.2     #learning tempo
    recalcFlag = True 
    while recalcFlag == True:
        for i in range(len(X)):
            ans = signum(F(W,X[i], i))
            print("F=sign({0}\t;{1}={2}".format(W,X[i],ans))
            
            if ans != Y[i]:
                W = recalcWeight(W, X[i], Y[i], C)
                print("Recalculating weight ...\nW = {0}".format(W))
                recalcFlag = True
            else:
                recalcFlag = False

if __name__ == "__main__":
    main()