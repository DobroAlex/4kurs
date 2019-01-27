import networkx as nx
import PIL
from PIL import ImageDraw, Image, ImageFont, ImageOps
import infection as Infection
import math
import place
import possible_states_enum as PSE
from matplotlib import pyplot
import urllib
import os
import imageio
import parsing_utils as PU
import string
import agent

global_font: ImageFont = PIL.ImageFont.load_default()  # setting up global variable for font storing. Probably not best practice in
# terms of OOP but simple solution
global_path: str = ""  # path to global_font dir will be stored here


def initialize_fonts(size: int = 20, path_and_name_to_font: str = "") -> None:
    """
    setting non-default font if needed. If you don't need to use non-default font you  may skip this part
    If you need to set non-default font this function must be called before calling graph_show_and_save

    Parameters
    ----------
    size : int
        Size of font in pixels. Default is 20
    path_and_name_to_font : int
        Full path to file with name of one and extension. May be full (ex  "C:\stuff\fonts\my_font.ttf") or relative (ex "source/font/test.ttf")

    Returns
    -------
    None
        Setting up global_font if correct path is given, else loading default font  
    """
    try:
        global global_font
        global_font = ImageFont.truetype(font=path_and_name_to_font, size=size)
        global global_path
        global_path = path_and_name_to_font
        print("Font changed to " + path_and_name_to_font)
    except:
        print("No suitable font found in " + path_and_name_to_font)
        print("Using default font")


def find_amount_of_person_infected_with(target_infection: Infection.infection, persons: list) -> int:
    """
    Searches for all person infected with target infection in persons list

    Parameters
    ----------
    target_infection : Infection.infection
        Target infection which will be searched in list of persons
    persons : list
        List of person to work with

    Returns
    -------
        int : amount of persons infected with target_infection in list of persons
    """
    retVal = 0
    for target_person in persons:
        if target_infection in target_person.infected_with:
            retVal += 1
    return retVal


def is_all_nodes_visited(G: nx.Graph) -> bool:
    """
    Detect  if all nodes in graph satisfies  condition [some_node]['data'].state != possible_states_enum.possible_state.not_wisited
    
    Parameters
    ----------
    G : nx.Graph
        Target graph  for inspection

     Returns
    -------
        bool : True if all nodes satisfies  conidition, False else

    """
    for i in G.nodes:
        if G.nodes[i]['data'].state == PSE.possible_state.not_wisited:
            return False
    return True


def form_nodes_color_map(G: nx.Graph):
    color_map = list()
    for i in G.nodes:
        if G.nodes[i]['data'].state == PSE.possible_state.not_wisited:
            color_map.append('grey')
        elif G.nodes[i]['data'].state == PSE.possible_state.not_infected:
            color_map.append('green')
        else:
            color_map.append('red')
    return color_map


def form_nodes_labels(G: nx.Graph) -> None:
    labels = dict()
    for i in G.nodes:
        labels[i] = G.nodes[i]['data'].name + "\nPopulation= " + str(G.nodes[i]['data'].population)
    return labels


def find_next_node_in_ascending_order(G: nx.Graph, current_node_index: int) -> int:
    try:
        is_current_node_exists = False
        for i in G.nodes:
            if G.nodes[i]['data'].number == current_node_index:
                is_current_node_exists = True
                break
        if not is_current_node_exists:
            raise ValueError("No node with given number ({0}) exists in graph".format(current_node_index))
        for i in G.nodes:
            if G.nodes[i]['data'].number > current_node_index:
                return i
        return None
    except ValueError as ValErr:
        print(ValErr)


def unify_images_size(path_to_images: str, file_name_start: str, file_name_extension: str = ".png") -> None:
    images = []
    for filename in os.listdir(path_to_images):
        if filename.startswith(file_name_start) and filename.endswith(file_name_extension):
            images.append(filename)
    max_width = -1
    max_height = -1
    for image in images:
        full_path = os.path.join(path_to_images + image)
        tmp = Image.open(full_path)
        max_width = tmp.size[0] if tmp.size[0] > max_width else max_width
        max_height = tmp.size[1] if tmp.size[1] > max_height else max_height
    for image in images:
        full_path = os.path.join(path_to_images + image)
        tmp = Image.open(full_path)
        tmp = ImageOps.fit(tmp, (max_width, max_height), method=Image.BICUBIC)
        tmp.save(full_path)


