import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
data=pd.read_csv(r"C:\Users\Saumya Nagia\PycharmProjects\NGAFID_Regression\flight_0.csv",error_bad_lines=False)
print(data.shape)
data.head()
print(data)

Y=data['E1 CHT1'].values
X=data['Time'].values

mean_x=np.mean(X)
mean_y=np.mean(Y)

n=len(X)
numer=0
denom=0

for i in range(n):
    numer +=(X[i]-mean_x) * (Y[i] - mean_y)
    denom +=(X[i]-mean_x) ** 2
    b1=numer/denom
    b0= mean_y - (b1*mean_x)
print(b1,b0)

max_x=np.max(X) + 100
min_x=np.min(X) - 100

x=np.linspace(min_x,max_x,1000)
y=b0 + b1 * x
plt.plot(x,y,color='#58b970',label='regression line')
plt.scatter(X,Y,c='#ef5423',label='Scatter Plot')
plt.xlabel('time')
plt.ylabel('E1 CHT1')
plt.legend()
plt.show()

ss_t=0
ss_r=0
for i in range(n):
    y_pred= b0+b1 * X[i]
    ss_t += (Y[i] -mean_y) ** 2
    ss_r += (Y[i] - y_pred) ** 2

r2 = 1 - (ss_r/ss_t)
print(r2)
