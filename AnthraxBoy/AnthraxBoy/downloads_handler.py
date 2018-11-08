import os
def clear_previous_map(path_to_clear:str):      #https://www.w3schools.com/python/python_file_remove.asp
    if os.path.exists(path_to_clear):
        os.remove(path_to_clear)

