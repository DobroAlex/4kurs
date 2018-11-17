from enum import Enum
import os 
import sys
import possible_states_enum
class place:
    
    def __init__(self, name:str, number:int, state:possible_states_enum.possible_state, receptivity:float = 0.5):
        self.name = name
        self.number = number
        self.state = state
        self.receptivity = receptivity
    #def __hash__():
    #    return hash(repr(self))
    def parse_list_from_txt_file(path_to_file: str):
        result = list()
        parsed = open("resources/places.txt", "r").read().split('\n')   #TODO: fix empty last element
        del parsed[-1]      #Last element always occurs somehow
        for cur_place in parsed:
            result.append(place(cur_place, parsed.index(cur_place) , possible_states_enum.possible_state.not_wisited))
                
        return result