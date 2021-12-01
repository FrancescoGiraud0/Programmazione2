def distanza(p1, p2=(0,0)):
    return ((p1[0]-p2[0])**2+(p1[1]-p2[1])**2)**0.5

def piu_vicino(points):
    if len(points)<=0:
        print("Errore: lista vuota")
        exit()

    vicino = points[0]
    for p in points[1:]:
        if distanza(p) <= distanza(vicino):
            vicino = p
    return vicino

def input_pt():
    s = input('P(x,y): ')
    if s=='':
        return False
    
    p = tuple(map(float, s.split()))

    if len(p)!=2:
        print("Errore: punto inserito incorretamente")
        exit()
    
    return p


if __name__=='__main__':
    points = []
    p = True

    print('Inserisci lista di punti (coordinate separate da spazi), premere INVIO per terminare')

    while p:
        p = input_pt()
        if p:
            points.append(p)

    print('Distanza dall\'origine: ')
    
    for p in points:
        print(f'{p}<--->O = {distanza(p)}')
    
    print(f'\n{piu_vicino(points)} è il punto più vicino')