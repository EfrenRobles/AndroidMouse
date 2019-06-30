#include "stdafx.h"
#include "Globals.h"

INPUT    Input = {0};													// Create our input.

#pragma region ------------------------ export dll ------------------------
EXPORT_DLL bool startApp(void) {
	return Globals::getInstance()->startApp();
}

EXPORT_DLL bool getConeccionStatus(void) {
	return Globals::getInstance()->getConeccionStatus();
}

EXPORT_DLL int getMonitorResolution(short *buffer) {
	ivector2 result = Globals::getInstance()->getMonitorResolution();
	buffer[0] = result.X;
	buffer[1] = result.Y;
	return 0;
}

EXPORT_DLL int getAndroidResolution(short *buffer) {
	ivector2 result = Globals::getInstance()->getAndroidResolution();
	buffer[0] = result.X;
	buffer[1] = result.Y;
	return 0;
}

EXPORT_DLL int getOffsetResolution(short *buffer) {
	Globals::getInstance()->setOffsetResolution(buffer[0], buffer[1]);
	return 0;
}
#pragma endregion

#pragma region ------------------------ single instance ------------------------
Globals *Globals::s_instance = NULL;

Globals *Globals::getInstance(void) {
	if (!s_instance) {
		s_instance = new Globals();
		s_instance->iResult = SOCKET_ERROR;
		s_instance->id_Status = ID_INIT;
		s_instance->onClick = false;
	}
	return s_instance;
}

void Globals::DestroyInstance(void) {
	if (s_instance) {
		delete[] s_instance;
		s_instance = NULL;
	}
}
#pragma endregion

#pragma region ------------------------ windows events ------------------------
ivector2 Globals::getMonitorResolution(void) {
	RECT desktop;
	ivector2 result = ivector2();
	const HWND hDesktop = GetDesktopWindow();
	GetWindowRect(hDesktop, &desktop);
	result.X = (short)desktop.right;
	result.Y = (short)desktop.bottom;
	return result;
}

ivector2 Globals::getAndroidResolution(void) {
	ivector2 result = ivector2();
	return result;
}

void Globals::setOffsetResolution(short X, short Y) {
	oRes.X = X;
	oRes.Y = Y;
}

bool Globals::getConeccionStatus(void) {
	if (iResult != 0) {
		return false;
	} 
	return true;
}
#pragma endregion

#pragma region ------------------------ socket events ------------------------

bool Globals::startApp(void) {
	//probablement meter esto en un hilo.

	while (true) {
		id_Status = onInit();

		if (id_Status == ID_ISERVER) {
			id_Status = ipServer();
		}

		if (id_Status == ID_CSOCKET) {
			id_Status = creatingSocket();
		}

		if (id_Status == ID_ISOCKET) {
			id_Status = initSocket();
		}

		if (id_Status == ID_LSOCKET) {
			id_Status = listenSocket();
		}

		if (id_Status == ID_ACONN) {
			id_Status = acceptingConnection();
		}

		if (id_Status == ID_RDATA) {
			id_Status = receiveData();
		}

		if (id_Status == ID_CCONN) {
			id_Status = closeConnection();
		}

		id_Status = anyFail();
	}

	return true;
}

GSTATES Globals::onInit(void) {

	oRes = ivector2();

	lSocket = INVALID_SOCKET;
	cSocket = INVALID_SOCKET;
	struct addrinfo *_result = NULL;
	result = _result;
	recvbuflen = DEFAULT_BUFLEN;

	// Initialize Winsock
	iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
	if (iResult != 0) {
		printf("WSAStartup failed with error: %d\n", iResult);
		return ID_FAIL;
	}
	return ID_ISERVER;
}

