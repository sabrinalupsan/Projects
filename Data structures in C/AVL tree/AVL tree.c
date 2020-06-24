#include <stdio.h>
#include <malloc.h>
#include <string.h>

struct Librarie {
	int versiune;
	int tip;
	char* denumire;
};

struct Node
{
	Node* right = NULL;
	Node* left = NULL;
	Librarie l;
};

struct Node2
{
	Node2* right = NULL;
	Node2* left = NULL;
	Librarie* l;
};

struct AVL
{
	Node* root = NULL;
};

struct AVL2
{
	Node2* root = NULL;
};

struct QueueNode
{
	QueueNode* next = NULL;
	Librarie l;
};

struct Queue
{
	QueueNode* head = NULL;
};

Librarie initLibrarie(int v, int t, const char* d) {
	Librarie librarie;
	librarie.tip = t;
	librarie.versiune = v;
	librarie.denumire = (char*)malloc(strlen(d) + 1);
	strcpy(librarie.denumire, d);
	return librarie;
}

Librarie copyLibrarie(Librarie l)
{
	return initLibrarie(l.versiune, l.tip, l.denumire);
}

void printLibrarie(Librarie l)
{
	printf("\nVersiune: %d, tip: %d, denumire: %s.", l.versiune, l.tip, l.denumire);
}

int max(int a, int b)
{
	return a > b ? a : b;
}

int getHeight(Node* node)
{
	if (node == NULL)
		return 0;
	return 1 + max(getHeight(node->right), getHeight(node->left));
}

int getBalanceFactor(Node* node)
{
	int rightHeight = getHeight(node->right);
	int leftHeight = getHeight(node->left);
	return rightHeight - leftHeight;
}

Node* leftRotate(Node* pivot)
{
	Node* temp = pivot->right;
	pivot->right = temp->left;
	temp->left = pivot;
	return temp;
}

Node* rightRotate(Node* pivot)
{
	Node* temp = pivot->left;
	pivot->left = temp->right;
	temp->right = pivot;
	return temp;
}

void rebalance(Node* *node)
{
	int balanceFactor = getBalanceFactor(*node);
	if (balanceFactor == -2)
	{
		if (getBalanceFactor((*node)->left) == 1)
		{
			(*node)->left = leftRotate((*node)->left);
		}
		*node = rightRotate(*node);
	}
	else
		if (balanceFactor == 2)
		{
			if (getBalanceFactor((*node)->right) == -1)
			{
				(*node)->right = rightRotate((*node)->left);
			}
			*node = leftRotate(*node);
		}
}

Node* initNode(Librarie l)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->l = copyLibrarie(l);
	newNode->left = NULL;
	newNode->right = NULL;
	return newNode;
}

void insertLibrarieInAVL(Node* *node, Librarie l)
{
	Node* newNode = initNode(l);

	if (*node == NULL)
		*node = newNode;
	else
	{
		if ((*node)->l.versiune > l.versiune)
		{
			insertLibrarieInAVL(&(*node)->left, l);
		}
		else
			if ((*node)->l.versiune < l.versiune)
			{
				insertLibrarieInAVL(&(*node)->right, l);
			}
		rebalance(node);
	}
}

void printAVLInorder(Node* node)
{
	if (node != NULL)
	{
		printAVLInorder(node->left);
		printLibrarie(node->l);
		printAVLInorder(node->right);
	}
}

void printAVLPreorder(Node* node)
{
	if (node != NULL)
	{
		printLibrarie((node->l));
		printAVLPreorder(node->left);
		printAVLPreorder(node->right);
	}
}

