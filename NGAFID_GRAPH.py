import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
data=pd.read_csv(r"C:\Users\Saumya Nagia\PycharmProjects\NGAFID_Regression\flight-0_60_3.csv",error_bad_lines=False)

print(data.shape)
data.head()
print(data)

Y=data['AltAGL'].values
X=data['Time'].values

mean_x=np.mean(X)
mean_y=np.mean(Y)

n=len(X)

numer=0
denom=0

b1 = 0

for i in range(n):
     numer +=(X[i]-mean_x) * (Y[i] - mean_y)
     denom +=(X[i]-mean_x) ** 2
     b1=numer/denom
    #b1 += (X[i] - mean_x) * (Y[i] - mean_y) / (X[i] - mean_x) ** 2
     b0= mean_y - (b1*mean_x)

print("slope")
print(b1)

max_x=np.max(X)
min_x=np.min(X)

x=np.linspace(min_x,max_x,1000)
y=b0 + b1 * x
plt.plot(x,y,color='#58b970',label='regression line flight-0  60 - 4 ')

plt.scatter(X,Y,c='#ef5423',label='Scatter Plot')
plt.xlabel('time')
plt.ylabel('AltAGL')
plt.legend()
plt.show()
"""

ss_t=0
ss_r=0
for i in range(n):
    y_pred= b0+b1 * X[i]
    ss_t += (Y[i] -mean_y) ** 2
    ss_r += (Y[i] - y_pred) ** 2

r2 = 1 - (ss_r/ss_t)
print(r2)
"""