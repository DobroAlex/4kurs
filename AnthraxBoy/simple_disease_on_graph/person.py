import math
import graph_utils as GU
import infection as Infection
import json
import random
class person:
    def __init__(self, age:int, sex:chr, receptivity:float, infected_with:dict = dict() ):
        self.age = age
        self.sex = sex
        self.receptivity = receptivity
        self.infected_with = infected_with
    def calc_infection_probability(target_infection:Infection.infection, target_person,  persons:list()) -> float:
        return 1.0 - math.exp(1.0 * GU.find_amount_of_person_infected_with(target_infection, persons) * math.log(1 - target_person.receptivity * target_infection.permissibility) )
    def get_random_age(start:int=18, end:int = 95) -> int:
        return random.randint(start,end)

    def parse_person_from_json(json_object:dict) :
        return person(age = json_object["age"] if json_object.get("age") else person.get_random_age(),
                        sex = json_object["sex"] if json_object.get("sex") else random.choice(["m", "f"]),
                        receptivity = json_object["receptivity"] if json_object.get("receptivity") else random.uniform(0.1,1),
                        infected_with = None)                   
    def parse_persons_from_place_json(json_object:list) -> list:
        retVal = list()
        for json_str in json_object:
            retVal.append(person.parse_person_from_json(json_str))

