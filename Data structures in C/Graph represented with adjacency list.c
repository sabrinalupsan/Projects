#include<malloc.h>
#include<string.h>
#include<stdlib.h>
#include<stdio.h>

struct Magazin
{
	int id;
	char* nume;
	char* localitate;
	float suprafata;
	int nrAngajati;
};

struct Node
{
	Node* next = NULL;
	Magazin m;
	float weight;
};

struct AdjacencyList
{
	Magazin m;
	Node* headOfSecondaryLists = NULL;
	AdjacencyList* next;
};

struct Graph
{
	AdjacencyList* headOfMainList = NULL;
};

struct StackNode
{
	StackNode* next = NULL;
	int id;
};

struct Stack
{
	StackNode* head = NULL;
};

struct QueueNode
{
	QueueNode* next = NULL;
	QueueNode* prev = NULL;
	int id;
};

struct Queue
{
	QueueNode* head = NULL;
	QueueNode* tail = NULL;
};

Magazin initMagazin(int id, const char* nume, const char* localitate,
	float suprafata, int nrAngajati)
{
	Magazin m;
	m.id = id;
	m.nume = (char*)malloc(strlen(nume) + 1);
	strcpy(m.nume, nume);
	m.localitate = (char*)malloc(strlen(localitate) + 1);
	strcpy(m.localitate, localitate);
	m.suprafata = suprafata;
	m.nrAngajati = nrAngajati;
	return m;
}

Magazin copyMagazin(Magazin m);

Node* createNode(Magazin m)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->next = NULL;
	newNode->m = copyMagazin(m);
	return newNode;
}

Magazin copyMagazin(Magazin m)
{
	return initMagazin(m.id, m.nume, m.localitate, m.suprafata, m.nrAngajati);
}

void printMagazin(Magazin m)
{
	printf("%d %s %s %f %d\n", m.id, m.localitate, m.nume, m.suprafata, m.nrAngajati);
}

void addNodes(Graph* g, Magazin m)
{
	AdjacencyList* newNode = (AdjacencyList*)malloc(sizeof(AdjacencyList));
	newNode->headOfSecondaryLists = NULL;
	newNode->next = NULL;
	newNode->m = copyMagazin(m);
	AdjacencyList* temp = g->headOfMainList;
	if (temp == NULL)
		g->headOfMainList = newNode;
	else
	{
		while (temp->next != NULL)
			temp = temp->next;
		temp->next = newNode;
	}
}


void addEdge(Graph* graph, Magazin m0, Magazin m1, float time)
{
	Node* n0 = createNode(m0);
	Node* n1 = createNode(m1);
	AdjacencyList* temp = graph->headOfMainList;
	while (temp != NULL)
		if (temp->m.id == n0->m.id)
			break;
		else
			temp = temp->next;
	
	n1->next = temp->headOfSecondaryLists;
	temp->headOfSecondaryLists = n1;
	n0->weight = 0;
	n1->weight = time;

}

void traverseGraph(Graph graph)
{
	AdjacencyList* temp = graph.headOfMainList;
	while (temp != NULL)
	{
		Node* temporary = temp->headOfSecondaryLists;
		while (temporary != NULL)
		{
			printf("\n%d %d %.2f\n", temp->m.id, temporary->m.id, temporary->weight);
			temporary = temporary->next;
		}
		temp = temp->next;
	}	
}

float totalSurface(Graph g)
{
	float total = 0;
	AdjacencyList* temp = g.headOfMainList;
	while (temp != NULL)
	{
		total += temp->m.suprafata;
		temp = temp->next;
	}
	return total;
}

float* totalChirie(Graph g)
{
	float* chirii;
	int nrChirii = 0;
	AdjacencyList* temp = g.headOfMainList;
	while (temp != NULL)
	{
		nrChirii++;
		temp = temp->next;
	}
	chirii = (float*)malloc(nrChirii * sizeof(float));
	temp = g.headOfMainList;
	for (int i = 0; i < nrChirii; i++)
	{
		chirii[i] = temp->m.suprafata * 9;
		temp = temp->next;
	}
	return chirii;
}

void deleteNode(Graph* g, int code)
{
	AdjacencyList* prev = g->headOfMainList;
	AdjacencyList* temp = g->headOfMainList;
	while (temp != NULL) 
	{
		if (temp->m.id == code)
		{
			if (prev == temp)
				g->headOfMainList = g->headOfMainList->next;
			else
				prev->next = temp->next;
			free(temp->m.localitate);
			free(temp->m.nume);
			free(temp);
			temp = NULL;
		}
		else
		{
			Node* temporary = temp->headOfSecondaryLists;
			Node* previously = temporary;
			while (temporary != NULL)
				if (temporary->m.id == code)
				{
					if (previously == temporary)
						temp->headOfSecondaryLists = temp->headOfSecondaryLists->next;
					else
					{
						previously->next = temporary->next;
					}
					free(temporary->m.localitate);
					free(temporary->m.nume);
					free(temporary);
					temporary = NULL;
				}
				else
				{
					previously = temporary;
					temporary = temporary->next;
				}
		}
		if (temp == NULL)
			prev = prev->next;
		else
		{
			prev = temp;
			temp = temp->next;
		}
	}
}

