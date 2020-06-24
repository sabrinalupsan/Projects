#include "Assignment1SDD.h"

/*
#1
implement a method that creates a new package with the given values

@return a package with the given values; by default the package is not delivered
@input data for package fields
*/
package createPackage(const char* street, int streetNo, float weight, const char* contact)
{
	package newPack;
	newPack.isDelivered = false;
	newPack.adr.no = streetNo;
	strcpy(newPack.adr.street, street);
	newPack.weight = weight;
	if (contact != NULL)
	{
		newPack.contact = (char*)malloc(strlen(contact) + 1);
		strcpy(newPack.contact, contact);
	}
	return newPack;
};


/*
#2
implement a method that prints a package at the console

@input the package to print
*/
void printPackage(package myPackage)
{
	printf("\nThe weight is: %.2f", myPackage.weight);
	printf("\nThe address is: %d, %s street", myPackage.adr.no, myPackage.adr.street);
	printf("\nThe contact is: %s", myPackage.contact);
	if (myPackage.isDelivered == true)
		printf("\nThe package is delivered");
	else
		printf("\nThe package is not delivered");
	printf("\n............");
};

/*
#3
implement a method that converts the package structure to the serialized_version

@return returns a serialized_package article based on the received package
@input an existing package
*/
serialized_package serializePackage(package p)
{
	serialized_package newPack;
	newPack.weight = p.weight;
	newPack.adr.no = p.adr.no;
	strcpy(newPack.adr.street, p.adr.street);
	if (p.contact != NULL)
		strcpy(newPack.contact, p.contact);
	newPack.isDelivered = p.isDelivered;
	return newPack;
};

void printSerializedPackage(serialized_package myPackage) //this is my function in order to check myself
{
	printf("\nThe weight is: %.2f", myPackage.weight);
	printf("\nThe address is: %d, %s street", myPackage.adr.no, myPackage.adr.street);
	printf("\nThe contact is: %s", myPackage.contact);
	if (myPackage.isDelivered == true)
		printf("\nThe package is delivered");
	else
		printf("\nThe package is not delivered");
}


/*
#4
implement a method that convert the serialized_package structure to a package one
@return returns a package article based on the received serialized_package
@input an existing serialized_package

Important ! watch out for the dynamic field in the package structure

*/
package deserializePackage(serialized_package s_p)
{
	package newPack;
	newPack.weight = s_p.weight;
	strcpy(newPack.adr.street, s_p.adr.street);
	newPack.adr.no = s_p.adr.no;
	newPack.contact = (char*)malloc(strlen(s_p.contact) + 1);
	strcpy(newPack.contact, s_p.contact);
	newPack.isDelivered = s_p.isDelivered;
	return newPack;
}

/*
#5
method for inserting a serialized_package element in the database

@return number of inserted packages
@input the database and the serialized package

*/
int insert(db myDb, serialized_package pack)
{
	fwrite((char*)&pack.weight, sizeof(float), 1, myDb.pf);
	fwrite(pack.adr.street, sizeof(pack.adr.street), 1, myDb.pf);
	fwrite((char*)&pack.adr.no, sizeof(int), 1, myDb.pf);
	fwrite(pack.contact, sizeof(pack.contact), 1, myDb.pf);
	fwrite((char*)&pack.isDelivered, sizeof(bool), 1, myDb.pf);
	return 1;
};

/*
#6
method for inserting an array of serialized_package elements in the database

@return number of inserted packages
@input the database, the array and its number of elements; the array can have from 1 to N packages

*/
int insert(db myDb, serialized_package* pack, int noPacks)
{
	int insertedPacks = 0;
	int ok = 0;
	for (int i = 0; i < noPacks; i++) {
		ok = insert(myDb, pack[i]);
		if (ok == 1)
			insertedPacks++;
		ok = 0;
	}
	return insertedPacks;
};

/*
#7
method for selecting the first package for a given contact name

@return the first package with the given contact; if there is no package for the contact then the functions
		return a package with a "non existing" contact
@input the database, the array and its number of elements
*/
package selectFirstOrDefault(db myDb, const char* contact)
{
	package pack;
	int ok = 0;
	float weight = 0;
	char street[50];
	int no;
	char contacts[1000];
	bool isDelivered;
	unsigned int x = getFileSize(myDb.pf);
	unsigned int i;
	for (i = 0; i < x && ok == 0; i = i + sizeof(serialized_package))
	{
		fread(&weight, sizeof(float), 1, myDb.pf);
		fread(&street, sizeof(street), 1, myDb.pf);
		fread(&no, sizeof(int), 1, myDb.pf);
		fread(&contacts, sizeof(contacts), 1, myDb.pf);
		fread(&isDelivered, sizeof(bool), 1, myDb.pf);
		if (strcmp(contact, contacts) == 0)
		{
			ok = 1;
			pack = createPackage(street, no, weight, contacts);
			pack.isDelivered = isDelivered;
		}
	}
	if (ok == 0)
	{
		pack = createPackage("Non existing", 0, 0, "Non existing");
		pack.isDelivered = false;
	}
	return pack;
};

