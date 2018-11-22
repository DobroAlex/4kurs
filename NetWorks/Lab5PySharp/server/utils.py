import socket
def find_free_socket(address:str = "localhost", port_to_start_with:int = 6868 ) -> int :
    free_port = port_to_start_with
    try:
        testing_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        testing_address = (address, free_port)
        testing_server.bind(testing_address)
        testing_server.close()
        return free_port
    except Exception :
        free_port += 1
        return find_free_socket(address, free_port)
