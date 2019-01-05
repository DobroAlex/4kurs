import networkx as nx
import PIL
from PIL import ImageDraw, Image, ImageFont 
import infection as Infection
import place
import possible_states_enum as PSE
from matplotlib import pyplot
import urllib
import os
import imageio
import parsing_utils as PU

global_font = ImageFont.load_default()
def initialize_fonts(size:int = 20, path_and_name_to_save:str=""):
    try:
        global_font = ImageFont.truetype(font = path_and_name_to_save, size = size)
        print ("Font changed to " + path_and_name_to_save)
    except:
        print("No suitable font found in " + path_and_name_to_save)
        print("Using default font")
        


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
def graph_show_and_save(G: nx.Graph, name_to_save:str = "unnamed_graph",  path_to_save:str="/output/matplotlib_animated_map/frames/", to_save:bool = True, text:str = "", path_and_name_to_font:str = ""):
    pos = nx.get_node_attributes(G, "pos")
    nx.draw(G, pos,  with_labels = True, node_color = form_nodes_color_map(G), labels = form_nodes_labels(G))
    fullpath = name_to_save
    if path_to_save:
        fullpath = os.path.join(path_to_save, name_to_save)
    if to_save == True:
        pyplot.savefig(fullpath + ".png", format = "PNG", transparent=True)
        image = PIL.Image.open(fullpath + ".png")
        ImageDraw.Draw(image).text((0,0), text, (0,0,0), font=global_font)
        image.save(fullpath + ".png")

    else :
        pyplot.show(block = not to_save)
    
def get_map(G: nx.Graph, path_to_static_map_params:str = "resources/static_map_params.json",with_labels:bool = True, name_to_save:str = "static_map.png", path_to_save:str = None ) -> None:
    URL = "https://static-maps.yandex.ru/1.x/?"
    URL += PU.parse_map_params(path_to_static_map_params)
    if with_labels:
        URL += "&pt="
        for i in G.nodes:
            URL += str(G.nodes[i]['data'].longitude) + "," + str(G.nodes[i]['data'].latitude) + ","
            URL += "pm"
            URL += "gr" if G.nodes[i]['data'].state == PSE.possible_state.not_wisited else "gn" if  G.nodes[i]['data'].state == PSE.possible_state.not_infected else "rd"
            URL += "s"  #mark will be small
            URL += "~"

        URL = URL[:-1]  #deleting last element which will be "~"
    if path_to_save != None :
        name_to_save = os.path.join(path_to_save, name_to_save)
    urllib.request.urlretrieve(URL, name_to_save ) 
def create_animation_from_dir(path_to_files:str="output/animated_map/frames/", name_to_save:str="animated_map.gif", path_to_save:str=None) -> None:
    images = []
    for filename in [f for f in os.listdir(path_to_files) if os.path.isfile(os.path.join(path_to_files, f))]:
        filename = os.path.join(path_to_files, filename)
        images.append(imageio.imread(filename))
        full_path_and_name_to_save = os.path.join(path_to_save, name_to_save) if path_to_save != None else name_to_save
        if os.path.exists(full_path_and_name_to_save):
            os.remove(full_path_and_name_to_save)
        else:
            print("Old {0} at {1} is already deleted, nothing to del".format(name_to_save, full_path_and_name_to_save))
        
        imageio.mimsave(full_path_and_name_to_save, images, duration = 1)
        
