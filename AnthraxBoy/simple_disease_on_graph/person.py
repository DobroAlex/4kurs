import random
from scipy import stats
from possible_person_states_enum import PossiblePersonState as PossiblePersonState


class person:
    def __init__(self, age: int, sex: chr, receptivity: float, infected_with: dict = dict(),
                 person_state: PossiblePersonState = PossiblePersonState.not_infected):
        self.age = age
        self.sex = sex
        self.receptivity = receptivity
        self.infected_with = infected_with
        self.person_state = person_state

    def __str__(self):
        return "Age : " + str(self.age) + " sex : " + self.sex + " receptivity : " + str(
            self.receptivity) + " infected with : " + str(self.infected_with) + "current state : " + str(self.person_state)

    @staticmethod
    def get_random_age(start: int = 18, end: int = 95) -> int:
        return random.randint(start, end)

    @staticmethod
    def parse_person_from_json(json_object: dict):
        ret_person = person(age=json_object.get("age", None),
                            sex=json_object.get("sex", None),
                            receptivity=json_object.get("receptivity", 0.1 if stats.norm.rvs(loc=0.5,
                                                                                             scale=0.25 ** 0.5) <= 0 else 0.99 if stats.norm.rvs(
                                loc=0.5, scale=0.25 ** 0.5) >= 1 else stats.norm.rvs(loc=0.5, scale=0.25 ** 0.5))
                            )
        if ret_person.infected_with is None:
            ret_person.infected_with = dict()
        return ret_person

    @staticmethod
    def parse_persons_from_place_json(json_object: list) -> list:
        retVal = list()
        for json_str in json_object:
            retVal.append(person.parse_person_from_json(json_str))
