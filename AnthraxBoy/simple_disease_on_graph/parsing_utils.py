import json
def parse_json_filed_if_present(json_obj:dict, field_name:str) :
    return json_obj if json_obj.get(field_name) else None