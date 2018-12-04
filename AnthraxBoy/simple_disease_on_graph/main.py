import networkx as nx   #networkx >=2.3 required
import matplotlib as mpl    #matplotlib as fresh as possible
from matplotlib import pyplot
from  matplotlib.pyplot import show, draw
import math
import sys  #for future parsing of sys.args
import os   #for file handling  #TODO: should be removed from here ans used only in places.py
import place    #class for modeling places which may or may not be infected
import possible_states_enum as PSE  #all possibles condition of places  
import graph_utils as GU    #some basic utils for supporting  drawing & iterating over graph
import random   #for random int and float numbers generation
import json     #for JSON parse/dump
import infection as Infection
import person as Person
def do_visit(G: nx.Graph, agent:Person.person, is_node_visited_only_once: bool = False, start_node: int = -1) -> None: 
    if start_node == -1:
        current_node  = random.randint(0, G.__len__() - 1)
    else :
        current_node = start_node
    is_firts_visit = True
    while not GU.is_all_nodes_visited(G):
        if is_firts_visit == True:
            node_to_visit = current_node
            is_firts_visit = False
        else:
            node_to_visit = random.randint(0, random.choice(GU.find_all_connected_nodes(G, current_node)))
        if is_node_visited_only_once == True:
            while True:
                if G.nodes[node_to_visit]['data'].state == PSE.possible_state.not_wisited:
                    break
                else:
                    node_to_visit = random.randint(0, G.__len__() - 1)
        else:
            node_to_visit = random.randint(0, G.__len__() - 1)
        for target_person in  G.nodes[node_to_visit]['data'].persons:  
            #calculating probability that agent will infect persons 
            for disease_of_agent , disease_of_agent_permissibility in agent.infected_with.items():  #iterating over dict's items https://stackoverflow.com/questions/5466618/too-many-values-to-unpack-iterating-over-a-dict-key-string-value-list    
                #probability = 1.0 - math.exp(G.nodes[node_to_visit]['data'].direction 
                #    * GU.find_amount_of_person_infected_with(disease_of_agent, G.nodes[node_to_visit]['data'].persons ) 
                #    *  math.log(1-target_person.receptivity*agent.infected_with[disease_of_agent]))       
                probability =  Person.person.calc_infection_probability(Infection.infection(disease_of_agent,disease_of_agent_permissibility), target_person, G.nodes[node_to_visit]['data'].persons) #TODO : TEST THIS LINE   
                if probability >= 0.5:
                    target_person.infected_with[disease_of_agent] = agent.infected_with[disease_of_agent]
                    G.nodes[node_to_visit]['data'].state == PSE.possible_state.infected
                else:
                    if G.nodes[node_to_visit]['data'].state !=  PSE.possible_state.infected :
                        G.nodes[node_to_visit]['data'].state = PSE.possible_state.not_infected
            for disease_of_person, disease_of_person_permissibility in target_person.infected_with.items() :   #calculating probability that person will infect agent with something new
                if disease_of_person in agent.infected_with:
                    break
                probability = Person.person.calc_infection_probability(Infection.infection(disease_of_person, disease_of_person_permissibility), agent, G.nodes[node_to_visit]['data'].persons   )
                if probability >= 0.5:
                    agent.infected_with[disease_of_person] =  disease_of_person_permissibility   
        current_node = node_to_visit

def main():
    agent = Person.person(age=35, sex = 'm', receptivity=0.5, infected_with = {'Ветрянка': 0.1, 'ОРВИ': 0.5, 'СПИДОРАК': 0.8}  )
    G = nx.Graph()  #creates new empty graph
    parsed_places = place.place.parse_list_of_places_from_json(path_to_file="resources/places.json")
    for item in parsed_places:
        G.add_node(parsed_places.index(item), data=item, pos = (item.latitude, item.longitude ))    #adding places as nodes to graph
    for i  in G.nodes:
        print("node {0}{1} = {2}".format(G.nodes[i]['data'].name, G.nodes[i]['data'].number, G.nodes[i]['data'].state)) #https://stackoverflow.com/questions/18169965/how-to-delete-last-item-in-list
    while not (nx.is_connected(G)): #while each node doesn't have at least one edge
        G.add_edge(random.randint(0, G.__len__()-1), random.randint(0, G.__len__()-1))  #adding random edge
    GU.graph_show_and_save(G, "graph", to_save=False)
    do_visit(G, agent = agent,  start_node = 0, is_node_visited_only_once=False) #see do_visit()
    GU.graph_show_and_save(G, "infected_graph", to_save = False)


if __name__ == "__main__":
    main()