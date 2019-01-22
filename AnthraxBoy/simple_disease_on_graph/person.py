import random
class person:
    def __init__(self, age: int, sex: chr, receptivity: float, infected_with: dict = dict()):
        self.age = age
        self.sex = sex
        self.receptivity = receptivity
        self.infected_with = infected_with

    def __str__(self):
        return "Age : " + str(self.age) + " sex : " + self.sex + " receptivity : " + str(
            self.receptivity) + " infected with : " + str(self.infected_with)

    @staticmethod
    def get_random_age(start: int = 18, end: int = 95) -> int:
        return random.randint(start, end)

    @staticmethod
    def parse_person_from_json(json_object: dict):
        retPerson = person(age=json_object.get("age", None),
                           sex=json_object.get("sex", "None"),
                           receptivity=json_object.get("receptivity", 0.1)
                           )
        if retPerson.receptivity is None:
            retPerson.receptivity == random.uniform(0.1, 1)
        if retPerson.infected_with is None:
            retPerson.infected_with = dict()
        return retPerson

    @staticmethod
    def parse_persons_from_place_json(json_object: list) -> list:
        retVal = list()
        for json_str in json_object:
            retVal.append(person.parse_person_from_json(json_str))
