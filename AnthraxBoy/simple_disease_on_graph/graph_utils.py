import networkx as nx
import place
import possible_states_enum as PSE
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
def form_nodes_labels(G:nx.Graph):
    labels = dict()
    for i in G.nodes:
        labels[i] = G.nodes[i]['data'].name #TODO:fix
    return labels