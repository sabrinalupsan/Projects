#include<malloc.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

struct Student {
	int studentId = -1;
	char* name = NULL;
	int nrGrades = -1;
	int* grades = NULL;
	char faculty[100] = "";
};

struct HashTableProbing {
	Student* students = NULL;
	int noStudents = 0;
};

struct Node
{
	Student student;
	Node* next = NULL;
};

struct HashTableChaining {
	Node* *arrayOfLists = NULL;
	int noElements = 0;
};

Student copyStudent(Student source);
Student initStudent(int studentId, const char* name, int nrGrades, int* grades, const char* faculty);
void printStudent(Student s);

int hashFunctionProbing(int key, int n);
HashTableProbing initHashTableProbing(int noStudents);
int loadedDegreeProbing(HashTableProbing table);
int insertStudentProbing(HashTableProbing table, Student student);
void resizeHashTableProbing(HashTableProbing* table);
void loadStudentsProbing(FILE* pf, HashTableProbing* table);
Student searchProbing(HashTableProbing table, int id);
int deleteRecordProbing(HashTableProbing* table, int id);

int hashFunctionChaining(int id, const char* faculty, int n);
HashTableChaining initHashTableChaining(int noElements);
int loadedDegreeChaining(HashTableChaining table);
Node* createNode(Student student);
Student* searchStudentChaining(Node* node, int id);
Student searchTableChaining(HashTableChaining table, int id, const char* faculty);
Node* insertAtHeadChaining(Node* node, Student student);
void loadStudentsChaining(FILE* pf, HashTableChaining* table);
void resizeHashTableChaining(HashTableChaining* table);
void deleteList(Node* node);
int insert(HashTableChaining table, Student student);

Student initStudent(int studentId, const char* name, int nrGrades, int* grades, const char* faculty)
{
	Student s;
	s.studentId = studentId;
	if (name != NULL)
	{
		s.name = (char*)malloc(strlen(name) + 1);
		strcpy(s.name, name);
	}
	s.nrGrades = nrGrades;
	if (grades != NULL)
	{
		s.grades = (int*)malloc(sizeof(int)*nrGrades);
		for (int i = 0; i < nrGrades; i++)
			s.grades[i] = grades[i];
	}
	strcpy(s.faculty, faculty);
	return s;
}

Student copyStudent(Student source)
{
	Student dest;
	dest.studentId = source.studentId;
	if (source.name != NULL)
	{
		dest.name = (char*)malloc(strlen(source.name) + 1);
		strcpy(dest.name, source.name);
	}
	strcpy(dest.faculty, source.faculty);
	dest.nrGrades = source.nrGrades;
	if (source.nrGrades > 0)
	{
		dest.grades = (int*)malloc(sizeof(int)*dest.nrGrades);
		for (int i = 0; i < source.nrGrades; i++)
			dest.grades[i] = source.grades[i];
	}
	return dest;
}

int hashFunctionProbing(int key, int n)
{
	int x = 0;
	while (key >= 1)
	{
		x = (x + key % 10) * 31;
		key = key / 10;
	}
	return x % n;
}

HashTableProbing initHashTableProbing(int noStudents)
{
	HashTableProbing table;
	table.students = (Student*)malloc(noStudents * sizeof(Student));
	for (int i = 0; i < noStudents; i++)
		table.students[i] = initStudent(-1, NULL, 0, NULL, "");
	table.noStudents = noStudents;
	return table;
}

int loadedDegreeProbing(HashTableProbing table)
{
	int x = 0;
	for (int i = 0; i < table.noStudents; i++)
		if (table.students[i].studentId != -1)
			x++;
	float percent;
	percent = (x / (float)table.noStudents) * 100;
	return percent;
}

void printStudent(Student s)
{
	printf("\nStudent ID: %d.", s.studentId);
	printf("\nName: %s", s.name);
	printf("\nFaculty: %s", s.faculty);
	printf("\nNumber of grades: %d", s.nrGrades);
	if (s.nrGrades == 0)
		printf("\nThere are no grades");
	else
	{
		printf("\nThe grades: ");
		int i = 0;
		for (i = 0; i < s.nrGrades; i++)
			printf("%d ", s.grades[i]);
	}
	printf("\n------------------");
}

//1 - the student was successfuly inserted
//0 - the student was already there
//-1 - the student was not inserted
int insertStudentProbing(HashTableProbing table, Student student)
{
	int index = hashFunctionProbing(student.studentId, table.noStudents);
	for (int i = index; i < table.noStudents; i++)
		if (table.students[i].studentId == -1)
		{
			table.students[i] = copyStudent(student);
			return 1;
		}
		else
			if (table.students[i].studentId == student.studentId)
			{
				if (student.nrGrades > table.students[i].nrGrades)
				{
					free(table.students[i].grades);
					table.students[i].nrGrades = student.nrGrades;
					table.students[i].grades = (int*)malloc(student.nrGrades * sizeof(int));
					for (int j = 0; j < student.nrGrades; j++)
						table.students[i].grades[j] = student.grades[j];
				}
				strcpy(table.students[i].faculty, student.faculty);
				return 0;
			}
	for (int i = 0; i < index; i++)
		if (table.students[i].studentId == -1)
		{
			table.students[i] = copyStudent(student);
			return 1;
		}
		else
			if (table.students[i].studentId == student.studentId)
			{
				if (student.nrGrades > table.students[i].nrGrades)
				{
					free(table.students[i].grades);
					table.students[i].nrGrades = student.nrGrades;
					table.students[i].grades = (int*)malloc(student.nrGrades * sizeof(int));
					for (int j = 0; j < student.nrGrades; j++)
						table.students[i].grades[j] = student.grades[j];
					return 0;
				}
			}
	return -1;
}

