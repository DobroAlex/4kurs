import math
def is_prime(num: int) -> bool:
    for i in range (2, int(math.sqrt(num))):
        if num % i != 0 :
            return False
    return True