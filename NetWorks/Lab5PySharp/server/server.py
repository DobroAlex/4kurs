import socket
import asyncio
import sys
import client
import utils
GLOBAL_NUM_OF_CLIENTS = 0
BUFF_SIZE = 1048576     #1 Mebibyte, 2^20

        
def main():
    global GLOBAL_NUM_OF_CLIENTS
    SERVER_ADDRESS = ('localhost', utils.find_free_socket('localhost', 6868))
    serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serv.bind(SERVER_ADDRESS)
    serv.listen(100)
    print("Server have started at {0}".format(SERVER_ADDRESS))
    tasks = list()
    while True :
        ioloop = asyncio.get_event_loop()
        tasks.append(ioloop.create_task(client.client(serv, GLOBAL_NUM_OF_CLIENTS).routine() ))
        GLOBAL_NUM_OF_CLIENTS += 1
        wait_tasks = asyncio.wait(tasks)
        ioloop.run_until_complete(wait_tasks)
        #client_sock, addr = serv.accept()
        #print("Connected to {0}".format(addr))
        #while True:
        #    data = client_sock.recv(BUFF_SIZE)
        #    data = data.decode("utf8")
        #    if not  data.isdigit():
        #        print("{0}: recived not-natural input : {1}".format(client_sock.getsockname(), data))
        #        client_sock.sendall(("Bad input at {0}".format(data)).encode("utf8"))
        #    else:
        #        print("Is {0} prime?{1}\tSending answer to {2}".format(data, CU.is_prime(int(data)), client_sock.getsockname()))
        #        client_sock.sendall("{0}:Prime = {1}".format(data, CU.is_prime(int(data))).encode("utf8"))
        #    if not data:
        #        print("{0}  has disconnected".format(client_sock.getsockname() ))
        #        client_sock.close()
        #        break
            #print("Data recived from {0}:{1}".format(addr, data.decode("utf8")))
    serv.close()
    ioloop.close() 



if __name__ == "__main__":
    main()