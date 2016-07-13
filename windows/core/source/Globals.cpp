#include "stdafx.h"
#include "Globals.h"

INPUT    Input = {0};													// Create our input.

#pragma region ------------------------ export dll ------------------------

EXPORT_DLL int leftClick(int isPresed) {
	Globals::getInstance()->leftClick(isPresed);
	return 0;
}

EXPORT_DLL int rightClick(int isPresed) {
	Globals::getInstance()->rightClick(isPresed);
	return 0;
}

#pragma endregion

#pragma region ------------------------ single instance ------------------------
Globals *Globals::s_instance = NULL;

Globals *Globals::getInstance(void) {
	if (!s_instance) {
		s_instance = new Globals();
		//s_instance->isPresed = 0;

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

#pragma region ------------------------ cursor events ------------------------

void Globals::leftClick(int isPresed) {
	if (isClickOn == ID_TRUE) {
		if (isPresed == ID_FALSE) {
			isClickOn = ID_FALSE;
		} else {
			return;
		}
	} else {
		if (isPresed == ID_TRUE) {
			isClickOn = ID_TRUE;
		} else {
			return;
		}
	}

	if (isPresed == ID_TRUE) {
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

void Globals::rightClick(int isPresed) {
	if (isClickOn == ID_TRUE) {
		if (isPresed == ID_FALSE) {
			isClickOn = ID_FALSE;
		} else {
			return;
		}
	} else {
		if (isPresed == ID_TRUE) {
			isClickOn = ID_TRUE;
		} else {
			return;
		}
	}

	if (isPresed == ID_TRUE) {
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