void changeEmployees(Graph* g, int code, int newNumber)
{
	AdjacencyList* temp = g->headOfMainList;
	while (temp != NULL)
	{
		if (temp->m.id == code)
			temp->m.nrAngajati = newNumber;
		else
		{
			Node* temporary = temp->headOfSecondaryLists;
			while (temporary != NULL)
				if (temporary->m.id == code)
				{
					temporary->m.nrAngajati = newNumber;
					break;
				}
				else
					temporary = temporary->next;
		}
		temp = temp->next;
	}
}

int loadAdjacencyMatrix(Graph g, int*** matrix)
{
	AdjacencyList* temp = g.headOfMainList;
	int n = 0;
	while (temp != NULL)
	{
		temp = temp->next;
		n++;
	}
	*matrix = (int**)malloc(n * sizeof(int));
	for (int i = 0; i < n; i++)
	{
		(*matrix)[i] = (int*)malloc(n * sizeof(int));
		for (int j = 0; j < n; j++)
			(*matrix)[i][j] = 0;
	}
	temp = g.headOfMainList;
	while (temp != NULL)
	{
		Node* temporary = temp->headOfSecondaryLists;
		while (temporary != NULL)
		{
			(*matrix)[temp->m.id][temporary->m.id] = 1;
			temporary = temporary->next;
		}
		temp = temp->next;
	}
	return n;
}

//float totalSurface(Graph graph)
//{
//	float total = 0.0;
//	for (int i = 0; i < graph.noVertices; i++)
//		if (graph.vertices[i].head != NULL)
//			total += TotalSurfaceOfList(graph.vertices[i].head);
//	return total;
//}
//
//float* vectorChirii(Graph graph)
//{
//	float* vector = (float*)malloc(graph.noVertices*(sizeof(float)));
//	for (int i = 0; i < graph.noVertices; i++)
//	{
//		if (graph.vertices[i].head != NULL)
//			vector[graph.vertices[i].head->m.id] = graph.vertices[i].head->m.suprafata * 9;
//	}
//	return vector;
//}
//
//void deleteNode(Graph* graph, int id)
//{
//	for (int i = 0; i < graph->noVertices; i++)
//	{
//		Node* prev = graph->vertices[i].head;
//		Node* temp = graph->vertices[i].head;
//		while (temp != NULL) {
//			if (temp->m.id == id)
//			{
//				if (temp == graph->vertices[i].head)
//				{
//					prev = temp->next;
//					free(temp->m.localitate);
//					free(temp->m.nume);
//					free(temp);
//					temp = NULL;
//					graph->vertices[i].head = prev;
//				}
//				else {
//					prev->next = temp->next;
//					free(temp->m.localitate);
//					free(temp->m.nume);
//					free(temp);
//					temp = NULL;
//				}
//				break;
//			}
//			else
//			{
//				prev = temp;
//				temp = temp->next;
//			}
//		}
//		if (i == id)
//		{
//			Node* prev = graph->vertices[i].head;
//			Node* temp = graph->vertices[i].head;
//			while (temp != NULL)
//			{
//				prev = temp->next;
//				free(temp->m.localitate);
//				free(temp->m.nume);
//				free(temp);
//				temp = NULL;
//				if (prev == NULL)
//					temp = NULL;
//				else
//					temp = prev->next;
//			}
//			graph->vertices[i].head = NULL;
//		}
//	}
//}
//void change(Graph* graph, int id, int newNoAngajati)
//{
//	for (int i = 0; i < graph->noVertices; i++)
//	{
//		Node* temp = graph->vertices[i].head;
//		while (temp != NULL)
//			if (temp->m.id == id)
//			{
//				temp->m.nrAngajati = newNoAngajati;
//				break;
//			}
//			else
//				temp = temp->next;
//	}
//	//traverseGraph(*graph);
//}

bool checkIfVisited(int* vec,int n, int code)
{
	for (int i = 0; i < n; i++)
		if (vec[i] == code)
			return true;
	return false;
}

int popStack(Stack* stack)
{
	StackNode* temp = stack->head;
	int x = temp->id;
	stack->head = stack->head->next;
	free(temp);
	return x;
}

void pushStack(Stack* stack, int code)
{
	StackNode* newNode = (StackNode*)malloc(sizeof(StackNode));
	newNode->next = stack->head;
	newNode->id = code;
	stack->head = newNode;
}

