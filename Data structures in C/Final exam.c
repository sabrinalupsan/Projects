#include<string.h>
#include<malloc.h>
#include<stdlib.h>
#include<stdio.h>

struct Room
{
	char* floor;
	int roomNumber;
	char* roomType;
	float price;
};

struct Node
{
	Node* left = NULL;
	Node* right = NULL;
	Room room;
};

struct BST
{
	Node* root = NULL;
};

Room initRoom(const char* floor, int roomNumber, const char* roomType, float price)
{
	Room r;
	r.floor = (char*)malloc(strlen(floor) + 1);
	strcpy(r.floor, floor);
	r.roomNumber = roomNumber;
	r.roomType = (char*)malloc(strlen(roomType) + 1);
	strcpy(r.roomType, roomType);
	r.price = price;
	return r;
}

Room copyRoom(Room r)
{
	return initRoom(r.floor, r.roomNumber, r.roomType, r.price);
}

void printRoom(Room r)
{
	printf("Floor: %s, room number: %d, room type: %s, price: %.2f.\n",
		r.floor, r.roomNumber, r.roomType, r.price);
}

int hashFunction(const char* floor)
{
	int x = 0;
	for (int i = 0; i < strlen(floor); i++)
		x += floor[i];
	return x;
}

void insertNode(BST* bst, Room room)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->room = copyRoom(room);
	newNode->left = NULL;
	newNode->right = NULL;
	int key = hashFunction(room.floor);
	if (bst->root == NULL)
		bst->root = newNode;
	else
	{
		Node* temp = bst->root;
		bool inserted = false;
		while (inserted == false)
		{
			int key2 = hashFunction(temp->room.floor);
			if (key2>key)
				if (temp->left == NULL)
				{
					inserted = true;
					temp->left = newNode;
				}
				else
					temp = temp->left;
			else
				if(key2<key)
					if (temp->right == NULL)
					{
						inserted = true;
						temp->right = newNode;
					}
					else
						temp = temp->right;
				else
					if (temp->room.roomNumber > room.roomNumber)
					{
						if (temp->left == NULL)
						{
							inserted = true;
							temp->left = newNode;
						}
						else
							temp = temp->left;
					}
					else
						if (temp->room.roomNumber < room.roomNumber)
						{
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
}

void printBSTInorder(Node* node)
{
	if (node != NULL)
	{
		printBSTInorder(node->left);
		printRoom(node->room);
		printBSTInorder(node->right);
	}
}

float totalIncome(Node* node, const char* floor, const char* type)
{
	if (node != NULL)
		if (strcmp(node->room.floor, floor) == 0 && strcmp(node->room.roomType, type) == 0)
			return node->room.price + totalIncome(node->left, floor, type) + totalIncome(node->right, floor, type);
		else
			return totalIncome(node->left, floor, type) + totalIncome(node->right, floor, type);
	return 0;
}

Node* subtree(BST bst, const char* floor, int roomNumber)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	if (bst.root->room.floor == floor && bst.root->room.roomNumber == roomNumber)
	{
		newNode->room = copyRoom(bst.root->room);
		newNode->left = bst.root->left;
		newNode->right = bst.root->right;
		bst.root = NULL;
	}
	else
	{
		Node* node = bst.root;
		bool done = false;
		while (node != NULL && done == false)
		{
			if (node->left != NULL && strcmp(node->left->room.floor, floor) == 0 && node->left->room.roomNumber == roomNumber)
			{
				newNode = node->left;
				node->left = NULL;
				done = true;
			}
			else if (node->right != NULL && strcmp(node->right->room.floor, floor) == 0 && node->right->room.roomNumber == roomNumber)
			{
				newNode = node->left;
				node->left = NULL;
				done = true;
			}
			else if (hashFunction(node->room.floor) > hashFunction(floor))
				node = node->left;
			else if (hashFunction(node->room.floor) < hashFunction(floor))
				node = node->right;
			else
			{
				if (node->room.roomNumber > roomNumber)
					node = node->left;
				else
					node = node->right;
			}
		}
	}
	return newNode;
}

int max(int a, int b)
{
	return a > b ? a : b;
}

int getHeight(Node* root)
{
	if (root != NULL)
		return 1 + max(getHeight(root->right), getHeight(root->left));
	else
		return 0;
}

void printAllNodes(Node* node, int level)
{
	if (node != NULL)
	{
		if (level == 1)
			printRoom(node->room);
		else
			if (level > 1)
			{
				printAllNodes(node->left, level - 1);
				printAllNodes(node->right, level - 1);
			}
	}
}

void printAllLevels(Node* node)
{
	int x = getHeight(node);
	for (int i = 1; i <= x; i++)
	{
		printf("\nThe nodes on level: %d are: \n", i);
		printAllNodes(node, i);
	}
}

void freeSpace(Node* node)
{
	if (node != NULL)
	{
		free(node->left);
		free(node->right);
		free(node->room.floor);
		free(node->room.roomType);
		free(node);
	}
}

void freeSpaceBST(BST* bst)
{
	freeSpace(bst->root);
	bst->root = NULL;
}

void printByFloorAux(Node* node, char** floor)
{
	if (node != NULL)
	{
		printByFloorAux(node->left, floor);
		if (*floor == NULL && node->left == NULL)
		{
			if (*floor != NULL)
				free(*floor);
			*floor = (char*)malloc(strlen(node->room.floor) + 1);
			strcpy((*floor), node->room.floor);
		}
		else if (strcmp(*floor, node->room.floor) != 0)
		{
			printf("\n--------------\n");
			if (*floor != NULL)
				free(*floor);
			*floor = (char*)malloc(strlen(node->room.floor) + 1);
			strcpy((*floor), node->room.floor);
		}
		printRoom(node->room);
		printByFloorAux(node->right, floor);
	}
}

void printByFloor(BST bst)
{
	char* currentFloor = NULL;
	printByFloorAux(bst.root, &currentFloor);
}

int main()
{
	FILE* f;
	f = fopen("rooms.txt", "r");
	if (f)
	{
		BST bst;
		while (!feof(f))
		{
			char buffer[1000];
			fscanf(f, "%s", buffer);
			int number;
			fscanf(f, "%d", &number);
			char buffer2[1000];
			fscanf(f, "%s", buffer2);
			float price;
			fscanf(f, "%f", &price);
			Room r = initRoom(buffer, number, buffer2, price);
			insertNode(&bst, r);
			//printRoom(r);
		}
		printBSTInorder(bst.root);
		printf("\n%.2f\n", totalIncome(bst.root, "IV", "Single"));

		printf("\nPrinting by levels in the tree...\n");
		printAllLevels(bst.root);

		printf("\nPrinting by floors...\n");
		printByFloor(bst);

		////no sure this is ok so i left it last
		//BST bst2;
		//bst2.root = subtree(bst, "IV", 42);
		//printBSTInorder(bst2.root);
		//printf("\n------------------\n");
		//printBSTInorder(bst.root);


		freeSpaceBST(&bst);
		printBSTInorder(bst.root); //this won't display anything because i freed up the space
		//freeSpaceBST(&bst2);
	}
}