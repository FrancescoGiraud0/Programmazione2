import math
from math import pi

def distanza(p1, p2=(0,0)):
    return ((p1[0]-p2[0])**2+(p1[1]-p2[1])**2)**0.5

def angolo(p=(0,0)):
    x,y=p
    sign = lambda x: 1 if x>=0 else -1
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

def min_func(points, func):
    if len(points)<=0:
        print("Errore: lista vuota")
        exit()

    v = points[0]
    for p in points[1:]:
        if func(p) <= func(v):
            v = p
    return v

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

    print('Distanza dall\'origine punti inseriti: ')
    for p in points:
        print(f'{p}\t<--->O = {distanza(p)}')

    print('Angoli punti inseriti: ')
    for p,ang in list(zip( points, list(map(angolo, points)) )):
        print(f'{p}\t--->\t{ang} rads [~{round(math.degrees(ang))}°]')

    print(f'\n{min_func(points, distanza)} è il punto più vicino')
    print(f'{min_func(points, angolo)} è il punto con angolo minore')
    