import statistics

n_list = input("Inserire la lista di valori separati da spazi (Premere INVIO per terminare):\n")

if(n_list==""):
    print("Errore: nessun valore inserito")
    exit()

n_list = n_list.split()

for i,n in enumerate(n_list):
    try:
        n_list[i] = float(n)
    except ValueError:
        print("Errore: impossibile convertire valore ", i+1, ", valore non numerico")

print("Media: ",statistics.mean(n_list))
print("Mediana: ",statistics.median(n_list))
print("Varianza: ",statistics.variance(n_list))