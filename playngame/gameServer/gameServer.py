import scipy.io.wavfile
import numpy
import socket
import speech_recognition as sr

# 접속할 서버 주소입니다. 여기에서는 루프백(loopback) 인터페이스 주소 즉 localhost를 사용합니다.
HOST = '192.168.101.153'

# 클라이언트 접속을 대기하는 포트 번호입니다.
PORT = 3030
url = ""


def server():
    # 소켓 객체를 생성합니다.
    # 주소 체계(address family)로 IPv4, 소켓 타입으로 TCP 사용합니다.
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # 포트 사용중이라 연결할 수 없다는
    # WinError 10048 에러 해결를 위해 필요합니다.
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    # bind 함수는 소켓을 특정 네트워크 인터페이스와 포트 번호에 연결하는데 사용됩니다.
    # HOST는 hostname, ip address, 빈 문자열 ""이 될 수 있습니다.
    # 빈 문자열이면 모든 네트워크 인터페이스로부터의 접속을 허용합니다.
    # PORT는 1-65535 사이의 숫자를 사용할 수 있습니다.
    server_socket.bind((HOST, PORT))

    # 서버가 클라이언트의 접속을 허용하도록 합니다.
    server_socket.listen()

    # accept 함수에서 대기하다가 클라이언트가 접속하면 새로운 소켓을 리턴합니다.
    client_socket, addr = server_socket.accept()

    # 접속한 클라이언트의 주소입니다.
    print('Connected by', addr)

    dd = bytearray(0)

    # 무한루프를 돌면서
    while True:

        try:
            # 클라이언트가 보낸 메시지를 수신하기 위해 대기합니다.
            data = client_socket.recv(1024)

            # 수신받은 문자열을 출력합니다.
            print('Received from', addr, data)
            if data == b'':
                print("done")
                break

            ar = bytearray(data)
            dd = dd + ar
            print(dd)

            # client_socket.sendall(data)

            print("end")


        except:
            print("ssssss")

            break

    b = numpy.array(dd, dtype=numpy.int16)

    scipy.io.wavfile.write(r"C:\Users\ojh21\PycharmProjects\jjp\datarecord.wav", 16000, b)

    stt()

    client_socket.close()
    server_socket.close()
    server()

    # 받은 문자열을 다시 클라이언트로 전송해줍니다.(에코)


# 소켓을 닫습니다.

def stt():
    with sr.AudioFile('C:/Users/ojh21/PycharmProjects/jjp/datarecord.wav') as source:
        audio_text = sr.listen(source)

        # recoginize_() method will throw a request error if the API is unreachable, hence using exception handling
        try:

            # using google speech recognition
            text = sr.recognize_google(audio_text)
            print('Converting audio transcripts into text ...')
            print(text)

        except:
            print('Sorry.. run again...')


server()