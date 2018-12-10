import json
def parse_json_filed_if_present(json_obj:dict, field_name:str) :
    return json_obj.get(field_name)     if json_obj.get(field_name) else None
def parse_map_params(path_to_file:str = "resources/static_map_params.json") -> str :    #returns a pretty much finished requtset for yandex static API. Does NOT constains https://static-maps.yandex.ru/1.x/?
                                                #must be handled in graph_utils.get_map() 
    with open(path_to_file, encoding="utf8") as json_file:
        json_obj = json.load(json_file)
        separator = "&"
        return   str(
                    "l=" + json_obj.get("map_type", "map") 
                    + separator 
                    + "ll=" + json_obj.get("latitude", "34.100318") + "," + json_obj.get("longitude", "44.948237") 
                    + separator
                    + "spn=" + json_obj.get("spn_lon", "0.05") + "," + json_obj.get("spn_lat", "0.05")
                    + separator
                    + "size=" + json_obj.get("map_size_x", "650") + "," + json_obj.get("map_size_y", "450")
                    + separator
                    + "z=" + json_obj.get("map_scale", "10")
                )
        