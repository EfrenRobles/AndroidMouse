#pragma once

#include <windows.h>
#include <conio.h>

#define USE_NETWORK_FUNCTIONS 0

typedef unsigned char ubyte;
typedef unsigned int uint;
typedef unsigned short ushort;

enum GSTATES {
	ID_INIT,		//oninit
	ID_ISERVER,		//ip server
	ID_CSOCKET,		//creating socket
	ID_ISOCKET,		//init socket
	ID_LSOCKET,		//listening socket
	ID_ACONN,		//Acpeting connection
	ID_RDATA,		//recieve data
	ID_CCONN,		//close connection
	ID_FAIL			//else fail
};

enum CSTATES {
	ID_FALSE = 0,
	ID_TRUE = 1
};

struct ivector2 {
	short X;
	short Y;
};

#ifndef WIN32_LEAN_AND_MEAN
	#define WIN32_LEAN_AND_MEAN
#endif

#include <windows.h>
#include <stdlib.h>
#include <stdio.h>

#if USE_NETWORK_FUNCTIONS
	#include <winsock2.h>
	#include <ws2tcpip.h>

	// Need to link with Ws2_32.lib
	#pragma comment (lib, "Ws2_32.lib")
	// #pragma comment (lib, "Mswsock.lib")

	#define DEFAULT_BUFLEN	64
	#define DEFAULT_PORT	"1800"
#endif

#define	EXPORT_DLL extern "C" __declspec(dllexport)

class Globals {
public:

	static	Globals		*getInstance	(void);
	static	void		DestroyInstance	(void);

	// Desc    : start the main class engine
	// Returns : true is all be alright false if fail.

public:
	// Desc    : get the resolution from the monitor on windows.
	// Returns : the resolution from windows monitor in ivector2 struct.
	ivector2	getMonitorResolution	(void);

	// Desc    : get the resolution from android client.
	// Returns : the resolution from client in ivector2 struct.
	ivector2	 getAndroidResolution	(void);

	// Desc    : set the offset limit for the cursor in the resolution of monitor
	// Returns : Nothing.
	void		setOffsetResolution		(short X, short Y);

	// Desc    : set the mouse possition computer's screen.
	// Returns : Nothing.
	void	setMousePos				(char *recvbuf);

#if USE_NETWORK_FUNCTIONS
	bool		startApp				(void);

	// Desc    : get the actual connection status
	// Returns : true is success, false if fail to connect
	bool		getConeccionStatus		(void);

	// Desc    : get the actual raw data from device
	// Returns : Nothing
	char		*getRawData				(void);
#endif //USE_NETWORK_FUNCTIONS

protected:

private:

	static	Globals		*s_instance;

	ivector2	oRes;
	ivector2	aRes;
	char		*recRawData;

	CSTATES		onClick;
	bool		isClickOn;


	// Desc    : Clicks the left mouse button down and releases it.
	// Returns : Nothing.
	void	leftClick				(CSTATES isPresed);

	// Desc    : Clicks the right mouse button down and releases it.
	// Returns : Nothing.
	void	rightClick				(bool isPresed);

#if USE_NETWORK_FUNCTIONS
	GSTATES	id_Status;

	WSADATA	wsaData;
	int		iResult;

	SOCKET	lSocket;
	SOCKET	cSocket;

	struct	addrinfo				*result;
	struct	addrinfo				hints;

	int		iSendResult;
	char	recvbuf					[DEFAULT_BUFLEN];
	int		recvbuflen;

	//---------------------------------------------------------------
	// Desc    : to initialize globals class with winsocks
	// Returns : true if sucess, false if fail.
	GSTATES	onInit					(void);

	// Desc    : resolve the server address and port.
	// Returns : true if success, false if fail.
	GSTATES	ipServer				(void);

	// Desc    : setup the tcp listening socket
	// Returns : true if success, false if fail.
	GSTATES	initSocket				(void);

	// Desc    : create a socket for connection to android client
	// Returns : true if success, false if fail.
	GSTATES	creatingSocket			(void);

	// Desc    : listen for incoming client.
	// Returns : true if success, false if fail.
	GSTATES	listenSocket			(void);

	// Desc    : Accept a client socket from android client
	// Returns : true if sucess, false if fail.
	GSTATES	acceptingConnection		(void);

	// Desc    : Accept a client socket from android client
	// Returns : true if sucess, false if fail.
	GSTATES	receiveData				(void);

	// Desc    : disconnecting a client socket from android client
	// Returns : true if sucess, false if fail.
	GSTATES	closeConnection			(void);

	// Desc    : disconnecting a client socket from android client
	// Returns : true if sucess, false if fail.
	GSTATES	anyFail					(void);
#endif //USE_NETWORK_FUNCTIONS
};

