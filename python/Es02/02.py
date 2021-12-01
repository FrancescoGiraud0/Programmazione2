import math
from math import pi

def distanza(p1, p2=(0,0)):
    return ((p1[0]-p2[0])**2+(p1[1]-p2[1])**2)**0.5

def pt_angolominore(points):
    if len(points)<=0:
        print("Errore: lista vuota")
        exit()

    min_angolo = points[0]
    for p in points[1:]:
        if angolo(p)<angolo(min_angolo):
            min_angolo=p
    
    return min_angolo

def angolo(p=(0,0)):
    x,y=p
    alpha= math.nan

    if x>=0 and y>=0:
        alpha=math.asin(y/distanza(p))
    elif x<0 and y>=0:
        alpha=pi-math.asin(y/distanza(p))
    elif x<0 and y<0:
        alpha=pi-math.asin(y/distanza(p))
    else: # x>=0 and y<0
        alpha=2*pi+math.asin(y/distanza(p))
    
    return alpha

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

    print('Angoli punti inseriti: ')
    for p,ang in list(zip( points, list(map(angolo, points)) )):
        print(f'{p} ---> {ang}')

    print(f'\n{pt_angolominore(points)} è il punto con angolo minore')

