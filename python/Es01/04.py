import numpy as np

def isPrime(n):
    for p in range(2, int(np.sqrt(n)) + 1):
        if (n % p == 0):
            return False

    return True

try:
    n = int(input("Inserire numero massimo: "))
except ValueError:
    print("Errore: inserito valore non intero")
    exit()

if n<=0:
    print("Errore: inserito valore <= 0")
    exit()

print("Numeri primi <=", n , "sono:\n[",end="")

if 2<=n:
    print(2, end="")

for i in range(3,n+1,2):
    if isPrime(i):
        print(", ",i, end="")

print("]")