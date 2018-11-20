using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
namespace ConsoleClient
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            Console.Write("Input address:");
            IPAddress address = IPAddress.Parse(Console.ReadLine());
            Console.Write("Input port");
            int port = int.Parse(Console.ReadLine());
            TcpClient client = null;
            try
            {
                client = new TcpClient(address.ToString(), port);
                NetworkStream stream = client.GetStream();
                Console.WriteLine("Client is alive and conndected to {0} {1}", client.ToString(), stream.ToString());
                while (true)
                {
                    string message = Console.ReadLine();
                    byte[] dataToSend = Encoding.UTF8.GetBytes(message);
                    stream.Write(dataToSend,0, dataToSend.Length);

                    byte[] dataResponse = new byte[256];
                    StringBuilder responseBuilder = new StringBuilder();
                    do
                    {
                        int bytes = stream.Read(dataResponse, 0, dataResponse.Length);
                        responseBuilder.Append(Encoding.UTF8.GetString(dataResponse, 0, bytes));
                    }
                    while (stream.DataAvailable);
                    Console.WriteLine("Recived answer from {0} : {1}", address, responseBuilder);

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message + "\nAborting");
            }
        }
    }
}
