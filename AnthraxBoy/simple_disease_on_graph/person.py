import math
import graph_utils as GU
import infection as Infection

class person:
    def __init__(self, age:int, sex:chr, receptivity:float, infected_with:dict = dict() ):
        self.age = age
        self.sex = sex
        self.receptivity = receptivity
        self.infected_with = infected_with
    def calc_infection_probability(target_infection:Infection.infection, target_person,  persons:list()) -> float:
        return 1.0 - math.exp(1.0 * GU.find_amount_of_person_infected_with(target_infection, persons) * math.log(1 - target_person.receptivity * target_infection.permissibility) )