void resizeHashTableProbing(HashTableProbing* table)
{
	int newSize = table->noStudents * 2 + 1;
	HashTableProbing table2 = initHashTableProbing(newSize);

	for (int i = 0; i < table->noStudents; i++)
	{
		if (table->students[i].studentId != -1)
			insertStudentProbing(table2, table->students[i]);
	}

	free(table->students);
	table->noStudents = newSize;
	table->students = (Student*)malloc(newSize * sizeof(Student));
	for (int i = 0; i < newSize; i++)
		table->students[i] = copyStudent(table2.students[i]);
	free(table2.students);
}

void loadStudentsProbing(FILE* pf, HashTableProbing* table)
{
	char buffer[1000], buffer2[1000];
	int* grades = NULL;
	int id, noGrades, value;
	while (!feof(pf))
	{
		fscanf(pf, "%d", &id);
		fscanf(pf, "%s", &buffer);
		fscanf(pf, "%s", &buffer2);
		fscanf(pf, "%d", &noGrades);
		if (grades != NULL)
			free(grades);
		grades = (int*)malloc(sizeof(int)*noGrades);
		for (int j = 0; j < noGrades; j++)
		{
			fscanf(pf, "%d", &value);
			grades[j] = value;
		}
		Student s = copyStudent(initStudent(id, buffer, noGrades, grades, buffer2));
		if (loadedDegreeProbing(*table) > 70)
			resizeHashTableProbing(table);
		insertStudentProbing(*table, s);
	}
}

Student searchProbing(HashTableProbing table, int id)
{
	int index = hashFunctionProbing(id, table.noStudents);
	for (int i = index; i < table.noStudents; i++)
	{
		if (table.students[i].studentId == id)
			return table.students[i];
	}
	for (int i = 0; i < index; i++)
	{
		if (table.students[i].studentId == id)
			return table.students[i];
	}
	return initStudent(-1, NULL, 0, NULL, "");
}

//1 - the student was successfuly deleted
//-1 - the student was not deleted
int deleteRecordProbing(HashTableProbing* table, int id)
{
	int index = hashFunctionProbing(id, table->noStudents);
	for (int i = index; i < table->noStudents; i++)
	{
		if (table->students[i].studentId == id)
			table->students[i] = initStudent(-1, NULL, 0, NULL, "");
		return 1;
	}
	for (int i = 0; i < index; i++)
	{
		if (table->students[i].studentId == id)
			table->students[i] = initStudent(-1, NULL, 0, NULL, "");
		return 1;
	}
	return -1;
}

int hashFunctionChaining(int id, const char* faculty, int n)
{
	int key = 0;
	while (id >= 1)
	{
		key = (key + id % 10) * 31;
		id = id / 10;
	}
	for (int i = 0; i < strlen(faculty); i++)
		key += faculty[i] * 7;
	return key % n;
}

HashTableChaining initHashTableChaining(int noElements)
{
	HashTableChaining table;
	table.noElements = noElements;
	table.arrayOfLists = (Node**)malloc(sizeof(Node*)*noElements);
	for (int i = 0; i < noElements; i++)
		table.arrayOfLists[i] = NULL;
	return table;
}

int loadedDegreeChaining(HashTableChaining table)
{
	int x=0;
	for (int i = 0; i < table.noElements; i++)
		if (table.arrayOfLists[i] != NULL)
			x++;
	float percent;
	percent = (x / (float)table.noElements) * 100;
	return percent;
}

void loadStudentsChaining(FILE* pf, HashTableChaining* table)
{
	char buffer[1000], buffer2[1000];
	int* grades = NULL;
	int id, noGrades, value;
	int i = 0;
	while (!feof(pf))
	{
		fscanf(pf, "%d", &id);
		fscanf(pf, "%s", &buffer);
		fscanf(pf, "%s", &buffer2);
		fscanf(pf, "%d", &noGrades);
		if (grades != NULL)
			free(grades);
		grades = (int*)malloc(sizeof(int)*noGrades);
		for (int j = 0; j < noGrades; j++)
		{
			fscanf(pf, "%d", &value);
			grades[j] = value;
		}
		Student s = copyStudent(initStudent(id, buffer, noGrades, grades, buffer2));
		if (loadedDegreeChaining(*table) > 70)
			resizeHashTableChaining(table);
		insert(*table, s);
	}
}

