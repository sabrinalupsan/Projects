def bkt(val_mom=0, w_mom=0):
    global val_optim, sol_optim
    ok = False
    for obj in data:
        if obj not in sol and obj[1] + w_mom <= w_max:
            ok = True
            sol.add(obj)
            val_mom += obj[0]
            w_mom += obj[1]
            bkt(val_mom, w_mom)
            sol.remove(obj)
            val_mom -= obj[0]
            w_mom -= obj[1]
    if not ok:
        if val_optim < val_mom:
            val_optim = val_mom
            sol_optim = sol.copy()


data = [(3, 8), (2, 12), (1, 6), (7, 10), (4, 6), (9, 16), (10, 10), (21, 14), (12, 12)]
w_max = 50
sol = set()
val_optim = -1
sol_optim = set()

bkt()
print("The optimum solution is: ")
print(sol_optim)
print("The optimum value reached within the maximum cost is: " + str(val_optim))
