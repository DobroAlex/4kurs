import numpy as np
from numpy import linalg
def sign(n:float) -> int:
    return 1 if n > 0 else   -1 if n < 0 else 0 
def main():
    X = [np.array([-1., 1., -1., 1.]),
        np.array([1., -1., 1., 1.]),
        np.array([-1., 1., -1., -1.])
        ]

    Y = np.array([-1, 1, -1, -1])
    stability_flag = False
    is_founded_flag = False
    prev_vector = np.ndarray
    while(is_founded_flag == False and stability_flag == False):
        
        Weights = np.ndarray((4,4),dtype = np.float )
        for sample in X:
            xT = sample.reshape((sample.size,1))    #Transposing sample vector to be column-vector insted of row-vector https://jakevdp.github.io/PythonDataScienceHandbook/02.02-the-basics-of-numpy-arrays.html
            Weights += (xT.dot(sample.reshape(1, sample.size)))  #multipluying column and row vectors with reshaping row vector to single-dimensional matrix https://stackoverflow.com/questions/29163126/multiplying-column-and-row-vectors-in-numpy
        print("Weights = {0}".format(Weights))
        W = np.matrix(Weights)
        print ("W = {0}".format(W))
        for i in range(0, W[0].size):
            W[i,i] = 0
        print(W)
        y_star = np.matmul(W, Y.reshape((Y.size,1)) )
        y_star = y_star.A1  #https://stackoverflow.com/questions/3337301/numpy-matrix-to-array
        print("y* = {0}".format(y_star))
        #y_star = y_star.reshape((1,y_star.size))
        print("y transposed* = {0}".format(y_star))
        for i in range(0, y_star.size):
            y_star[i] = sign(y_star[i])
        print("sign(Y*) = {0}".format(y_star))
        for i in range(0, len(X[0])):
            if np.array_equal(y_star,X[i]):
                print("Founded exact match, {0} = {1}, #{2} ".format(y_star,X[i], i))
                is_founded_flag = True
                break
        if (not is_founded_flag):
            print("Match not found, y = y*")
            if (np.array_equal(y_star,Y)):
                print("Stability occurred, breaking") 
                stability_flag = True
                break
            Y = y_star
        
if __name__ == "__main__":
    main()