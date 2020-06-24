#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Platforma
{
	int id;
	float pret;
	char* denumire;
	int nrMinutePeZi;
	int* minute;
};

struct MinHeap
{
	Platforma* array;
	int size;
	int capacity;
};

struct BSTNode
{
	BSTNode* right = NULL;
	BSTNode* left = NULL;
	Platforma p;
};

struct BST
{
	BSTNode* root = NULL;
};

Platforma initPlatforma(int id, float pret, const char* den,
	int nrMin, int* min)
{
	Platforma p;
	p.id = id;
	p.pret = pret;
	p.denumire = (char*)malloc(strlen(den) + 1);
	strcpy(p.denumire, den);
	p.nrMinutePeZi = nrMin;
	p.minute = (int*)malloc(nrMin * sizeof(int));
	for (int i = 0; i < nrMin; i++)
		p.minute[i] = min[i];
	return p;
}

Platforma copyPlatforma(Platforma pf)
{
	return initPlatforma(pf.id, pf.pret, pf.denumire, pf.nrMinutePeZi, pf.minute);
}

void printPlatforma(Platforma p)
{
	printf("ID: %d, pret: %.2f, denumire: %s, nr minute: %d, minutele pe zile: ", p.id,
		p.pret, p.denumire, p.nrMinutePeZi);
	for (int i = 0; i < p.nrMinutePeZi; i++)
		printf(" %d", p.minute[i]);
	printf("\n");
}

MinHeap initMinHeap(int capacity)
{
	MinHeap heap;
	heap.size = 0;
	heap.capacity = capacity;
	heap.array = (Platforma*)malloc(capacity * sizeof(Platforma));
	return heap;
}

void resizeHeap(MinHeap* heap)
{
	Platforma* aux = (Platforma*)malloc((heap->capacity * 2) * sizeof(Platforma));
	for (int i = 0; i < heap->size; i++)
		aux[i] = copyPlatforma(heap->array[i]);
	free(heap->array);
	heap->array = aux;
	heap->capacity = heap->capacity * 2;
}

void insertInHeap(MinHeap* heap, Platforma p)
{
	if (heap->size <= heap->capacity)
	{
		heap->array[heap->size] = copyPlatforma(p);
		int i = heap->size;
		heap->size++;
		while ((i - 1) / 2 >= 0 && heap->array[(i - 1) / 2].pret > p.pret)
		{
			Platforma p2 = copyPlatforma(heap->array[(i - 1) / 2]);
			heap->array[(i - 1) / 2] = copyPlatforma(heap->array[i]);
			heap->array[i] = copyPlatforma(p2);
			i = (i - 1) / 2;
		}
	}
	else
		resizeHeap(heap);
}

Platforma popRoot(MinHeap* heap)
{
	Platforma p = copyPlatforma(heap->array[0]);
	heap->array[0] = copyPlatforma(heap->array[heap->size - 1]);
	heap->size--;
	int i = 0; 
	int min = 0;
	while (i < (heap->size - 2) / 2)
	{
		min = i;
		if (heap->array[min].pret > heap->array[2 * i + 1].pret)
			min = 2 * i + 1;
		if ((2 * i + 2) < heap->size && heap->array[min].pret > heap->array[2 * i + 2].pret)
			min = 2 * i + 2;
		if (min == i)
			i = heap->size; //just to exit the loop
		else
		{
			Platforma pi = copyPlatforma(heap->array[min]);
			heap->array[min] = copyPlatforma(heap->array[i]);
			heap->array[i] = copyPlatforma(pi);
		}
	}
	return p;
}

void changeRoot(MinHeap* heap)
{
	Platforma p = copyPlatforma(popRoot(heap));
	p.pret += p.pret;
	insertInHeap(heap, p);
}

void printHeap(MinHeap heap)
{
	for (int i = 0; i < heap.size; i++)
		printPlatforma(heap.array[i]);
}

void insertInBST(BST* bst, MinHeap heap, int n)
{
	for (int i = 0; i < n; i++)
	{
		BSTNode* newNode = (BSTNode*)malloc(sizeof(BSTNode));
		newNode->p = copyPlatforma(heap.array[i]);
		newNode->right = NULL;
		newNode->left = NULL;
		if (bst->root == NULL)
			bst->root = newNode;
		else
		{
			BSTNode* temp = bst->root;
			bool inserted = false;
			while (inserted == false)
			{
				if (temp->p.id < newNode->p.id)
					if (temp->right == NULL)
					{
						temp->right = newNode;
						inserted = true;
					}
					else
						temp = temp->right;
				else
					if (temp->left == NULL)
					{
						temp->left = newNode;
						inserted = true;
					}
					else
						temp = temp->left;
			}
		}
	}
}

void printInOrder(BSTNode* node)
{
	if (node != NULL && (node->right==NULL || node->left == NULL))
	{
		printInOrder(node->left);
		printPlatforma(node->p);
		printInOrder(node->right);
	}
}

void dezalocaHeap(MinHeap* heap)
{
	for (int i = 0; i < heap->size; i++)
	{
		free(heap->array[i].denumire);
		free(heap->array[i].minute);
	}
	free(heap->array);
	heap->size = 0;
	heap->capacity = 0;
	heap->array = NULL;
}

void dezalocaBST(BSTNode* node)
{
	if (node != NULL)
	{
		dezalocaBST(node->left);
		dezalocaBST(node->right);
		free(node->p.denumire);
		free(node->p.minute);
		free(node);
	}
}

void dezalocaTotalBST(BST* bst)
{
	dezalocaBST(bst->root);
	bst->root = NULL;
}

int main()
{
	FILE* f = fopen("platforme.txt", "r");
	if (f)
	{
		MinHeap heap = initMinHeap(101);
		while (!feof(f))
		{
			int x;
			fscanf(f, "%d", &x);
			float pret;
			fscanf(f, "%f", &pret);
			char buff[1000];
			fscanf(f, "%s", buff);
			int y;
			fscanf(f, "%d", &y);
			int* mins = (int*)malloc(y * sizeof(int));
			for (int i = 0; i < y; i++)
				fscanf(f, "%d", &mins[i]);
			Platforma p = initPlatforma(x, pret, buff, y, mins);
			//printPlatforma(p);
			insertInHeap(&heap, p);
		}
		printHeap(heap);
		printf("\n---------------------------\n");
		changeRoot(&heap);
		printHeap(heap);
		BST bst;
		insertInBST(&bst, heap, 3);
		printInOrder(bst.root);
		dezalocaHeap(&heap);
		printHeap(heap);
		dezalocaTotalBST(&bst);
		printInOrder(bst.root);
		insertInBST(&bst, heap, 3);
		printInOrder(bst.root);
	}
}