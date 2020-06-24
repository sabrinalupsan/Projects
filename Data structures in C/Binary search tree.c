#include<malloc.h>
#include<string.h>
#include<stdlib.h>
#include<stdio.h>

struct Proiect
{
	unsigned int cod;
	char* titlu;
	char* beneficiar;
	int nrExecutanti;
	char** listaExecutanti;
	float buget;
};

struct Node
{
	Node* right = NULL;
	Node* left = NULL;
	Proiect proiect;
};

struct BST
{
	Node* root = NULL;
};

struct ListNode
{
	ListNode* next = NULL;
	Proiect proiect;
};

struct List
{
	ListNode* head = NULL;
};

Proiect initProiect(unsigned int cod, const char* titlu, const char* beneficiar,
	int nrExecutanti, char** lista, float buget)
{
	Proiect proiect;
	proiect.cod = cod;
	proiect.titlu = (char*)malloc(strlen(titlu) + 1);
	proiect.beneficiar = (char*)malloc(strlen(beneficiar) + 1);
	strcpy(proiect.titlu, titlu);
	strcpy(proiect.beneficiar, beneficiar);
	proiect.nrExecutanti = nrExecutanti;
	proiect.listaExecutanti = (char**)malloc(nrExecutanti * sizeof(char*));
	for (int i = 0; i < nrExecutanti; i++)
	{
		proiect.listaExecutanti[i]= (char*)malloc(strlen(lista[i]) + 1);
		strcpy(proiect.listaExecutanti[i], lista[i]);
	}
	proiect.buget = buget;
	return proiect;
}

Proiect copyProiect(Proiect p)
{
	Proiect proiect = initProiect(p.cod, p.titlu, p.beneficiar, p.nrExecutanti,
		p.listaExecutanti, p.buget);
	return proiect;
}

void printProiect(Proiect p)
{
	printf("%d %s %s %d executanti:\n", p.cod, p.titlu, p.beneficiar, p.nrExecutanti);
	for (int i = 0; i < p.nrExecutanti; i++)
		printf("%s ", p.listaExecutanti[i]);
	printf("%f\n", p.buget);
}

void insertProiect(BST* bst, Proiect pr)
{
	Node* newNode = (Node*)malloc(sizeof(Node));
	newNode->left = NULL;
	newNode->right = NULL;
	newNode->proiect = copyProiect(pr);
	if (bst->root == NULL)
		bst->root = newNode;
	else
	{
		Node* temp = bst->root;
		bool inserted = false;
		while (inserted == false)
		{
			if (temp->proiect.cod > pr.cod)
			{
				if (temp->left == NULL)
				{
					temp->left = newNode;
					inserted = true;
				}
				else
					temp = temp->left;
			}
			if (temp->proiect.cod < pr.cod)
			{
				if (temp->right == NULL)
				{
					temp->right = newNode;
					inserted = true;
				}
				else
					temp = temp->right;
			}
		}
	}
}

void printBST(Node* node)
{
	if (node != NULL)
	{
		printProiect(node->proiect);
		printBST(node->right);
		printBST(node->left);
	}
}

void insertIntoList(Proiect proiect, ListNode* *node)
{
	ListNode* newNode = (ListNode*)malloc(sizeof(ListNode));
	newNode->next = NULL;
	newNode->proiect = copyProiect(proiect);
	if (*node == NULL)
	{
		*node = newNode;
	}
	else
	{
		ListNode* temp = *node;
		while (temp->next != NULL)
			temp = temp->next;
		temp->next = newNode;
	}
}

void printBugets(Node* node, float budget, List* list)
{
	if (node != NULL)
	{
		if (node->proiect.buget > budget)
			insertIntoList(node->proiect, &(list->head));
		printBugets(node->right, budget, list);
		printBugets(node->left, budget, list);
	}
}

void Bugets(BST bst, float buget)
{
	List list;
	printBugets(bst.root, buget, &list);
	ListNode* temp = list.head;
	while (temp != NULL)
	{
		printProiect(temp->proiect);
		temp = temp->next;
	}
	/*ListNode* newTemp = list.head;
	while (list.head->next != NULL)
	{
		free(list.head)
	}*/
}

Node* gasesteNod(Node* node, int cod)
{
	Node* temp = node;
	while (temp != NULL)
	{
		if (temp->proiect.cod > cod)
			temp = temp->left;
		else
			if (temp->proiect.cod < cod)
				temp = temp->right;
			else
				return temp;
	}
	return NULL;
}

void ModificaExecutanti(BST* bst, int cod)
{
	Node* node = gasesteNod(bst->root, cod);
	for (int i = 0; i < node->proiect.nrExecutanti; i++)
	{
		printf("%s ", node->proiect.listaExecutanti[i]);
	}
	for (int i = 0; i < node->proiect.nrExecutanti; i++)
	{
		free(node->proiect.listaExecutanti[i]);
		node->proiect.listaExecutanti[i] = (char*)malloc(strlen("Executant nou") + 1);
		strcpy(node->proiect.listaExecutanti[i], "Executant nou");
	}
	for (int i = 0; i < node->proiect.nrExecutanti; i++)
	{
		printf("%s ", node->proiect.listaExecutanti[i]);
	}
}

int main()
{
	char** executanti = (char**)malloc(3 * sizeof(char*));
	executanti[0] = (char*)malloc(strlen("Sabrina") + 1);
	executanti[1] = (char*)malloc(strlen("Cosmin") + 1);
	executanti[2] = (char*)malloc(strlen("Antonia") + 1);
	strcpy(executanti[0], "Sabrina");
	strcpy(executanti[1], "Cosmin");
	strcpy(executanti[2], "Antonia");
	Proiect p1 = initProiect(1, "Metrou", "DTR", 3, executanti, 198.3);
	Proiect p2 = initProiect(3, "MALL", "Cotroceni", 3, executanti, 150);
	Proiect p3 = initProiect(6, "Autostrada", "DTR", 3, executanti, 2000.8);
	char** executanti2 = (char**)malloc(4 * sizeof(char*));
	executanti2[0] = (char*)malloc(strlen("Andrei") + 1);
	executanti2[1] = (char*)malloc(strlen("Mariuca") + 1);
	executanti2[2] = (char*)malloc(strlen("Damon") + 1);
	executanti2[3] = (char*)malloc(strlen("Ricardo") + 1);
	strcpy(executanti2[0], "Andrei");
	strcpy(executanti2[1], "Mariuca");
	strcpy(executanti2[2], "Damon");
	strcpy(executanti2[3], "Ricardo");
	Proiect p4 = initProiect(2, "Muzeul de istorie", "Primaria", 4, executanti2, 60.8);
	Proiect p5 = initProiect(7, "Toaletele de la ASE", "ASE", 4, executanti2, 10);

	
	BST bst;
	insertProiect(&bst, p1);
	insertProiect(&bst, p2);
	insertProiect(&bst, p3);
	insertProiect(&bst, p4);
	insertProiect(&bst, p5);
	//printBST(bst.root);

	//Bugets(bst, 1500);
	ModificaExecutanti(&bst, 7);
}