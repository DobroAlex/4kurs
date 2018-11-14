#Тестовая слав-строка
import networkx as nx   #networkx >=2.3 required
import matplotlib as mpl
from matplotlib import pyplot
from  matplotlib.pyplot import show, draw
import sys
import os
import place
import possible_states_enum as PSE
import graph_utils as GU
import random
def main():
    G = nx.Graph()  #creates new empty graph
    parsed_places = place.place.parse_list_from_txt_file("resources/places.txt")
    #parsed_nodes = open("resources/places.txt", "r").read().split('\n')
    #for line in open("resources/places.txt", "r"):
    #    parsed_nodes.append(line)
    for item in parsed_places:
        G.add_node(parsed_places.index(item), data=item)
    for i  in G.nodes:
        print("node {0}{1} = {2}".format(G.nodes[i]['data'].name, G.nodes[i]['data'].number, G.nodes[i]['data'].state)) #https://stackoverflow.com/questions/18169965/how-to-delete-last-item-in-list
    
    while not (nx.is_connected(G)):
        G.add_edge(random.randint(0, G.__len__()-1), random.randint(0, G.__len__()-1))
    
    nx.draw(G, with_labels = True, node_color = GU.form_nodes_color_map(G))
    pyplot.show() 
if __name__ == "__main__":
    main()