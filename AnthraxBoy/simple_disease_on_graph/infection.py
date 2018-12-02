import place as Place
class infection:
    def __init__(self, name:str, permissibility: float):
        self.name = name
        self.permissibility = permissibility

    def find_amount_of_people_infected_with(self, persons: list()) -> int:
        retVal = 0
        for target_person in persons:
            if self in target_person.infected_with:
                retVal += 1
        return retVal