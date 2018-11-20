import socket
import multiprocessing
import sys
import calc_utils as CU
GLOBAL_NUM_OF_CLIENTS = 0
BUFF_SIZE = 1048576     #1 Mebibyte, 2^20
def work_routine(client_socket: socket.socket, address):
    global GLOBAL_NUM_OF_CLIENTS
    CLIENT_ID = str(GLOBAL_NUM_OF_CLIENTS) + str(client_socket.getsockname())
    GLOBAL_NUM_OF_CLIENTS += 1
    print("{1} : Connected to {0}".format(CLIENT_ID, client_socket.getsockname()))
    while True:
        data = client_socket.recv(BUFF_SIZE)
        data = data.decode("utf8")
        print("Recived {0}".format(data))
        if not data:
            print("{0}  has disconnected".format(client_socket.getsockname()))
            client_socket.close()
            break
        client_socket.sendall(("We will now search for {0}  in news".format(data)).encode("utf8"))
    client_socket.close()
        
def main():
    SERVER_ADDRESS = ('localhost', 6868)
    serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serv.bind(SERVER_ADDRESS)
    serv.listen(100)
    print("Server have started at {0}".format(SERVER_ADDRESS))
    while True :
        print("Waiting for clients")
        client_sock, addr = serv.accept()
        serv.setblocking(0)        
        P = multiprocessing.Process(target=work_routine(client_sock, addr))
        P.start()
        P.join()
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
    



if __name__ == "__main__":
    main()