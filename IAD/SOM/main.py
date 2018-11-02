import random
import math
import copy

def create_random_vector(size, min=0, max=1):	#generates randow vector[size] with  float elements   in range [min,max]
    ret_val = list()
    for _ in range(0, size):
        ret_val.append(random.uniform(min, max))
    return ret_val


def distance(O, W):	#euclidian distance between two vectors  :sqrt((O[0]-W[0])^2+....+O[i]-W[i])^2)
    retVal = 0
    for i in range(len(O)):
        retVal += math.pow((O[i]-W[i]), 2)
    return math.sqrt(retVal)

def findNearestCentroid (targetO, W):	#searches for closest centroid W and distance to it   for  given sample O from given options 
	minDist = distance(targetO, W[0])
	centroidNum = 0
	for i in range(len(W)):
		if distance(targetO, W[i]) < minDist:
			minDist = distance(targetO, W[i])
			centroidNum = i
	return minDist, centroidNum	#returns minimal euclidian distance and number of closest *centroid*
			
		

def main():
    C = 0.6	#learning tempo   
    O = [[1, 1, 0, 0],		#binary sample
         [0, 0, 0, 1],
         [1, 0, 0, 0],
         [0, 0, 1, 1]]
    #O = [[1,1],		#real-numbers sample
    #	[6,7],
    #	[2,1],
    #	[3,3],
    #	[7,8],
    #	[9,9]]
    W = list()
    for i in range(len(O)):
        for j in range(len(O[i])):
            print('O[{0}][{1}] = {2}'.format(i, j, O[i][j]))
        print('\n')
    #for i in range(len(O[0])):
	#		W.append(create_random_vector(len(O[i])))
    
    W = [create_random_vector(len(O[0])),
	create_random_vector(len(O[1]))]
    #W = [[0.2, 0.6, 0.5, 0.9],
    #      [0.8, 0.4, 0.7, 0.3]]
    for i in range(len(W))	:
	print ("W[{0}]={1}".format(i, W[i]))
    epochNum = 0	#number of epoch // iteration 
    zeroFlag = False	#indicates zero-element occured in W
    stableFlag = False	#indicates W is not changing anymore 
    while True:
	if zeroFlag == True or stableFlag == True or C == 0:
		break
        for oCur in O :
            epochNum += 1
	    W_on_prev_step = copy.deepcopy(W)	
	    print("W_on_prev_step = {0}".format(W_on_prev_step))
	    d = list()
	    for i in range(len(W)):
		d.append(round(distance(oCur, W[i]), 3))
		print('d(O[{0}, W{0})={1}\nd(O1, W2)={1}'.format(i, d[i]))

	    minD = min(d)
	    minDindex = d.index(minD)
	    print ("d_min = {0}, it's index= {1}".format(minD, minDindex))
	    for i in range(len(W[minDindex])):
		W[minDindex][i] = round(W[minDindex][i] + C * (oCur[i]-W[minDindex][i]), 3)

	    
	    for line in W:
		print("W[{0}] = {1}".format(W.index(line), line))
	    if epochNum % 100 == 0:
		C/=2.0
	    if C == 0:
		break
            print("Epoch #{0} is finished\nNew C = {1}".format(epochNum, C))
	    if W_on_prev_step == W:
		stableFlag = True
		print("W-vectors are now stable, finishing. W_old = {0}, W_new={1}".format(W_on_prev_step, W))
		break
            for wCur in W:
                if 0 in wCur:
                    print ("0-element has been found in W, finishing, W_old = {0}, W_new={1}".format(W_on_prev_step, W) )
                    zeroFlag = True
		    break
	    if zeroFlag == True or stableFlag == True:
		break
    
    
    for testingO in O:
	minDist, centroidNum = findNearestCentroid(testingO, W)
	print("O = {0}, Nearest centr = {1}({2}), dist = {3}".format(testingO, centroidNum, centroidNum+1,  minDist))
                

if __name__ == "__main__":
    main()
