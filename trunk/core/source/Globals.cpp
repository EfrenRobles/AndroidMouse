#include "stdafx.h"
#include "Globals.h"

INPUT    Input = {0};													// Create our input.

#pragma region ------------------------ export dll ------------------------
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
#pragma enregion

#pragma region ------------------------ single instance ------------------------
Globals *Globals::s_instance = NULL;

Globals *Globals::getInstance(void) {
	if (!s_instance) {
		s_instance = new Globals();
		s_instance->iResult = SOCKET_ERROR;
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
	offsetResolution.X = X;
	offsetResolution.Y = Y;
}

bool Globals::getConeccionStatus(void) {
	if (iResult != 0) {
		return false;
	} 
	return true;
}
#pragma endregion

#pragma region ------------------------ socket events ------------------------
bool Globals::onInit(void) {

	offsetResolution = ivector2();

	lSocket = INVALID_SOCKET;
	cSocket = INVALID_SOCKET;
	struct addrinfo *_result = NULL;
	result = _result;
	recvbuflen = DEFAULT_BUFLEN;

	// Initialize Winsock
	iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
	if (iResult != 0) {
		printf("WSAStartup failed with error: %d\n", iResult);
		return false;
	}
	return true;
}

bool Globals::ipServer(void) {
	ZeroMemory(&hints, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_protocol = IPPROTO_TCP;
	hints.ai_flags = AI_PASSIVE;

	// Resolve the server address and port
	iResult = getaddrinfo(NULL, DEFAULT_PORT, &hints, &result);
	if (iResult != 0) {
		printf("getaddrinfo failed with error: %d\n", iResult);
		WSACleanup();
		return false;
	}
	return true;
}

bool Globals::creatingSocket(void) {
	lSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
	if (lSocket == INVALID_SOCKET) {
		printf("socket failed with error: %ld\n", WSAGetLastError());
		freeaddrinfo(result);
		WSACleanup();
		return false;
	}
	return true;
}

bool Globals::initSocket(void) {
	iResult = bind(lSocket, result->ai_addr, (int)result->ai_addrlen);
	if (iResult == SOCKET_ERROR) {
		printf("bind failed with error: %d\n", WSAGetLastError());
		freeaddrinfo(result);
		closesocket(lSocket);
		WSACleanup();
		return false;
	}

	freeaddrinfo(result);
	return true;
}


bool Globals::listenSocket(void) {
	iResult = listen(lSocket, SOMAXCONN);
	if (iResult == SOCKET_ERROR) {
		printf("listen failed with error: %d\n", WSAGetLastError());
		closesocket(lSocket);
		WSACleanup();
		return false;
	}
	return true;
}

bool Globals::acceptingConnection(void) {
	cSocket = accept(lSocket, NULL, NULL);
	if (cSocket == INVALID_SOCKET) {
		printf("accept failed with error: %d\n", WSAGetLastError());
		closesocket(lSocket);
		WSACleanup();
		return false;
	}

	// No longer need server socket
	closesocket(lSocket);
	return true;
}

bool Globals::receiveData(void) {
	do {
		iResult = recv(cSocket, recvbuf, recvbuflen, 0);
		if (iResult > 0) {
			printf("Bytes received: %d\n", iResult);

			// Echo the buffer back to the sender
			iSendResult = send(cSocket, recvbuf, iResult, 0);
			if (iSendResult == SOCKET_ERROR) {
				printf("send failed with error: %d\n", WSAGetLastError());
				closesocket(cSocket);
				WSACleanup();
				return false;
			}
			printf("Bytes sent: %d\n", iSendResult);
		} else if (iResult == 0)
			printf("Connection closing...\n");
		else {
			printf("recv failed with error: %d\n", WSAGetLastError());
			closesocket(cSocket);
			WSACleanup();
			return false;
		}

	} while (iResult > 0);

	return true;

}

bool Globals::closeConnection(void) {
	iResult = shutdown(cSocket, SD_SEND);

	closesocket(cSocket);
	WSACleanup();

	if (iResult == SOCKET_ERROR) {
		printf("shutdown failed with error: %d\n", WSAGetLastError());
		return false;
	}


	return true;
}
#pragma endregion

#pragma region ------------------------ cursor events ------------------------

void Globals::setMousePos(uint posX, uint posY) {
	SetCursorPos(posX, posY);
}

void Globals::leftClick(bool isPresed) {
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
