import numpy as np
import matplotlib.pyplot as plt

import pandas as pd
import matplotlib.pyplot as plt

data=pd.read_csv(r"C:\Users\Saumya Nagia\PycharmProjects\NGAFID_Regression\c172_flight_16858.csv",error_bad_lines=False)

i=0
n1= 4860
while i < n1:
    numer = 0
    denom = 0
    mean_x = 0
    mean_y = 0

    b1 = 0
    b0 = 0
    max_x = 0
    min_x = 0

    data1 = data['Time'][i: i+60]
    X = data1.to_numpy()
    print(X)

    data2 = data['AltAGL'][i: i + 60]
    Y = data2.to_numpy()
    print(Y)
    #plt.figure()
    #plt.plot(n,n2)
    # Show/save figure as desired.
    #plt.show()
    mean_x = np.mean(X)
    mean_y = np.mean(Y)

    #print("j is", j)
    j=i
    print("j is",j)
    print("i is", i)
    for j in range(60):
        numer += (X[j] - mean_x) * (Y[j] - mean_y)
        denom += (X[j] - mean_x) ** 2
        b1 = numer/denom
    print("slope starts at ", i)
    print(b1)

    max_x = np.max(X)
    min_x = np.min(X)
    b0 = mean_y - (b1 * mean_x)


    x = np.linspace(min_x, max_x, 1000)
    y = b0 + b1 * x
    plt.plot(x, y, color='#58b970', label='regression line flight-0 range 60 ')

    plt.scatter(X, Y, c='#ef5423', label='Scatter Plot')
    plt.xlabel('time')
    plt.ylabel('AltAGL')
    plt.legend()
    plt.show()


    i = i+60





