from enum import Enum
import os 
import sys
import json
import possible_states_enum
import person as Person
import graph_utils as GU
import random
class place:
    
    def __init__(self, name:str, number:int,  direction:float = 1,  population:int = 500, persons:list = None, latitude:float = 100, longitude:float = 100):
        self.name = name
        self.number = number
        self.state = possible_states_enum.possible_state.not_wisited
        self.direction = direction
        self.population = population
        if persons == None:
            self.persons = list()
            for person in range(0, population):
                self.persons.append(Person.person(random.randint(16,65), 'm', 0.5))
        
        self.latitude = latitude
        self.longitude = longitude

    def __str__(self):
        return "Name : " + self.name + " number : " + str(self.number) + " state : " + self.state.__str__() + " direction : " + str(self.direction) + " population : " + str(self.population)

    def parse_list_from_txt_file(path_to_file: str):
        result = list()
        parsed = open("resources/places.txt", "r").read().split('\n')   #TODO: fix empty last element
        del parsed[-1]      #Last element always occurs somehow
        for cur_place in parsed:
            result.append(place(cur_place, parsed.index(cur_place) , possible_states_enum.possible_state.not_wisited))
                
        return result
    def parse_place_from_json(json_object: dict)  : 
        return place(name= json_object.get("name", "Anonim"), 
                    number=json_object.get("number", random.randint(1000,100000)), 
                    direction=json_object.get("direction", 1.0),
                    population=json_object.get("population", 500),
                    latitude=json_object.get("latitude", 34.100318),
                    longitude=json_object.get("longitude", 44.948237),
                    persons = Person.person.parse_persons_from_place_json(json_object.get("persons", dict())))
    def parse_list_of_places_from_json(path_to_file:str = "recources/places.json") -> list:
        list_of_places = list()
        with open(path_to_file, encoding="utf8") as data_file:
            for json_str in  json.load(data_file):
                list_of_places.append(place.parse_place_from_json(json_object=json_str))
        return list_of_places
    