/*
#8
method for selecting all

@input the database and the given weight
@return the packageList of the packages with a weight greater than the given one
*/
packageList selectHeavyPackages(db myDb, float weight) //there are too many people in the database
{
	packageList List;
	float weights;
	char street[50];
	int no;
	char contacts[1000];
	bool isDelivered;
	unsigned int x = getFileSize(myDb.pf);
	for (unsigned int i = 0; i < x; i = i + sizeof(serialized_package))
	{
		fread(&weights, sizeof(float), 1, myDb.pf);
		fread(&street, sizeof(street), 1, myDb.pf);
		fread(&no, sizeof(int), 1, myDb.pf);
		fread(&contacts, sizeof(contacts), 1, myDb.pf);
		fread(&isDelivered, sizeof(bool), 1, myDb.pf);
		if (weights > weight)
		{
			package pack = createPackage(street, no, weights, contacts);
			pack.isDelivered = isDelivered;
			if (List.noPackages == 0)
			{
				List.packages = (package*)malloc(sizeof(package));
				List.packages[0] = pack;
				List.noPackages++;
			}
			else
			{
				package* packs = (package*)malloc(sizeof(package)*List.noPackages);
				for (int i = 0; i < List.noPackages; i++)
					packs[i] = List.packages[i];
				List.noPackages++;
				free(List.packages);
				List.packages = (package*)malloc(sizeof(package)*List.noPackages);
				for (int i = 0; i < List.noPackages - 1; i++)
					List.packages[i] = packs[i];
				List.packages[List.noPackages - 1] = pack;
				free(packs);
			}
		}
	}
	return List;
};


/*
#9
method for printing to the console all packages from a package list

@input the given packageList
*/
void printAll(packageList list)
{
	for (int i = 0; i < list.noPackages; i++)
	{
		printf("\n-----------------------\n");
		printSerializedPackage(serializePackage(list.packages[i]));
	}
};


/*
#10
method for printing to the console all packages from the database

@input the database
*/
void printAll(db myDb)
{
	printf("\n-----------------------\n");
	float weights;
	char street[50];
	int no;
	char contacts[1000];
	bool isDelivered;
	unsigned int x = getFileSize(myDb.pf);
	for (unsigned int i = 0; i < x; i = i + sizeof(serialized_package))
	{
		fread(&weights, sizeof(float), 1, myDb.pf);
		fread(&street, sizeof(street), 1, myDb.pf);
		fread(&no, sizeof(int), 1, myDb.pf);
		fread(&contacts, sizeof(contacts), 1, myDb.pf);
		fread(&isDelivered, sizeof(bool), 1, myDb.pf);
		package pack = createPackage(street, no, weights, contacts);
		pack.isDelivered = isDelivered;
		printPackage(pack);
	}
	printf("\n-----------------------\n");


};

bool connect2(db* myDB, const char* user, const char* pass) {
	if (strcmp(user, myDB->userName) == 0 &&
		strcmp(pass, myDB->pass) == 0) {
		myDB->pf = fopen(myDB->fileName, "ab+");
		if (myDB->pf == NULL)
			return false;
		return true;
	}
	else
		return false;
}

int main()
{
	const char* streets[] = { "Calea Dorobanti", "Calea Victoriei", "Eminescu" };
	int numbers[] = { 10, 5, 87 };
	const char* contacts[] = { "John", "Anna", "Bob" };
	//the weights were declared as int!
	float weights[] = { 10.6, 3.5, 80 };


	//generate data
	package testData[3];
	for (int i = 0; i < 3; i++) {
		testData[i] = createPackage(streets[i], numbers[i], weights[i], contacts[i]);
	}

	//PART 1

	//print test data
	printf("*Test Data* The packages are: \n");
	for (int i = 0; i < 3; i++) {
		printPackage(testData[i]);
	}

	serialized_package myPackage = serializePackage(testData[0]);
	package p = deserializePackage(myPackage);
	printf("\nThe package, which I serialized and then deserialized is: \n");
	printPackage(p);
	//printSerializedPackage(myPackage); here I made my own function to print the serialized packages

	db packageDB = createDB("admin", "1234", "TestPack");
	if (connect(&packageDB, "admin", "1234")) {
		printf("\n *Info* You are connected\n");

		for (int i = 0; i < 3; i++) {
			//insert the serialized version
			insert(packageDB, serializePackage(testData[i]));
		}

		reset(packageDB);

		printf("\nSize of the serialized article is %d bytes", sizeof(serialized_package));
		printf("\nFile size is %d bytes", getFileSize(packageDB.pf));

		reset(packageDB);

		package p5 = selectFirstOrDefault(packageDB, "John");
		printf("\nThe package with the contact given is: \n");
		printPackage(p5);

		reset(packageDB);

		packageList l = selectHeavyPackages(packageDB, 5);
		printf("\nThe heavy packages are: ");
		printAll(l);


		//print the file content
		printf("\n *File Data*\n");
		printAll(packageDB);

		close(&packageDB);
	}
	else
		printf("\n *Error* No connection to the DB");

	//PART 2
	db inputDb = createDB("admin", "1234", "InputPack");

	if (connect2(&inputDb, "admin", "1234"))
	{
		printf("\n *Info* You are connected");
		printf("\n *File Data*\n");


		serialized_package testData2[3];
		for (int i = 0; i < 3; i++)
			testData2[i] = serializePackage(testData[i]);
		insert(inputDb, testData2, 3); //checking the second insert function
		printAll(inputDb);

		reset(inputDb);

		printf("\nSize of the serialized article is %d bytes", sizeof(serialized_package));
		printf("\nFile size is %d bytes", getFileSize(inputDb.pf));

		package item = selectFirstOrDefault(inputDb, "Anna");

		printf("\nThe searched package is for %s and its weight is %f\n", item.contact, item.weight);

		reset(inputDb);

		packageList list = selectHeavyPackages(inputDb, 3.5);
		printf("\nThe heavy packages are: ");
		printAll(list);

		//here you wrote close(&packageDB);
		close(&inputDb);
	}
	else
		printf("\n *Error* No connection to the DB");
	printf("\n");
}