int* DF(Graph g, int code)
{
	AdjacencyList* temp = g.headOfMainList;
	int n = 0;
	while (temp != NULL)
	{
		temp = temp->next;
		n++;
	}
	int* vizitate = (int*)malloc(n * sizeof(int));
	int i = 1;
	vizitate[0] = code;
	Stack stack;
	StackNode* node = (StackNode*)malloc(sizeof(StackNode));
	node->next = NULL;
	node->id = code;
	stack.head = node;
	while (stack.head != NULL)
	{
		code = popStack(&stack);
		temp = g.headOfMainList;
		while (temp->m.id != code)
			temp = temp->next;
		Node* temporary = temp->headOfSecondaryLists;
		while (temporary != NULL)
		{
			if (checkIfVisited(vizitate, i, temporary->m.id) == false)
			{
				i++;
				vizitate[i - 1] = temporary->m.id;
				pushStack(&stack, temporary->m.id);
			}
			temporary = temporary->next;
		}
	}
	return vizitate;
}

int popQueue(Queue* queue)
{
	if (queue->head != NULL)
	{
		int x = queue->tail->id;
		if (queue->head->next == NULL)
		{
			free(queue->head);
			queue->head = NULL;
			queue->tail = NULL;
		}
		else
		{
			queue->tail = queue->tail->prev;
			free(queue->tail->next);
			queue->tail->next = NULL;
		}
		return x;

	}
}

void pushQueue(Queue* queue, int code)
{
	QueueNode* node = (QueueNode*)malloc(sizeof(QueueNode));
	node->id = code;
	if (queue->head == NULL)
	{
		queue->head = node;
		queue->tail = node;
		queue->head->next = NULL;
		queue->tail->next = NULL;
	}
	else
	{
		queue->tail->next = node;
		node->prev = queue->tail;
		queue->tail = node;
		node->next = NULL;
	}
}

int* BF(Graph g, int code)
{
	AdjacencyList* temp = g.headOfMainList;
	int n = 0;
	while (temp != NULL)
	{
		temp = temp->next;
		n++;
	}
	int* vizitate = (int*)malloc(n * sizeof(int));
	int i = 1;
	vizitate[0] = code;
	Queue queue;
	//QueueNode* node = (QueueNode*)malloc(sizeof(StackNode));
	//node->next = NULL;
	//node->id = code;
	pushQueue(&queue, code);
	while (queue.head != NULL)
	{
		code = popQueue(&queue);
		temp = g.headOfMainList;
		while (temp->m.id != code)
			temp = temp->next;
		Node* temporary = temp->headOfSecondaryLists;
		while (temporary != NULL)
		{
			if (checkIfVisited(vizitate, i, temporary->m.id) == false)
			{
				i++;
				vizitate[i - 1] = temporary->m.id;
				pushQueue(&queue, temporary->m.id);
			}
			temporary = temporary->next;
		}
	}
	return vizitate;
}

int main()
{
	Magazin m0 = initMagazin(0, "Lidl", "Bucuresti", 100, 20);
	Magazin m1 = initMagazin(1, "Mega Image", "Bucuresti", 50, 18);
	Magazin m2 = initMagazin(2, "Pepco", "Sinaia", 10, 8);
	Magazin m3 = initMagazin(3, "Lidl", "Bucuresti", 100, 20);
	Magazin m4 = initMagazin(4, "Cora", "Iasi", 150, 50);
	Graph g;
	addNodes(&g, m0);
	addNodes(&g, m1);
	addNodes(&g, m2);
	addNodes(&g, m3);
	addNodes(&g, m4);
	addEdge(&g, m0, m1, 10);
	addEdge(&g, m1, m2, 20);
	addEdge(&g, m1, m4, 20);
	addEdge(&g, m1, m3, 30);
	addEdge(&g, m2, m3, 30);
	addEdge(&g, m3, m4, 30);
	addEdge(&g, m2, m4, 30);
	addEdge(&g, m4, m0, 30);
	traverseGraph(g);
	printf("\n%f\n", totalSurface(g));
	float* v = totalChirie(g);
	AdjacencyList* temp = g.headOfMainList;
	int i = 0;
	while (temp != NULL)
	{
		printf("%.2f ", v[i]);
		i++;
		temp = temp->next;
	}
	deleteNode(&g, 4);
	traverseGraph(g);

	changeEmployees(&g, 2, 20);
	printf("%d", g.headOfMainList->next->next->m.nrAngajati);

	int** matrix;
	int n = loadAdjacencyMatrix(g, &matrix);
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
			printf("%d ", matrix[i][j]);
		printf("\n");
	}

	int* vec = BF(g, 0);
	for (int i = 0; i < n; i++)
		printf("%d ", vec[i]);
}