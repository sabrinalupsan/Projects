#pragma once
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>
#include <malloc.h>
#include <string.h>

//to disable VS compiler padding 
#pragma pack(1)
struct address {
	char street[50];
	int no;
};

struct package {
	float weight = 0;
	address adr;
	char* contact = NULL;
	bool isDelivered = false;
};

struct db {
	char fileName[50];
	char userName[50];
	char* pass;
	FILE *pf = NULL;
};

struct packageList {
	package* packages = NULL;
	int noPackages = 0;
};

//the serialized version of the package
//force the compiler not to use padding

struct serialized_package {
	float weight = 0;
	address adr;
	char contact[1000];
	bool isDelivered = false;
};

db createDB(const char* user, const char* pass,
	const char* database) {
	db newDb;
	strcpy(newDb.userName, user);
	newDb.pass = (char*)malloc(strlen(pass) + 1);
	strcpy(newDb.pass, pass);
	//the filename is = database +.mydb
	if (strlen(database) + strlen(".mydb") + 1
		<= sizeof(newDb.fileName)) {
		strcpy(newDb.fileName, database);
		strcat(newDb.fileName, ".mydb");
	}
	else
	{
		//copy only the first 50 - 6 chars
		strncpy(newDb.fileName, database,
			sizeof(newDb.fileName) - strlen(".mydb") - 1);
		strcat(newDb.fileName, ".mydb");
	}

	return newDb;
}

//function that opens a db
bool connect(db* myDB, const char* user, const char* pass) {
	if (strcmp(user, myDB->userName) == 0 &&
		strcmp(pass, myDB->pass) == 0) {
		myDB->pf = fopen(myDB->fileName, "wb+");
		if (myDB->pf == NULL)
			return false;
		return true;
	}
	else
		return false;
}


//method for inserting a package in the database
int insert(db myDb, package pack, ...) {
	//write the weight
	fwrite((char*)&pack.weight, sizeof(float), 1, myDb.pf);
	//write the street from the address
	fwrite(pack.adr.street, sizeof(pack.adr.street), 1, myDb.pf);
	fwrite((char*)&pack.adr.no, sizeof(int), 1, myDb.pf);
	char buffer[1000];
	strcpy(buffer, pack.contact);
	fwrite(buffer, sizeof(buffer), 1, myDb.pf);
	fwrite((char*)&pack.isDelivered, sizeof(bool), 1, myDb.pf);
	return 1;
}

//function that closes the file 
void close(db* myDb) {
	fclose(myDb->pf);
	myDb->pf = NULL;
}

//function that reset the file header to the beginning
void reset(db myDb) {
	fseek(myDb.pf, 0, SEEK_SET);
}

//function that determines the size of a file
long getFileSize(FILE* pf) {
	if (pf == NULL)
		return -1;
	else
	{
		fseek(pf, 0, SEEK_END);
		long fileSize = ftell(pf);
		//move the cursor back at the beginning
		fseek(pf, 0, SEEK_SET);
		return fileSize;
	}
}