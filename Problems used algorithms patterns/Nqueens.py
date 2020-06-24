def printQueens(M):
    for row in M:
        for x in row:
            print(x, end='  ')
        print()


def check(row, col):
    for i in range(row):
        if table[i][col] == 1:
            return False
    i, j = row-1, col-1
    while i >= 0 and j >= 0:
        if table[i][j] == 1:
            return False
        i -= 1; j -= 1
    i, j = row-1, col+1
    while i >= 0 and j < n:
        if table[i][j] == 1:
            return False
        i -= 1; j += 1
    return True


def place_queen(row=0):
    if row == n:
        ok = True
        printQueens(table)
        print()
    for col in range(n):
        if check(row, col):
            table[row][col] = 1
            place_queen(row+1)
            table[row][col] = 0


n = int(input("Please input the size of the board: "))
table = [[0 for i in range(n)] for i in range(n)]

place_queen()