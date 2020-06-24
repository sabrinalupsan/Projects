def bkt(val_mom=0, w_mom=0):
    global val_optim, sol_optim, w_max
    ok = False
    for obj in data:
        if obj not in sol and obj[1] + w_mom <= w_max:
            ok = True
            sol.append(obj)
            val_mom += obj[0]
            w_mom += obj[1]
            bkt(val_mom, w_mom)
            sol.pop()
            val_mom -= obj[0]
            w_mom -= obj[1]
    if not ok:
        if w_mom == w_max:
            if val_optim < val_mom:
                val_optim = val_mom
                sol_optim = sol.copy()
        else:
            for obj in data:
                if obj not in sol:
                    remained = w_max - w_mom
                    val_mom += obj[0] * (remained/obj[1])
                    w_mom += remained
                    copie = (obj[0] * (remained/obj[1]), remained)
                    sol.append(copie)
                    if val_optim < val_mom:
                        val_optim = val_mom
                        sol_optim = sol.copy()
                    sol.pop()
                    val_mom -= obj[0] * (remained/obj[1])
                    w_mom -= remained


data = [(3, 8), (2, 12), (1, 6), (7, 10), (4, 6), (9, 16), (10, 10), (21, 14), (12, 12)]
w_max = 35
sol = []
val_optim = -1
sol_optim = []

bkt()
print("The optimum solution is: ")
print(sol_optim)
print("The optimum value within the maximum cost is: "+str(val_optim))
