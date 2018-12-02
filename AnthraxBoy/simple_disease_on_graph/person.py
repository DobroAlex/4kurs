import infection as Infection
class person:
    def __init__(self, age:int, sex:chr, receptivity:float, infected_with:dict = dict() ):
        self.age = age
        self.sex = sex
        self.receptivity = receptivity
        self.infected_with = infected_with