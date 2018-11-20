import possible_states_enum as PSE
import place 
import math
class infection:
    def init(self, permissibility:float, name:str):
        self.permissibility = permissibility
        self.name = name
    
def calcuc_infection_probability(target_infection:infection, target_place:place):
    probability = 1
    
list_of_infections = [infection(0.9, "Чума"), infection(0.5, "Тубик"), infection(0.1, "Ветрянка")]