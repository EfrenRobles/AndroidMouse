#pragma once

#include <windows.h>
#include <conio.h>


typedef unsigned int uint;
typedef unsigned short ushort;

struct ivector2 {
	short X;
	short Y;
};

#ifndef WIN32_LEAN_AND_MEAN
	#define WIN32_LEAN_AND_MEAN
#endif

#include <windows.h>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdlib.h>
#include <stdio.h>

// Need to link with Ws2_32.lib
#pragma comment (lib, "Ws2_32.lib")
// #pragma comment (lib, "Mswsock.lib")

#define DEFAULT_BUFLEN	512
#define DEFAULT_PORT	"1800"
#define	EXPORT_DLL extern "C" __declspec(dllexport)

class Globals {
public:

	static	Globals		*getInstance	(void);
	static	void		DestroyInstance	(void);

	// Desc    : start the main class engine
	// Returns : Nothing.
	void	startApp				(void);

	// Desc    : get the resolution from the monitor on windows.
	// Returns : the resolution from windows monitor in ivector2 struct.
	ivector2	getMonitorResolution(void);

	// Desc    : get the resolution from android client.
	// Returns : the resolution from client in ivector2 struct.
	ivector2 getAndroidResolution	(void);

	// Desc    : set the offset limit for the cursor in the resolution of monitor
	// Returns : Nothing.
	void	setOffsetResolution		(short X, short Y);

	// Desc    : get the actual connection status
	// Returns : true is success, false if fail to connect
	bool	getConeccionStatus		(void);



protected:



private:

	static	Globals		*s_instance;


	WSADATA	wsaData;
	int		iResult;

	SOCKET	lSocket;
	SOCKET	cSocket;

	struct	addrinfo				*result;
	struct	addrinfo				hints;

	int		iSendResult;
	char	recvbuf[DEFAULT_BUFLEN];
	int		recvbuflen;

	ivector2	offsetResolution;


	// Desc    : set the mouse possition computer's screen.
	// Returns : Nothing.
	void	setMousePos				(uint posX, uint posY);

	// Desc    : Clicks the left mouse button down and releases it.
	// Returns : Nothing.
	void	leftClick				(bool isPresed);

	// Desc    : Clicks the right mouse button down and releases it.
	// Returns : Nothing.
	void	rightClick				(bool isPresed);

	// Desc    : to initialize globals class with winsocks
	// Returns : true if sucess, false if fail.
	bool	onInit					(void);

	// Desc    : resolve the server address and port.
	// Returns : true if success, false if fail.
	bool	ipServer				(void);

	// Desc    : setup the tcp listening socket
	// Returns : true if success, false if fail.
	bool	initSocket				(void);

	// Desc    : create a socket for connection to android client
	// Returns : true if success, false if fail.
	bool	creatingSocket			(void);

	// Desc    : listen for incoming client.
	// Returns : true if success, false if fail.
	bool	listenSocket			(void);

	// Desc    : Accept a client socket from android client
	// Returns : true if sucess, false if fail.
	bool	acceptingConnection		(void);

	// Desc    : Accept a client socket from android client
	// Returns : true if sucess, false if fail.
	bool	receiveData				(void);

	// Desc    : disconnecting a client socket from android client
	// Returns : true if sucess, false if fail.
	bool	closeConnection			(void);

};

