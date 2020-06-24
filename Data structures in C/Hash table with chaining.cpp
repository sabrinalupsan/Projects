#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

using namespace std;

struct Team {
	int code;
	char* captain;
	int topic;
	int noOfStudents;
	float points;
};

struct Node {
	Node* next= NULL;
	int code;
};

struct HashTable
{
	Team* *array = NULL;
	int size = 0;
};

HashTable initHashTable(int size)
{
	HashTable table;
	table.array = (Team**)malloc(size * sizeof(Team*));
	for (int i = 0; i < size; i++)
		table.array[i] = NULL;
	table.size = size;
	return table;
}

Team initTeam(int code, const char* captain, int topic, int noStudents, float points)
{
	Team t;
	t.code = code;
	t.captain = (char*)malloc(strlen(captain) + 1);
	strcpy(t.captain, captain);
	t.topic = topic;
	t.noOfStudents = noStudents;
	t.points = points;
	return t;
}

Team copyTeam(Team team) {
	Team t;
	t.code = team.code;
	t.captain = (char*)malloc(strlen(team.captain) + 1);
	strcpy(t.captain, team.captain);
	t.topic = team.topic;
	t.noOfStudents = team.noOfStudents;
	t.points = team.points;
	return t;
}

void printTeam(Team t)
{
	printf("%d %s %d %d %f\n", t.code, t.captain, t.topic, t.noOfStudents, t.points);
}

int hashFunction(int code, int size)
{
	int x = 0;
	while (code > 0)
	{
		x = code % 10;
		code = code / 10;
	}
	x += 13;
	return x % size;
}

void insertTeam(HashTable* table, Team* team)
{
	int index = hashFunction(team->code, table->size);
	int x = index;
	bool ok = false;
	while (x < table->size && ok==false)
	{
		if (table->array[x] == NULL)
		{
			ok = true;
			table->array[index] = team;
		}
		else
		{
			x++;
		}
	}
	if (ok == false)
	{
		for (int i = 0; i < index && ok==false; i++)
			if (table->array[i] == NULL) {
				table->array[i] = team;
				ok = true;
			}
	}
	//printTeam(*(table->array[index]));
}

Node* insertTeam2(HashTable* table, Team* team, Node* head)
{
	insertTeam(table, team);
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->next = NULL;
	newNode->code = (*team).code;
	if (head == NULL)
	{
		head = newNode;
	}
	else
	{
		Node* temp = head;
		while (temp->next != NULL)
		{
			temp = temp->next;
		}
		temp->next = newNode;
	}
	return head;
}

void printHashTable(HashTable table)
{
	for (int i = 0; i < table.size; i++)
		if (table.array[i] != NULL)
			printTeam(*table.array[i]);
}

Team** morePointsThan(HashTable table, float points, int* n)
{
	Team* *arr;
	float ptsPerStud;
	int x = 0;
	for (int i = 0; i < table.size; i++)
	{
		if (table.array[i] != NULL) {
			ptsPerStud = table.array[i]->points / table.array[i]->noOfStudents;
			if (ptsPerStud > points)
			{
				x++;
			}
		}
	}

	arr = (Team**)malloc(x * sizeof(Team*));
	x = 0;
	for (int i = 0; i < table.size; i++)
	{
		if (table.array[i] != NULL) {
			Team* temp = table.array[i];
			ptsPerStud = table.array[i]->points / table.array[i]->noOfStudents;
			if (ptsPerStud > points)
				arr[x++] = table.array[i];
			printTeam(*arr[x - 1]);
		}
	}
	*n = x;

	/*for (int j = 0; j < table.size; j++)
	{
		if (table.array[j] != NULL) {
			ptsPerStud = table.array[j]->points / table.array[j]->noOfStudents;
			if (ptsPerStud > points)
			{
				(*n)++;
				Team** temp = (Team**)malloc((*n) * sizeof(Team*));
				for (int i = 0; i < (*n) - 1; i++)
					temp[i] = arr[i];
				temp[(*n) - 1] = table.array[j];
				if ((*n) > 1)
					free(arr);
				arr = (Team**)malloc((*n) * sizeof(Team*));
				for (int i = 0; i < (*n); i++)
					arr[i] = temp[i];
				free(temp);
			}
		}
	}*/
	return arr;
}

float avgPerTable(HashTable table)
{
	float x = 0; 
	int y = 0;
	for (int i = 0; i < table.size; i++)
	{
		if (table.array[i] != NULL)
		{
			y++;
			x += (table.array[i]->noOfStudents);
		}
	}
	return x / y;
}

void printList(Node* head)
{
	Node* temp = head;
	while (temp != NULL)
	{
		printf("\n%d", temp->code);
		temp = temp->next;
	}
}

int main()
{
	Team t1 = initTeam(1, "Sabruna", 2, 22, 29.4);
	Team t2 = initTeam(2, "Cosbuz", 2, 23, 34.4);
	Team t3 = initTeam(3, "Andrei", 0, 10, 29.4);
	HashTable table = initHashTable(101);
	insertTeam(&table, &t1);
	insertTeam(&table, &t2);
	insertTeam(&table, &t3);
	int n = 0;
	Team* *t = morePointsThan(table, 1, &n);
	for (int i = 0; i < n; i++)
		printTeam(*t[i]);
	printf("\n%f", avgPerTable(table));

	HashTable table2 = initHashTable(101);
	Node* head = insertTeam2(&table2, &t1, NULL);
	head = insertTeam2(&table2, &t2, head);
	head = insertTeam2(&table2, &t3, head);
	printList(head);
}