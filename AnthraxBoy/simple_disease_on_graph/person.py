import math
import graph_utils as GU
import infection as Infection
import json
import random
import parsing_utils as PU
class person:
    def __init__(self, age:int, sex:chr, receptivity:float, infected_with:dict = dict() ):
        self.age = age
        self.sex = sex
        self.receptivity = receptivity
        self.infected_with = infected_with
    def __str__(self):
        return "Age : " + str(self.age) + " sex : " + self.sex + " receptivity : " + str(self.receptivity) + " infected with : " + str(self.infected_with)
    def calc_infection_probability(target_infection:Infection.infection, target_person,  persons:list()) -> float:
        amount_of_infected_with_similar_infection = GU.find_amount_of_person_infected_with(target_infection, persons) if GU.find_amount_of_person_infected_with(target_infection, persons) != 0 else 1    
        return 1.0 - math.exp(1.0 * amount_of_infected_with_similar_infection * math.log(1 - target_person.receptivity * target_infection.permissibility))
    def get_random_age(start:int=18, end:int = 95) -> int:
        return random.randint(start,end)

    def parse_person_from_json(json_object:dict) :
        retPerson =  person(age = json_object.get("age", None),
                        sex = json_object.get("sex", "None"),
                        receptivity = json_object.get("receptivity", 0.1)
                        )                   
        if retPerson.receptivity == None:
            retPerson.receptivity == random.uniform(0.1,1)
        if retPerson.infected_with == None:
            retPerson.infected_with = dict()
        return retPerson
    def parse_persons_from_place_json(json_object:list) -> list:
        retVal = list()
        for json_str in json_object:
            retVal.append(person.parse_person_from_json(json_str))

