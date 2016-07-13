#pragma once

#include <windows.h>
#include <conio.h>
#include <stdlib.h>
#include <stdio.h>



enum CSTATES {
	ID_FALSE = 0,
	ID_TRUE = 1
};

#define	EXPORT_DLL extern "C" __declspec(dllexport)

class Globals {
public:

	static	Globals		*getInstance(void);
	static	void		DestroyInstance(void);

public:

	// Desc    : Clicks the left mouse button down and releases it.
	// Returns : Nothing.
	void	leftClick	(int isPresed);

	// Desc    : Clicks the right mouse button down and releases it.
	// Returns : Nothing.
	void	rightClick	(int isPresed);

protected:

private:

	static	Globals		*s_instance;

	bool				isClickOn;
	


};