GSTATES Globals::ipServer(void) {
	ZeroMemory(&hints, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_protocol = IPPROTO_TCP;
	hints.ai_flags = AI_PASSIVE;

	// Resolve the server address and port
	iResult = getaddrinfo(NULL, DEFAULT_PORT, &hints, &result);
	if (iResult != 0) {
		printf("getaddrinfo failed with error: %d\n", iResult);
		//WSACleanup();
		return ID_FAIL;
	}
	return ID_CSOCKET;
}

GSTATES Globals::creatingSocket(void) {
	lSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
	if (lSocket == INVALID_SOCKET) {
		printf("socket failed with error: %ld\n", WSAGetLastError());
		return ID_FAIL;
	}
	return ID_ISOCKET;
}

GSTATES Globals::initSocket(void) {
	iResult = bind(lSocket, result->ai_addr, (int)result->ai_addrlen);
	if (iResult == SOCKET_ERROR) {
		printf("bind failed with error: %d\n", WSAGetLastError());
		freeaddrinfo(result);
		return ID_FAIL;
	}

	freeaddrinfo(result);
	return ID_LSOCKET;
}


GSTATES Globals::listenSocket(void) {
	iResult = listen(lSocket, SOMAXCONN);
	if (iResult == SOCKET_ERROR) {
		printf("listen failed with error: %d\n", WSAGetLastError());
		return ID_FAIL;
	}
	return ID_ACONN;
}

GSTATES Globals::acceptingConnection(void) {
	cSocket = accept(lSocket, NULL, NULL);
	if (cSocket == INVALID_SOCKET) {
		printf("accept failed with error: %d\n", WSAGetLastError());
		return ID_FAIL;
	}

	// No longer need server socket
	closesocket(lSocket);
	return ID_RDATA;
}

GSTATES Globals::receiveData(void) {
	do {
		iResult = recv(cSocket, recvbuf, recvbuflen, 0);
		if (iResult > 0) {
			printf("Bytes received: %d\n", iResult);
			//printf("Data received: %s\n", recvbuf);
			setMousePos(recvbuf);
			// Echo the buffer back to the sender
			iSendResult = send(cSocket, recvbuf, iResult, 0);
			if (iSendResult == SOCKET_ERROR) {
				printf("send failed with error: %d\n", WSAGetLastError());
				return ID_FAIL;
			}
			printf("Bytes sent: %d\n", iSendResult);
		} else if (iResult == 0)
			printf("Connection closing...\n");
		else {
			printf("recv failed with error: %d\n", WSAGetLastError());
			return ID_FAIL;
		}

	} while (iResult > 0);

	return ID_CCONN;

}

GSTATES Globals::closeConnection(void) {
	iResult = shutdown(cSocket, SD_SEND);

	if (iResult == SOCKET_ERROR) {
		printf("shutdown failed with error: %d\n", WSAGetLastError());
		return ID_FAIL;
	}


	return ID_CSOCKET; //not sure if ID_LSOCKET or ID_CSOCKET
}
GSTATES Globals::anyFail(void) {
	closesocket(cSocket);
	WSACleanup();

	return ID_CSOCKET;
}
#pragma endregion

#pragma region ------------------------ cursor events ------------------------

void Globals::setMousePos(char *data) {
	ivector2 pos = ivector2();
	char *temp;
	char *context = NULL;

	temp = strtok_s(data, ",", &context);
	pos.X = atoi(temp) + oRes.X;
	temp = strtok_s(NULL, ",", &context);
	pos.Y = atoi(temp) + oRes.Y;
	temp = strtok_s(NULL, ",", &context);
	onClick = atoi(temp);

	leftClick(onClick);
	SetCursorPos(pos.X, pos.Y);
}

void Globals::leftClick(bool isPresed) {
	if (isClickOn) {
		if (isPresed == 0) {
			isClickOn = false;
		} else {
			return;
		}
	} else {
		if (isPresed == 1) {
			isClickOn = true;
		} else {
			return;
		}
	}



	if (isPresed) {
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;					// We are setting left mouse button down.
		SendInput(1, &Input, sizeof(INPUT));						// Send the input.
	} else {
		ZeroMemory(&Input, sizeof(INPUT));							// Fills a block of memory with zeros.
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_LEFTUP;						// We are setting left mouse button up.
		SendInput(1, &Input, sizeof(INPUT));						// Send the input.
	}
}

void Globals::rightClick(bool isPresed) {
	if (isPresed) {
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;					// We are setting right mouse button down.
		SendInput(1, &Input, sizeof(INPUT));						// Send the input.
	} else {
		ZeroMemory(&Input, sizeof(INPUT));							// Fills a block of memory with zeros.
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;					// We are setting right mouse button up.
		SendInput(1, &Input, sizeof(INPUT));						// Send the input.
	}
}

#pragma endregion
