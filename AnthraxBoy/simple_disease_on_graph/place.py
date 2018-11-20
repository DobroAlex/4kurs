from enum import Enum
import os 
import sys
import possible_states_enum
import infection as infec
class place:
    
    def __init__(self, name:str, number:int, state:possible_states_enum.possible_state, direction:float = 1,  receptivity:float = 0.5, population:int = 500, infections:list = infec.list_of_infections, ):
        self.name = name
        self.number = number
        self.state = state
        self.direction = direction
        self.receptivity = receptivity
        self.population = population
        self.infections = infections

    def parse_list_from_txt_file(path_to_file: str):
        result = list()
        parsed = open("resources/places.txt", "r").read().split('\n')   #TODO: fix empty last element
        del parsed[-1]      #Last element always occurs somehow
        for cur_place in parsed:
            result.append(place(cur_place, parsed.index(cur_place) , possible_states_enum.possible_state.not_wisited))
                
        return result