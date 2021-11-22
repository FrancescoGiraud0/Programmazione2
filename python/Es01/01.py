n=0
while(n<=0):
    try:
        n = int(input("Inserire un numero N: "))
        if(n<=0):
            print("Errore: Inserito valore <= 0")
    except ValueError:
        print("Errore: Inserito valore non numerico")

serie_n = [i**2 for i in range(0, n)]
print(serie_n)
print("Somma: ",sum(serie_n))