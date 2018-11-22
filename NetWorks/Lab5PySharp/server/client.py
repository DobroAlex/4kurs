import socket
import multiprocessing
import sys
import server
import asyncio
class client: 
    def __init__(self, server_socket: socket.socket, num: int):
        self.server_socket = server_socket
        self.num = num
        #server.GLOBAL_NUM_OF_CLIENTS+=1     #Bad tone in OOP, edit
        self.BUF_SIZE = server.BUFF_SIZE
    async def establish_connection(self):
        self.client_socket, self.addr = self.server_socket.accept()
        print("#{0}:Connected to {1}".format(self.num, self.client_socket.getsockname()))
    async def routine(self):
        await self.establish_connection()
        while True:
            data = self.client_socket.recv(self.BUF_SIZE)
            if not data:
                break
            data = data.decode("utf8")
            print("{0}:recived data : {1}".format(self.num, data))
            answer = "We will now search for {0} in news".format(data)
            self.client_socket.sendall(answer.encode("utf8"))
        self.client_socket.close()