#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <malloc.h>
#include<string.h>
#include<assert.h>

const float MAX_VALUE = 10000;
const int MAX_AGE = 65;
const int MIN_AGE = 20;
const int MAX_CATEGORY_CODE = 20;

const char* names[] = { "Alice", "John", "Bob", "Leo", "Anna", "Bianca", "Rose", "David" };
const int NAMES_NO = 8;

struct Customer {
	char* name;
	int age;
	int id;
	float cartValue;
	int categoryCode;
};

struct Node {
	Customer customer;
	Node* next = NULL;
};

struct List {
	Node* head = NULL;
	Node* tail = NULL;
};

const char* getRandomNumber() {
	int index = rand() % NAMES_NO;
	return names[index];
}

//0.5p define a functions that will generate a random age (between MIN_AGE and MAX_AGE)
int getRandomAge() {
	int age = rand() % (MAX_AGE - 19);
	return (age + 19);
}	

//0.5p define a function that will generate a random cart value (between 0 and MAX_VALUE)
float getRandomCartValue() {
	float decimalPart = (rand() % 100) / 100.0;
	int integerPart = rand() % (int)MAX_VALUE;
	float cartValue = (float)integerPart + decimalPart;

	return cartValue;
}

//0.5p define a function that will generate a random category code value (between 0 and MAX_CATEGORY_CODE)
int getRandomCode() {
	int code = rand() % (MAX_CATEGORY_CODE+1);
	return code;
}

//define a function that generates a Customer with random generated data
Customer initCustomer()
{
	Customer c1;
	c1.age = getRandomAge();
	c1.cartValue = getRandomCartValue();
	c1.categoryCode = getRandomCode();
	char buffer[500];
	strcpy(buffer, getRandomNumber());
	c1.name = (char*)malloc(strlen(buffer) + 1);
	strcpy(c1.name, buffer);
	return c1;
}

//0.5p define a function that will generate a dynamic array of Customers (the customers number is given; 
//		each customer has a unique ID and the rest of attributes are initialized with the previous functions)
Customer* generate(int size)
{
	Customer* testArray = (Customer*)malloc(sizeof(Customer)*size);
	for (int i = 0; i < size; i++)
	{
		testArray[i] = initCustomer();
		testArray[i].id = i;
	}
	return testArray;
}

Customer copyCustomers(Customer c2)
{
	Customer c1;
	if (c2.name == NULL)
	{
		c1.name = (char*)malloc(strlen("Nobody") + 1);
		strcpy(c1.name, "Nobody");
	}
	else
	{
		char buffer[1000];
		strcpy(buffer, c2.name);
		c1.name = (char*)malloc(strlen(buffer) + 1);
		strcpy(c1.name, buffer);
	}
	c1.age = c2.age;
	c1.id = c2.id;
	c1.cartValue = c2.cartValue;
	c1.categoryCode = c2.categoryCode;
	return c1;
}
//implement the rest of the requested methods

//QUEUE:
//I know "push" and "pop" is not the right keyword for them, sorry

void pushQueue(Customer cust, List* queue)
{
	if (queue->tail == NULL)
	{
		queue->tail = (Node*)malloc(sizeof(Node));
		queue->tail->customer = copyCustomers(cust);
		queue->head = queue->tail;
		queue->tail->next = NULL;
		return;
	}
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->next = NULL;
	//newNode->customer = cust;
	newNode->customer = copyCustomers(cust);
	queue->tail->next = newNode;
	queue->tail = queue->tail->next;
}

Customer popQueue(List* queue)
{
	if (queue->tail == NULL)
	{
		Customer c;
		c.name = (char*)malloc(strlen("Nobody") + 1);
		c.id = 0;
		c.age = 0; 
		c.cartValue = 0;
		c.categoryCode = 0;
		return c;
	}
	if (queue->head == queue->tail)
	{
		Customer newCustomer = copyCustomers(queue->tail->customer);
		free(queue->tail);
		queue->tail = NULL;
		queue->head = NULL;
		return newCustomer;
	}
	Customer newCustomer = copyCustomers(queue->head->customer);
	Node* aux = queue->head->next;
	free(queue->head);
	queue->head = aux;
	return newCustomer; //i know that this case is very similar but you suggested that we do the same thing everytime
}


List generateFIFO(Customer* array, int size)
{
	List queue;
	Customer* array2 = (Customer*)malloc(size * sizeof(Customer));
	for (int i = 0; i < size; i++)
		array2[i] = copyCustomers(array[i]);
	for (int i = 0; i < size; i++)
	{
		float min = -1;
		Customer minCust;
		int index;
		for (int i = 0; i < size; i++)
		{
			if (array2[i].cartValue != -1)
			{
				if (min == -1 || (min < array2[i].cartValue))
				{
					min = array2[i].cartValue;
					minCust = copyCustomers(array2[i]);
					index = i;
				}

			}
		}
		array2[index].cartValue = -1;
		pushQueue(minCust, &queue);
	}
	return queue;
}

//STACK

void pushStack(Customer customer, List* stack)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	if (stack->head == NULL)
	{
		stack->head = newNode;
		stack->head->customer = copyCustomers(customer);
		stack->head->next = NULL;
		return;
	}
	newNode->next = stack->head;
	stack->head = newNode;
	stack->head->customer = copyCustomers(customer);
}

Customer popStack(List* stack)
{
	if (stack->head == NULL)
	{
		Customer c;
		c.name = (char*)malloc(strlen("Nobody") + 1);
		c.id = 0;
		c.age = 0;
		c.cartValue = 0;
		c.categoryCode = 0;
		return c;
	}
	if(stack->head)
	if (stack->head->next == NULL)
	{
		Customer c = copyCustomers(stack->head->customer);
		free(stack->head);
		stack->head = NULL;
		return c;
	}
	Customer c = copyCustomers(stack->head->customer);
	Node* p = stack->head->next;
	free(stack->head);
	stack->head = p;
	return c;
}