def graph_show_and_save(G: nx.Graph, name_to_save: str = "unnamed_graph",
                        path_to_save: str = "/output/matplotlib_animated_map/frames/", to_save: bool = True,
                        text: str = None):
    pos = nx.get_node_attributes(G, "pos")
    nx.draw(G, pos, with_labels=True, node_color=form_nodes_color_map(G), labels=form_nodes_labels(G))
    fullpath = name_to_save
    if path_to_save:
        fullpath = os.path.join(path_to_save, name_to_save)
    if to_save == True:
        global global_font
        global global_path
        pyplot.savefig(fullpath + ".png", format="PNG", transparent=False)
        if text is not None:
            image = PIL.Image.open(fullpath + ".png")
            font = global_font
            if font.getsize(text)[0] > image.size[0]:
                #print("Text is too width, resizing. Current width {0}, height {1}".format(image.size[0], image.size[1]))
                resize_factor = font.getsize(text)[0] / image.size[0]
                image = PIL.ImageOps.fit(image,
                                         (int(image.size[0] * resize_factor), int(image.size[1] * resize_factor)),
                                         method=Image.BICUBIC)
            ImageDraw.Draw(image).text((0, 0), text, (0, 0, 0), font=font)
            image.save(fullpath + ".png", "PNG")  # Saving as actual .png,
            # not just "name.png" with original extensions
            # https://stackoverflow.com/questions/19651055/saving-image-with-pil
        else:
            print("Nothing to print on image  {0}, text is {1}".format(fullpath, text))

    else:
        pyplot.show(block=not to_save)


def get_map(G: nx.Graph, agent: agent, path_to_static_map_params: str = "resources/static_map_params.json",
            with_labels: bool = True,
            name_to_save: str = "static_map.png", path_to_save: str = None) -> None:
    #path_to_save = os.path.join(path_to_save, "frames/")
    URL = "https://static-maps.yandex.ru/1.x/?"
    URL += PU.parse_map_params(path_to_static_map_params)
    if with_labels:
        URL += "&pt="
        for i in G.nodes:
            URL += str(G.nodes[i]['data'].longitude) + "," + str(G.nodes[i]['data'].latitude) + ","
            URL += "pm"
            URL += "gr" if G.nodes[i]['data'].state == PSE.possible_state.not_wisited else "gn" if G.nodes[i][
                                                                                                       'data'].state == PSE.possible_state.not_infected else "rd"
            URL += "s"  # mark will be small
            URL += str(G.nodes[i]['data'].number + 1)  # adding node number + 1 to map
            URL += "~"
        URL = URL[:-1]  # deleting last element which will be "~"
        if len(agent.visited_nodes) > 1:
            URL += "&pl="
            URL += "c:A800A8FF,"
            URL += "w:5,"
            for node in agent.visited_nodes:
                URL += str(G.nodes[node]['data'].longitude) + "," + str(G.nodes[node]['data'].latitude) + ","
            URL = URL[:-1]
    if path_to_save is not None:
        name_to_save = os.path.join(path_to_save, name_to_save)
    else:
        name_to_save = os.path.join(os.getcwd(), "/yandex_frames")
    try:
        urllib.request.urlretrieve(URL, name_to_save)
    except urllib.error.HTTPError as BadRequest:
        raise RuntimeError(
            "Yandex static API can't proceed your request. That means your either reached your daily limit which may "
            "happen if you used API too often today OR address {0} is incorrect.Contact author at "
            "github.com/dobroalex if you met this problem and provide address printed before".format(
                URL))
    except urllib.error.URLError as URLError:
        raise RuntimeError("Something went horribly wrong while trying to access {0}".format(URL))


def create_animation_from_dir(path_to_files: str, path_to_save: str,
                              name_to_save: str) -> None:
    images = []
    for filename in [f for f in os.listdir(path_to_files) if os.path.isfile(os.path.join(path_to_files, f))]:
        filename = os.path.join(path_to_files, filename)
        images.append(imageio.imread(filename))
        full_path_and_name_to_save = os.path.join(path_to_save, name_to_save) if path_to_save is not None else name_to_save
        if os.path.exists(full_path_and_name_to_save):
            os.remove(full_path_and_name_to_save)
        else:
            print("Old {0} at {1} is already deleted, nothing to del".format(name_to_save, full_path_and_name_to_save))

        imageio.mimsave(full_path_and_name_to_save, images, duration=1)


def calc_infection_probability(target_infection: Infection.infection, target_person, persons: list) -> float:
    amount_of_infected_with_similar_infection = find_amount_of_person_infected_with(target_infection,
                                                                                    persons) if find_amount_of_person_infected_with(
        target_infection=target_infection, persons=persons) != 0 else 1
    return 1.0 - math.exp(1.0 * amount_of_infected_with_similar_infection * math.log(
        1 - target_person.receptivity * target_infection.permissibility))
