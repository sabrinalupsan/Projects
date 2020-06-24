#include<stdlib.h>
#include<string.h>
#include<malloc.h>
#include<stdio.h>

struct Car
{
	int idVehicle;
	char* carMaker;
	int year;
	float price;
	int noSeats;
};

struct Node
{
	Node* left = NULL;
	Node* right = NULL;
	Car* car;
};

struct BST
{
	Node* root = NULL;
};

Car* initCar(int idVehicle, const char* carMaker, int year, float price, int noSeats)
{
	Car* car = (Car*)malloc(sizeof(Car));
	car->idVehicle = idVehicle;
	car->carMaker = (char*)malloc(strlen(carMaker) + 1);
	strcpy(car->carMaker, carMaker);
	car->year = year;
	car->price = price;
	car->noSeats = noSeats;
	return car;
}

void insertNode(BST* bst, Car* car)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->car = car;
	newNode->left = NULL;
	newNode->right = NULL;
	if (bst->root == NULL)
		bst->root = newNode;
	else
	{
		Node* temp = bst->root;
		bool inserted = false;
		while(inserted == false)
		{
			if (temp->car->idVehicle > car->idVehicle)
				if (temp->left == NULL) 
				{
					inserted = true;
					temp->left = newNode;
				}
				else
					temp = temp->left;
			else
				if (temp->right == NULL)
				{
					inserted = true;
					temp->right = newNode;
				}
				else
					temp = temp->right;
		}
	}
}

void printCar(Car* car)
{
	if (car != NULL)
		printf("%d %s %d %f %d\n", car->idVehicle, car->carMaker, car->year, car->price, car->noSeats);
	else
		printf("Not found.\n");
}

void printBSTTree(Node* node)
{
	if (node != NULL)
	{
		printBSTTree(node->right);
		printCar(node->car);
		printBSTTree(node->left);
	}
}

int maxPrice(Node* node, int* cod)
{
	if (node != NULL)
	{
		int price1 = -1;
		int price2 = -1;
		if(node->right!=NULL)
			price1 = maxPrice(node->right, cod);
		int price3 = node->car->price;
		if(node->left!=NULL)
			price2 = maxPrice(node->left, cod);

		if (price1 > price2 && price1 > price3)
		{
			return price1;
		}
		else 
			if (price2 > price3 && price2 > price1)
				return price2; 
			else
			{
				*cod = (node->car->idVehicle);
				return price3;
			}
	}
	return 0;
}

Car* search(BST bst, int idVehicle);


Car* find(BST bst)
{
	int x = 0;
	maxPrice(bst.root, &x);
	return search(bst, x);
}



Car* copyCar(Car* car)
{
	Car* c = (Car*)malloc(sizeof(Car));
	c->carMaker = (char*)malloc(strlen(car->carMaker) + 1);
	strcpy(c->carMaker, car->carMaker);
	c->idVehicle = car->idVehicle;
	c->noSeats = car->noSeats;
	c->price = car->price;
	c->year = car->year;
	return c;
}

Car* search(BST bst, int idVehicle)
{
	Node* temp = bst.root;
	while (temp != NULL)
		if (temp->car->idVehicle < idVehicle)
			temp = temp->right;
		else
			if (temp->car->idVehicle == idVehicle)
				return temp->car;
			else
				temp = temp->left;
	return NULL;
}

Car* extract(BST* bst, int idVehicle)
{
	Car* c = NULL;
	Node* *temp = &(bst->root);
	
	while ((*temp) != NULL && (*temp)->car->idVehicle != idVehicle)
		if ((*temp)->car->idVehicle < idVehicle)
			temp = &((*temp)->right);
		else
			temp = &((*temp)->left);
	if ((*temp) == NULL)
		return NULL;
	c = copyCar((*temp)->car);

	if ((*temp)->right == NULL && (*temp)->left == NULL)
	{
		free((*temp)->car->carMaker);
		free((*temp)->car);
		free((*temp));
		(*temp) = NULL;
	}
	else
	{
		if ((*temp)->right == NULL || (*temp)->left == NULL)
			if ((*temp)->right != NULL)
			{
				Node* aux = (*temp);
				(*temp) = (*temp)->right;
				free(aux->car->carMaker);
				free(aux->car);
				free(aux);
				aux = NULL;
			}
			else
			{
				Node* aux = (*temp);
				(*temp) = (*temp)->left;
				free(aux->car->carMaker);
				free(aux->car);
				free(aux);
				aux = NULL;
			}
		else
		{
			Node* pred = (*temp);
			pred = (*temp)->left;
			while (pred->right != NULL)
				pred = pred->right;
			free((*temp)->car);
			(*temp)->car = copyCar(pred->car);
			Node* aux = pred;
			pred = pred->left;
			free(aux->car->carMaker);
			free(aux->car);
			free(aux);
			aux = NULL;
		}
	}
	return c;
}



int main()
{
	Car* c1 = initCar(1, "Renault", 2018, 10000, 6);
	Car* c2 = initCar(2, "Dacia papuc", 2012, 1500, 2);
	Car* c3 = initCar(3, "Mazda", 2020, 18000, 4);
	BST bst;
	insertNode(&bst, c1);
	insertNode(&bst, c2);
	insertNode(&bst, c3);
	printBSTTree(bst.root);
	Car* c = extract(&bst, 1);
	printCar(c);

	printBSTTree(bst.root);

	printf("%d", maxPrice(bst.root));

	Car* c4 = find(bst);
	printCar(c4);
}

