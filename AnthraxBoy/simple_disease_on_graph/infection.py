import possible_states_enum as PSE
import place 
import math
class infection:
    def init(self, permissibility:float, name:str):
        self.permissibility = permissibility
        self.name = name
    
def calcuc_infection_probability(target_infection:infection, target_place:place.place):
    if target_infection  not in  target_place.infections:
        target_place.infections.add(target_infection)
    
    probability = 1.0
    sum = 0
    for 

list_of_infections = [infection(0.9, "Чума"), infection(0.5, "Тубик"), infection(0.1, "Ветрянка")]