# Hypothesis:
# The salesman can leave from any city, must visit every city exactly once and must return to the starting point


f = open('input_TSP.txt')


def read(M):
    m = int(f.readline())
    for i in range(m):
        triplet = [int(x) for x in f.readline().split()]
        M[triplet[0]][triplet[1]] = M[triplet[1]][triplet[0]] = triplet[2]


def bkt(k=0, dist=0):
    global Solution, min_dist
    if len(S) == n:
        dist += A[S[-1]][S[0]]
        S.append(S[0])
        if dist < min_dist:
            min_dist = dist
            Solution = S.copy()
        dist -= A[S[-1]][S[0]]
        S.pop()
    else:
        for i in range(1, n+1):
            if i not in S:
                if S:
                    dist += A[S[-1]][i]
                S.append(i)
                bkt(k+1, dist)
                S.pop()
                if S:
                    dist -= A[S[-1]][i]


n = int(f.readline())
A = [[0 for j in range(n+1)] for i in range(n+1)]
read(A)

S = []
Solution = []
min_dist = 15000
bkt()

print("The optimum solution and its distance is:")
print(Solution)
print(min_dist)
