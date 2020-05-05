import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
length=5567
skip = 60
i=0
arrslope = []
arrtime = []

data=pd.read_csv(r"C:\Users\Saumya Nagia\PycharmProjects\NGAFID_Regression\c172_flight_16858.csv",error_bad_lines=False)


def getSlope(i,offset):


    data1 = data['Time'][i: offset]
    X = data1.to_numpy()
    print("time is from" ,i ,"till", offset)
    print(X)

    data2 = data['AltAGL'][i: offset]
    Y = data2.to_numpy()

    print(Y)
    mean_x = np.mean(X)
    mean_y = np.mean(Y)

    numer = 0
    denom = 0

    print("altitude is from", i, "till", offset, "at altitude",Y[0])
    slope = 0

    for i in range(skip):
        numer += (X[i] - mean_x) * (Y[i] - mean_y)
        denom += (X[i] - mean_x) ** 2
        slope = numer / denom



    return slope


while(i < length - skip):





      b0 = 0
      max_x = 0
      min_x = 0

      slope = getSlope(i, i + skip)
      print("slope at ", slope, "time ",i, "is")

      arrslope.append(slope)
      arrtime.append(i)

      i = i + 1;
result1=len(arrslope)
print("result 1 is ", result1)
result2=len(arrtime)

plt.scatter(arrtime,arrslope,c='#ef5423',label='Scatter Plot')
plt.xlabel('time')
plt.ylabel('Slope')
plt.legend()
plt.show()


#for i in range(result1):
#print(i)











"""
     
plt.scatter(arrtime, arrslope, c='#ef5423', label='Scatter Plot')
plt.xlabel('time')
plt.ylabel('slope')
plt.legend()
plt.show()

"""