from enum import Enum
import os
import sys
import json
import possible_states_enum
import person as Person
import random


class place:

    def __init__(self, name: str, number: int, direction: float = 1, population: int = 500, persons: list = None,
                 latitude: float = 100, longitude: float = 100):
        self.name = name
        self.number = number
        self.state = possible_states_enum.possible_state.not_wisited
        self.direction = direction
        self.population = population
        if persons is None:
            self.persons = list()
            for person in range(0, population):
                self.persons.append(Person.person(age=Person.person.get_random_age(),sex=random.choice(['m','f']),receptivity=0.5))

        self.latitude = latitude
        self.longitude = longitude

    @property
    def __str__(self):
        return "Name : " + self.name + " number : " + str(
            self.number) + " state : " + self.state.__str__() + " direction : " + str(
            self.direction) + " population : " + str(self.population)

    @staticmethod
    def parse_place_from_json(json_object: dict):

        retval = place(name=json_object.get("name", "Anonim"),
                     number=json_object.get("number"),  # NB: place should always have number & number of node ==
                                                        # number of place
                     direction=json_object.get("direction", 1.0),
                     population=json_object.get("population", 500),
                     latitude=json_object.get("latitude", 34.100318),
                     longitude=json_object.get("longitude", 44.948237),
                     persons=Person.person.parse_persons_from_place_json(json_object.get("persons", dict())))
        retval.population = len(retval.persons) if len(retval.persons) > retval.population else retval.population
        return  retval

    @staticmethod
    def parse_list_of_places_from_json(path_to_file: str = "resources/places.json") -> list:
        list_of_places = list()
        with open(path_to_file, encoding="utf8") as data_file:
            for json_str in json.load(data_file):
                list_of_places.append(place.parse_place_from_json(json_object=json_str))
        return list_of_places
