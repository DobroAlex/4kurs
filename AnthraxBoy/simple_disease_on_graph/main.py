import networkx as nx   #networkx >=2.3 required
import matplotlib as mpl    #matplotlib as fresh as possible
from matplotlib import pyplot
from  matplotlib.pyplot import show, draw
import sys  #for future parsing of sys.args
import os   #for file handling  #TODO: should be removed from here ans used only in places.py
import place    #class for modeling places which may or may not be infected
import possible_states_enum as PSE  #all possibles condition of places  
import graph_utils as GU    #some basic utils for supporting  drawing & iterating over graph
import random   #for random int and float numbers generation
def do_visit(G: nx.Graph):
    while not GU.is_all_nodes_visited(G):
        node_to_visit = random.randint(0, G.__len__() - 1)
        while True:
            if G.nodes[node_to_visit]['data'].state == PSE.possible_state.not_wisited:
                break
            else:
                node_to_visit = random.randint(0, G.__len__() - 1)
        probability = random.uniform(0 , 1)
        if probability  > 0.5:
            G.nodes[node_to_visit]['data'].state = PSE.possible_state.infected
        else :
            G.nodes[node_to_visit]['data'].state = PSE.possible_state.not_infected

def main():
    G = nx.Graph()  #creates new empty graph

    #TODO: change to JSON
    parsed_places = place.place.parse_list_from_txt_file("resources/places.txt")    #parsing names from file    
    for item in parsed_places:
        G.add_node(parsed_places.index(item), data=item)    #adding places as nodes to graph
    for i  in G.nodes:
        print("node {0}{1} = {2}".format(G.nodes[i]['data'].name, G.nodes[i]['data'].number, G.nodes[i]['data'].state)) #https://stackoverflow.com/questions/18169965/how-to-delete-last-item-in-list
    while not (nx.is_connected(G)): #while each node doesn't have at least one edge
        G.add_edge(random.randint(0, G.__len__()-1), random.randint(0, G.__len__()-1))  #adding random edge
    do_visit(G) #see do_visit()
    nx.draw(G, with_labels = True, node_color = GU.form_nodes_color_map(G), labels = GU.form_nodes_labels(G))                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    pyplot.show() 
if __name__ == "__main__":
    main()