Node* createNode(Student student)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->student = copyStudent(student);
	newNode->next = NULL;
	return newNode;
}

Node* insertAtHeadChaining(Node* node, Student student)
{
	Node* newNode = createNode(student);
	newNode->next = node;
	return newNode;
}

//0 for empty list
//1 for new inserted student
//2 for student updated
int insert(HashTableChaining table, Student student)
{
	if (table.arrayOfLists == NULL)
		return 0;
	int index = hashFunctionChaining(student.studentId, student.faculty, table.noElements);
	Student* p = searchStudentChaining(table.arrayOfLists[index], student.studentId);
	if (p == NULL)
	{
		table.arrayOfLists[index] =
			insertAtHeadChaining(table.arrayOfLists[index], student);
		return 1;
	}
	else
	{
		if (p->studentId == student.studentId)
		{
			if (student.nrGrades > p->nrGrades)
			{
				free(p->grades);
				p->nrGrades = student.nrGrades;
				p->grades = (int*)malloc(student.nrGrades * sizeof(int));
				for (int i = 0; i < student.nrGrades; i++)
					p->grades[i] = student.grades[i];
			}
			strcpy(p->faculty, student.faculty);
			return 2;
		}

	}
}

Student* searchStudentChaining(Node* node, int id)
{
	if (node == NULL)
		return NULL;
	for (Node* i = node; i != NULL; i = i->next)
		if (i->student.studentId == id)
			return &i->student;
	return NULL;
}

Student searchTableChaining(HashTableChaining table, int id, const char* faculty)
{
	int index = hashFunctionChaining(id, faculty, table.noElements);
	Student* isThere = searchStudentChaining(table.arrayOfLists[index], id);
	if (isThere != NULL)
		return *isThere;
	else
		return initStudent(-1, NULL, 0, NULL, "");
}

void resizeHashTableChaining(HashTableChaining* table)
{
	int newSize = table->noElements * 2 + 1;
	Node* *newArray = (Node**)malloc(newSize * sizeof(Node*));
	for (int i = 0; i < newSize; i++)
		newArray[i] = NULL;

	for (int i = 0; i < table->noElements; i++)
	{
		for (Node* j = table->arrayOfLists[i]; j != NULL; j = j->next)
		{
			int newIndex = hashFunctionChaining(j->student.studentId, j->student.faculty, newSize);
			newArray[newIndex] = insertAtHeadChaining(newArray[newIndex], j->student);
		}
	}

	for (int i = 0; i < table->noElements; i++)
		deleteList(table->arrayOfLists[i]);

	free(table->arrayOfLists);
	table->noElements = newSize;
	table->arrayOfLists = newArray;
}

void deleteList(Node* node)
{
	Node* temp = node;
	while (temp != NULL)
	{
		Node* p = temp;
		temp = temp->next;
		free(p);
	}
}

void printStudentList(Node* node)
{
	for (Node* p = node; p != NULL; p=p->next)
		printStudent(p->student);
}

//-1 means not found
//1 means found and deleted
int deleteRecordChaining(HashTableChaining* table, int id, const char* faculty)
{
	int index = hashFunctionChaining(id, faculty, table->noElements);
	if (table->arrayOfLists[index] == NULL)
		return -1;
	else
	{
		Node* temp = table->arrayOfLists[index];
		Node* p = temp;
		while (temp != NULL)
		{
			p = p->next;
			if (p!= NULL && p->student.studentId == id)
			{
				temp->next = p->next;
				free(p);
				return 1;
			}
			else
				temp = p;
		}
		return -1;
	}
}

int main()
{
	FILE* pf = fopen("students.txt", "r");

	printf("***********PART ONE, PROBING***********\n");

	HashTableProbing probingTable = initHashTableProbing(8888);
	loadStudentsProbing(pf, &probingTable);

	printStudent(searchProbing(probingTable, 9898)); //this exists
	deleteRecordProbing(&probingTable, 9898);
	printStudent(searchProbing(probingTable, 9898)); //this doesn't exist anymore

	printf("\nThe loading percentage is: %d\n", loadedDegreeProbing(probingTable));

	for (int i = 0; i < 15; i++)
		printStudent(probingTable.students[i]);


	printf("\n***********PART TWO, CHAINING***********\n");
	rewind(pf);

	HashTableChaining chainingTable = initHashTableChaining(400);
	loadStudentsChaining(pf, &chainingTable);

	printStudent(searchTableChaining(chainingTable, 16, "FABIZ")); //this exists
	printStudent(searchTableChaining(chainingTable, 16, "CSIE")); //this doesn't exist

	deleteRecordChaining(&chainingTable, 16, "FABIZ");
	printStudent(searchTableChaining(chainingTable, 16, "FABIZ")); //now this doesn't exist anymore
	deleteRecordChaining(&chainingTable, 16, "FABIZ"); //this won't work

	printf("\nThe loading percentage is: %d %.\n", loadedDegreeChaining(chainingTable));

	for (int j = 0; j < 10; j++)
		printStudentList(chainingTable.arrayOfLists[j]);

}