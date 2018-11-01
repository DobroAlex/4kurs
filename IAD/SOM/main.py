import random
import math


def create_random_vector(size, min=0, max=1): #генерит рандомный вектор размера size  с эл-ми в интервале [min,max] 
    ret_val = list()
    for _ in range(0, size):
        ret_val.append(random.uniform(min, max))
    return ret_val


def distance(O, W): #эвклидовое расстояние между векторами
    retVal = 0
    for i in range(len(O)):
        retVal += math.pow((O[i]-W[i]), 2)
    return math.sqrt(retVal)


def main():
    C = 0.6  # Шаг обучения
    O = [[1, 1, 0, 0],
         [0, 0, 0, 1],
         [1, 0, 0, 0],
         [0, 0, 1, 1]]
    for i in range(len(O)):
        for j in range(len(O[i])):
            print('O[{0}][{1}] = {2}'.format(i, j, O[i][j]))
        print('\n')

    #W1 = create_random_vector(len(O))
    #W2 = create_random_vector(len(W1))
    W = [[0.2, 0.6, 0.5, 0.9],
         [0.8, 0.4, 0.7, 0.3]]
    print('W1 = {0}\nW2 = {1}'.format(W[0], W[1]))
    epocheNum = 0
    while True:
        for oCur in O :
            epocheNum += 1
            d1 = distance(oCur, W[0])
            d2 = distance(oCur, W[1])
            print('d(O1, W1)={0}\nd(O1, W2)={1}'.format(d1, d2))
            if (d1 < d2):
                for i in range(len(W[0])):
                    W[0][i] = W[0][i] + C * (oCur[i]-W[0][i])
            else:
                for i in range(len(W[0])):
                    W[1][i] = W[1][i] + C * (oCur[i]-W[1][i])
            print('W1 = {0}\nW2 = {1}'.format(W[0], W[1]))
            #C/=2
            print("Epoch #{0} is finished\nNew C = {1}".format(epocheNum, C))
            for wCur in W:
                if 0 in wCur:
                    print ("0-element has been found in W, finishing")
                    return 0
        

if __name__ == "__main__":
    main()