List generateLIFO(Customer* array, int size)
{
	List stack;
	int x = MAX_VALUE;
	while (x >= 0) //I used the fact that I know the lower and the upper limits of the category code => O(20*n) = O(n)
	{
		for (int i = 0; i < size; i++)
		{
			if (array[i].categoryCode == x)
				pushStack(array[i], &stack);
		}
		x--;
	}
	return stack;
}

//LIST

void pushList(Customer cust, List* list)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	if (list->head == NULL)
	{
		newNode->customer = copyCustomers(cust);
		newNode->next = NULL;
		list->head = newNode;
		return;
	}
	Node* aux = list->head;
	if (cust.categoryCode < list->head->customer.categoryCode)
	{
		newNode->customer = copyCustomers(cust);
		newNode->next = list->head;
		list->head = newNode;
		return;
	}
	while (aux->next != NULL && aux->next->customer.categoryCode < cust.categoryCode)
	{
		aux = aux->next;
	}
	if (aux->next == NULL)
	{
		aux->next = newNode;
		newNode->next = NULL;
		newNode->customer = copyCustomers(cust);
	}
	else
	{
		newNode->next = aux->next;
		newNode->customer = copyCustomers(cust);
		aux->next = newNode;
	}
}

Customer popList(List* list, int index)
{
	//assert(list.head != NULL);
	if (list->head == NULL)
	{
		Customer c;
		c.name = (char*)malloc(strlen("Nobody") + 1);
		c.id = 0;
		c.age = 0;
		c.cartValue = 0;
		c.categoryCode = 0;
		return c;
	}
	Customer c;
	if (list->head->next == NULL)
	{
		c = copyCustomers(list->head->customer);
		free(list->head);
		list->head = NULL;
		return c;
	}
	Node* aux = list->head;
	if (index == 0) {
		c = copyCustomers(list->head->customer);
		list->head = list->head->next;
		free(aux);
		return c;
	}
	for (int i = 0; i < index && aux->next->next != NULL; i++)
	{
		aux = aux->next;
	}
	if (aux->next->next == NULL)
	{
		c = copyCustomers(aux->next->customer);
		free(aux->next);
		aux->next = NULL;
	}
	else
	{
		c = copyCustomers(aux->next->customer);
		Node* aux2 = aux->next;
		aux = aux->next->next;
		free(aux2);
	}
	return c;
}

List generateList(Customer* array, int size) {
	List list;
	for (int i = 0; i < size; i++)
		pushList(array[i], &list);
	return list;
}

void printGeneralList(List list, int elements)
{
	Node* aux = list.head;
	int i = 0;
	while (aux != NULL && i < elements)
	{
		
		printf("\nThe customer %s is %d years old, their ID is %d, their cart value is %.2f and their category code is %d.",
			aux->customer.name, aux->customer.age, aux->customer.id, aux->customer.cartValue, aux->customer.categoryCode);
		aux = aux->next;
		i++;
	}
	printf("\n-----------------------------");
}

void print(List list, FILE* pf)
{
	Node* aux = list.head;
	while (aux != NULL)
	{
		fprintf(pf, "%s#", aux->customer.name);
		fprintf(pf, "%d#", aux->customer.age);
		fprintf(pf, "%d#", aux->customer.id);
		fprintf(pf, "%f#", aux->customer.cartValue);
		fprintf(pf, "%d\n", aux->customer.categoryCode);
		aux = aux->next;
	}
}

void main() {

	const int ARRAY_SIZE = 10000;
	//Customer testArray[ARRAY_SIZE];
	
	srand(1000);

	int x = getRandomAge();
	int y = getRandomAge();
	float z = getRandomCartValue();
	int t = getRandomCode();
	printf("\n%d %d %f %d", x, y, z, t);

	clock_t start, end;
	double cpu_time_used;
	start = clock();

	Customer* testArray = generate(ARRAY_SIZE);

	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\nTime for generating the 10000 elements array is %f seconds\n", cpu_time_used);

	printf("\n-----------------------------");

	start = clock();
	List queue = generateFIFO(testArray, ARRAY_SIZE);
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\nTime for generating the queue is %f seconds\n", cpu_time_used);
	printGeneralList(queue, 10);

	start = clock();
	List stack = generateLIFO(testArray, ARRAY_SIZE);
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\nTime for generating the stack is %f seconds\n", cpu_time_used);
	printGeneralList(stack, 10);

	start = clock();
	List list = generateList(testArray, ARRAY_SIZE);
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\nTime for generating a regular list is %f seconds\n", cpu_time_used);
	printGeneralList(list, 10);


	printf("\n");


	FILE *pS, *pQ, *pL;
	pS = fopen("Stack.txt", "w");
	pQ = fopen("Queue.txt", "w");
	pL = fopen("List.txt", "w");
	print(queue, pQ);
	print(stack, pS);
	print(list, pL);

	//just randomly checking push and pop for all of my data structures

	for (int i = 0; i < 10; i++) 
		popStack(&stack);
	pushStack(testArray[99], &stack);
	pushStack(testArray[98], &stack);
	printGeneralList(stack, 2);

	for (int i = 0; i < 10; i++)
		popQueue(&queue);
	pushQueue(testArray[99], &queue);
	pushQueue(testArray[98], &queue);
	printGeneralList(queue, 2);

	for (int i = 0; i < 10; i++)
		popList(&list, 0);
	pushList(testArray[99], &list);
	pushList(testArray[98], &list);
	printGeneralList(list, 2);
}