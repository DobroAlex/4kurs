from enum import Enum
import os 
import sys
import json
import possible_states_enum
class place:
    
    def __init__(self, name:str, number:int,  direction:float = 1,  receptivity:float = 0.5, population:int = 500, latitude:float = 100, longitude:float = 100):
        self.name = name
        self.number = number
        self.state = possible_states_enum.possible_state.not_wisited
        self.direction = direction
        self.receptivity = receptivity
        self.population = population
         
    def parse_list_from_txt_file(path_to_file: str):
        result = list()
        parsed = open("resources/places.txt", "r").read().split('\n')   #TODO: fix empty last element
        del parsed[-1]      #Last element always occurs somehow
        for cur_place in parsed:
            result.append(place(cur_place, parsed.index(cur_place) , possible_states_enum.possible_state.not_wisited))
                
        return result
    def parse_place_from_json(json_object: dict)  : 
        return place(name = json_object["name"], 
                    number=json_object["number"], 
                    direction=json_object["direction"],
                    receptivity=json_object["receptivity"],
                    population=json_object["population"],
                    latitude=json_object["latitude"],
                    longitude=json_object["longitude"])
    def parse_list_of_places_from_json(path_to_file:str = "recources/places.json") -> list:
        list_of_places = list()
        with open(path_to_file) as data_file:
            for json_str in  json.load(data_file):
                list_of_places.append(place.parse_place_from_json(json_object=json_str))
        return list_of_places
