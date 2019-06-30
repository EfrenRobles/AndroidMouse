// core.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"

#include <windows.h>
#include <conio.h>


// Desc    : Clicks the left mouse button down and releases it.
// Returns : Nothing.
void  leftClick(bool isPresed);
void  rightClick(bool isPresed);

int main()
{
	POINT p;

	bool test = true;
	bool click = true;

	while(true)  {

		if (GetCursorPos(&p)) {
			p.x;
			p.y;
			//SetCursorPos(p.x, p.y);
			test = !test;
		}

		_getch();

		if(click) 
			leftClick(test);
		else
			rightClick(test);

		click = !click;
	}

    return 0;
}

// Desc    : Clicks the left mouse button down and releases it.
// Returns : Nothing.
void leftClick(bool isPresed) {
	INPUT    Input = {0};													// Create our input.

	if (isPresed) {
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;							// We are setting left mouse button down.
		SendInput(1, &Input, sizeof(INPUT));								// Send the input.
	} else {
		ZeroMemory(&Input, sizeof(INPUT));									// Fills a block of memory with zeros.
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_LEFTUP;								// We are setting left mouse button up.
		SendInput(1, &Input, sizeof(INPUT));								// Send the input.
	}
}

// Desc    : Clicks the left mouse button down and releases it.
// Returns : Nothing.
void rightClick(bool isPresed) {
	INPUT    Input = {0};													// Create our input.

	if (isPresed) {
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;							// We are setting left mouse button down.
		SendInput(1, &Input, sizeof(INPUT));								// Send the input.
	} else {
		ZeroMemory(&Input, sizeof(INPUT));									// Fills a block of memory with zeros.
		Input.type = INPUT_MOUSE;									// Let input know we are using the mouse.
		Input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;								// We are setting left mouse button up.
		SendInput(1, &Input, sizeof(INPUT));								// Send the input.
	}
}