Node* deleteInAVL(Node* node, int versiune)
{
	if (node == NULL)
		return node;
	if (node->l.versiune > versiune)
		node->left = deleteInAVL(node->left, versiune);
	else
		if (node->l.versiune < versiune)
			node->right = deleteInAVL(node->right, versiune);
		else
		{
			if (node->left == NULL || node->right == NULL)
			{
				Node* temp;
				if (node->left == NULL)
					temp = node->right;
				else
					temp = node->left;

				if (temp == NULL)
				{
					temp = node;
					node = NULL;
				}
				else
					*node = *temp;

				free(temp);
			}
			else
			{
				Node* temp = node->right;
				while (temp->left != NULL)
					temp = temp->left;
				node->l = copyLibrarie(temp->l);
				node->right = deleteInAVL(node->right, temp->l.versiune);
			}
		}

	if (node == NULL)
		return node;

	rebalance(&node);
	return node;
}

//---------------------------------------

int getHeight(Node2* node)
{
	if (node == NULL)
		return 0;
	return 1 + max(getHeight(node->right), getHeight(node->left));
}

int getBalanceFactor(Node2* node)
{
	int rightHeight = getHeight(node->right);
	int leftHeight = getHeight(node->left);
	return rightHeight - leftHeight;
}

Node2* leftRotate(Node2* pivot)
{
	Node2* temp = pivot->right;
	pivot->right = temp->left;
	temp->left = pivot;
	return temp;
}

Node2* rightRotate(Node2* pivot)
{
	Node2* temp = pivot->left;
	pivot->left = temp->right;
	temp->right = pivot;
	return temp;
}

void rebalance(Node2* *node)
{
	int balanceFactor = getBalanceFactor(*node);
	if (balanceFactor == -2)
	{
		if (getBalanceFactor((*node)->left) == 1)
		{
			(*node)->left = leftRotate((*node)->left);
		}
		*node = rightRotate(*node);
	}
	else
		if (balanceFactor == 2)
		{
			if (getBalanceFactor((*node)->right) == -1)
			{
				(*node)->right = rightRotate((*node)->left);
			}
			*node = leftRotate(*node);
		}
}

Node2* initNode(Librarie* l)
{
	Node2* newNode = (Node2*)malloc(sizeof(Node));
	newNode->l = l;
	newNode->left = NULL;
	newNode->right = NULL;
	return newNode;
}

void insertLibrarieInAVL(Node2* *node, Librarie* l)
{
	Node2* newNode = initNode(l);

	if (*node == NULL)
		*node = newNode;
	else
	{
		if ((*node)->l->versiune > l->versiune)
		{
			insertLibrarieInAVL(&(*node)->left, l);
		}
		else
			if ((*node)->l->versiune < l->versiune)
			{
				insertLibrarieInAVL(&(*node)->right, l);
			}
		rebalance(node);
	}
}

void chooseLibraries(Node* node, AVL2* avl, int tip)
{
	if (node != NULL)
	{
		if (node->l.tip >= tip)
			insertLibrarieInAVL(&(avl->root), &(node->l));
		chooseLibraries(node->right, avl, tip);
		chooseLibraries(node->left, avl, tip);
	}
}

void printAVLInorder(Node2* node)
{
	if (node != NULL)
	{
		printAVLInorder(node->left);
		printLibrarie(*(node->l));
		printAVLInorder(node->right);
	}
}

void printAVLPreorder(Node2* node)
{
	if (node != NULL)
	{
		printLibrarie(*(node->l));
		printAVLInorder(node->left);
		printAVLInorder(node->right);
	}
}


int main()
{
	FILE* f;
	f = fopen("librarii.txt", "r");
	if (f)
	{
		AVL avl;
		avl.root = NULL;
		while (!feof(f))
		{
			int x, y;
			char buffer[1000];
			fscanf(f, "%d", &x);
			fscanf(f, "%d", &y);
			fscanf(f, "%s", buffer);
			Librarie l = initLibrarie(x, y, buffer);
			insertLibrarieInAVL(&(avl.root), l);
		}
		printAVLInorder(avl.root);
		deleteInAVL(avl.root, 2);
		printf("\n--------------------------");

		printAVLPreorder(avl.root);
		printf("\n--------------------------");
		AVL2 avl2;
		chooseLibraries(avl.root, &avl2, 2);
		printAVLInorder(avl.root);
		printAVLPreorder(avl2.root);  
	}
}

