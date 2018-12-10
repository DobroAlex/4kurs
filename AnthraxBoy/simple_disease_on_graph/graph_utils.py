import networkx as nx
import infection as Infection
import place
import possible_states_enum as PSE
from matplotlib import pyplot
import urllib
import parsing_utils as PU

def find_amount_of_person_infected_with(target_infection:Infection.infection , persons:list()) -> int:
    retVal = 0
    for target_person in persons:
        if target_infection in target_person.infected_with:
            retVal += 1
    return retVal

def is_all_nodes_visited(G: nx.Graph):
    for i in G.nodes:
        if G.nodes[i]['data'].state == PSE.possible_state.not_wisited :
            return False
    return True
def find_all_connected_nodes(G : nx.Graph, i:int):
    list_of_connected_nodes = list()
    for j in range(i, len(G.nodes)):
        if j in G:
            list_of_connected_nodes.append(j)
    return list_of_connected_nodes
def form_nodes_color_map(G:nx.Graph):
    color_map = list()
    for i in G.nodes:
        if G.nodes[i]['data'].state == PSE.possible_state.not_wisited:
            color_map.append('grey')
        elif G.nodes[i]['data'].state == PSE.possible_state.not_infected:
            color_map.append('green')
        else:
            color_map.append('red')
    return color_map
def form_nodes_labels(G:nx.Graph) -> None:
    labels = dict()
    for i in G.nodes:
        labels[i] = G.nodes[i]['data'].name + "\nPopulation= " +  str(G.nodes[i]['data'].population) 
    return labels
def graph_show_and_save(G: nx.Graph, name_to_save:str = "unnamed_graph", to_save:bool = True):
    pos = nx.get_node_attributes(G, "pos")
    nx.draw(G, pos,  with_labels = True, node_color = form_nodes_color_map(G), labels = form_nodes_labels(G))
    pyplot.show(block =  not to_save)
    if to_save == True:
        pyplot.savefig(name_to_save + ".png", transparent=True)
def get_map(G: nx.Graph, path_to_static_map_params:str = "resources/static_map_params.json", name_to_save:str = "static_map.png" ) -> None:
    urllib.request.urlretrieve("https://static-maps.yandex.ru/1.x/?" + PU.parse_map_params(path_to_static_map_params), name_to_save)