input_str = input("Inserire le parole che si vuole ordinare separate da spazi (Premere INVIO per terminare):\n")
list_str = sorted(list(set(input_str.split())), key=str.upper) #Per ordine alfabetico
print(list_str)